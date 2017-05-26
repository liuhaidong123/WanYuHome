package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/26.
 */
//注册
public class Bean_Register
{

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * personalId : 18
     * code : 0
     * token : 152F79C4A367F3480223B3C7FE101049
     */
    private String message;
    private String result;
    private int personalId;
    private String code;
    private String token;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
