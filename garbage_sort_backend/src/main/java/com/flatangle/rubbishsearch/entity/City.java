package com.flatangle.rubbishsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-02-26-9:55
 */
@Data
@TableName("city")
public class City {

    @TableId(value = "cityID",type = IdType.AUTO)
    private int cityID;

    private String cityName;
}
