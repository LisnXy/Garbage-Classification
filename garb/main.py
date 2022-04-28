from fastapi import FastAPI, UploadFile, File
import io
import os
import json
import torch
import uuid
from Conv.conv_classifier import ConvClassifier
from PIL import Image
from torchvision import transforms
from Conv.model import convnext_tiny as create_model
from Yolo5.models.common import DetectMultiBackend
from Yolo5.detect import run

# 全局 device 声明
device = torch.device('cpu')
# 配置 ConvNeXt 参数
num_classes = 144
img_size = 224
data_transform = transforms.Compose(
    [transforms.Resize(int(img_size * 1.14)),
     transforms.CenterCrop(img_size),
     transforms.ToTensor(),
     transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])])
json_path = 'Conv/class_indices.json'
assert os.path.exists(json_path), "file: '{}' dose not exist.".format(json_path)
json_file = open(json_path, "r")
class_indict = json.load(json_file)
# 配置 Yolo5 参数
conf_thres = 0.25
iou_thres = 0.45
classes = None
agnostic_nms = False
augment = False
max_det = 1000
save_file_dir = 'Yolo5/data/Files'
# 创建分类器
conv_classifier = ConvClassifier()


# 初始化 ConvNeXt 模型
def init_conv_model():
    # create model
    weights_dict = torch.load("Conv/weights/best_model1.pth", map_location=device)["model"]
    model = create_model(num_classes=num_classes).to(device)
    # load model weights
    model_weight_path = "Conv/weights/best_model1.pth"
    model.load_state_dict(weights_dict, strict=False)
    model.eval()
    print("conv loaded successfully")
    return model


# 初始化 Yolov5 模型
def init_yolo_model():
    # load model
    model = DetectMultiBackend(r'Yolo5/weights/best.pt', device=device, dnn=False, data=r'Yolo5/data/data.yaml',
                               fp16=False)
    print('yolo loaded successfully')
    return model


# 图片文件读取，输出Image.Image格式
def read_imagefile(file) -> Image.Image:
    image = Image.open(io.BytesIO(file))
    return image


model_conv = init_conv_model()
model_yolo = init_yolo_model()
app = FastAPI()


# 图像分类接口
@app.post('/predictConv')
async def predict_conv(file: UploadFile = File(...)):
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
        type = conv_classifier.classify(class_res)
        class_res = conv_classifier.name_process(class_res)
        prob = predict[predict_cla].numpy()
    return {"garbageName": f"{class_res}", "probability": f"{format(prob, '.3f')}", "garbageType": f"{type}"}


# 目标检测接口
@app.post('/predictYolo')
async def predict_yolo(file: UploadFile = File(...)):
    # 验证图像格式
    extension = file.filename.split(".")[-1]
    is_image = extension in ("jpg", "jpeg", "png")
    if not is_image:
        return "Image must be jpg or png format!"
    contents = await file.read()
    file_name = uuid.uuid1()
    save_file_name = fr'{save_file_dir}/{file_name}.{extension}'
    with open(save_file_name, 'wb') as f:
        f.write(contents)
    classes, confs = run(model=model_yolo, source=save_file_name, device=device)
    types = []
    for item in classes:
        types.append(conv_classifier.classify(item))
    result = []
    for x in range(len(classes)):
        result.append({
            "garbageName": classes[x],
            "probability": confs[x],
            "garbageType": types[x],
        })
    return {
        "result": result,
        "url": f"{file_name}.{extension}"
    }


@app.get('/status')
async def hello():
    return 'Service running...'
