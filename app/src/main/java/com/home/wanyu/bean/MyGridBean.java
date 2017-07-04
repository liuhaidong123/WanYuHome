package com.home.wanyu.bean;

/**
 * Created by liuhaidong on 2017/6/29.
 */

public class MyGridBean {
    private String name;
    private Integer img;

    public MyGridBean(String name, Integer img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
