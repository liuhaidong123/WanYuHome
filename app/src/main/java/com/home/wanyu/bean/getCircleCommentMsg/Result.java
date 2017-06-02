package com.home.wanyu.bean.getCircleCommentMsg;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class Result {

    private StateEntity stateEntity;

    private List<Comment> comment;

    private List<LikeNum> likeNum;

    public StateEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public void setComment(List<Comment> comment){
        this.comment = comment;
    }
    public List<Comment> getComment(){
        return this.comment;
    }

    public List<LikeNum> getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(List<LikeNum> likeNum) {
        this.likeNum = likeNum;
    }
}
