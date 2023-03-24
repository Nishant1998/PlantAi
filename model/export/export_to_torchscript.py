import os
import argparse
import torch.onnx
from model.config import cfg
from model.model.make_model import make_model
from model.dataset.dataloader import make_dataloader
from torch.utils.mobile_optimizer import optimize_for_mobile


current_dir = os.getcwd()
parent_dir = os.path.abspath(os.path.join(current_dir, os.pardir))
os.chdir(parent_dir)
print(os.getcwd())

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="")
    parser.add_argument(
        "--config_file", default=r"config\plantai_resnet_18.yaml", help="path to config file", type=str
    )

    parser.add_argument("opts", help="Modify config options using the command-line", default=None,
                        nargs=argparse.REMAINDER)
    parser.add_argument("--local_rank", default=0, type=int)
    args = parser.parse_args()

    if args.config_file != "":
        cfg.merge_from_file(args.config_file)
    cfg.merge_from_list(args.opts)

    cfg.freeze()
    print(cfg)

    PATH = r"log\plant_ai_resnet18_96\\best_model.pth"
    # _, _, _, _, num_classes = make_dataloader(cfg)
    num_classes = 39
    model = make_model(cfg, num_classes)
    model.load_state_dict(torch.load(PATH), strict=False)

    example = torch.rand(1, 3, 256, 256)
    traced_script_module = torch.jit.trace(model, example)
    traced_script_module_optimized = optimize_for_mobile(traced_script_module)
    traced_script_module_optimized._save_for_lite_interpreter(r"export\model.ptl")

