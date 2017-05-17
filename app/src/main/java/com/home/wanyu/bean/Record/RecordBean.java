package com.home.wanyu.bean.Record;

/**
 * Created by liuhaidong on 2017/5/16.
 */

public class RecordBean {
    private String type;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RecordBean(String type, int id) {
        this.type = type;
        this.id = id;
    }
}
