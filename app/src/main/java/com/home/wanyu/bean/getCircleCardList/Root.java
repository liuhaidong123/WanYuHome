package com.home.wanyu.bean.getCircleCardList;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class Root {
    private List<Result> result;

    private String code;

    private long userid;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setResult(List<Result> result){
        this.result = result;
    }
    public List<Result> getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }

}
