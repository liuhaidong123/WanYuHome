package com.home.wanyu.bean.getCircleCommentMsg;

/**
 * Created by liuhaidong on 2017/6/2.
 */

public class StateEntity {
    private long upid;

    private String createTimeString;

    private long personalId;

    private String updateTimeString;

    private String rname;

    private long residentialQuartersId;

    private String cname;

    private String avatar;

    private String userName;

    private String content;

    private String picture;

    private int likeNum;

    private int commentNum;

    private boolean islike;

    private int visibleRange;

    private long id;

    private long categoryId;

    public long getUpid() {
        return upid;
    }

    public void setUpid(long upid) {
        this.upid = upid;
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

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public long getResidentialQuartersId() {
        return residentialQuartersId;
    }

    public void setResidentialQuartersId(long residentialQuartersId) {
        this.residentialQuartersId = residentialQuartersId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public boolean islike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public int getVisibleRange() {
        return visibleRange;
    }

    public void setVisibleRange(int visibleRange) {
        this.visibleRange = visibleRange;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
