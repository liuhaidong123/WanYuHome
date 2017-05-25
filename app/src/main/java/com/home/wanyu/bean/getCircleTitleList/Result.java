package com.home.wanyu.bean.getCircleTitleList;

import java.io.PipedReader;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class Result {
    private String cname;

    private long id;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
