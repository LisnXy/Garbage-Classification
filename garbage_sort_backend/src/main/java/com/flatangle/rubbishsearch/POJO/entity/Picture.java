package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("resource")
public class Picture {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private String path;

    private int label;
}