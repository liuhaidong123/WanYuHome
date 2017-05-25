package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/18.
 *///获取家人权限
public class Bean_getFamilyUserPri {

    /**
     * result : success
     * code : 0
     * MyFamily : {"pmsnCtrlAdd":0,"personalId":1,"gender":2,"pmsnCtrl005":0,"pmsnCtrlDevice":1,"pmsnCtrl003":0,"pmsnCtrl004":0,"pmsnCtrl001":0,"trueName":"刘海懂","pmsnCtrl002":0,"pmsnCtrlDel":0,"id":4,"avatar":"/static/image/2017517/33fcf14c9abf477bb8bb36d06c37773e.jpg","pmsnCtrlDoor":1,"userName":"刘海","comment":"18335277251","familyPersonalId":11}
     */

    private String result;
    private String code;
    private MyFamilyBean MyFamily;

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

    public MyFamilyBean getMyFamily() {
        return MyFamily;
    }

    public void setMyFamily(MyFamilyBean MyFamily) {
        this.MyFamily = MyFamily;
    }

    public static class MyFamilyBean {
        /**
         * pmsnCtrlAdd : 0
         * personalId : 1
         * gender : 2
         * pmsnCtrl005 : 0
         * pmsnCtrlDevice : 1
         * pmsnCtrl003 : 0
         * pmsnCtrl004 : 0
         * pmsnCtrl001 : 0
         * trueName : 刘海懂
         * pmsnCtrl002 : 0
         * pmsnCtrlDel : 0
         * id : 4
         * avatar : /static/image/2017517/33fcf14c9abf477bb8bb36d06c37773e.jpg
         * pmsnCtrlDoor : 1
         * userName : 刘海
         * comment : 18335277251
         * familyPersonalId : 11
         */

        private int pmsnCtrlAdd;
        private int personalId;
        private int gender;
        private int pmsnCtrl005;
        private int pmsnCtrlDevice;
        private int pmsnCtrl003;
        private int pmsnCtrl004;
        private int pmsnCtrl001;
        private String trueName;
        private int pmsnCtrl002;
        private int pmsnCtrlDel;
        private int id;
        private String avatar;
        private int pmsnCtrlDoor;
        private String userName;
        private String comment;
        private int familyPersonalId;

        public int getPmsnCtrlAdd() {
            return pmsnCtrlAdd;
        }

        public void setPmsnCtrlAdd(int pmsnCtrlAdd) {
            this.pmsnCtrlAdd = pmsnCtrlAdd;
        }

        public int getPersonalId() {
            return personalId;
        }

        public void setPersonalId(int personalId) {
            this.personalId = personalId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getPmsnCtrl005() {
            return pmsnCtrl005;
        }

        public void setPmsnCtrl005(int pmsnCtrl005) {
            this.pmsnCtrl005 = pmsnCtrl005;
        }

        public int getPmsnCtrlDevice() {
            return pmsnCtrlDevice;
        }

        public void setPmsnCtrlDevice(int pmsnCtrlDevice) {
            this.pmsnCtrlDevice = pmsnCtrlDevice;
        }

        public int getPmsnCtrl003() {
            return pmsnCtrl003;
        }

        public void setPmsnCtrl003(int pmsnCtrl003) {
            this.pmsnCtrl003 = pmsnCtrl003;
        }

        public int getPmsnCtrl004() {
            return pmsnCtrl004;
        }

        public void setPmsnCtrl004(int pmsnCtrl004) {
            this.pmsnCtrl004 = pmsnCtrl004;
        }

        public int getPmsnCtrl001() {
            return pmsnCtrl001;
        }

        public void setPmsnCtrl001(int pmsnCtrl001) {
            this.pmsnCtrl001 = pmsnCtrl001;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getPmsnCtrl002() {
            return pmsnCtrl002;
        }

        public void setPmsnCtrl002(int pmsnCtrl002) {
            this.pmsnCtrl002 = pmsnCtrl002;
        }

        public int getPmsnCtrlDel() {
            return pmsnCtrlDel;
        }

        public void setPmsnCtrlDel(int pmsnCtrlDel) {
            this.pmsnCtrlDel = pmsnCtrlDel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPmsnCtrlDoor() {
            return pmsnCtrlDoor;
        }

        public void setPmsnCtrlDoor(int pmsnCtrlDoor) {
            this.pmsnCtrlDoor = pmsnCtrlDoor;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getFamilyPersonalId() {
            return familyPersonalId;
        }

        public void setFamilyPersonalId(int familyPersonalId) {
            this.familyPersonalId = familyPersonalId;
        }
    }
}
