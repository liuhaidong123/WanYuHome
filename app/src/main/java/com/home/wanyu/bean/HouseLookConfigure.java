package com.home.wanyu.bean;

/**
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseLookConfigure {
    private String name;
    private int img;
    private long id;

    public HouseLookConfigure(String name, int img, long id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
