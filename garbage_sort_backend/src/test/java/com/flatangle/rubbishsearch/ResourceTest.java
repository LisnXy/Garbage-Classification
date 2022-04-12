package com.flatangle.rubbishsearch;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.POJO.entity.Picture;
import com.flatangle.rubbishsearch.mapper.PictureMapper;
import com.flatangle.rubbishsearch.service.AnswerService;
import com.flatangle.rubbishsearch.service.GetLabelService;
import com.flatangle.rubbishsearch.service.MultiProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class ResourceTest {

    @Resource
    GetLabelService getLabelService;
    @Resource
    PictureMapper pictureMapper;

    @Test
    public void testfind(){

        System.out.println(pictureMapper.selectById(1));

    }

    @Test
    public void testupadate(){

        Picture picture = new Picture();
        picture.setPath("E");
        picture.setLabel(-1);
        pictureMapper.insert(picture);
    }

    @Test
    public void testexe(){
        getLabelService.insertPicture("D:\\TrashC\\ConvNeXt\\eval\\a.jpg");
        System.out.println(getLabelService.getLabel("D:\\TrashC\\ConvNeXt\\eval\\a.jpg"));
    }

    @Test
    public void testjson(){
        getLabelService.readJson("88");
    }

    @Test
    public void testbase64() throws IOException {
        getLabelService.getImageStr("D:\\TrashC\\ConvNeXt\\eval\\a.jpg");
    }

    @Test
    public void testFilter(){
        System.out.println(getLabelService.Filter("C:/Users/86187/Desktop/0/bf5157b2-52ed-47db-8dbc-a247ca239c2f.png;dining table 0.33;bottle 0.61"));
    }
    @Test
    public void testBu() throws IOException {

        MultiProcessService p = new MultiProcessService();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String line = null;
                    while ((line = p.errorReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception ex) {
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String line = null;
                    while ((line = p.bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception ex) {
                }
            }
        };

        t1.start();
        t2.start();

        while(t1.isAlive() || t2.isAlive()){}

        try {
            p.bufferedReader.close();
            p.errorReader.close();
            p.proc.destroy();
        } catch (Exception ex) {}


    }
}
