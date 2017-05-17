package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/12.
 */
//情景添加修改返回的细腻
public class Bean_sceneSetting {

    /**
     * result : success
     * code : 0
     * Scene : {"createTimeString":"","sceneDistance":100,"updateTimeString":"","repeatMode":"","sceneName":"离家","sceneTime":null,"oid":2,"equipmentList":[{"iconId":4,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":4,"toState":0,"toExtendState":"","name":"窗帘","iconUrl":"","id":4,"state":0},{"iconId":5,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":5,"toState":0,"toExtendState":"","name":"空调","iconUrl":"","id":5,"state":1},{"iconId":6,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":6,"toState":0,"toExtendState":"","name":"门锁","iconUrl":"","id":6,"state":1}],"familyId":0,"id":2,"sceneTimeString":"","sceneModel":3,"sceneState":1}
     */

    private String result;
    private String code;
    private SceneBean Scene;

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

    public SceneBean getScene() {
        return Scene;
    }

    public void setScene(SceneBean Scene) {
        this.Scene = Scene;
    }

    public static class SceneBean {
        /**
         * createTimeString :
         * sceneDistance : 100
         * updateTimeString :
         * repeatMode :
         * sceneName : 离家
         * sceneTime : null
         * oid : 2
         * equipmentList : [{"iconId":4,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":4,"toState":0,"toExtendState":"","name":"窗帘","iconUrl":"","id":4,"state":0},{"iconId":5,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":5,"toState":0,"toExtendState":"","name":"空调","iconUrl":"","id":5,"state":1},{"iconId":6,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"","roomId":1,"familyId":0,"sceneTaskId":6,"toState":0,"toExtendState":"","name":"门锁","iconUrl":"","id":6,"state":1}]
         * familyId : 0
         * id : 2
         * sceneTimeString :
         * sceneModel : 3
         * sceneState : 1
         */

        private String createTimeString;
        private int sceneDistance;
        private String updateTimeString;
        private String repeatMode;
        private String sceneName;
        private Object sceneTime;
        private int oid;
        private int familyId;
        private int id;
        private String sceneTimeString;
        private int sceneModel;
        private int sceneState;
        private List<EquipmentListBean> equipmentList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getSceneDistance() {
            return sceneDistance;
        }

        public void setSceneDistance(int sceneDistance) {
            this.sceneDistance = sceneDistance;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public String getRepeatMode() {
            return repeatMode;
        }

        public void setRepeatMode(String repeatMode) {
            this.repeatMode = repeatMode;
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

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
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

        public String getSceneTimeString() {
            return sceneTimeString;
        }

        public void setSceneTimeString(String sceneTimeString) {
            this.sceneTimeString = sceneTimeString;
        }

        public int getSceneModel() {
            return sceneModel;
        }

        public void setSceneModel(int sceneModel) {
            this.sceneModel = sceneModel;
        }

        public int getSceneState() {
            return sceneState;
        }

        public void setSceneState(int sceneState) {
            this.sceneState = sceneState;
        }

        public List<EquipmentListBean> getEquipmentList() {
            return equipmentList;
        }

        public void setEquipmentList(List<EquipmentListBean> equipmentList) {
            this.equipmentList = equipmentList;
        }

        public static class EquipmentListBean {
            /**
             * iconId : 4
             * createTimeString :
             * serialNumber : 1
             * updateTimeString :
             * extendState :
             * roomId : 1
             * familyId : 0
             * sceneTaskId : 4
             * toState : 0
             * toExtendState :
             * name : 窗帘
             * iconUrl :
             * id : 4
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
