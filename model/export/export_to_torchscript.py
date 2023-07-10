import os
import argparse
import torch
from model.config import cfg
from model.model.make_model import make_model
from model.dataset.dataloader import make_dataloader
from torch.utils.mobile_optimizer import optimize_for_mobile

# Change to project root
current_dir = os.getcwd()
parent_dir = os.path.abspath(os.path.join(current_dir, os.pardir))
os.chdir(parent_dir)
print("Working directory:", os.getcwd())

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="PyTorch → TorchScript (.ptl) conversion")
    parser.add_argument("--config_file", default="config/plantai_resnet_18.yaml", help="Path to config file", type=str)
    parser.add_argument("opts", help="Modify config options via command-line", default=None, nargs=argparse.REMAINDER)
    parser.add_argument("--local_rank", default=0, type=int)
    args = parser.parse_args()

    # Load config
    if args.config_file != "":
        cfg.merge_from_file(args.config_file)
    cfg.merge_from_list(args.opts)
    cfg.freeze()
    print("✅ Config loaded")

    # Load model
    model_path = cfg.MODEL.WEIGHTS
    num_classes = cfg.MODEL.NUM_CLASSES if hasattr(cfg.MODEL, "NUM_CLASSES") else 39
    model = make_model(cfg, num_classes)
    model.load_state_dict(torch.load(model_path, map_location="cpu"), strict=False)
    model.eval()

    # Trace and export TorchScript
    example = torch.rand(1, 3, 256, 256)
    traced_script_module = torch.jit.trace(model, example)
    traced_script_module_optimized = optimize_for_mobile(traced_script_module)

    os.makedirs("export", exist_ok=True)
    traced_script_module_optimized._save_for_lite_interpreter("export/model.ptl")
    print("✅ TorchScript model saved to: export/model.ptl")
