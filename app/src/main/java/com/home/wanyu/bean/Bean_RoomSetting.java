package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/16.
 */

public class Bean_RoomSetting {

    /**
     * result : success
     * code : 0
     * Room : {"createTimeString":"","updateTimeString":"","oid":3,"equipmentList":[],"pictures":"/static/image/2017516/3844e979aafc423294f61076a07e7aca.jpg","roomName":"次卧","familyId":1,"id":3}
     */

    private String result;
    private String code;
    private RoomBean Room;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
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
         * oid : 3
         * equipmentList : []
         * pictures : /static/image/2017516/3844e979aafc423294f61076a07e7aca.jpg
         * roomName : 次卧
         * familyId : 1
         * id : 3
         */

        private String createTimeString;
        private String updateTimeString;
        private int oid;
        private String pictures;
        private String roomName;
        private int familyId;
        private int id;
        private List<?> equipmentList;

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

        public List<?> getEquipmentList() {
            return equipmentList;
        }

        public void setEquipmentList(List<?> equipmentList) {
            this.equipmentList = equipmentList;
        }
    }
}
