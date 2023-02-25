import torch
import torch.nn as nn
from efficientnet_pytorch import EfficientNet
import torchvision.models as models


def make_resnet_model(name):
    print(name)
    assert name in ["resnet18", "resnet34", "resnet50"]
    if name == "resnet18":
        model = models.resnet18(pretrained=True)
    elif name == "resnet34":
        model = models.resnet34(pretrained=True)
    elif name == "resnet50":
        model = models.resnet50(pretrained=True)
    else:
        model = None
    return model


class ImageNet(nn.Module):
    def __init__(self):
        super().__init__()
        self.imageNetModel = make_resnet_model("resnet18")
        self.softmax = nn.Softmax(dim=1)

    def forward(self, x):
        y = self.imageNetModel(x)
        y = self.softmax(y)
        prob, index = torch.max(y, 1)
        return index


class PlantAi(nn.Module):
    def __init__(self, cfg, num_classes):
        super(PlantAi, self).__init__()
        self.imageNetModel = ImageNet()
        if cfg.MODEL.MODEL_TYPE == "efficientnet":
            self.base = EfficientNet.from_name(cfg.MODEL.MODEL_NAME)
            self.base.fc = nn.Linear(self.base._fc.in_features, num_classes)
        elif cfg.MODEL.MODEL_TYPE == "resnet":
            self.base = make_resnet_model(cfg.MODEL.MODEL_NAME)
            self.base.fc = nn.Linear(self.base.fc.in_features, num_classes)
        self.softmax = nn.Softmax(dim=1)

    def forward(self, x):
        obj_class = self.imageNetModel(x)
        x = self.base(x)
        x = self.softmax(x)
        return x, obj_class



def make_model(cfg, num_classes):
    model = PlantAi(cfg, num_classes)
    return model
