package com.home.wanyu.bean.haveAddress;

import java.io.Serializable;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class Result implements Serializable {
    private String detailAddress;

    private String city;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }
}
