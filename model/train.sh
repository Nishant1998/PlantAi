#python model/train.py --config_file "model/config/plantai_resnet_18.yaml"
#python model/train.py --config_file "model/config/plantai_resnet_34.yaml"
#python model/train.py --config_file "model/config/plantai_resnet_50.yaml"

python model/export/export_to_torchscript.py --config_file "model/config/plantai_resnet_18.yaml"