package com.home.wanyu.Icons;

/**
 * Created by wanyu on 2017/5/9.
 */

public enum Device {
    //0=无设备
//    1=开关
//    2=灯
//    3=电视
//    4=窗帘
//    5=空调
//    6=门锁
    NONE,SWITCH,LIGHT,TV,CURTAIN,AC,LOCK;

    public static Device getDevice(int device){
        switch (device){
            case 0:
            return NONE;
            case 1:
                return SWITCH;
            case 2:
                return LIGHT;
            case 3:
                return TV;
            case 4:
                return CURTAIN;
            case 5:
                return AC;
            case 6:
                return LOCK;
            default:
                return NONE;
        }

        }
}
