import matplotlib.pyplot as plt


def plot_acc_loss(epochs, title, data, path):
    plt.plot(range(1, epochs + 1), data, label='accuracy')
    plt.legend()
    plt.xlabel('Epoch')
    plt.ylabel('Value')
    plt.title(title)
    plt.savefig(f'{path}_{title.replace(" ", "_")}.png')
    plt.show()
