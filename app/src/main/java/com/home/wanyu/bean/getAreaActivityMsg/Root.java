package com.home.wanyu.bean.getAreaActivityMsg;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class Root {
    private Result result;

    private String code;

    private boolean Islike;

    private boolean Joined;

    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setIslike(boolean Islike){
        this.Islike = Islike;
    }
    public boolean getIslike(){
        return this.Islike;
    }
    public void setJoined(boolean Joined){
        this.Joined = Joined;
    }
    public boolean getJoined(){
        return this.Joined;
    }
}
