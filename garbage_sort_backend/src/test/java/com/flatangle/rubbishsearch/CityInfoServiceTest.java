package com.flatangle.rubbishsearch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flatangle.rubbishsearch.POJO.entity.City;
import com.flatangle.rubbishsearch.POJO.entity.ClassificationDesc;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.service.CityInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DaY1zz
 * @create 2022-03-02-11:08
 */
@SpringBootTest
public class CityInfoServiceTest {

    @Resource
    CityInfoService cityInfoService;

    @Test
    public void testFindAll(){
        List<City> cities = cityInfoService.findAllCity();
        System.out.println(cities);
    }

    @Test
    public void testFindTarget() {
        List<ClassificationDesc> descs = cityInfoService.findTargetDesc("福州市");
        System.out.println(descs);
    }

    @Test
    public void testFindGarbagePage() {
        Page<Garbage> page = cityInfoService.findGarbagePage(14, 1, 1, 10);
        System.out.println(page.getRecords());
    }
    @Test
    public void testFindGarbageTypeByKey() {
//        List<Garbage> garbageList = cityInfoService.findGarbageTypeByKey(3, "龙虾");
        List<Garbage> garbageList = cityInfoService.findGarbage(0, 2, "纸");
        for(Garbage garbage : garbageList)
            System.out.println(garbage);
    }



}
