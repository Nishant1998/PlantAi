import torch
import torch.nn as nn
from efficientnet_pytorch import EfficientNet


class ImageNet(nn.Module):
    def __init__(self):
        super().__init__()
        self.imageNetModel = EfficientNet.from_name('efficientnet-b0')

    def forward(self, x):
        y = self.imageNetModel(x)
        index = torch.argmax(y)
        return index


class PlantAi(nn.Module):
    def __init__(self, cfg, num_classes):
        super(PlantAi, self).__init__()
        self.imageNetModel = ImageNet()
        self.base = EfficientNet.from_name(cfg.MODEL.MODEL_NAME)
        self.base.fc = nn.Linear(self.base._fc.in_features, num_classes)

    def forward(self, x):
        obj_class = self.imageNetModel(x)
        x = self.base(x)
        return x, obj_class


def make_model(cfg, num_classes):
    model = PlantAi(cfg, num_classes)
    return model
