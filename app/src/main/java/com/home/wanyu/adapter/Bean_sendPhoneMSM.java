package com.home.wanyu.adapter;

import java.io.Serializable;

/**
 * Created by wanyu on 2017/5/18.
 */

public class Bean_sendPhoneMSM
{

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * homePersonal : {"createTimeString":"","gender":2,"idCard":"","myFamilyId":0,"trueName":"3","id":3,"email":"","authentication":0,"qq":"","updateTimeString":"","telephone":13717883007,"avatar":"","userName":"3","familyId":0,"weixin":"","comment":"","userType":0,"familyPersonalId":0}
     * code : 0
     */
    private String message;
    private String result;
    private HomePersonalBean homePersonal;
    private String code;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HomePersonalBean getHomePersonal() {
        return homePersonal;
    }

    public void setHomePersonal(HomePersonalBean homePersonal) {
        this.homePersonal = homePersonal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class HomePersonalBean implements Serializable{
        /**
         * createTimeString :
         * gender : 2
         * idCard :
         * myFamilyId : 0
         * trueName : 3
         * id : 3
         * email :
         * authentication : 0
         * qq :
         * updateTimeString :
         * telephone : 13717883007
         * avatar :
         * userName : 3
         * familyId : 0
         * weixin :
         * comment :
         * userType : 0
         * familyPersonalId : 0
         */

        private String createTimeString;
        private int gender;
        private String idCard;
        private int myFamilyId;
        private String trueName;
        private int id;
        private String email;
        private int authentication;
        private String qq;
        private String updateTimeString;
        private long telephone;
        private String avatar;
        private String userName;
        private int familyId;
        private String weixin;
        private String comment;
        private int userType;
        private int familyPersonalId;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getMyFamilyId() {
            return myFamilyId;
        }

        public void setMyFamilyId(int myFamilyId) {
            this.myFamilyId = myFamilyId;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getAuthentication() {
            return authentication;
        }

        public void setAuthentication(int authentication) {
            this.authentication = authentication;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public long getTelephone() {
            return telephone;
        }

        public void setTelephone(long telephone) {
            this.telephone = telephone;
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

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getFamilyPersonalId() {
            return familyPersonalId;
        }

        public void setFamilyPersonalId(int familyPersonalId) {
            this.familyPersonalId = familyPersonalId;
        }
    }
}
