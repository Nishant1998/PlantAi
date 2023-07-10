import argparse
import os
import torch.onnx
import onnx
import tensorflow as tf
from onnx_tf.backend import prepare
from model.model.make_model import make_model
from model.config import cfg

# Move to project root
current_dir = os.getcwd()
parent_dir = os.path.abspath(os.path.join(current_dir, os.pardir))
os.chdir(parent_dir)
print("Working directory:", os.getcwd())

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="PyTorch → TFLite conversion")
    parser.add_argument("--config_file", default="config/plantai_resnet_18.yaml", help="Path to config file", type=str)
    parser.add_argument("opts", help="Modify config options via command-line", default=None, nargs=argparse.REMAINDER)
    parser.add_argument("--local_rank", default=0, type=int)
    args = parser.parse_args()

    if args.config_file != "":
        cfg.merge_from_file(args.config_file)
    cfg.merge_from_list(args.opts)
    cfg.freeze()

    # Load PyTorch model
    model_path = cfg.MODEL.WEIGHTS
    num_classes = cfg.MODEL.NUM_CLASSES if hasattr(cfg.MODEL, "NUM_CLASSES") else 39
    model = make_model(cfg, num_classes)
    model.load_state_dict(torch.load(model_path, map_location="cpu"))
    model.eval()

    # Step 1: Export to ONNX
    dummy_input = torch.randn(1, 3, 256, 256)
    torch.onnx.export(
        model,
        dummy_input,
        "model.onnx",
        input_names=["input"],
        output_names=["output"],
        dynamic_axes={"input": {0: "batch_size"}, "output": {0: "batch_size"}},
        opset_version=11
    )
    print("✅ Exported PyTorch model to ONNX")

    # Step 2: ONNX → TensorFlow
    onnx_model = onnx.load("model.onnx")
    tf_rep = prepare(onnx_model)
    tf_rep.export_graph("saved_model_tf")
    print("✅ Converted ONNX to TensorFlow SavedModel")

    # Step 3: TensorFlow → TFLite
    converter = tf.lite.TFLiteConverter.from_saved_model("saved_model_tf")
    converter.optimizations = [tf.lite.Optimize.DEFAULT]
    tflite_model = converter.convert()

    with open("model.tflite", "wb") as f:
        f.write(tflite_model)

    print("✅ Successfully converted TensorFlow model to TFLite: model.tflite")
