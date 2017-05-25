package com.home.wanyu.bean.Express;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class ExpressBean {
    private String name;
    private  int img;
    private int phoneImg;
    private long phoneNum;

    public ExpressBean(String name, int img, int phoneImg, long phoneNum) {
        this.name = name;
        this.img = img;
        this.phoneImg = phoneImg;
        this.phoneNum = phoneNum;
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

    public int getPhoneImg() {
        return phoneImg;
    }

    public void setPhoneImg(int phoneImg) {
        this.phoneImg = phoneImg;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}
