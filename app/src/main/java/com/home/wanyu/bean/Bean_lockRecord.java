package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/23.
 */

public class Bean_lockRecord {

    /**
     * total : 14
     * rows : [{"createTimeString":"2017-05-23 14:01:05","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"","id":14,"userType":0},{"createTimeString":"2017-05-23 13:59:51","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"","id":13,"userType":0},{"createTimeString":"2017-05-23 13:56:48","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"","id":12,"userType":0},{"createTimeString":"2017-05-23 12:00:02","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":11,"userType":0},{"createTimeString":"2017-05-23 12:00:00","personalId":1,"updateTimeString":"","gender":1,"openMode":2,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":10,"userType":0},{"createTimeString":"2017-05-23 11:59:59","personalId":1,"updateTimeString":"","gender":1,"openMode":2,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":9,"userType":0},{"createTimeString":"2017-05-23 11:59:58","personalId":1,"updateTimeString":"","gender":1,"openMode":2,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":8,"userType":0},{"createTimeString":"2017-05-23 11:59:56","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":7,"userType":0},{"createTimeString":"2017-05-23 11:59:55","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":6,"userType":0},{"createTimeString":"2017-05-23 11:59:53","personalId":1,"updateTimeString":"","gender":1,"openMode":1,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","equipmentId":6,"familyId":1,"trueName":"李朋伟","comment":"sdfsdf","id":5,"userType":0}]
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
         * createTimeString : 2017-05-23 14:01:05
         * personalId : 1
         * updateTimeString :
         * gender : 1
         * openMode : 1
         * avatar : /static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg
         * userName : lhd
         * equipmentId : 6
         * familyId : 1
         * trueName : 李朋伟
         * comment :
         * id : 14
         * userType : 0
         */

        private String createTimeString;
        private int personalId;
        private String updateTimeString;
        private int gender;
        private int openMode;
        private String avatar;
        private String userName;
        private int equipmentId;
        private int familyId;
        private String trueName;
        private String comment;
        private int id;
        private int userType;

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

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getOpenMode() {
            return openMode;
        }

        public void setOpenMode(int openMode) {
            this.openMode = openMode;
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

        public int getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(int equipmentId) {
            this.equipmentId = equipmentId;
        }

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
