package com.flatangle.rubbishsearch.POJO.params;

import lombok.Data;

@Data
public class DetectParams {
    String openID;
    DetectResult[] detectResults;
}
