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
     * personalList : [{"gender":2,"idCard":"","myFamilyId":4,"trueName":"刘海懂","id":11,"email":"","authentication":0,"qq":"","telephone":18335277251,"avatar":"/static/image/2017517/33fcf14c9abf477bb8bb36d06c37773e.jpg","userName":"刘海","familyId":1,"weixin":"","comment":"18335277251","userType":0,"familyPersonalId":11}]
     */
    private String message;
    private String result;
    private String code;

    public boolean isSele() {
        return Sele;
    }

    public void setSele(boolean sele) {
        Sele = sele;
    }

    private boolean Sele;
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
        public boolean isSele() {
            return sele;
        }

        public void setSele(boolean sele) {
            this.sele = sele;
        }

        /**
         * gender : 2
         * idCard :
         * myFamilyId : 4
         * trueName : 刘海懂
         * id : 11
         * email :
         * authentication : 0
         * qq :
         * telephone : 18335277251
         * avatar : /static/image/2017517/33fcf14c9abf477bb8bb36d06c37773e.jpg
         * userName : 刘海
         * familyId : 1
         * weixin :
         * comment : 18335277251
         * userType : 0
         * familyPersonalId : 11
         */
        private boolean sele;
        private int gender;
        private String idCard;
        private int myFamilyId;
        private String trueName;
        private int id;
        private String email;
        private int authentication;
        private String qq;
        private long telephone;
        private String avatar;
        private String userName;
        private int familyId;
        private String weixin;
        private String comment;
        private int userType;
        private int familyPersonalId;

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
