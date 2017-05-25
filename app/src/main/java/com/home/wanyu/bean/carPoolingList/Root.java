package com.home.wanyu.bean.carPoolingList;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/25.
 */

public class Root {
    private List<Result> result;

    private String code;

    private long userid;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
