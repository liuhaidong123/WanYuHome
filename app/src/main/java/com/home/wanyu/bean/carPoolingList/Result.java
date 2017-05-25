package com.home.wanyu.bean.carPoolingList;

import java.io.Serializable;

/**
 * Created by liuhaidong on 2017/5/25.
 */

public class Result implements Serializable{
    private String createTimeString;

    private long personalId;

    private String user_name;

    private int cnumber;

    private String trueName;

    private String end;

    private long  id;

    private long telephone;

    private String avatar;

    private String departureTimeString;

    private int cstate;

    private int ctype;

    private int participateNumber;

    private boolean islike;

    private int comment;

    private String departurePlace;
    private boolean isOrders;

    public boolean isOrders() {
        return isOrders;
    }

    public void setOrders(boolean orders) {
        isOrders = orders;
    }

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getCnumber() {
        return cnumber;
    }

    public void setCnumber(int cnumber) {
        this.cnumber = cnumber;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartureTimeString() {
        return departureTimeString;
    }

    public void setDepartureTimeString(String departureTimeString) {
        this.departureTimeString = departureTimeString;
    }

    public int getCstate() {
        return cstate;
    }

    public void setCstate(int cstate) {
        this.cstate = cstate;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public int getParticipateNumber() {
        return participateNumber;
    }

    public void setParticipateNumber(int participateNumber) {
        this.participateNumber = participateNumber;
    }

    public boolean islike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }
}
