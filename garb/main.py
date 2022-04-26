from fastapi import FastAPI, UploadFile, File
import io
import os
import json
import torch
from PIL import Image
from torchvision import transforms
import matplotlib.pyplot as plt
from model import convnext_tiny as create_model

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
print(f"using {device} device.")
num_classes = 144
img_size = 224

data_transform = transforms.Compose(
    [transforms.Resize(int(img_size * 1.14)),
     transforms.CenterCrop(img_size),
     transforms.ToTensor(),
     transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])])

json_path = './class_indices.json'
assert os.path.exists(json_path), "file: '{}' dose not exist.".format(json_path)

json_file = open(json_path, "r")
class_indict = json.load(json_file)


def init_conv_model():
    # create model
    weights_dict = torch.load("./weights/best_model1.pth", map_location=device)["model"]
    model = create_model(num_classes=num_classes).to(device)
    # load model weights
    model_weight_path = "./weights/best_model1.pth"
    model.load_state_dict(weights_dict, strict=False)
    model.eval()
    print("conv loaded successfully")
    return model


# 图片文件读取，输出Image.Image格式
def read_imagefile(file) -> Image.Image:
    image = Image.open(io.BytesIO(file))
    return image


model_conv = init_conv_model()
app = FastAPI()


@app.post('/predictConv')
async def predict(file: UploadFile = File(...)):
    extension = file.filename.split(".")[-1] in ("jpg", "jpeg", "png")
    if not extension:
        return "Image must be jpg or png format!"
    img = read_imagefile(await file.read())
    img = img.convert('RGB')
    img = data_transform(img)
    img = torch.unsqueeze(img, dim=0)
    with torch.no_grad():
        # predict class
        output = torch.squeeze(model_conv(img.to(device))).cpu()
        predict = torch.softmax(output, dim=0)
        predict_cla = torch.argmax(predict).numpy()
        class_res = class_indict[str(predict_cla)]
        prob = predict[predict_cla].numpy()
    print_res = "class: {}   prob: {:.3}".format(class_indict[str(predict_cla)],
                                                 predict[predict_cla].numpy())
    return {"class": f"{class_res}", "prob": f"{format(prob,'.3f')}"}
