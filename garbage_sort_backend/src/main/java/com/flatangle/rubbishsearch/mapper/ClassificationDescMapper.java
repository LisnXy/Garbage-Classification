package com.flatangle.rubbishsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flatangle.rubbishsearch.entity.ClassificationDesc;

import java.util.List;

/**
 * @author DaY1zz
 * @create 2022-02-26-10:02
 */
public interface ClassificationDescMapper extends BaseMapper<ClassificationDesc> {

    List<ClassificationDesc> findTargetDesc(String cityName);

}
