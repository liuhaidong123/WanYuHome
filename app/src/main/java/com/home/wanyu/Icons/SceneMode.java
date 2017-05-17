package com.home.wanyu.Icons;

/**
 * Created by wanyu on 2017/5/11.
 */

public enum  SceneMode {
    DEFAULT,ONEKEY,TIMING,LOCATION;//无法识别的模式，1一键开启，2定时开启，3定位开启
    public static SceneMode intToMode(int Id){
            if (Id==1){
                return ONEKEY;
            }
        else if (Id==2){
                return TIMING;
            }
        else if (Id==3){
                return LOCATION;
            }
        else {
                return DEFAULT;
            }
    }
    public static String getModeName(SceneMode mode){
        switch (mode){
            case ONEKEY:
                return "一键开启";
            case TIMING:
                return "定时开启";
            case LOCATION:
                return "定位开启";
            default:
                return "不支持的模式";
        }
    }
    private static String ModeToString(SceneMode mode){
        switch (mode){
            case ONEKEY:
                return "1";
            case TIMING:
                return "2";
            case LOCATION:
                return "3";
            default:
                return "0";
        }
    }
}
