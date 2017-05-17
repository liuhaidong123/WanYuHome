package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/9.
 */

public class Bean_HomeSceneOnOff {

    /**
     * result : sucess
     * code : 0
     * scene : {"sceneDistance":0,"sceneName":"回家","sceneTime":null,"oid":1,"equipmentList":[{"iconId":1,"serialNumber":"","roomId":1,"toState":0,"name":"开关","iconUrl":"","id":1,"state":0},{"iconId":2,"serialNumber":"","roomId":2,"toState":1,"name":"灯","iconUrl":"","id":2,"state":1},{"iconId":3,"serialNumber":"","roomId":3,"toState":0,"name":"电视","iconUrl":"","id":3,"state":0}],"familyId":0,"id":1,"sceneTimeString":"","sceneModel":1,"sceneState":1}
     */

    private String result;
    private String code;
    private SceneBean scene;

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
        return scene;
    }

    public void setScene(SceneBean scene) {
        this.scene = scene;
    }

    public static class SceneBean {
        /**
         * sceneDistance : 0
         * sceneName : 回家
         * sceneTime : null
         * oid : 1
         * equipmentList : [{"iconId":1,"serialNumber":"","roomId":1,"toState":0,"name":"开关","iconUrl":"","id":1,"state":0},{"iconId":2,"serialNumber":"","roomId":2,"toState":1,"name":"灯","iconUrl":"","id":2,"state":1},{"iconId":3,"serialNumber":"","roomId":3,"toState":0,"name":"电视","iconUrl":"","id":3,"state":0}]
         * familyId : 0
         * id : 1
         * sceneTimeString :
         * sceneModel : 1
         * sceneState : 1
         */

        private Long sceneDistance;
        private String sceneName;
        private Object sceneTime;
        private Long oid;
        private Long familyId;
        private Long id;
        private String sceneTimeString;
        private Long sceneModel;
        private Long sceneState;
        private List<EquipmentListBean> equipmentList;

        public Long getSceneDistance() {
            return sceneDistance;
        }

        public void setSceneDistance(Long sceneDistance) {
            this.sceneDistance = sceneDistance;
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

        public static class EquipmentListBean {
            /**
             * iconId : 1
             * serialNumber :
             * roomId : 1
             * toState : 0
             * name : 开关
             * iconUrl :
             * id : 1
             * state : 0
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
