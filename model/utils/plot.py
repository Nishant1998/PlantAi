import matplotlib.pyplot as plt


def plot_acc_loss(epochs, title, accuracies, losses, path):
    plt.plot(range(1, epochs + 1), accuracies, label='accuracy')
    plt.plot(range(1, epochs + 1), losses, label='loss')
    plt.legend()
    plt.xlabel('Epoch')
    plt.ylabel('Value')
    plt.title(title)
    plt.savefig(f'{path}_{title.replace(" ", "_")}.png')
    plt.show()
