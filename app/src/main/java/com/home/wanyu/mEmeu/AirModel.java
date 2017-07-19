package com.home.wanyu.mEmeu;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/7/12.
 */
//空调的可选模式
public enum  AirModel {
    AUTO,COOD,HOT,WET,WIND;//自动，制冷，制热，除湿，送风
    int []modelRes={R.mipmap.selfmotiont,R.mipmap.coldt,R.mipmap.hott,R.mipmap.wett,R.mipmap.windt};
    //获取对应模式的名称
    public String getModeName(AirModel model){
        String name="未定义模式";
        switch (model){
            case AUTO:
                name="自动";
                break;
            case COOD:
                name="制冷";
                break;
            case HOT:
                name="制热";
                break;
            case WET:
                name="除湿";
                break;
            case WIND:
                name="送风";
        }
        return name;
    }
    //获取对应的模式图标
    public int getModelRes(AirModel model){
        int res=R.mipmap.errorphoto;
        switch (model){
            case AUTO:
                res=modelRes[0];
                break;
            case COOD:
                res=modelRes[1];
                break;
            case HOT:
                res=modelRes[2];
                break;
            case WET:
                res=modelRes[3];
                break;
            case WIND:
                res=modelRes[4];
        }
        return res;
    }
}
