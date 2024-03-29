import os
import random

import torch
from torch.utils.data import DataLoader
from torchvision import transforms, datasets
from torch.utils.data.sampler import WeightedRandomSampler
from sklearn.model_selection import StratifiedShuffleSplit


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
        transforms.RandomCrop(size=cfg.DATALOADER.IMAGE_SIZE[0], padding=16),
        transforms.RandomRotation(degrees=30),
        transforms.RandomHorizontalFlip(),
        transforms.RandomVerticalFlip(),
        transforms.ToTensor(),
        # GaussianNoise(stddev=random.uniform(0, 0.1)),
        transforms.Normalize(cfg.DATASET.MEAN, cfg.DATASET.STD)
    ])

    # Load the Plant Village dataset
    plant_village_dataset = datasets.ImageFolder(root=f"{cfg.DATASET.PATH}/{cfg.DATASET.NAME}", transform=transforms.ToTensor())
    num_classes = len(plant_village_dataset.classes)

    # Split the dataset into train and test sets
    # train_size = int(0.8 * len(plant_village_dataset))
    # test_size = len(plant_village_dataset) - train_size
    # train_dataset, test_dataset = torch.utils.data.random_split(plant_village_dataset, [train_size, test_size])
    data, labels = zip(*plant_village_dataset.imgs)
    # Split the data and labels using stratified sampling
    sss = StratifiedShuffleSplit(n_splits=1, test_size=0.2, random_state=42)
    train_idx, test_idx = next(sss.split(data, labels))
    # Create the training and testing datasets
    train_dataset = torch.utils.data.Subset(plant_village_dataset, train_idx)
    test_dataset = torch.utils.data.Subset(plant_village_dataset, test_idx)

    # calculate classes weights for imbalance correction
    class_folders = [f.path for f in os.scandir(f"{cfg.DATASET.PATH}/{cfg.DATASET.NAME}") if f.is_dir()]
    num_images_per_class = [len(os.listdir(folder)) for folder in class_folders]
    class_weights = [1.0 / count for count in num_images_per_class]
    sample_weights = [class_weights[label] for _, label in train_dataset]
    # Use weighted sampler for the train_loader
    weighted_sampler = WeightedRandomSampler(sample_weights, len(train_dataset))

    # Define the data loaders
    train_loader = DataLoader(train_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=False,
                              sampler=weighted_sampler, num_workers=2, pin_memory=True)
    test_loader = DataLoader(test_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=False,
                             num_workers=2, pin_memory=True)

    # Apply the data transformation to the training set only
    train_dataset.dataset.transform = data_transforms

    print(f"NUMBER IMG PER CLASS: {num_images_per_class}")
    return train_loader, test_loader, train_dataset, test_dataset, num_classes, torch.tensor(class_weights)
