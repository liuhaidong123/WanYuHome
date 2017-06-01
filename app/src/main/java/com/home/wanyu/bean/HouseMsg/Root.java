package com.home.wanyu.bean.HouseMsg;

/**
 * Created by liuhaidong on 2017/5/27.
 */

public class Root {
    private Result result;

    private String code;

    private int Recordnumber;

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
    public void setRecordnumber(int Recordnumber){
        this.Recordnumber = Recordnumber;
    }
    public int getRecordnumber(){
        return this.Recordnumber;
    }
}
