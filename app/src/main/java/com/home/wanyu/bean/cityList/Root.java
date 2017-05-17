package com.home.wanyu.bean.cityList;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class Root {
    private String result;

    private List<BaseAreaList> baseAreaList;

    private String code;

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setBaseAreaList(List<BaseAreaList> baseAreaList){
        this.baseAreaList = baseAreaList;
    }
    public List<BaseAreaList> getBaseAreaList(){
        return this.baseAreaList;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
