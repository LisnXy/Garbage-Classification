package com.flatangle.rubbishsearch;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import cn.hutool.core.io.file.FileReader;
import com.flatangle.rubbishsearch.mapper.CityMapper;
import com.flatangle.rubbishsearch.mapper.ClassificationDescMapper;
import com.flatangle.rubbishsearch.mapper.GarbageMapper;
import com.flatangle.rubbishsearch.jsonobject.JsonRubbish;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Data
class JsonList{
    public List<JsonRubbish> list;
}

@Data
class JsonGarbage{
    private String name;
    private int category;
}

@Data
class JsonDescription {
    private String city_name;
    private String type;
    private String desc;
    private String requirement;
}

@Data
class JsonCity {
    private int id;
    private String city_name;
}

@SpringBootTest
class RubbishsearchApplicationTests {

    @Resource
    private GarbageMapper garbageMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private ClassificationDescMapper classificationDescMapper;

    @Test
    void contextLoads() {

//        FileReader file1 = new FileReader("C:\\Users\\DaY1zz\\Desktop\\服创\\分类描述.json");
//        String descString = file1.readString();
//        List<JsonDescription> jsonDescriptions = JSONUtil.toList(descString, JsonDescription.class);

        FileReader file2 = new FileReader("C:\\Users\\DaY1zz\\Desktop\\服创\\大件垃圾信息.json");
        String cityString = file2.readString();
        JSONObject cityData = JSONUtil.parseObj(cityString);
        List<JsonRubbish> list = cityData.getBeanList("list", JsonRubbish.class);
        for(JsonRubbish rubbish : list){
            Garbage garbage = new Garbage();
            garbage.setCityID(4);
            garbage.setType(5);
            garbage.setGarbageName(rubbish.getExample());
            garbageMapper.insert(garbage);
        }


        //可回收物 == 可回收物
        //有害垃圾 == 有害垃圾
        //厨余垃圾 == 厨余垃圾36/易腐垃圾3/湿垃圾1/餐厨垃圾4/1/1
        //其他垃圾 == 其他垃圾45/干垃圾1
//
//        for(JsonDescription jsonDescription : jsonDescriptions) {
//            ClassificationDesc classificationDesc = new ClassificationDesc();
//
//            for(JsonCity jsonCity :jsonCities) {
//                if(jsonCity.getCity_name().equals(jsonDescription.getCity_name())){
//                    classificationDesc.setCityID(jsonCity.getId()-1);
//                }
//            }
//            classificationDesc.setTypeName(jsonDescription.getType());
//            classificationDesc.setRequirement(jsonDescription.getRequirement());
//            classificationDesc.setDescription(jsonDescription.getDesc());
//            if (jsonDescription.getType().equals("可回收物"))
//                classificationDesc.setType(1);
//            else if(jsonDescription.getType().equals("有害垃圾"))
//                classificationDesc.setType(2);
//            else if(jsonDescription.getType().equals("其他垃圾") || jsonDescription.getType().contains("干"))
//                classificationDesc.setType(3);
//            else if(jsonDescription.getType().contains("厨") || jsonDescription.getType().contains("易腐") || jsonDescription.getType().contains("湿"))
//                classificationDesc.setType(4);
//            else if(jsonDescription.getType().contains("大件垃圾"))
//                classificationDesc.setType(5);
//
//            classificationDescMapper.insert(classificationDesc);
//
//        }



//        JsonList jsonList = JSONUtil.toBean(stringBuilder.toString(), JsonList.class);
//        for(JsonRubbish rubbish : jsonList.list1) {
//            Garbage garbage = new Garbage();
//            garbage.setCityID(15);
//            garbage.setType(rubbish.getType_id());
//            garbage.setGarbageName(rubbish.getExample());
//            garbageMapper.insert(garbage);
//        }
//        for(JsonRubbish rubbish : jsonList.list2) {
//            Garbage garbage = new Garbage();
//            garbage.setCityID(15);
//            garbage.setType(rubbish.getType_id());
//            garbage.setGarbageName(rubbish.getExample());
//            garbageMapper.insert(garbage);
//        }
//        for(JsonRubbish rubbish : jsonList.list3) {
//            Garbage garbage = new Garbage();
//            garbage.setCityID(15);
//            garbage.setType(rubbish.getType_id());
//            garbage.setGarbageName(rubbish.getExample());
//            garbageMapper.insert(garbage);
//        }
//        for(JsonRubbish rubbish : jsonList.list4) {
//            Garbage garbage = new Garbage();
//            garbage.setCityID(15);
//            garbage.setType(rubbish.getType_id());
//            garbage.setGarbageName(rubbish.getExample());
//            garbageMapper.insert(garbage);
//        }


    }

}
