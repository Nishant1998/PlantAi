from model.make_model import make_model
import torch
from torchvision import transforms
import os
from PIL import Image


if __name__ == '__main__':
    class cfg:
        class MODEL:
            MODEL_TYPE = "resnet"
            MODEL_NAME = "resnet18"


    transform = transforms.Compose([
        transforms.Resize(224),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406],
                             std=[0.229, 0.224, 0.225])
    ])


    num_classes = 39

    plant_name = ['Apple', 'Apple', 'Apple', 'Apple', 'Background', 'Blueberry', 'Cherry', 'Cherry', 'Corn', 'Corn',
                  'Corn', 'Corn',
                  'Grape', 'Grape', 'Grape', 'Grape', 'Orange', 'Peach', 'Peach', 'Pepper', 'Pepper', 'Potato',
                  'Potato', 'Potato',
                  'Raspberry', 'Soybean', 'Squash', 'Strawberry', 'Strawberry', 'Tomato', 'Tomato', 'Tomato', 'Tomato',
                  'Tomato',
                  'Tomato', 'Tomato', 'Tomato', 'Tomato', 'Tomato']

    disease_name = ['Apple_scab', 'Black_rot', 'Cedar_apple_rust', 'healthy', 'Background', 'healthy', 'Powdery_mildew',
                    'healthy',
                    'Cercospora_leaf_spot Gray_leaf_spot', 'Common_rust', 'Northern_Leaf_Blight', 'healthy',
                    'Black_rot',
                    'Esca_(Black_Measles)', 'Leaf_blight_(Isariopsis_Leaf_Spot)', 'healthy',
                    'Haunglongbing_(Citrus_greening)',
                    'Bacterial_spot', 'healthy', 'Bacterial_spot', 'healthy', 'Early_blight', 'Late_blight', 'healthy',
                    'healthy',
                    'healthy', 'Powdery_mildew', 'Leaf_scorch', 'healthy', 'Bacterial_spot', 'Early_blight',
                    'Late_blight',
                    'Leaf_Mold', 'Septoria_leaf_spot', 'Spider_mites Two-spotted_spider_mite', 'Target_Spot',
                    'Tomato_Yellow_Leaf_Curl_Virus', 'Tomato_mosaic_virus', 'healthy']

    # Model
    model = make_model(cfg, num_classes)
    model.load_state_dict(torch.load("F:\\nis\Codes\ANDROID PROJECTs\PlantAi\model\log\plant_ai_resnet18_96\latest_model.pth"))
    image_names = ["apple_black_rot.jpeg", "apple_health.jpeg", "apple_scrab.jpeg", "corn_Nleaf_blight.jpeg",
                   "potato_early_blight.jpeg", "potato_health.jpeg", "tomato_spider.jpeg"]
    image_path = "data/example/"

    for i in image_names:
        # image = Image.open(image_path + image_names[0]).convert('RGB')
        print(i)
        image = Image.open(image_path + i).convert('RGB')
        image = transform(image)
        image = image.unsqueeze(0)

        with torch.no_grad():
            output, obj_type = model(image)
            topk_values, topk_indices = torch.topk(output, k=3, dim=1)

            print(topk_indices)
            # print([int(i.item()*100) for i in topk_values])
            print(topk_values)

            mask = topk_values > 0.75
            topk_values = topk_values[mask]
            topk_indices = topk_indices[mask]

            if len(topk_values) != 0:
                print(f"Plant is {plant_name[topk_indices[0]]} with Disease {disease_name[topk_indices[0]]}")
            else:
                print("I am not confident enough.")
            print()

    print("*")
