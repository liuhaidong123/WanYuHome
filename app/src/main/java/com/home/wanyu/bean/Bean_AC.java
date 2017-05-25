package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/22.
 */
//我的活动
public class Bean_AC
{

    /**
     * total : 2
     * rows : [{"createTimeString":"2017-05-05 16:15:52","personalId":1,"residentialQuartersId":1,"user_name":"","activityAddress":"地点在哪","starttime":null,"likeNum":0,"activityTelephone":0,"starttimeString":"","id":2,"activityContent":"内容啊","joined":false,"activityTheme":"活动主题1","avatar":"","participateNumber":1,"islike":false,"visibleRange":0,"activitypictureId":0,"comment":2,"time":null,"activityNumber":12,"activityType":0,"endtimeString":"2017-05-05 16:15:30"},{"createTimeString":"2017-05-05 16:14:59","personalId":1,"residentialQuartersId":1,"user_name":"","activityAddress":"活动地点","starttime":null,"likeNum":0,"activityTelephone":0,"starttimeString":"","id":1,"activityContent":"活动内容","joined":false,"activityTheme":"活动主题","avatar":"","participateNumber":3,"islike":false,"visibleRange":1,"activitypictureId":0,"comment":0,"time":null,"activityNumber":5,"activityType":1,"endtimeString":"2017-05-05 16:14:22"}]
     * colmodel : []
     */

    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean {
        public boolean isSele() {
            return sele;
        }

        public void setSele(boolean sele) {
            this.sele = sele;
        }

        /**
         * createTimeString : 2017-05-05 16:15:52
         * personalId : 1
         * residentialQuartersId : 1
         * user_name :
         * activityAddress : 地点在哪
         * starttime : null
         * likeNum : 0
         * activityTelephone : 0
         * starttimeString :
         * id : 2
         * activityContent : 内容啊
         * joined : false
         * activityTheme : 活动主题1
         * avatar :
         * participateNumber : 1
         * islike : false
         * visibleRange : 0
         * activitypictureId : 0
         * comment : 2
         * time : null
         * activityNumber : 12
         * activityType : 0
         * endtimeString : 2017-05-05 16:15:30
         */
        private boolean sele;
        private String createTimeString;
        private int personalId;
        private int residentialQuartersId;
        private String user_name;
        private String activityAddress;
        private Object starttime;
        private int likeNum;
        private int activityTelephone;
        private String starttimeString;
        private int id;
        private String activityContent;
        private boolean joined;
        private String activityTheme;
        private String avatar;
        private int participateNumber;
        private boolean islike;
        private int visibleRange;
        private int activitypictureId;
        private int comment;
        private Object time;
        private int activityNumber;
        private int activityType;
        private String endtimeString;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getPersonalId() {
            return personalId;
        }

        public void setPersonalId(int personalId) {
            this.personalId = personalId;
        }

        public int getResidentialQuartersId() {
            return residentialQuartersId;
        }

        public void setResidentialQuartersId(int residentialQuartersId) {
            this.residentialQuartersId = residentialQuartersId;
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

        public Object getStarttime() {
            return starttime;
        }

        public void setStarttime(Object starttime) {
            this.starttime = starttime;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getActivityTelephone() {
            return activityTelephone;
        }

        public void setActivityTelephone(int activityTelephone) {
            this.activityTelephone = activityTelephone;
        }

        public String getStarttimeString() {
            return starttimeString;
        }

        public void setStarttimeString(String starttimeString) {
            this.starttimeString = starttimeString;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public boolean isIslike() {
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

        public int getActivitypictureId() {
            return activitypictureId;
        }

        public void setActivitypictureId(int activitypictureId) {
            this.activitypictureId = activitypictureId;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public int getActivityNumber() {
            return activityNumber;
        }

        public void setActivityNumber(int activityNumber) {
            this.activityNumber = activityNumber;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getEndtimeString() {
            return endtimeString;
        }

        public void setEndtimeString(String endtimeString) {
            this.endtimeString = endtimeString;
        }
    }
}
