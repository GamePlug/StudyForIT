package com.leichao.studyforit.appmode.index;

/**
 *
 * Created by leichao on 2016/5/3.
 */
public class SubjectBean {
    private Class clazz;
    private int imageResource;
    private String desc;

    public SubjectBean(Class clazz, int imageResource, String desc) {
        this.clazz = clazz;
        this.imageResource = imageResource;
        this.desc = desc;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
