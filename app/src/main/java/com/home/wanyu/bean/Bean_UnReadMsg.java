package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/26.
 */

public class Bean_UnReadMsg
{

    /**
     * result : success
     * code : 0
     * hasMessage : false
     */

    private String result;
    private String code;
    private boolean hasMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }
}
