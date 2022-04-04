package com.flatangle.rubbishsearch.POJO.params;

import java.util.List;

public class MultiFileContainer {

    private String imgstr;
    private List<String> label;

    public MultiFileContainer(String imgstr, List<String> label) {
        this.imgstr = imgstr;
        this.label = label;
    }

    public String getImgstr() {
        return imgstr;
    }

    public void setImgstr(String imgstr) {
        this.imgstr = imgstr;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
