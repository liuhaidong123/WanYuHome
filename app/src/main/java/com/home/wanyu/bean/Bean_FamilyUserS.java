package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/16.
 */

public class Bean_FamilyUserS {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * code : 0
     * personalList : [{"qq":"123456","gender":1,"idCard":"123456","telephone":13717883005,"avatar":"","userMame":"lipengwei","familyId":1,"trueName":"李朋伟","weixin":"123456","id":1,"userType":0,"email":"123456@123.com","authentication":0},{"qq":"","gender":0,"idCard":"","telephone":18782931356,"avatar":"","userMame":"123","familyId":1,"trueName":"13","weixin":"","id":10,"userType":1,"email":"","authentication":0},{"qq":"","gender":0,"idCard":"","telephone":18335277251,"avatar":"","userMame":"liuhaidong","familyId":1,"trueName":"刘海懂","weixin":"","id":11,"userType":2,"email":"","authentication":0}]
     */
    private String message;
    private String result;
    private String code;
    private List<PersonalListBean> personalList;

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

    public List<PersonalListBean> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<PersonalListBean> personalList) {
        this.personalList = personalList;
    }

    public static class PersonalListBean implements Serializable{
        /**
         * qq : 123456
         * gender : 1
         * idCard : 123456
         * telephone : 13717883005
         * avatar :
         * userMame : lipengwei
         * familyId : 1
         * trueName : 李朋伟
         * weixin : 123456
         * id : 1
         * userType : 0
         * email : 123456@123.com
         * authentication : 0
         */

        private String qq;
        private int gender;
        private String idCard;
        private long telephone;
        private String avatar;
        private String userMame;
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

        public String getUserMame() {
            return userMame;
        }

        public void setUserMame(String userMame) {
            this.userMame = userMame;
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
