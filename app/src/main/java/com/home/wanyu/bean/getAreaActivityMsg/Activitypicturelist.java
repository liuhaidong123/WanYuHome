package com.home.wanyu.bean.getAreaActivityMsg;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class Activitypicturelist {
    private long personalId;

    private String picture;

    public void setPersonalId(long personalId){
        this.personalId = personalId;
    }
    public long getPersonalId(){
        return this.personalId;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return this.picture;
    }
}
