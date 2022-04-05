package com.flatangle.rubbishsearch.POJO.params;

import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-03-30-15:56
 */
@Data
public class CompleteAnswerParams {
    int userID;
    int score;
    int[] falseRecord;
}
