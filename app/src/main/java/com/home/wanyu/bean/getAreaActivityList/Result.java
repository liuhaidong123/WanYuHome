package com.home.wanyu.bean.getAreaActivityList;

import java.io.Serializable;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class Result implements Serializable {

    private int over;
    private String createTimeString;

    private long personalId;

    private String user_name;

    private String activityAddress;

    private int likeNum;

    private long activityTelephone;

    private String starttimeString;

    private long id;

    private String activityContent;

    private boolean joined;

    private String activityTheme;

    private String avatar;

    private int participateNumber;

    private boolean islike;

    private long activitypictureId;

    private int comment;

    private int activityNumber;

    private String endtimeString;

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

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public long getActivityTelephone() {
        return activityTelephone;
    }

    public void setActivityTelephone(long activityTelephone) {
        this.activityTelephone = activityTelephone;
    }

    public String getStarttimeString() {
        return starttimeString;
    }

    public void setStarttimeString(String starttimeString) {
        this.starttimeString = starttimeString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public long getActivitypictureId() {
        return activitypictureId;
    }

    public void setActivitypictureId(long activitypictureId) {
        this.activitypictureId = activitypictureId;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getEndtimeString() {
        return endtimeString;
    }

    public void setEndtimeString(String endtimeString) {
        this.endtimeString = endtimeString;
    }


}
