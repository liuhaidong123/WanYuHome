package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/24.
 */

public class Bean_otherPersonInfo {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * code : 0
     * Personal : {"gender":1,"myFamilyId":0,"trueName":"李朋伟","id":1,"authentication":0,"telephone":15537570815,"avatar":"/static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg","userName":"lhd","familyId":1,"comment":"","userType":0,"familyPersonalId":0}
     */
    private String message;
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

    public static class PersonalBean {
        /**
         * gender : 1
         * myFamilyId : 0
         * trueName : 李朋伟
         * id : 1
         * authentication : 0
         * telephone : 15537570815
         * avatar : /static/image/2017519/506772f3f17848259b1b89192d7b00aa.jpg
         * userName : lhd
         * familyId : 1
         * comment :
         * userType : 0
         * familyPersonalId : 0
         */

        private int gender;
        private int myFamilyId;
        private String trueName;
        private int id;
        private int authentication;
        private long telephone;
        private String avatar;
        private String userName;
        private int familyId;
        private String comment;
        private int userType;
        private int familyPersonalId;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
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

        public int getAuthentication() {
            return authentication;
        }

        public void setAuthentication(int authentication) {
            this.authentication = authentication;
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
