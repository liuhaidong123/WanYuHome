package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/11.
 */
//获取单个房间的信息
public class Bean_getRoomData {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * code : 0
     * Room : {"createTimeString":"","updateTimeString":"","oid":0,"equipmentList":[{"iconId":2,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":8,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"灯灯","iconUrl":"","id":7,"state":0}],"pictures":"","roomName":"有没有","familyId":1,"id":8}
     */
    private String message;
    private String result;
    private String code;
    private RoomBean Room;

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

    public RoomBean getRoom() {
        return Room;
    }

    public void setRoom(RoomBean Room) {
        this.Room = Room;
    }

    public static class RoomBean {
        /**
         * createTimeString :
         * updateTimeString :
         * oid : 0
         * equipmentList : [{"iconId":2,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":8,"familyId":1,"sceneTaskId":0,"toState":0,"toExtendState":"","name":"灯灯","iconUrl":"","id":7,"state":0}]
         * pictures :
         * roomName : 有没有
         * familyId : 1
         * id : 8
         */

        private String createTimeString;
        private String updateTimeString;
        private int oid;
        private String pictures;
        private String roomName;
        private int familyId;
        private int id;
        private List<EquipmentListBean> equipmentList;

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
             * iconId : 2
             * createTimeString :
             * serialNumber : 1
             * updateTimeString :
             * extendState :
             * roomId : 8
             * familyId : 1
             * sceneTaskId : 0
             * toState : 0
             * toExtendState :
             * name : 灯灯
             * iconUrl :
             * id : 7
             * state : 0
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
}
