package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/9.
 */
//房间设备的一键开关
public class Bean_HomeRoomOnOff {

    /**
     * result : sucess
     * code : 0
     * room : {"oid":3,"equipmentList":[{"iconId":3,"serialNumber":"","roomId":3,"toState":0,"name":"灯3","iconUrl":"","id":3,"state":1}],"pictures":"","roomName":"次卧","familyId":1,"id":3}
     */

    private String result;
    private String code;
    private RoomBean room;

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
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public static class RoomBean {
        /**
         * oid : 3
         * equipmentList : [{"iconId":3,"serialNumber":"","roomId":3,"toState":0,"name":"灯3","iconUrl":"","id":3,"state":1}]
         * pictures :
         * roomName : 次卧
         * familyId : 1
         * id : 3
         */

        private Long oid;
        private String pictures;
        private String roomName;
        private Long familyId;
        private Long id;
        private List<EquipmentListBean> equipmentList;

        public Long getOid() {
            return oid;
        }

        public void setOid(Long oid) {
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

        public Long getFamilyId() {
            return familyId;
        }

        public void setFamilyId(Long familyId) {
            this.familyId = familyId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<EquipmentListBean> getEquipmentList() {
            return equipmentList;
        }

        public void setEquipmentList(List<EquipmentListBean> equipmentList) {
            this.equipmentList = equipmentList;
        }

        public static class EquipmentListBean {
            /**
             * iconId : 3
             * serialNumber :
             * roomId : 3
             * toState : 0
             * name : 灯3
             * iconUrl :
             * id : 3
             * state : 1
             */

            private int iconId;
            private String serialNumber;
            private Long roomId;
            private Long toState;
            private String name;
            private String iconUrl;
            private Long id;
            private Long state;

            public int getIconId() {
                return iconId;
            }

            public void setIconId(int iconId) {
                this.iconId = iconId;
            }

            public String getSerialNumber() {
                return serialNumber;
            }

            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
            }

            public Long getRoomId() {
                return roomId;
            }

            public void setRoomId(Long roomId) {
                this.roomId = roomId;
            }

            public Long getToState() {
                return toState;
            }

            public void setToState(Long toState) {
                this.toState = toState;
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

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getState() {
                return state;
            }

            public void setState(Long state) {
                this.state = state;
            }
        }
    }
}
