package com.home.wanyu.bean.carPoolingMsg;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/25.
 */

public class Result {
    private CarpoolingEntity carpoolingEntity;

    private List<Commentlist> commentlist;

    private boolean IsLike;

    public void setCommentlist(List<Commentlist> commentlist){
        this.commentlist = commentlist;
    }
    public List<Commentlist> getCommentlist(){
        return this.commentlist;
    }
    public void setIsLike(boolean IsLike){
        this.IsLike = IsLike;
    }
    public boolean getIsLike(){
        return this.IsLike;
    }

    public CarpoolingEntity getCarpoolingEntity() {
        return carpoolingEntity;
    }

    public void setCarpoolingEntity(CarpoolingEntity carpoolingEntity) {
        this.carpoolingEntity = carpoolingEntity;
    }


}
