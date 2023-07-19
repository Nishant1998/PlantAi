import argparse
import os
from pathlib import Path

import torch
import torch.onnx
import onnx
import tensorflow as tf
from onnx_tf.backend import prepare

from model.model.make_model import make_model
from model.config import cfg


def export_pytorch_to_onnx(model: torch.nn.Module, onnx_path: str) -> None:
    """Export a PyTorch model to ONNX with dynamic batch size.

    Args:
        model: Loaded PyTorch model in evaluation mode.
        onnx_path: Output path for the ONNX file.
    """
    dummy_input = torch.randn(1, 3, 224, 224)
    torch.onnx.export(
        model,
        dummy_input,
        onnx_path,
        input_names=["input"],
        output_names=["output"],
        dynamic_axes={"input": {0: "batch_size"}, "output": {0: "batch_size"}},
        opset_version=11,
    )


def onnx_to_tensorflow(onnx_path: str, saved_model_dir: str) -> None:
    """Convert an ONNX model to TensorFlow SavedModel.

    Args:
        onnx_path: Path to the ONNX model.
        saved_model_dir: Directory where the TensorFlow model will be saved.
    """
    onnx_model = onnx.load(onnx_path)
    tf_rep = prepare(onnx_model, device="CPU")
    tf_rep.export_graph(saved_model_dir)


class NHWCWrapper(tf.Module):
    """TensorFlow wrapper that converts NHWC input to NCHW and applies softmax.

    TensorFlow Lite prefers NHWC input【773839884652591†L74-L78】.  The underlying
    model (converted from PyTorch via ONNX) expects NCHW input, so we
    transpose the incoming tensor before calling the model and apply
    `tf.nn.softmax` on the outputs.  Returning a dictionary with a
    deterministic key (`"output"`) ensures the converter preserves the name.
    """

    def __init__(self, tf_model_path: str):
        super().__init__()
        self.model = tf.saved_model.load(tf_model_path)

    @tf.function(input_signature=[tf.TensorSpec([None, 224, 224, 3], tf.float32)])
    def __call__(self, input_tensor: tf.Tensor) -> dict:
        nchw = tf.transpose(input_tensor, perm=[0, 3, 1, 2])
        logits = self.model(input=nchw)["output"]
        probs = tf.nn.softmax(tf.cast(logits, tf.float32))
        return {"output": probs}


def convert_to_tflite(saved_model_dir: str, tflite_path: str) -> None:
    """Convert a TensorFlow SavedModel to TFLite and save it to disk.

    Args:
        saved_model_dir: Directory containing the wrapped SavedModel (NHWC input).
        tflite_path: Output path for the `.tflite` file.
    """
    converter = tf.lite.TFLiteConverter.from_saved_model(saved_model_dir)
    converter.optimizations = [tf.lite.Optimize.DEFAULT]
    converter.target_spec.supported_ops = [
        tf.lite.OpsSet.TFLITE_BUILTINS,
        tf.lite.OpsSet.SELECT_TF_OPS,
    ]
    converter._experimental_lower_tensor_list_ops = False  # type: ignore[attr-defined]

    tflite_model = converter.convert()
    with open(tflite_path, "wb") as f:
        f.write(tflite_model)


def main():
    parser = argparse.ArgumentParser(description="Convert PyTorch model to TFLite for Android")
    parser.add_argument(
        "--config_file",
        default=r"D:\Code\android\PlantAi\model\config\plantai_resnet_18.yaml",
        help="Path to the configuration YAML used by PlantAI",
    )
    parser.add_argument(
        "opts",
        help="Additional options that override the config file",
        nargs=argparse.REMAINDER,
        default=None,
    )
    parser.add_argument(
        "--output", default="plantAi_model.tflite", help="Name of the output TFLite file"
    )
    args = parser.parse_args()

    cwd = Path.cwd()
    project_root = cwd.parent if (cwd / "model").exists() else cwd
    os.chdir(project_root)

    # Load configuration
    if args.config_file:
        cfg.merge_from_file(args.config_file)
    if args.opts:
        cfg.merge_from_list(args.opts)
    cfg.freeze()

    # Construct model and load weights
    model_path = cfg.MODEL.WEIGHTS
    num_classes = getattr(cfg.MODEL, "NUM_CLASSES", 39)
    model = make_model(cfg, num_classes=num_classes)
    model.load_state_dict(torch.load(model_path, map_location="cpu"))
    model.eval()

    model = model.to(memory_format=torch.channels_last)
    torch_tensor = torch.rand(1, 3, 224, 224)
    print(model(torch_tensor)[0].shape)

    # Step 1: Export to ONNX
    onnx_path = "temp_model.onnx"
    export_pytorch_to_onnx(model, onnx_path)
    print(f"✅ Exported PyTorch model to ONNX: {onnx_path}")

    # Step 2: Convert ONNX → TensorFlow
    tf_model_dir = "temp_saved_model"
    onnx_to_tensorflow(onnx_path, tf_model_dir)
    print(f"✅ Converted ONNX to TensorFlow SavedModel: {tf_model_dir}")

    # Step 3: Wrap with NHWC input and softmax
    wrapped_dir = "temp_saved_model_nhwc"
    wrapper = NHWCWrapper(tf_model_dir)
    # Save with an explicit signature for TFLite converter
    tf.saved_model.save(
        wrapper,
        wrapped_dir,
        signatures={"serving_default": wrapper.__call__.get_concrete_function()},
    )
    print(f"✅ Created NHWC wrapper SavedModel: {wrapped_dir}")

    # Step 4: Convert to TFLite
    convert_to_tflite(wrapped_dir, args.output)
    print(f"✅ Successfully converted to TFLite: {args.output}")

    # Clean up temporary artifacts
    for path in [onnx_path, tf_model_dir, wrapped_dir]:
        try:
            if os.path.isfile(path):
                os.remove(path)
            else:
                import shutil

                shutil.rmtree(path)
        except Exception:
            pass


if __name__ == "__main__":
    main()
    import numpy as np
    import tensorflow as tf
    import torch

    # Load the TFLite model
    interpreter = tf.lite.Interpreter(model_path=r"D:\Code\android\PlantAi\model\export\model.tflite")
    interpreter.allocate_tensors()

    # Get input details
    input_details = interpreter.get_input_details()
    output_details = interpreter.get_output_details()

    # Prepare dummy input (adjust shape as needed, e.g., NHWC [1, 224, 224, 3])
    input_shape = input_details[0]['shape']
    input_data = np.random.rand(*input_shape).astype(np.float32)
    print(input_data.shape)
    # Run inference
    interpreter.set_tensor(input_details[0]['index'], input_data)
    interpreter.invoke()
    output_data = interpreter.get_tensor(output_details[0]['index'])

    # Convert output to PyTorch tensor
    output_tensor = torch.tensor(output_data)
    print("PyTorch Tensor Output:", output_tensor.shape)
