package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-03-30-14:53
 */
@Data
@TableName("answer_record")
public class AnswerRecord {
    @TableId("userID")
    private int userID;

    private int recycleFalseCount;   //可回收垃圾错误数

    private int harmfulFalseCount;   //有害垃圾错误数

    private int kitchenFalseCount;   //厨余垃圾错误数

    private int otherFalseCount;     //其他垃圾错误数

    private int score;   //答题得分
}
