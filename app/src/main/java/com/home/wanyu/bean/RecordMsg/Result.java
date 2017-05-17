package com.home.wanyu.bean.RecordMsg;

/**
 * Created by liuhaidong on 2017/5/16.
 */

public class Result {
    private String createTimeString;

    private long personalId;

    private String updateTimeString;

    private long contactTelephone;

    private String contactName;

    private int repairType;

    private String picture;

    private String contactAddress;

    private String details;

    private long id;

    private int state;

    private String processingTimeString;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(long personalId) {
        this.personalId = personalId;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public long getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(long contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getRepairType() {
        return repairType;
    }

    public void setRepairType(int repairType) {
        this.repairType = repairType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProcessingTimeString() {
        return processingTimeString;
    }

    public void setProcessingTimeString(String processingTimeString) {
        this.processingTimeString = processingTimeString;
    }
}
