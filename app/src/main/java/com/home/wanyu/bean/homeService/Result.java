package com.home.wanyu.bean.homeService;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/26.
 */

public class Result {
    private List<Menulist> menulist;

    private BusinessEntity businessEntity;

    public void setMenulist(List<Menulist> menulist){
        this.menulist = menulist;
    }
    public List<Menulist> getMenulist(){
        return this.menulist;
    }
    public void setBusinessEntity(BusinessEntity businessEntity){
        this.businessEntity = businessEntity;
    }
    public BusinessEntity getBusinessEntity(){
        return this.businessEntity;
    }
}
