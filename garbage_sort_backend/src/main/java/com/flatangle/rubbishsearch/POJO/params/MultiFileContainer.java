package com.flatangle.rubbishsearch.POJO.params;

public class MultiFileContainer {

    private String imgstr;
    private String[] label;

    public MultiFileContainer(String imgstr, String[] label) {
        this.imgstr = imgstr;
        this.label = label;
    }

    public String getImgstr() {
        return imgstr;
    }

    public void setImgstr(String imgstr) {
        this.imgstr = imgstr;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }
}
