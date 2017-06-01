package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/17.
 */

public class Bean_changeHouseInfo {

    /**
     * result : success
     * code : 0
     */
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private String result;
    private String code;

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
}
