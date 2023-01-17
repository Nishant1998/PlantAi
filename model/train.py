import argparse
import os
import random
import numpy as np
import torch
from torch import optim, nn
from torch.optim.lr_scheduler import StepLR
from utils.logger import setup_logger
from dataset.dataloader import make_dataloader
from model.make_model import make_model
from config import cfg
from sklearn.model_selection import KFold
from torch.utils.data import DataLoader
from utils.meter import AverageMeter, MaxMeter, Meter, Counter
from utils.plot import plot_acc_loss

def set_seed(seed):
    torch.manual_seed(seed)
    torch.cuda.manual_seed(seed)
    torch.cuda.manual_seed_all(seed)
    np.random.seed(seed)
    random.seed(seed)
    torch.backends.cudnn.deterministic = True
    torch.backends.cudnn.benchmark = False
    torch.cuda.empty_cache()


def train_model(cfg, model, criterion, optimizer, dataset):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model = model.to(device)
    best_model = model
    kf = KFold(n_splits=cfg.DATALOADER.K_FOLD)
    # Iterate over the folds
    for fold, (train_idx, val_idx) in enumerate(kf.split(dataset)):
        logger.info(f"Fold : {fold}")
        # Create the training and validation datasets
        train_dataset = torch.utils.data.Subset(dataset, train_idx)
        val_dataset = torch.utils.data.Subset(dataset, val_idx)

        # Create the data loaders
        train_loader = DataLoader(train_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=True)
        val_loader = DataLoader(val_dataset, batch_size=cfg.DATALOADER.BATCH_SIZE, shuffle=False)

        record_train_acc = Meter()
        record_train_loss = Meter()
        record_val_acc = Meter()
        record_val_loss = Meter()
        max_val_acc_meter = MaxMeter()
        early_stopping_counter = Counter()

        logger.info("Starting Training.")
        for epoch in range(cfg.SOLVER.MAX_EPOCHS):
            model.train()
            acc_meter = AverageMeter()
            loss_meter = AverageMeter()

            for i, (inputs, labels) in enumerate(train_loader):
                # Move the input and label tensors to the GPU
                inputs = inputs.to(device)
                labels = labels.to(device)

                # Forward pass
                outputs, obj_class = model(inputs)

                loss = criterion(outputs, labels)
                loss_meter.update(loss.item())

                _, preds = torch.max(outputs, 1)
                acc_meter.update((preds == labels).sum().item(), len(labels))

                # Backward pass and optimization
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

                if (i+1) % cfg.SOLVER.LOG_PERIOD == 0:
                    logger.info(f"Train :: Epoch : {epoch+1}/{cfg.SOLVER.MAX_EPOCHS}, Iter : {i+1}/{len(train_loader)}, "
                                f"loss : {loss_meter.val:.4f}")

            record_train_acc.update(acc_meter.val)
            record_train_loss.update(loss_meter.val)
            acc_meter.reset()
            loss_meter.reset()

            # Evaluation on validation set
            with torch.no_grad():
                val_acc_meter = AverageMeter()
                val_loss_meter = AverageMeter()
                model.eval()
                for i, (inputs, labels) in enumerate(val_loader):
                    inputs = inputs.to(device)
                    labels = labels.to(device)

                    outputs, obj_class = model(inputs)

                    _, preds = torch.max(outputs, 1)
                    val_acc_meter.update((preds == labels).sum().item(), len(labels))

                    loss = criterion(outputs, labels)
                    val_loss_meter.update(loss.item())

                    if (i + 1) % cfg.SOLVER.LOG_PERIOD == 0:
                        logger.info(f"Validation :: Epoch : {epoch+1}/{cfg.SOLVER.MAX_EPOCHS}, Iter : {i + 1}/{len(val_loader)}, "
                                    f"loss : {val_loss_meter.val:.4f}, acc : {val_acc_meter.val:.4f}")

                # save model
                if val_acc_meter.val > max_val_acc_meter.val:
                    best_model = model
                    early_stopping_counter.reset()
                elif abs(val_acc_meter.val - max_val_acc_meter.val) >= cfg.SOLVER.TOLERANCE:
                    early_stopping_counter.increment()
                torch.save(model.state_dict(), cfg.OUTPUT_DIR + '/latest_model.pth')

                record_val_acc.update(val_acc_meter.val)
                record_val_loss.update(val_loss_meter.val)
                max_val_acc_meter.update(val_acc_meter.val)

                val_acc_meter.reset()
                val_loss_meter.reset()

                if early_stopping_counter.count > cfg.SOLVER.PATIENCE:
                    logger.info(f"Initiating early stopping, "
                                f"validation accuracy is not increased since {early_stopping_counter.count} epochs.")
                    break

        # plot graph
        torch.save(best_model.state_dict(), cfg.OUTPUT_DIR + '/best_model.pth')
        plot_acc_loss(epoch+1, "Training Accuracy and Loss per Epoch",
                      record_train_acc.values, record_train_loss.values, path=f"{cfg.OUTPUT_DIR}/{fold}_")
        plot_acc_loss(epoch+1, "Validation Accuracy and Loss per Epoch",
                      record_val_acc.values, record_val_loss.values, path=f"{cfg.OUTPUT_DIR}/{fold}_")


def test_model(model, test_loader):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model = model.to(device)
    model.eval()
    logger.info("Testing model")
    acc_meter = AverageMeter()
    for i, (inputs, labels) in enumerate(test_loader):
        inputs = inputs.to(device)
        labels = labels.to(device)

        outputs, obj_class = model(inputs)

        _, preds = torch.max(outputs, 1)
        acc_meter.update((preds == labels).sum().item(), len(labels))

    logger.info(f"Testing accuracy : {acc_meter.val}")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="ReID Baseline Training")
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
    set_seed(cfg.SOLVER.SEED)

    output_dir = cfg.OUTPUT_DIR
    try:
        os.makedirs(output_dir)
    except:
        pass

    logger = setup_logger("transreid", output_dir, if_train=True)
    logger.info("Saving model in the path :{}".format(cfg.OUTPUT_DIR))

    if args.config_file != "":
        logger.info("Loaded configuration file {}".format(args.config_file))
        with open(args.config_file, 'r') as cf:
            config_str = "\n" + cf.read()

    logger.info("Config")
    logger.info(cfg)

    # Dataloader
    train_loader, test_loader, train_dataset, test_dataset, num_classes = make_dataloader(cfg)
    # Model
    model = make_model(cfg, num_classes)

    # Define the loss function and optimizer
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.001)

    train_model(cfg, model, criterion, optimizer, train_dataset)

    test_model(model, test_loader)
