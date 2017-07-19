package com.home.wanyu.mEmeu;

import com.home.wanyu.Icons.SceneMode;

/**
 * Created by wanyu on 2017/7/5.
 */
//情景的启动方式
public enum  SceneStartMode {
    BUTTON,TIME,LOCATION,UNKONE;//一键启动，定时启动，定位启动，未知的启动方式
    //根据数值返回启动模式
    public  SceneStartMode intToModel(int model){
        SceneStartMode mod;
        switch (model){
            case 0:
                mod=BUTTON;
                break;
            case 1:
                mod=TIME;
                break;
            case 2:
                mod=LOCATION;
                break;
            default:
                mod=UNKONE;
                break;
        }
        return mod;
    }
    //更具启动模式返回名称
    public  String getModelName(SceneStartMode model){
        String name="";
        switch (model){
            case BUTTON:
                name="一键启动";
                break;
            case TIME:
                name="定时启动";
                break;
            case LOCATION:
                name="定位启动";
                break;
            case UNKONE:
                name="未知方式";
                break;
        }
        return name;
    }
    //更具启动模式返回名称
    public int getModelType(SceneStartMode model){
        int name=-1;
        switch (model){
            case BUTTON:
                name=0;
            case TIME:
                name=1;
                break;
            case LOCATION:
                name=2;
                break;
            case UNKONE:
                name=3;
                break;
        }
        return name;
    }
}
