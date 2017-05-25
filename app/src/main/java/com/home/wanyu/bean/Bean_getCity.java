package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/18.
 */
//获取城市列表
public class Bean_getCity
{

    /**
     * result : success
     * baseAreaList : [{"level":4,"platMark":100003006023000,"parentId":131,"areaCode":"130681","expand":0,"areaName":"涿州市","id":154,"childrenList":[]},{"level":3,"platMark":100001001000000,"parentId":1,"areaCode":"110000","expand":0,"areaName":"北京市","id":2,"childrenList":[]}]
     * code : 0
     */

    private String result;
    private String code;
    private List<BaseAreaListBean> baseAreaList;

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

    public List<BaseAreaListBean> getBaseAreaList() {
        return baseAreaList;
    }

    public void setBaseAreaList(List<BaseAreaListBean> baseAreaList) {
        this.baseAreaList = baseAreaList;
    }

    public static class BaseAreaListBean {
        /**
         * level : 4
         * platMark : 100003006023000
         * parentId : 131
         * areaCode : 130681
         * expand : 0
         * areaName : 涿州市
         * id : 154
         * childrenList : []
         */

        private int level;
        private long platMark;
        private int parentId;
        private String areaCode;
        private int expand;
        private String areaName;
        private int id;
        private List<?> childrenList;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public long getPlatMark() {
            return platMark;
        }

        public void setPlatMark(long platMark) {
            this.platMark = platMark;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public int getExpand() {
            return expand;
        }

        public void setExpand(int expand) {
            this.expand = expand;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<?> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<?> childrenList) {
            this.childrenList = childrenList;
        }
    }
}
