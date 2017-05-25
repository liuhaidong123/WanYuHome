package com.home.wanyu.bean.getCircleCommentMsg;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class LikeNum {
    private String createTimeString;

    private long personalId;

    private String updateTimeString;

    private long stateId;

    private String avatar;

    private String userName;

    private long id;

    private int upVoteType;

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

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUpVoteType() {
        return upVoteType;
    }

    public void setUpVoteType(int upVoteType) {
        this.upVoteType = upVoteType;
    }
}
