package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/22.
 */
//分页查询我的圈子
public class Bean_QZ {

    /**
     * total : 6
     * rows : [{"upid":0,"createTimeString":"2017-05-12 15:02:19","personalId":1,"rname":"","residentialQuartersId":2,"cname":"","avatar":"","userName":"","content":"好好教育孩子","picture":"","likeNum":3,"commentNum":0,"islike":false,"visibleRange":1,"id":4,"categoryId":2},{"upid":0,"createTimeString":"2017-05-17 16:10:54","personalId":1,"rname":"","residentialQuartersId":3,"cname":"","avatar":"","userName":"","content":"Very good","picture":"/static/image/2017517/db8faa274ce44af7a649b39eaaa71ee0.png;","likeNum":0,"commentNum":0,"islike":false,"visibleRange":2,"id":10,"categoryId":2},{"upid":0,"createTimeString":"2017-05-17 20:12:34","personalId":1,"rname":"","residentialQuartersId":1,"cname":"","avatar":"","userName":"","content":"骨灰挺好哦理解","picture":"/static/image/2017517/0c3ea28793794f3aa04cbd13f4526fa0.jpg;/static/image/2017517/2d9b35d0147748e99fab0cbc8d97c1c7.jpg;/static/image/2017517/007f6a27805d49a4907bf8acf546a82f.jpg;/static/image/2017517/3331368e28274bb39537124092f4182f.jpg;","likeNum":0,"commentNum":0,"islike":false,"visibleRange":2,"id":17,"categoryId":2},{"upid":0,"createTimeString":"2017-05-17 20:12:55","personalId":1,"rname":"","residentialQuartersId":1,"cname":"","avatar":"","userName":"","content":"骨灰挺好哦理解","picture":"/static/image/2017517/2469dbd2c2ac44d3a0c25569c91036d1.jpg;/static/image/2017517/f85a469939fd4f2a95ce2749125f6b34.jpg;/static/image/2017517/bd6d9a134b564e6687a6bb581abbc72f.jpg;/static/image/2017517/92cf03ebc50e420b986587bd41df1b93.jpg;","likeNum":0,"commentNum":0,"islike":false,"visibleRange":2,"id":27,"categoryId":2},{"upid":0,"createTimeString":"2017-05-17 20:13:01","personalId":1,"rname":"","residentialQuartersId":1,"cname":"","avatar":"","userName":"","content":"骨灰挺好哦理解","picture":"/static/image/2017517/193bd805c5db4781b874e62ae0bb75f7.jpg;/static/image/2017517/f7840dac82134707b5d3d204b10a40c2.jpg;/static/image/2017517/20223d48db084597b19a41723813c818.jpg;/static/image/2017517/e57c66a5ac744d04979f0c66d625d82d.jpg;","likeNum":1,"commentNum":0,"islike":false,"visibleRange":2,"id":31,"categoryId":2}]
     * colmodel : []
     */
    private String code;
    private String message;
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
         * upid : 0
         * createTimeString : 2017-05-12 15:02:19
         * personalId : 1
         * rname :
         * residentialQuartersId : 2
         * cname :
         * avatar :
         * userName :
         * content : 好好教育孩子
         * picture :
         * likeNum : 3
         * commentNum : 0
         * islike : false
         * visibleRange : 1
         * id : 4
         * categoryId : 2
         */
        private boolean sele;
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
        private Long id;
        private Long categoryId;

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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
    }
}
