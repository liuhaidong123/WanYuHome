package com.home.wanyu.bean.Express;

/**
 * Created by liuhaidong on 2017/5/26.
 */

public class Result {
    private long personalId;

    private String signTimeString;

    private int personalType;

    private String ename;

    private String expressNumber;

    private int propertyType;

    private long id;

    private String cabinet;

    private String deliveryCode;

    private long expressId;

    public long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(long personalId) {
        this.personalId = personalId;
    }

    public String getSignTimeString() {
        return signTimeString;
    }

    public void setSignTimeString(String signTimeString) {
        this.signTimeString = signTimeString;
    }

    public int getPersonalType() {
        return personalType;
    }

    public void setPersonalType(int personalType) {
        this.personalType = personalType;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public long getExpressId() {
        return expressId;
    }

    public void setExpressId(long expressId) {
        this.expressId = expressId;
    }
}
