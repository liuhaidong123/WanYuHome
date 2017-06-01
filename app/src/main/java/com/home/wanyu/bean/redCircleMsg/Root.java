package com.home.wanyu.bean.redCircleMsg;

/**
 * Created by liuhaidong on 2017/5/27.
 */

public class Root {
    private String result;

    private String code;

    private boolean hasMessage;

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setHasMessage(boolean hasMessage){
        this.hasMessage = hasMessage;
    }
    public boolean getHasMessage(){
        return this.hasMessage;
    }
}
