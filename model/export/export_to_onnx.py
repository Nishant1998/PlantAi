import argparse

import torch
import torch.onnx
import onnx
from model.dataset.dataloader import make_dataloader
from model.model.make_model import make_model
from model.config import cfg
import onnxruntime as ort
import numpy as np
import os
import onnxruntime

current_dir = os.getcwd()
parent_dir = os.path.abspath(os.path.join(current_dir, os.pardir))
os.chdir(parent_dir)
print(os.getcwd())

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="")
    parser.add_argument(
        "--config_file", default="", help="path to config file", type=str
    )

    parser.add_argument("opts", help="Modify config options using the command-line", default=None,
                        nargs=argparse.REMAINDER)
    parser.add_argument("--local_rank", default=0, type=int)
    args = parser.parse_args()

    if args.config_file != "":
        cfg.merge_from_file(args.config_file)
    cfg.merge_from_list(args.opts)

    cfg.freeze()

    PATH = "F:\\nis\Codes\ANDROID PROJECTs\PlantAi\model\log\debug\\best_model.pth"
    # _, _, _, _, num_classes = make_dataloader(cfg)
    num_classes = 6
    model = make_model(cfg, num_classes)
    model.load_state_dict(torch.load(PATH))
    # model = model.float()

    torch.onnx.export(model, torch.zeros((1, 3, 256, 256)), "model.onnx")
    # opset_version=12

    # ONNX
    # Load the ONNX model
    session = onnxruntime.InferenceSession("model.onnx")

    # Run the model and get the output
    input_name = session.get_inputs()[0].name
    output_name = session.get_outputs()[0].name
    x = np.zeros((1, 3, 256, 256))
    x = np.array(x, dtype=np.float32)
    output_data = session.run([output_name], {input_name: x})[0]
