package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/24.
 */
//他人圈子
public class Bean_otherCircle {

    /**
     * total : 1
     * rows : [{"upid":0,"createTimeString":"2017-05-17 20:13:04","personalId":1,"rname":"","residentialQuartersId":1,"cname":"","avatar":"","userName":"","content":"骨灰挺好哦理解","picture":"/static/image/2017517/0746543a8e994828b098ea2a23d5eb9f.jpg;/static/image/2017517/0f050378af1e4222b1e69568435a587a.jpg;/static/image/2017517/c2662cab6b6c476c8cc95eabcca0c19e.jpg;/static/image/2017517/511fc733ff77454eb9f82653fc1b3683.jpg;","likeNum":1,"commentNum":0,"islike":false,"visibleRange":2,"id":34,"categoryId":2}]
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
        /**
         * upid : 0
         * createTimeString : 2017-05-17 20:13:04
         * personalId : 1
         * rname :
         * residentialQuartersId : 1
         * cname :
         * avatar :
         * userName :
         * content : 骨灰挺好哦理解
         * picture : /static/image/2017517/0746543a8e994828b098ea2a23d5eb9f.jpg;/static/image/2017517/0f050378af1e4222b1e69568435a587a.jpg;/static/image/2017517/c2662cab6b6c476c8cc95eabcca0c19e.jpg;/static/image/2017517/511fc733ff77454eb9f82653fc1b3683.jpg;
         * likeNum : 1
         * commentNum : 0
         * islike : false
         * visibleRange : 2
         * id : 34
         * categoryId : 2
         */

        private int upid;
        private String createTimeString;
        private int personalId;
        private String rname;
        private int residentialQuartersId;
        private String cname;
        private String avatar;
        private String userName;
        private String content;
        private String picture;
        private int likeNum;
        private int commentNum;
        private boolean islike;
        private int visibleRange;
        private int id;
        private int categoryId;

        public int getUpid() {
            return upid;
        }

        public void setUpid(int upid) {
            this.upid = upid;
        }

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

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public int getResidentialQuartersId() {
            return residentialQuartersId;
        }

        public void setResidentialQuartersId(int residentialQuartersId) {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }
}
