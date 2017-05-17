package com.home.wanyu.bean.waterEleRan;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/12.
 */

public class Root {
    private String result;

    private String code;

    private List<ItemsYear> itemsYear;

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setItemsYear(List<ItemsYear> itemsYear){
        this.itemsYear = itemsYear;
    }
    public List<ItemsYear> getItemsYear(){
        return this.itemsYear;
    }
}
