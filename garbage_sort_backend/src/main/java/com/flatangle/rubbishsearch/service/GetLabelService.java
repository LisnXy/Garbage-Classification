package com.flatangle.rubbishsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.POJO.entity.MultiPicture;
import com.flatangle.rubbishsearch.POJO.entity.Picture;
import com.flatangle.rubbishsearch.POJO.entity.SearchRecord;
import com.flatangle.rubbishsearch.mapper.GarbageMapper;
import com.flatangle.rubbishsearch.mapper.MultiPictureMapper;
import com.flatangle.rubbishsearch.mapper.PictureMapper;
import com.flatangle.rubbishsearch.mapper.SearchRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;

@Service
public class GetLabelService {

    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private MultiPictureMapper multiPictureMapper;
    @Resource
    private GarbageMapper garbageMapper;
    @Resource
    private SearchRecordMapper searchRecordMapper;


    private String targetSrc = System.getProperty("user.dir") + "/garbage_sort_backend/src/main/resources/imageFiles/";

    private String saveDir = System.getProperty("user.dir") + "/garbage_sort_backend/src/main/resources/static/";

    private String command = "D:\\TrashC\\process_conv\\process -p 95279527hyz -d resource -t resource1 --class_num 144 -m D:\\TrashC\\ConvNeXt\\weights\\best_model1.pth";

    private String multi_command = "D:\\TrashC\\process_yolo5\\process.exe -p 95279527hyz -d resource -t multi_resource1 -m D:\\TrashC\\yolov5\\yolov5s.pt -y D:\\TrashC\\yolov5\\data\\coco128.yaml --save_dir C:\\Users\\86187\\Desktop\\0";

