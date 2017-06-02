package com.home.wanyu.bean.getAreaActivityMsg;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class Result {

    private ActivityEntity activityEntity;
    private List<UpVptelist> upVptelist;

    private List<Commentlist> commentlist;

    private List<Activitypicturelist> activitypicturelist;

    private List<ActivityLoglist> activityLoglist;

    public void setUpVptelist(List<UpVptelist> upVptelist){
        this.upVptelist = upVptelist;
    }
    public List<UpVptelist> getUpVptelist(){
        return this.upVptelist;
    }
    public void setCommentlist(List<Commentlist> commentlist){
        this.commentlist = commentlist;
    }
    public List<Commentlist> getCommentlist(){
        return this.commentlist;
    }
    public void setActivitypicturelist(List<Activitypicturelist> activitypicturelist){
        this.activitypicturelist = activitypicturelist;
    }
    public List<Activitypicturelist> getActivitypicturelist(){
        return this.activitypicturelist;
    }
    public void setActivityLoglist(List<ActivityLoglist> activityLoglist){
        this.activityLoglist = activityLoglist;
    }
    public List<ActivityLoglist> getActivityLoglist(){
        return this.activityLoglist;
    }

    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(ActivityEntity activityEntity) {
        this.activityEntity = activityEntity;
    }
}

