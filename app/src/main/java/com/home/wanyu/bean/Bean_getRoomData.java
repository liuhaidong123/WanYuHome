package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/11.
 */
//获取单个房间的信息
public class Bean_getRoomData {
    /**
     * code : 10000
     * message : 您还没有登录！
     * createTimeString :
     * updateTimeString :
     * oid : 1
     * equipmentList : [{"iconId":1,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"开关","iconUrl":"","id":1,"state":1},{"iconId":2,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"灯","iconUrl":"","id":2,"state":1},{"iconId":3,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"电视","iconUrl":"","id":3,"state":1},{"iconId":4,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"窗帘","iconUrl":"","id":4,"state":1},{"iconId":5,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"空调","iconUrl":"","id":5,"state":1},{"iconId":6,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"门锁","iconUrl":"","id":6,"state":1}]
     * pictures :
     * roomName : 客厅
     * familyId : 1
     * id : 1
     */

    private String code;
    private String message;
    private String createTimeString;
    private String updateTimeString;
    private int oid;
    private String pictures;
    private String roomName;
    private int familyId;
    private int id;
    private List<EquipmentListBean> equipmentList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EquipmentListBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentListBean> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public static class EquipmentListBean implements Serializable{
        /**
         * iconId : 1
         * createTimeString :
         * serialNumber : 1
         * updateTimeString :
         * extendState :
         * roomId : 1
         * familyId : 0
         * sceneTaskId : 0
         * toState : 0
         * toExtendState :
         * name : 开关
         * iconUrl :
         * id : 1
         * state : 1
         */

        private int iconId;
        private String createTimeString;
        private String serialNumber;
        private String updateTimeString;
        private String extendState;
        private int roomId;
        private int familyId;
        private int sceneTaskId;
        private int toState;
        private String toExtendState;
        private String name;
        private String iconUrl;
        private int id;
        private int state;

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public String getExtendState() {
            return extendState;
        }

        public void setExtendState(String extendState) {
            this.extendState = extendState;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public int getSceneTaskId() {
            return sceneTaskId;
        }

        public void setSceneTaskId(int sceneTaskId) {
            this.sceneTaskId = sceneTaskId;
        }

        public int getToState() {
            return toState;
        }

        public void setToState(int toState) {
            this.toState = toState;
        }

        public String getToExtendState() {
            return toExtendState;
        }

        public void setToExtendState(String toExtendState) {
            this.toExtendState = toExtendState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
