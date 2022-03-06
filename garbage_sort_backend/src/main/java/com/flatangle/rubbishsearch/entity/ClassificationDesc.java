package com.flatangle.rubbishsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-02-26-9:58
 */
@Data
@TableName("classification_desc")
public class ClassificationDesc {

    @TableId(value = "descID", type = IdType.AUTO)
    private int descID;

    @TableField(value = "cityID")
    private int cityID;

    private int type;

    private String typeName;

    private String description;

    private String requirement;
}
