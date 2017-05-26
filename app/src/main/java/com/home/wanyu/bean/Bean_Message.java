package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/26.
 */

public class Bean_Message {

    /**
     * total : 5
     * rows : [{"createTimeString":"2017-05-25 10:57:01","msgType":10,"targetId":0,"isRead":false,"avatar":"","title":"","fromId":0,"content":"{\"familyId\":1,\"personalId\":1,\"equipmentId\":6}","familyId":0,"referId":2,"id":3,"operation":3},{"createTimeString":"2017-05-23 17:25:40","msgType":10,"targetId":0,"isRead":false,"avatar":"","title":"","fromId":0,"content":"{\"familyId\":1,\"personalId\":1,\"equipmentId\":6}","familyId":0,"referId":3,"id":2,"operation":26},{"createTimeString":"2017-05-23 17:23:23","msgType":10,"targetId":0,"isRead":true,"avatar":"","title":"","fromId":0,"content":"{\"familyId\":1,\"personalId\":1,\"equipmentId\":6}","familyId":0,"referId":2,"id":1,"operation":5}]
     * colmodel : []
     */

    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean {
        /**
         * createTimeString : 2017-05-25 10:57:01
         * msgType : 10
         * targetId : 0
         * isRead : false
         * avatar :
         * title :
         * fromId : 0
         * content : {"familyId":1,"personalId":1,"equipmentId":6}
         * familyId : 0
         * referId : 2
         * id : 3
         * operation : 3
         */

        private String createTimeString;
        private int msgType;
        private int targetId;
        private boolean isRead;
        private String avatar;
        private String title;
        private int fromId;
        private String content;
        private int familyId;
        private int referId;
        private int id;
        private int operation;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public int getTargetId() {
            return targetId;
        }

        public void setTargetId(int targetId) {
            this.targetId = targetId;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFromId() {
            return fromId;
        }

        public void setFromId(int fromId) {
            this.fromId = fromId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public int getReferId() {
            return referId;
        }

        public void setReferId(int referId) {
            this.referId = referId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }
    }
}
