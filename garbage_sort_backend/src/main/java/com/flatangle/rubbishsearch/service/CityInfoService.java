package com.flatangle.rubbishsearch.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flatangle.rubbishsearch.entity.City;
import com.flatangle.rubbishsearch.entity.ClassificationDesc;
import com.flatangle.rubbishsearch.entity.Garbage;
import com.flatangle.rubbishsearch.mapper.CityMapper;
import com.flatangle.rubbishsearch.mapper.ClassificationDescMapper;
import com.flatangle.rubbishsearch.mapper.GarbageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DaY1zz
 * @create 2022-03-02-11:03
 */
@Service
public class CityInfoService {

    @Resource
    private CityMapper cityMapper;

    @Resource
    private ClassificationDescMapper classificationDescMapper;

    @Resource
    private GarbageMapper garbageMapper;

    /**
     * 查询所有试点城市
     * @return
     */
    public List<City> findAllCity() {

        LambdaQueryWrapper<City> queryWrapper = Wrappers.lambdaQuery();
        List<City> cities = cityMapper.selectList(queryWrapper);
        return cities;
    }

    /**
     * 查询目标城市的垃圾分类要求
     */
    public List<ClassificationDesc> findTargetDesc(String cityName) {
        List<ClassificationDesc> targetDesc = classificationDescMapper.findTargetDesc(cityName);
        return targetDesc;
    }

    /**
     * 分页查询目标城市的各类垃圾
     */
    public Page<Garbage> findGarbagePage(int cityID, int type,int pageNum, int pageSize) {

        LambdaQueryWrapper<Garbage> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Garbage::getCityID,cityID % 5).eq(Garbage::getType,type);
        Page<Garbage> selectedPage = garbageMapper.selectPage(new Page<Garbage>(pageNum, pageSize), lambdaQueryWrapper);
        return selectedPage;
    }

    /**
     * 根据关键词搜索垃圾的类别（目标城市）
     */
    public List<Garbage> findGarbageTypeByKey(int cityID, String search){
        LambdaQueryWrapper<Garbage> lambdaQueryWrapper = Wrappers.lambdaQuery();

        if(StrUtil.isNotBlank(search)) {
            lambdaQueryWrapper.eq(Garbage::getCityID,cityID % 5).like(Garbage::getGarbageName,search);
        }
        List<Garbage> garbageList = garbageMapper.selectList(lambdaQueryWrapper);
        return garbageList;
    }

    /**
     * 在特定类别中根据关键词搜索垃圾（目标城市）
     */
    public List<Garbage> findGarbage(int cityID, int type, String search) {
        LambdaQueryWrapper<Garbage> lambdaQueryWrapper = Wrappers.lambdaQuery();

        if(StrUtil.isNotBlank(search)) {
            lambdaQueryWrapper.eq(Garbage::getCityID,cityID % 5).eq(Garbage::getType,type).like(Garbage::getGarbageName,search);
        }
        List<Garbage> garbageList = garbageMapper.selectList(lambdaQueryWrapper);
        return garbageList;
    }

}
