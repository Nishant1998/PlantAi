import torch
from torch.utils.data import DataLoader
from torchvision import transforms, datasets


def make_dataloader(cfg):
    data_transforms = transforms.Compose([
        transforms.Resize(cfg.DATALOADER.IMAGE_SIZE),
        transforms.RandomHorizontalFlip(),
        transforms.RandomVerticalFlip(),
        transforms.ToTensor(),
        transforms.Normalize(cfg.DATASET.MEAN, cfg.DATASET.STD)
    ])

    # Load the Plant Village dataset
    plant_village_dataset = datasets.ImageFolder(root=f"{cfg.DATASET.PATH}/{cfg.DATASET.NAME}", transform=data_transforms)
    num_classes = len(plant_village_dataset.classes)

    # Split the dataset into train and test sets
    train_size = int(0.8 * len(plant_village_dataset))
    test_size = len(plant_village_dataset) - train_size
    train_dataset, test_dataset = torch.utils.data.random_split(plant_village_dataset, [train_size, test_size])

    # Define the data loaders
    train_loader = DataLoader(train_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=True, num_workers=4)
    test_loader = DataLoader(test_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=False, num_workers=4)

    return train_loader, test_loader, train_dataset, test_dataset, num_classes