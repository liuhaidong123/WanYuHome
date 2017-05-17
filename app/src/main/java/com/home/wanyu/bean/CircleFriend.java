package com.home.wanyu.bean;

/**
 * Created by liuhaidong on 2017/5/9.
 */

public class CircleFriend {
    private String type;
    private boolean flag;

    public CircleFriend(String type, boolean flag) {
        this.type = type;
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
