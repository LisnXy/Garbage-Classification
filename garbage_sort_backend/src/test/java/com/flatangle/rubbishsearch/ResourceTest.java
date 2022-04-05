package com.flatangle.rubbishsearch;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.POJO.entity.Picture;
import com.flatangle.rubbishsearch.mapper.PictureMapper;
import com.flatangle.rubbishsearch.service.AnswerService;
import com.flatangle.rubbishsearch.service.GetLabelService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.IOException;
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

}
