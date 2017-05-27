package com.home.wanyu.bean.cityList;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class BaseAreaList {
    private int level;

    private long platMark;

    private long parentId;

    private String areaCode;

    private int expand;

    private String areaName;

    private long id;



    private List<String> childrenList;

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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<String> childrenList) {
        this.childrenList = childrenList;
    }
}
