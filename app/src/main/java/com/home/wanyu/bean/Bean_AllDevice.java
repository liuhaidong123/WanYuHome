package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/15.
 */
//获取所有设备接口
public class Bean_AllDevice {

    /**
     * result : success
     * code : 0
     * equipmentList : [{"iconId":7,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":0,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"多","iconUrl":"","id":7,"state":0},{"iconId":6,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"门锁","iconUrl":"","id":6,"state":1},{"iconId":5,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"空调","iconUrl":"","id":5,"state":1},{"iconId":4,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"窗帘","iconUrl":"","id":4,"state":1},{"iconId":3,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"电视","iconUrl":"","id":3,"state":0},{"iconId":2,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"1","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"灯","iconUrl":"","id":2,"state":1},{"iconId":1,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"1","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"开关","iconUrl":"","id":1,"state":0}]
     */

    private String result;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private List<EquipmentListBean> equipmentList;

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

    public List<EquipmentListBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentListBean> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public static class EquipmentListBean implements Serializable{
        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        /**
         * iconId : 7
         * createTimeString :
         * serialNumber : 1
         * updateTimeString :
         * extendState :
         * roomId : 0
         * familyId : 1
         * sceneTaskId : 0
         * toState : 0
         * toExtendState :
         * name : 多
         * iconUrl :
         * id : 7
         * state : 0
         */
        private boolean flag;
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
