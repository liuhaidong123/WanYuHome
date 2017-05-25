package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/18.
 */
//获取小区接口
public class Bean_getXQ {

    /**
     * result : [{"rname":"名流一品小区","token":"","id":1},{"rname":"钻石广场小区","token":"","id":2},{"rname":"风景洋房小区","token":"","id":3},{"rname":"名流翡翠城小区","token":"","id":4},{"rname":"香邑溪谷小区","token":"","id":5}]
     * code : 0
     */

    private String code;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * rname : 名流一品小区
         * token :
         * id : 1
         */

        private String rname;
        private String token;
        private int id;

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
