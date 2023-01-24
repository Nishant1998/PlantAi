import random

import torch
from torch.utils.data import DataLoader
from torchvision import transforms, datasets


class GaussianNoise(object):
    def __init__(self, stddev):
        self.stddev = stddev

    def __call__(self, img):
        noise = torch.randn(*img.size()) * self.stddev
        noisy_img = img + noise
        return noisy_img


def make_dataloader(cfg):
    data_transforms = transforms.Compose([
        transforms.Resize(cfg.DATALOADER.IMAGE_SIZE),
        transforms.RandomCrop(size=256, padding=16),
        transforms.ColorJitter(brightness=0.5, contrast=0.5, saturation=0.5, hue=0.5),
        transforms.RandomRotation(degrees=30),
        transforms.RandomHorizontalFlip(),
        transforms.RandomVerticalFlip(),
        transforms.ToTensor(),
        GaussianNoise(stddev=random.uniform(0, 0.1)),
        transforms.Normalize(cfg.DATASET.MEAN, cfg.DATASET.STD)
    ])

    # Load the Plant Village dataset
    plant_village_dataset = datasets.ImageFolder(root=f"{cfg.DATASET.PATH}/{cfg.DATASET.NAME}", transform=transforms.ToTensor())
    num_classes = len(plant_village_dataset.classes)

    # Split the dataset into train and test sets
    train_size = int(0.8 * len(plant_village_dataset))
    test_size = len(plant_village_dataset) - train_size
    train_dataset, test_dataset = torch.utils.data.random_split(plant_village_dataset, [train_size, test_size])

    # Define the data loaders
    train_loader = DataLoader(train_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=True, num_workers=2)
    test_loader = DataLoader(test_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=False, num_workers=2)

    # Apply the data transformation to the training set only
    train_dataset.dataset.transform = data_transforms

    return train_loader, test_loader, train_dataset, test_dataset, num_classes
