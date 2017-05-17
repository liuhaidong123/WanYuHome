package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/8.
 */
//获取情景及房间
public class Bean_SceneAndRoom {
    /**
     * result : success
     * code : 0
     * sceneList : [{"createTimeString":"","sceneDistance":0,"updateTimeString":"","sceneName":"回家","sceneTime":null,"oid":1,"equipmentList":[{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":1,"state":0},{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":2,"state":1},{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":3,"state":0}],"familyId":0,"id":1,"sceneTimeString":"","sceneModel":1,"sceneState":1},{"createTimeString":"","sceneDistance":0,"updateTimeString":"","sceneName":"离家","sceneTime":null,"oid":2,"equipmentList":[],"familyId":0,"id":2,"sceneTimeString":"","sceneModel":1,"sceneState":1},{"createTimeString":"","sceneDistance":0,"updateTimeString":"","sceneName":"起床","sceneTime":null,"oid":3,"equipmentList":[],"familyId":0,"id":3,"sceneTimeString":"","sceneModel":1,"sceneState":1},{"createTimeString":"","sceneDistance":0,"updateTimeString":"","sceneName":"睡觉","sceneTime":null,"oid":4,"equipmentList":[],"familyId":0,"id":4,"sceneTimeString":"","sceneModel":1,"sceneState":1},{"createTimeString":"","sceneDistance":0,"updateTimeString":"","sceneName":"休闲","sceneTime":null,"oid":5,"equipmentList":[],"familyId":0,"id":5,"sceneTimeString":"","sceneModel":1,"sceneState":1}]
     * roomList : [{"createTimeString":"","updateTimeString":"","oid":3,"equipmentList":[{"iconId":3,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":3,"name":"灯3","iconUrl":"","id":3,"state":1}],"pictures":"","roomName":"次卧","familyId":1,"id":3},{"createTimeString":"","updateTimeString":"","oid":2,"equipmentList":[{"iconId":2,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":2,"name":"灯2","iconUrl":"","id":2,"state":0}],"pictures":"","roomName":"主卧","familyId":1,"id":2},{"createTimeString":"","updateTimeString":"","oid":1,"equipmentList":[{"iconId":1,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":1,"name":"灯1","iconUrl":"","id":1,"state":0}],"pictures":"","roomName":"客厅","familyId":1,"id":1}]
     */

    private String result;
    private String code;
    private List<SceneListBean> sceneList;
    private List<RoomListBean> roomList;

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

    public List<SceneListBean> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<SceneListBean> sceneList) {
        this.sceneList = sceneList;
    }

    public List<RoomListBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomListBean> roomList) {
        this.roomList = roomList;
    }

    public static class SceneListBean {
        /**
         * createTimeString :
         * sceneDistance : 0
         * updateTimeString :
         * sceneName : 回家
         * sceneTime : null
         * oid : 1
         * equipmentList : [{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":1,"state":0},{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":2,"state":1},{"iconId":0,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":0,"name":"","iconUrl":"","id":3,"state":0}]
         * familyId : 0
         * id : 1
         * sceneTimeString :
         * sceneModel : 1
         * sceneState : 1
         */

        private String createTimeString;
        private Long sceneDistance;
        private String updateTimeString;
        private String sceneName;
        private Object sceneTime;
        private Long oid;
        private Long familyId;
        private Long id;
        private String sceneTimeString;
        private Long sceneModel;
        private Long sceneState;
        private List<EquipmentListBean> equipmentList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public Long getSceneDistance() {
            return sceneDistance;
        }

        public void setSceneDistance(Long sceneDistance) {
            this.sceneDistance = sceneDistance;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public Object getSceneTime() {
            return sceneTime;
        }

        public void setSceneTime(Object sceneTime) {
            this.sceneTime = sceneTime;
        }

        public Long getOid() {
            return oid;
        }

        public void setOid(Long oid) {
            this.oid = oid;
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

        public String getSceneTimeString() {
            return sceneTimeString;
        }

        public void setSceneTimeString(String sceneTimeString) {
            this.sceneTimeString = sceneTimeString;
        }

        public Long getSceneModel() {
            return sceneModel;
        }

        public void setSceneModel(Long sceneModel) {
            this.sceneModel = sceneModel;
        }

        public Long getSceneState() {
            return sceneState;
        }

        public void setSceneState(Long sceneState) {
            this.sceneState = sceneState;
        }

        public List<EquipmentListBean> getEquipmentList() {
            return equipmentList;
        }

        public void setEquipmentList(List<EquipmentListBean> equipmentList) {
            this.equipmentList = equipmentList;
        }

        public static class EquipmentListBean implements Serializable{
            /**
             * iconId : 0
             * createTimeString :
             * serialNumber :
             * updateTimeString :
             * roomId : 0
             * name :
             * iconUrl :
             * id : 1
             * state : 0
             */
            private String toExtendState;
            private int iconId;

            public String getToExtendState() {
                return toExtendState;
            }

            public void setToExtendState(String toExtendState) {
                this.toExtendState = toExtendState;
            }

            private String createTimeString;
            private String serialNumber;
            private String updateTimeString;
            private Long roomId;
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

            public Long getRoomId() {
                return roomId;
            }

            public void setRoomId(Long roomId) {
                this.roomId = roomId;
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

    public static class RoomListBean {
        /**
         * createTimeString :
         * updateTimeString :
         * oid : 3
         * equipmentList : [{"iconId":3,"createTimeString":"","serialNumber":"","updateTimeString":"","roomId":3,"name":"灯3","iconUrl":"","id":3,"state":1}]
         * pictures :
         * roomName : 次卧
         * familyId : 1
         * id : 3
         */

        private String createTimeString;
        private String updateTimeString;
        private Long oid;
        private String pictures;
        private String roomName;
        private Long familyId;
        private Long id;
        private List<EquipmentListBeanX> equipmentList;

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

        public List<EquipmentListBeanX> getEquipmentList() {
            return equipmentList;
        }

        public void setEquipmentList(List<EquipmentListBeanX> equipmentList) {
            this.equipmentList = equipmentList;
        }

        public static class EquipmentListBeanX implements Serializable{
            public String getToExtendState() {
                return toExtendState;
            }

            public void setToExtendState(String toExtendState) {
                this.toExtendState = toExtendState;
            }

            /**
             * iconId : 3
             * createTimeString :
             * serialNumber :
             * updateTimeString :
             * roomId : 3
             * name : 灯3
             * iconUrl :
             * id : 3
             * state : 1
             */
            private String toExtendState;
            private int iconId;
            private String createTimeString;
            private String serialNumber;
            private String updateTimeString;
            private Long roomId;
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

            public Long getRoomId() {
                return roomId;
            }

            public void setRoomId(Long roomId) {
                this.roomId = roomId;
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
