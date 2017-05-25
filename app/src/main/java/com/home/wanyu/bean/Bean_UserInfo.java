package com.home.wanyu.bean;

import java.io.Serializable;

/**
 * Created by wanyu on 2017/5/17.
 */
//用户个人信息获取
public class Bean_UserInfo {

    /**
     * result : success
     * code : 0
     * Personal : {"qq":"","createTimeString":"2017-05-11 08:56:26","updateTimeString":"","gender":0,"idCard":"","telephone":18335277251,"avatar":"","userMame":"liuhaidong","familyId":1,"trueName":"刘海懂","weixin":"","id":11,"userType":0,"email":"","authentication":0}
     */

    private String result;
    private String code;
    private PersonalBean Personal;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PersonalBean getPersonal() {
        return Personal;
    }

    public void setPersonal(PersonalBean Personal) {
        this.Personal = Personal;
    }

    public static class PersonalBean implements Serializable{
        /**
         * qq :
         * createTimeString : 2017-05-11 08:56:26
         * updateTimeString :
         * gender : 0
         * idCard :
         * telephone : 18335277251
         * avatar :
         * userMame : liuhaidong
         * familyId : 1
         * trueName : 刘海懂
         * weixin :
         * id : 11
         * userType : 0
         * email :
         * authentication : 0
         */

        private String qq;
        private String createTimeString;
        private String updateTimeString;
        private int gender;
        private String idCard;
        private long telephone;
        private String avatar;
        private String userName;
        private int familyId;
        private String trueName;
        private String weixin;
        private int id;
        private int userType;
        private String email;
        private int authentication;

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
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

        public void setUserName(String userMame) {
            this.userName = userMame;
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

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
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
    }
}
