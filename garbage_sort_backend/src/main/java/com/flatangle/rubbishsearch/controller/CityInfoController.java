package com.flatangle.rubbishsearch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flatangle.rubbishsearch.common.Result;
import com.flatangle.rubbishsearch.entity.City;
import com.flatangle.rubbishsearch.entity.ClassificationDesc;
import com.flatangle.rubbishsearch.entity.Garbage;
import com.flatangle.rubbishsearch.service.CityInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author DaY1zz
 * @create 2022-03-02-14:21
 */
@RestController
@RequestMapping("/cityinfo")
public class CityInfoController {

    @Resource
    private CityInfoService cityInfoService;

    /**
     * 获取省市信息
     * @return
     */
    @GetMapping("/cities")
    public Result<?> getCities() {
        List<City> cities = cityInfoService.findAllCity();
        return Result.success(cities);
    }

    /**
     * 获取目标省市的分类要求描述
     * @param reqBodyMap
     * @return
     */
    @PostMapping("/desc")
    public Result<?> getTargetDesc(@RequestBody Map<String, Object> reqBodyMap) {
        String  cityName = (String) reqBodyMap.get("cityName");
        List<ClassificationDesc> targetDesc = cityInfoService.findTargetDesc(cityName);
        return Result.success(targetDesc);
    }

    /**
     * 分页获取目标省市的目标类别的垃圾信息
     * @param reqBodyMap
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/garbagetype")
    public Result<?> findGarbageType(@RequestBody Map<String,Object> reqBodyMap,
                                     @RequestParam(defaultValue = "1") int pageNum,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        int cityID = (int) reqBodyMap.get("cityID");
        int type = (int) reqBodyMap.get("type");
        Page<Garbage> page = cityInfoService.findGarbagePage(cityID,type,pageNum,pageSize);
        return Result.success(page);
    }

    /**
     * 模糊查询垃圾
     * @param reqBodyMap
     * @return
     */
    @PostMapping("/garbagesearch")
    public Result<?> garbageSearch(@RequestBody Map<String,Object> reqBodyMap) {

        int cityID = (int) reqBodyMap.get("cityID");
        String search = (String) reqBodyMap.get("search");
        int type = -1;
        if(reqBodyMap.containsKey("type"))
            type = (int) reqBodyMap.get("type");

        List<Garbage> garbageList;
        if(type == -1) {
            garbageList = cityInfoService.findGarbageTypeByKey(cityID, search);
        }
        else {
            garbageList = cityInfoService.findGarbage(cityID, type, search);
        }
        return Result.success(garbageList);

    }

}
