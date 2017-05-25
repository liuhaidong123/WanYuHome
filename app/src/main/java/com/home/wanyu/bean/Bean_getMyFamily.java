package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/19.
 */
//获取我的家
public class Bean_getMyFamily
{

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : success
     * mapList : [{"familyId":1,"personalId":1,"familyName":"我创建的家","currentFamilyId":1},{"familyId":4,"personalId":10,"familyName":"风雪创建的家","currentFamilyId":1}]
     * code : 0
     */
    private String message;
    private String result;
    private String code;
    private List<MapListBean> mapList;

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

    public List<MapListBean> getMapList() {
        return mapList;
    }

    public void setMapList(List<MapListBean> mapList) {
        this.mapList = mapList;
    }

    public static class MapListBean {
        /**
         * familyId : 1
         * personalId : 1
         * familyName : 我创建的家
         * currentFamilyId : 1
         */

        private int familyId;
        private int personalId;
        private String familyName;
        private int currentFamilyId;

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public int getPersonalId() {
            return personalId;
        }

        public void setPersonalId(int personalId) {
            this.personalId = personalId;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public int getCurrentFamilyId() {
            return currentFamilyId;
        }

        public void setCurrentFamilyId(int currentFamilyId) {
            this.currentFamilyId = currentFamilyId;
        }
    }
}
