package com.flatangle.rubbishsearch.service;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.MultiPicture;
import com.flatangle.rubbishsearch.POJO.entity.Picture;
import com.flatangle.rubbishsearch.mapper.MultiPictureMapper;
import com.flatangle.rubbishsearch.mapper.PictureMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.JavaBean;
import java.io.*;
import java.util.*;

@Service
public class GetLabelService {

    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private MultiPictureMapper multiPictureMapper;

    private String targetSrc = "C:\\Users\\86187\\Pictures\\ImageFile\\";

    private String saveDir = "C:\\Users\\86187\\Desktop\\0\\";

    private String command = "";

    private String multi_command = "D:\\TrashC\\yolov5\\process\\process.exe -p 95279527hyz -d resource -t multi_resource1 -m D:\\TrashC\\yolov5\\yolov5s.pt -y D:\\TrashC\\yolov5\\data\\coco128.yaml --save_dir C:\\Users\\86187\\Desktop\\0";

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
    public String getLabel(String path){

        LambdaQueryWrapper<Picture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Picture::getPath, path);
        while (true){
            Picture picture = pictureMapper.selectList(queryWrapper).get(0);
            if(picture.getLabel() != -1){
                pictureMapper.delete(queryWrapper);
                return (readJson(String.valueOf(picture.getLabel())));
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
                return (getImageStr(saveDir+path));
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
    public List<String> getLabels(String path) throws IOException {
        List<String> labels = new ArrayList<>();

        labels.add("88");
        return labels;
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
                        "    \"0\": \"\\u4e00\\u6b21\\u6027\\u9910\\u5177\",\n" +
                        "    \"1\": \"\\u4f20\\u5355\",\n" +
                        "    \"2\": \"\\u5145\\u7535\\u5b9d\",\n" +
                        "    \"3\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u4e32\\u4e32\\u7af9\\u7b7e\",\n" +
                        "    \"4\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u4fbf\\u5229\\u8d34\",\n" +
                        "    \"5\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u53e3\\u7f69\",\n" +
                        "    \"6\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u5e72\\u71e5\\u5242\",\n" +
                        "    \"7\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u6d82\\u6539\\u5e26\",\n" +
                        "    \"8\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u6e7f\\u7eb8\\u5dfe\",\n" +
                        "    \"9\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u7259\\u5237\",\n" +
                        "    \"10\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u773c\\u955c\",\n" +
                        "    \"11\": \"\\u5176\\u4ed6\\u5783\\u573e_\\u82cd\\u8747\\u62cd\",\n" +
                        "    \"12\": \"\\u5269\\u83dc\\u5269\\u996d\",\n" +
                        "    \"13\": \"\\u5305\",\n" +
                        "    \"14\": \"\\u5316\\u5986\\u54c1\\u74f6\",\n" +
                        "    \"15\": \"\\u536b\\u751f\\u7eb8\",\n" +
                        "    \"16\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u5496\\u5561\",\n" +
                        "    \"17\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u5723\\u5973\\u679c\",\n" +
                        "    \"18\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u5730\\u74dc\",\n" +
                        "    \"19\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u575a\\u679c\",\n" +
                        "    \"20\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u58f3\",\n" +
                        "    \"21\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u5de7\\u514b\\u529b\",\n" +
                        "    \"22\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u679c\\u51bb\",\n" +
                        "    \"23\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u6838\\u6843\",\n" +
                        "    \"24\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u68a8\",\n" +
                        "    \"25\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u6a59\\u5b50\",\n" +
                        "    \"26\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u6c34\\u679c\",\n" +
                        "    \"27\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u6ce1\\u83dc\",\n" +
                        "    \"28\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u706b\\u9f99\\u679c\",\n" +
                        "    \"29\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u70e4\\u9e21\",\n" +
                        "    \"30\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u74dc\\u5b50\",\n" +
                        "    \"31\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u7518\\u8517\",\n" +
                        "    \"32\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u756a\\u8304\",\n" +
                        "    \"33\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u79f8\\u79c6\\u676f\",\n" +
                        "    \"34\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u79f8\\u79c6\\u7897\",\n" +
                        "    \"35\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u7c89\\u6761\",\n" +
                        "    \"36\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u82f9\\u679c\",\n" +
                        "    \"37\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u8336\\u53f6\",\n" +
                        "    \"38\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u8349\\u8393\",\n" +
                        "    \"39\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u85af\\u6761\",\n" +
                        "    \"40\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u85af\\u7247\",\n" +
                        "    \"41\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u8611\\u83c7\",\n" +
                        "    \"42\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u86cb\\u631e\",\n" +
                        "    \"43\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u8c46\\u8150\",\n" +
                        "    \"44\": \"\\u53a8\\u4f59\\u5783\\u573e_\\u9e21\\u7fc5\",\n" +
                        "    \"45\": \"\\u53ef\\u56de\\u6536\\u7269_\\u4e0d\\u9508\\u94a2\\u5236\\u54c1\",\n" +
                        "    \"46\": \"\\u53ef\\u56de\\u6536\\u7269_\\u4f53\\u91cd\\u79e4\",\n" +
                        "    \"47\": \"\\u53ef\\u56de\\u6536\\u7269_\\u4fdd\\u6e29\\u676f\",\n" +
                        "    \"48\": \"\\u53ef\\u56de\\u6536\\u7269_\\u5355\\u8f66\",\n" +
                        "    \"49\": \"\\u53ef\\u56de\\u6536\\u7269_\\u5361\",\n" +
                        "    \"50\": \"\\u53ef\\u56de\\u6536\\u7269_\\u5439\\u98ce\\u673a\",\n" +
                        "    \"51\": \"\\u53ef\\u56de\\u6536\\u7269_\\u5e3d\\u5b50\",\n" +
                        "    \"52\": \"\\u53ef\\u56de\\u6536\\u7269_\\u624b\\u673a\",\n" +
                        "    \"53\": \"\\u53ef\\u56de\\u6536\\u7269_\\u624b\\u7535\\u7b52\",\n" +
                        "    \"54\": \"\\u53ef\\u56de\\u6536\\u7269_\\u624b\\u8868\",\n" +
                        "    \"55\": \"\\u53ef\\u56de\\u6536\\u7269_\\u6253\\u5370\\u673a\",\n" +
                        "    \"56\": \"\\u53ef\\u56de\\u6536\\u7269_\\u62c9\\u6746\\u7bb1\",\n" +
                        "    \"57\": \"\\u53ef\\u56de\\u6536\\u7269_\\u62d6\\u978b\",\n" +
                        "    \"58\": \"\\u53ef\\u56de\\u6536\\u7269_\\u6728\\u8d28\\u68b3\\u5b50\",\n" +
                        "    \"59\": \"\\u53ef\\u56de\\u6536\\u7269_\\u706d\\u706b\\u5668\",\n" +
                        "    \"60\": \"\\u53ef\\u56de\\u6536\\u7269_\\u70df\\u7070\\u7f38\",\n" +
                        "    \"61\": \"\\u53ef\\u56de\\u6536\\u7269_\\u70ed\\u6c34\\u74f6\",\n" +
                        "    \"62\": \"\\u53ef\\u56de\\u6536\\u7269_\\u71c3\\u6c14\\u74f6\",\n" +
                        "    \"63\": \"\\u53ef\\u56de\\u6536\\u7269_\\u7535\\u89c6\\u673a\",\n" +
                        "    \"64\": \"\\u53ef\\u56de\\u6536\\u7269_\\u7535\\u8def\\u677f\",\n" +
                        "    \"65\": \"\\u53ef\\u56de\\u6536\\u7269_\\u7535\\u98ce\\u6247\",\n" +
                        "    \"66\": \"\\u53ef\\u56de\\u6536\\u7269_\\u7eb8\\u724c\",\n" +
                        "    \"67\": \"\\u53ef\\u56de\\u6536\\u7269_\\u8033\\u673a\",\n" +
                        "    \"68\": \"\\u53ef\\u56de\\u6536\\u7269_\\u8ba1\\u7b97\\u5668\",\n" +
                        "    \"69\": \"\\u53ef\\u56de\\u6536\\u7269_\\u8bdd\\u7b52\",\n" +
                        "    \"70\": \"\\u53ef\\u56de\\u6536\\u7269_\\u8def\\u7531\\u5668\",\n" +
                        "    \"71\": \"\\u53ef\\u56de\\u6536\\u7269_\\u8f6e\\u80ce\",\n" +
                        "    \"72\": \"\\u53ef\\u56de\\u6536\\u7269_\\u94a5\\u5319\",\n" +
                        "    \"73\": \"\\u53ef\\u56de\\u6536\\u7269_\\u94c1\\u4e1d\\u7403\",\n" +
                        "    \"74\": \"\\u53ef\\u56de\\u6536\\u7269_\\u9505\\u76d6\",\n" +
                        "    \"75\": \"\\u53ef\\u56de\\u6536\\u7269_\\u952e\\u76d8\",\n" +
                        "    \"76\": \"\\u53ef\\u56de\\u6536\\u7269_\\u95f9\\u94c3\",\n" +
                        "    \"77\": \"\\u53ef\\u56de\\u6536\\u7269_\\u96e8\\u4f1e\",\n" +
                        "    \"78\": \"\\u53ef\\u56de\\u6536\\u7269_\\u9f20\\u6807\",\n" +
                        "    \"79\": \"\\u53f0\\u5386_\\u53ef\\u56de\\u6536\\u7269\",\n" +
                        "    \"80\": \"\\u5851\\u6599\\u73a9\\u5177\",\n" +
                        "    \"81\": \"\\u5851\\u6599\\u7897\\u76c6\",\n" +
                        "    \"82\": \"\\u5851\\u6599\\u8863\\u67b6\",\n" +
                        "    \"83\": \"\\u5927\\u9aa8\\u5934\",\n" +
                        "    \"84\": \"\\u5c3f\\u7247\",\n" +
                        "    \"85\": \"\\u5e72\\u7535\\u6c60\",\n" +
                        "    \"86\": \"\\u5e9f\\u5f03\\u6c34\\u94f6\\u6e29\\u5ea6\\u8ba1\",\n" +
                        "    \"87\": \"\\u5e9f\\u65e7\\u706f\\u7ba1\\u706f\\u6ce1\",\n" +
                        "    \"88\": \"\\u5feb\\u9012\\u7eb8\\u888b\",\n" +
                        "    \"89\": \"\\u62a5\\u7eb8\",\n" +
                        "    \"90\": \"\\u63d2\\u5934\\u7535\\u7ebf\",\n" +
                        "    \"91\": \"\\u65e7\\u4e66\",\n" +
                        "    \"92\": \"\\u65e7\\u8863\\u670d\",\n" +
                        "    \"93\": \"\\u6613\\u62c9\\u7f50\",\n" +
                        "    \"94\": \"\\u6709\\u5bb3\\u5783\\u573e_\\u7535\\u6c60\\u677f\",\n" +
                        "    \"95\": \"\\u6709\\u5bb3\\u5783\\u573e_\\u7ebd\\u6263\\u7535\\u6c60\",\n" +
                        "    \"96\": \"\\u6709\\u5bb3\\u5783\\u573e_\\u80f6\\u6c34\",\n" +
                        "    \"97\": \"\\u6709\\u5bb3\\u5783\\u573e_\\u84c4\\u7535\\u6c60\",\n" +
                        "    \"98\": \"\\u6740\\u866b\\u5242\\u5bb9\\u5668\",\n" +
                        "    \"99\": \"\\u6742\\u5fd7\",\n" +
                        "    \"100\": \"\\u6795\\u5934\",\n" +
                        "    \"101\": \"\\u679c\\u58f3\\u74dc\\u76ae\",\n" +
                        "    \"102\": \"\\u6905\\u5b50_\\u53ef\\u56de\\u6536\\u7269\",\n" +
                        "    \"103\": \"\\u6b8b\\u679d\\u843d\\u53f6\",\n" +
                        "    \"104\": \"\\u6bdb\\u7ed2\\u73a9\\u5177\",\n" +
                        "    \"105\": \"\\u6c34\\u679c\\u679c\\u76ae\",\n" +
                        "    \"106\": \"\\u6c34\\u679c\\u679c\\u8089\",\n" +
                        "    \"107\": \"\\u6c61\\u635f\\u5851\\u6599\",\n" +
                        "    \"108\": \"\\u6ce1\\u6cab\\u5851\\u6599\",\n" +
                        "    \"109\": \"\\u6d17\\u53d1\\u6c34\\u74f6\",\n" +
                        "    \"110\": \"\\u70df\\u8482\",\n" +
                        "    \"111\": \"\\u70e4\\u7bb1_\\u53ef\\u56de\\u6536\\u7269\",\n" +
                        "    \"112\": \"\\u7259\\u7b7e\",\n" +
                        "    \"113\": \"\\u725b\\u5976\\u76d2\\u7b49\\u5229\\u4e50\\u5305\\u88c5\",\n" +
                        "    \"114\": \"\\u73bb\\u7483\",\n" +
                        "    \"115\": \"\\u73bb\\u7483\\u74f6\\u7f50\",\n" +
                        "    \"116\": \"\\u7535\\u6c60\",\n" +
                        "    \"117\": \"\\u767d\\u841d\\u535c_\\u53a8\\u4f59\\u5783\\u573e\",\n" +
                        "    \"118\": \"\\u76ae\\u978b\",\n" +
                        "    \"119\": \"\\u7827\\u677f\",\n" +
                        "    \"120\": \"\\u7834\\u788e\\u82b1\\u76c6\\u53ca\\u789f\\u7897\",\n" +
                        "    \"121\": \"\\u7af9\\u7b77\",\n" +
                        "    \"122\": \"\\u7ea2\\u80a0_\\u53a8\\u4f59\\u5783\\u573e\",\n" +
                        "    \"123\": \"\\u7eb8\\u676f\",\n" +
                        "    \"124\": \"\\u7eb8\\u677f\\u7bb1\",\n" +
                        "    \"125\": \"\\u80e1\\u841d\\u535c_\\u53a8\\u4f59\\u5783\\u573e\",\n" +
                        "    \"126\": \"\\u82b1\\u751f\\u76ae_\\u53a8\\u4f59\\u5783\\u573e\",\n" +
                        "    \"127\": \"\\u8336\\u53f6\\u6e23\",\n" +
                        "    \"128\": \"\\u83dc\\u6897\\u83dc\\u53f6\",\n" +
                        "    \"129\": \"\\u843d\\u53f6\",\n" +
                        "    \"130\": \"\\u86cb\\u58f3\",\n" +
                        "    \"131\": \"\\u897f\\u9910\\u7cd5\\u70b9\",\n" +
                        "    \"132\": \"\\u8c03\\u6599\\u74f6\",\n" +
                        "    \"133\": \"\\u8d1d\\u58f3\",\n" +
                        "    \"134\": \"\\u8f6f\\u818f\",\n" +
                        "    \"135\": \"\\u8fc7\\u671f\\u836f\\u7269\",\n" +
                        "    \"136\": \"\\u9152\\u74f6\",\n" +
                        "    \"137\": \"\\u91d1\\u5c5e\\u7897_\\u53ef\\u56de\\u6536\\u7269\",\n" +
                        "    \"138\": \"\\u91d1\\u5c5e\\u98df\\u54c1\\u7f50\",\n" +
                        "    \"139\": \"\\u9505\",\n" +
                        "    \"140\": \"\\u9664\\u8349\\u5242\\u5bb9\\u5668\",\n" +
                        "    \"141\": \"\\u98df\\u7528\\u6cb9\\u6876\",\n" +
                        "    \"142\": \"\\u996e\\u6599\\u74f6\",\n" +
                        "    \"143\": \"\\u9c7c\\u9aa8\"\n" +
                        "}");

        System.out.println(object.getString(label));
        return object.getString(label);
    }


}
