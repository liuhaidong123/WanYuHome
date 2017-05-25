package com.home.wanyu.bean.carPoolingMsg;

/**
 * Created by liuhaidong on 2017/5/25.
 */

public class Commentlist {

    private String createTimeString;

    private long personalId;

    private String avatar;

    private String coverPersonalName;

    private String userName;

    private String content;

    private long coverPersonalId;

    private int commentType;

    private long id;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoverPersonalName() {
        return coverPersonalName;
    }

    public void setCoverPersonalName(String coverPersonalName) {
        this.coverPersonalName = coverPersonalName;
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

    public long getCoverPersonalId() {
        return coverPersonalId;
    }

    public void setCoverPersonalId(long coverPersonalId) {
        this.coverPersonalId = coverPersonalId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
