package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/15.
 */
//设备添加／修改接口
public class Bean_DeviceAdd_Change {

    /**
     * result : success
     * code : 0
     * Equipment : {"iconId":6,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"门锁2","iconUrl":"","id":6,"state":1}
     */

    private String result;
    private String code;
    private EquipmentBean Equipment;

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

    public EquipmentBean getEquipment() {
        return Equipment;
    }

    public void setEquipment(EquipmentBean Equipment) {
        this.Equipment = Equipment;
    }

    public static class EquipmentBean {
        /**
         * iconId : 6
         * createTimeString :
         * serialNumber : 1
         * updateTimeString :
         * extendState :
         * roomId : 1
         * familyId : 1
         * sceneTaskId : 0
         * toState : 0
         * toExtendState :
         * name : 门锁2
         * iconUrl :
         * id : 6
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
