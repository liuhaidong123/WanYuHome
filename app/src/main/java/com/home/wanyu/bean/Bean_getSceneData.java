package com.home.wanyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/5/11.
 */
//获取单个情景接口
public class Bean_getSceneData
{


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

    /**
     * createTimeString :
     * sceneDistance : 0
     * updateTimeString :
     * repeatMode :
     * sceneName : 回家
     * sceneTime : null
     * oid : 1
     * equipmentList : [{"iconId":1,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"1","roomId":1,"familyId":0,"sceneTaskId":1,"toState":0,"toExtendState":"","name":"开关","iconUrl":"","id":1,"state":1},{"iconId":2,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"1","roomId":1,"familyId":0,"sceneTaskId":2,"toState":1,"toExtendState":"","name":"灯","iconUrl":"","id":2,"state":1},{"iconId":3,"createTimeString":"","serialNumber":"1","updateTimeString":"","extendState":"1","roomId":1,"familyId":0,"sceneTaskId":3,"toState":0,"toExtendState":"","name":"电视","iconUrl":"","id":3,"state":1}]
     * familyId : 0
     * id : 1
     * sceneTimeString :
     * sceneModel : 1
     * sceneState : 1
     */
    private String code;
    private String message;
    private String createTimeString;
    private int sceneDistance;
    private String updateTimeString;
    private String repeatMode;
    private String sceneName;
    private Object sceneTime;
    private long oid;
    private long familyId;
    private long id;
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

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(long familyId) {
        this.familyId = familyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public static class EquipmentListBean implements Serializable{
        /**
         * iconId : 1
         * createTimeString :
         * serialNumber : 1
         * updateTimeString :
         * extendState : 1
         * roomId : 1
         * familyId : 0
         * sceneTaskId : 1
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
        private long familyId;
        private long sceneTaskId;
        private int toState;
        private String toExtendState;
        private String name;
        private String iconUrl;
        private long id;
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

        public long getFamilyId() {
            return familyId;
        }

        public void setFamilyId(long familyId) {
            this.familyId = familyId;
        }

        public long getSceneTaskId() {
            return sceneTaskId;
        }

        public void setSceneTaskId(long sceneTaskId) {
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