    /**
     * 基本无用
     * @param path
     * @return
     */
    public List<Picture> findByPath(String path){
        LambdaQueryWrapper<Picture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Picture::getPath, path);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        return pictures;
    }

    /**
     * 返回单目标种类
     * @param path
     * @return
     */
    public HashMap<String, Object> getLabel(String path, String openID){

        float odd = 0;
        HashMap<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<Picture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Picture::getPath, path);
        while (true){
            Picture picture = pictureMapper.selectList(queryWrapper).get(0);
            if(picture.getLabel() != -1){
                pictureMapper.delete(queryWrapper);
                String garbageName = readJson(String.valueOf(picture.getLabel()));
                LambdaQueryWrapper<Garbage> garbageLambdaQueryWrapper = Wrappers.lambdaQuery();
                garbageLambdaQueryWrapper.eq(Garbage::getGarbageName,garbageName);
                int garbageType = garbageMapper.selectList(garbageLambdaQueryWrapper).get(0).getType();

                //若识别正确率高于80%，则计入搜索记录中，用于分析用户对垃圾分类知识的了解
                if(picture.getOdd() > 0.8) {
                    SearchRecord searchRecord = searchRecordMapper.selectById(openID);
                    switch (garbageType) {
                        case 1: searchRecord.setRecycleCount(searchRecord.getRecycleCount() + 1); break;
                        case 2: searchRecord.setHarmfulCount(searchRecord.getHarmfulCount() + 1); break;
                        case 3: searchRecord.setKitchenCount(searchRecord.getKitchenCount() + 1); break;
                        case 4: searchRecord.setOtherCount(searchRecord.getOtherCount() + 1); break;
                    }
                    searchRecordMapper.updateById(searchRecord);
                }

                result.put("probability", picture.getOdd());
                result.put("garbageName", garbageName);
                result.put("garbageType", garbageType);
                return result;
            }
        }
    }

    public void insertPicture(String path){

        Picture picture = new Picture();
        picture.setPath(path);
        picture.setLabel(-1);
        pictureMapper.insert(picture);

    }

    public String getMultiImg(String path) throws IOException{

        LambdaQueryWrapper<MultiPicture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MultiPicture::getPath, targetSrc+path);
        while (true){
            MultiPicture picture = multiPictureMapper.selectList(queryWrapper).get(0);
            if(!Objects.equals(picture.getLabel(), "-1")){
                multiPictureMapper.delete(queryWrapper);
                return path;       //返回图片数据输出流 改为 返回图片文件名
            }
        }
    }

    public void insertMultiPicture(String path){

        MultiPicture picture = new MultiPicture();
        picture.setPath(path);
        picture.setLabel("-1");
        multiPictureMapper.insert(picture);

    }

    public  String getImageStr(String imgFile) throws IOException {

        FileInputStream inputStream = null;
        final Base64.Encoder encoder = Base64.getEncoder();

        try {

            inputStream = new FileInputStream(imgFile);

            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);

            String base64Str = encoder.encodeToString(bytes);
            return base64Str;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        return null;
    }

    /**
     * 开发中
     * @return 模型输出的结果数组
     */
    public List<String> getLabels(String path, String openID) throws IOException {
        List<String> labels = new ArrayList<>();
        LambdaQueryWrapper<MultiPicture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MultiPicture::getPath, path);
        while (true){
            MultiPicture picture = multiPictureMapper.selectList(queryWrapper).get(0);
            if(!Objects.equals(picture.getLabel(), "-1")){
                List<String> result = Filter(picture.getLabel());
                for(int i = 0; i < result.size(); i++){
                    String s = result.get(i);
                    String[] strs = s.split(" ");
                    String garbageName = strs[0];
                    String probability = strs[1];
                    LambdaQueryWrapper<Garbage> garbageLambdaQueryWrapper = Wrappers.lambdaQuery();
                    garbageLambdaQueryWrapper.eq(Garbage::getGarbageName,garbageName);
                    int garbageType = garbageMapper.selectList(garbageLambdaQueryWrapper).get(0).getType();

                    //若识别正确率高于80%，则计入搜索记录中，用于分析用户对垃圾分类知识的了解
                    if(Float.parseFloat(probability) > 0.8) {
                        SearchRecord searchRecord = searchRecordMapper.selectById(openID);
                        switch (garbageType) {
                            case 1: searchRecord.setRecycleCount(searchRecord.getRecycleCount() + 1); break;
                            case 2: searchRecord.setHarmfulCount(searchRecord.getHarmfulCount() + 1); break;
                            case 3: searchRecord.setKitchenCount(searchRecord.getKitchenCount() + 1); break;
                            case 4: searchRecord.setOtherCount(searchRecord.getOtherCount() + 1); break;
                        }
                        searchRecordMapper.updateById(searchRecord);
                    }

                    s = s + " " + garbageType;
                    result.set(i, s);
                }
                return result;
            }
        }
    }

    public void change2JPG(File uploadFile) {
        String path = uploadFile.getPath();
        BufferedImage bufferedImage= null;
        try {
            bufferedImage = ImageIO.read(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

        String fileName = path.substring(0,path.lastIndexOf("."));
        try {
            ImageIO.write(newBufferedImage, "jpg", new File(fileName+".jpg"));
            uploadFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getmultiLabels(InputStream inputStream){
        List<String> labels = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            inputStream.close();
            reader.close();
            return Filter(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labels;
    }

    public List<String> Filter(String raw){
        List<String> result = new ArrayList<>();

        int start = raw.indexOf(";") + 1;
        String semi_raw = raw.substring(start);
        result.addAll(List.of(semi_raw.split(";")));

        return result;
    }

    public void executeFile() throws IOException {

        Process process_file = Runtime.getRuntime().exec(command);
    }

    public InputStream executeMultiFile() throws IOException {


        Process process_multifile = Runtime.getRuntime().exec(multi_command);
        InputStream inputStream = process_multifile.getInputStream();

        return inputStream;
    }


    public String readJson(String label){
        JSONObject object = JSONObject
                .parseObject("{\n" +
                        "    \"0\": \"一次性餐具\",\n" +
                        "    \"1\": \"传单\",\n" +
                        "    \"2\": \"充电宝\",\n" +
                        "    \"3\": \"串串竹签\",\n" +
                        "    \"4\": \"便利贴\",\n" +
                        "    \"5\": \"口罩\",\n" +
                        "    \"6\": \"干燥剂\",\n" +
                        "    \"7\": \"涂改带\", \n" +
                        "    \"8\": \"湿纸巾\",\n" +
                        "    \"9\": \"牙刷\",\n" +
                        "    \"10\": \"眼镜\",\n" +
                        "    \"11\": \"苍蝇拍\",\n" +
                        "    \"12\": \"剩菜剩饭\",\n" +
                        "    \"13\": \"包\",\n" +
                        "    \"14\": \"化妆品瓶\",\n" +
                        "    \"15\": \"卫生纸\",\n" +
                        "    \"16\": \"咖啡\",\n" +
                        "    \"17\": \"圣女果\",\n" +
                        "    \"18\": \"地瓜\",\n" +
                        "    \"19\": \"坚果\",\n" +
                        "    \"20\": \"壳\",\n" +
                        "    \"21\": \"巧克力\",\n" +
                        "    \"22\": \"果冻\",\n" +
                        "    \"23\": \"核桃\",\n" +
                        "    \"24\": \"梨\",\n" +
                        "    \"25\": \"橙子\",\n" +
                        "    \"26\": \"水果\",\n" +
                        "    \"27\": \"泡菜\",\n" +
                        "    \"28\": \"火龙果\",\n" +
                        "    \"29\": \"烤鸡\",\n" +
                        "    \"30\": \"瓜子\",\n" +
                        "    \"31\": \"甘蔗\",\n" +
                        "    \"32\": \"番茄\",\n" +
                        "    \"33\": \"秸秆杯\",\n" +
                        "    \"34\": \"秸秆碗\",\n" +
                        "    \"35\": \"粉条\",\n" +
                        "    \"36\": \"苹果\",\n" +
                        "    \"37\": \"茶叶\",\n" +
                        "    \"38\": \"草莓\",\n" +
                        "    \"39\": \"薯条\",\n" +
                        "    \"40\": \"薯片\",\n" +
                        "    \"41\": \"蘑菇\",\n" +
                        "    \"42\": \"蛋挞\",\n" +
                        "    \"43\": \"豆腐\",\n" +
                        "    \"44\": \"鸡翅\",\n" +
                        "    \"45\": \"不锈钢制品\",\n" +
                        "    \"46\": \"体重秤\",\n" +
                        "    \"47\": \"保温杯\",\n" +
                        "    \"48\": \"单车\",\n" +
                        "    \"49\": \"卡\",\n" +
                        "    \"50\": \"吹风机\",\n" +
                        "    \"51\": \"帽子\",\n" +
                        "    \"52\": \"手机\",\n" +
                        "    \"53\": \"手电筒\",\n" +
                        "    \"54\": \"手表\",\n" +
                        "    \"55\": \"打印机\",\n" +
                        "    \"56\": \"拉杆箱\",\n" +
                        "    \"57\": \"拖鞋\",\n" +
                        "    \"58\": \"木质梳子\",\n" +
                        "    \"59\": \"灭火器\",\n" +
                        "    \"60\": \"烟灰缸\",\n" +
                        "    \"61\": \"热水瓶\",\n" +
                        "    \"62\": \"燃气瓶\",\n" +
                        "    \"63\": \"电视机\",\n" +
                        "    \"64\": \"电路板\",\n" +
                        "    \"65\": \"电风扇\",\n" +
                        "    \"66\": \"纸牌\",\n" +
                        "    \"67\": \"耳机\",\n" +
                        "    \"68\": \"计算器\",\n" +
                        "    \"69\": \"话筒\",\n" +
                        "    \"70\": \"路由器\",\n" +
                        "    \"71\": \"轮胎\",\n" +
                        "    \"72\": \"钥匙\",\n" +
                        "    \"73\": \"铁丝球\",\n" +
                        "    \"74\": \"锅盖\",\n" +
                        "    \"75\": \"键盘\",\n" +
                        "    \"76\": \"闹铃\",\n" +
                        "    \"77\": \"雨伞\",\n" +
                        "    \"78\": \"鼠标\",\n" +
                        "    \"79\": \"台历\",\n" +
                        "    \"80\": \"塑料玩具\",\n" +
                        "    \"81\": \"塑料碗盆\",\n" +
                        "    \"82\": \"塑料衣架\",\n" +
                        "    \"83\": \"大骨头\",\n" +
                        "    \"84\": \"尿片\",\n" +
                        "    \"85\": \"干电池\",\n" +
                        "    \"86\": \"废弃水银温度计\",\n" +
                        "    \"87\": \"废旧灯管灯泡\",\n" +
                        "    \"88\": \"快递纸袋\",\n" +
                        "    \"89\": \"报纸\",\n" +
                        "    \"90\": \"插头电线\",\n" +
                        "    \"91\": \"旧书\",\n" +
                        "    \"92\": \"旧衣服\",\n" +
                        "    \"93\": \"易拉罐\",\n" +
                        "    \"94\": \"电池板\",\n" +
                        "    \"95\": \"纽扣电池\",\n" +
                        "    \"96\": \"胶水\",\n" +
                        "    \"97\": \"蓄电池\",\n" +
                        "    \"98\": \"杀虫剂容器\",\n" +
                        "    \"99\": \"杂志\",\n" +
                        "    \"100\": \"枕头\",\n" +
                        "    \"101\": \"果壳瓜皮\",\n" +
                        "    \"102\": \"椅子\",\n" +
                        "    \"103\": \"残枝落叶\",\n" +
                        "    \"104\": \"毛绒玩具\",\n" +
                        "    \"105\": \"水果果皮\",\n" +
                        "    \"106\": \"水果果肉\",\n" +
                        "    \"107\": \"污损塑料\",\n" +
                        "    \"108\": \"泡沫塑料\",\n" +
                        "    \"109\": \"洗发水瓶\",\n" +
                        "    \"110\": \"烟蒂\",\n" +
                        "    \"111\": \"烤箱\",\n" +
                        "    \"112\": \"牙签\",\n" +
                        "    \"113\": \"牛奶盒包装\",\n" +
                        "    \"114\": \"玻璃\",\n" +
                        "    \"115\": \"玻璃瓶罐\",\n" +
                        "    \"116\": \"电池\",\n" +
                        "    \"117\": \"白萝卜\",\n" +
                        "    \"118\": \"皮鞋\",\n" +
                        "    \"119\": \"砧板\",\n" +
                        "    \"120\": \"破碎花盆及碟碗\",\n" +
                        "    \"121\": \"竹筷\",\n" +
                        "    \"122\": \"红肠\",\n" +
                        "    \"123\": \"纸杯\",\n" +
                        "    \"124\": \"纸板箱\",\n" +
                        "    \"125\": \"胡萝卜\",\n" +
                        "    \"126\": \"花生皮\",\n" +
                        "    \"127\": \"茶叶渣\",\n" +
                        "    \"128\": \"菜梗菜叶\",\n" +
                        "    \"129\": \"落叶\",\n" +
                        "    \"130\": \"蛋壳\",\n" +
                        "    \"131\": \"西餐糕点\",\n" +
                        "    \"132\": \"调料瓶\",\n" +
                        "    \"133\": \"贝壳\",\n" +
                        "    \"134\": \"软膏\",\n" +
                        "    \"135\": \"过期药物\",\n" +
                        "    \"136\": \"酒瓶\",\n" +
                        "    \"137\": \"金属碗\",\n" +
                        "    \"138\": \"金属食品罐\",\n" +
                        "    \"139\": \"锅\",\n" +
                        "    \"140\": \"除草剂容器\",\n" +
                        "    \"141\": \"食用油桶\",\n" +
                        "    \"142\": \"饮料瓶\",\n" +
                        "    \"143\": \"鱼骨\"\n" +
                        "}");

        return object.getString(label);

    }


}
