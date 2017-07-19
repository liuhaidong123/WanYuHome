package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/17.
 */
//个人信息修改
public class Bean_changeUserInfo {

    /**
     * result : success
     * code : 0
     */

    private String result;
    private String code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private String Message;
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
