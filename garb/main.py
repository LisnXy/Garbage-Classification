import numpy as np
from fastapi import FastAPI, UploadFile, File
import io
import os
import json
import torch
from Conv.conv_classifier import ConvClassifier
from PIL import Image
from torchvision import transforms
from Conv.model import convnext_tiny as create_model
from Yolo5.models.common import DetectMultiBackend
from Yolo5.utils.augmentations import letterbox
from Yolo5.utils.datasets import IMG_FORMATS, VID_FORMATS, LoadImages, LoadStreams
from Yolo5.utils.general import (LOGGER, check_file, check_img_size, check_imshow, check_requirements, colorstr,
                                 increment_path, non_max_suppression, print_args, scale_coords, strip_optimizer,
                                 xyxy2xywh)
from Yolo5.utils.plots import Annotator, colors, save_one_box
from Yolo5.utils.torch_utils import select_device, time_sync

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
    return {"label": f"{class_res}", "prob": f"{format(prob, '.3f')}", "type": f"{type}"}


# 目标检测接口
@app.post('/predictYolo')
async def predict_yolo(file: UploadFile = File(...)):
    # 验证图像格式
    extension = file.filename.split(".")[-1] in ("jpg", "jpeg", "png")
    if not extension:
        return "Image must be jpg or png format!"
    stride, names, pt = model_yolo.stride, model_yolo.names, model_yolo.pt
    imgsz = check_img_size((640, 640), s=stride)  # check image size
    # 读取图片
    img0 = read_imagefile(await file.read())
    img0 = img0.convert('RGB')
    # 处理图像
    img = letterbox(img0, imgsz, stride=stride, auto=True)[0]
    img = img.transpose((2, 0, 1))[::-1]  # HWC to CHW, BGR to RGB
    img = np.ascontiguousarray(img)
    # 开始推理
    model_yolo.warmup(imgsz=(1 if pt else pt, 3, *imgsz))
    dt, seen = [0.0, 0.0, 0.0], 0
    t1 = time_sync()
    im = torch.from_numpy(img).to(device)
    im = im.half() if model_yolo.fp16 else im.float()
    im /= 255  # 0 - 255 to 0.0 - 1.0
    if len(im.shape) == 3:
        im = im[None]  # expand for batch dim
    t2 = time_sync()
    dt[0] += t2 - t1
    # Inference
    visualize = False
    pred = model_yolo(im, augment=augment, visualize=visualize)
    t3 = time_sync()
    dt[1] += t3 - t2
    # NMS
    pred = non_max_suppression(pred, conf_thres, iou_thres, classes, agnostic_nms, max_det=max_det)
    dt[2] += time_sync() - t3
    print(pred)
    return 0
