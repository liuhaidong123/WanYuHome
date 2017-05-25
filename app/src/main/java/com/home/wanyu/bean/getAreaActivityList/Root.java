package com.home.wanyu.bean.getAreaActivityList;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class Root {
    private List<Result> result;

    private String code;

    private long userid;

    public void setResult(List<Result> result){
        this.result = result;
    }
    public List<Result> getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setUserid(long userid){
        this.userid = userid;
    }
    public long getUserid(){
        return this.userid;
    }
}
