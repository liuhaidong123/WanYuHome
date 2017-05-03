package com.home.wanyu.bean;

/**
 * Created by liuhaidong on 2017/5/2.
 */

public class CircleBean {
    private Integer img;
    private String name;
    private int flag;

    public CircleBean(Integer img, String name,int flag) {
        this.img = img;
        this.name = name;
        this.flag=flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
