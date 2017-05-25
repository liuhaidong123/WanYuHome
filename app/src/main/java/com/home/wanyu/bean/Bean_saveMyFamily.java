package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/19.
 */

public class Bean_saveMyFamily {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * code : 0
     */
    private String message;
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
