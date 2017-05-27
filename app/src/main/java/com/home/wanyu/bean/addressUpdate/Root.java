package com.home.wanyu.bean.addressUpdate;

/**
 * Created by liuhaidong on 2017/5/26.
 */

public class Root {
    private Result result;

    private String code;

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
}
