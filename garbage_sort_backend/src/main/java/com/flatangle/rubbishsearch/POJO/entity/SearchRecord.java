package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-03-30-14:53
 */
@Data
@TableName("search_record")
public class SearchRecord {

    @TableId("userID")
    private int userID;

    private int recycleCount;   //可回收垃圾查询数

    private int harmfulCount;   //有害垃圾查询数

    private int kitchenCount;   //厨余垃圾查询数

    private int otherCount;     //其他垃圾查询数


}
