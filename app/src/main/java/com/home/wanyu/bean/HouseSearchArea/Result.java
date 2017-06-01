package com.home.wanyu.bean.HouseSearchArea;

/**
 * Created by liuhaidong on 2017/5/27.
 */

public class Result {
    private String rname;

    private String city;

    private long  id;

    public void setRname(String rname){
        this.rname = rname;
    }
    public String getRname(){
        return this.rname;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setId(long  id){
        this.id = id;
    }
    public long  getId(){
        return this.id;
    }
}
