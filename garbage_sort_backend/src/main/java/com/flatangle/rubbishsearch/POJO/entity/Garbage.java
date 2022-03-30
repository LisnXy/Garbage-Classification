package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-02-23-16:21
 */
@Data
@TableName("garbage")
public class Garbage {

    @TableId(value = "garbageID", type = IdType.AUTO)
    private int garbageID;

    @TableField(value = "cityID")
    private int cityID;

    private int type;

    private  String garbageName;

}
