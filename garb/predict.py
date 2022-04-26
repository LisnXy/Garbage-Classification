import os
import json
import torch
from PIL import Image
from torchvision import transforms
import matplotlib.pyplot as plt

from Conv.model import convnext_tiny as create_model


def main():
    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    print(f"using {device} device.")

    num_classes = 144
    img_size = 224
    data_transform = transforms.Compose(
        [transforms.Resize(int(img_size * 1.14)),
         transforms.CenterCrop(img_size),
         transforms.ToTensor(),
         transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])])
    # read class_indict
    json_path = 'Conv/class_indices.json'
    assert os.path.exists(json_path), "file: '{}' dose not exist.".format(json_path)

    json_file = open(json_path, "r")
    class_indict = json.load(json_file)

    # create model
    weights_dict = torch.load("./weights/best_model1.pth", map_location=device)["model"]
    model = create_model(num_classes=num_classes).to(device)
    # load model weights
    model_weight_path = "Conv/weights/best_model1.pth"
    model.load_state_dict(weights_dict, strict=False)
    model.eval()

    # load image
    dirpath = r"D:\projects\dataset2\垃圾分类目录\垃圾目录\可回收垃圾\玻璃瓶罐"
    imgfiles = os.listdir(dirpath)
    assert len(imgfiles) != 0, "{} have no files".format(dirpath)
    imgfilepaths = [os.path.join(dirpath, imgfile) for imgfile in imgfiles]
    count = 0

    for img_path in imgfilepaths:
        assert os.path.exists(img_path), "file: '{}' dose not exist.".format(img_path)
        img = Image.open(img_path)
        img = img.convert('RGB')
        plt.imshow(img)
        # [N, C, H, W]
        img = data_transform(img)
        # expand batch dimension
        img = torch.unsqueeze(img, dim=0)

        with torch.no_grad():
            # predict class
            output = torch.squeeze(model(img.to(device))).cpu()
            predict = torch.softmax(output, dim=0)
            predict_cla = torch.argmax(predict).numpy()

        print_res = "class: {}   prob: {:.3}".format(class_indict[str(predict_cla)],
                                                     predict[predict_cla].numpy())
        # plt.title(print_res)
        print(print_res)
        if(class_indict[str(predict_cla)]=="玻璃瓶罐"):
            count += 1
    print(count/len(imgfilepaths))


if __name__ == '__main__':
    main()
