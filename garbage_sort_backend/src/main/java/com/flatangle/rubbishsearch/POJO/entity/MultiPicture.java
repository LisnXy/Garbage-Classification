package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("multi_resource")
public class MultiPicture {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField(value = "path")
    private String path;

    @TableField(value = "label")
    private String label;
}
