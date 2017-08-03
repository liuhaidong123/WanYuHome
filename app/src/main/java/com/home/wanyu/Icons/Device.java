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
//    6=音响
    //7门锁
    NONE,SWITCH,LIGHT,TV,CURTAIN,AC,SOUND,LOCK;
//    public static String[]mDeviceName={"未识别的设备","开关","灯","电视","窗帘","空调","音响","门锁"};
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
                return SOUND;
            case 7:
                return LOCK;
            default:
                return NONE;
        }

        }
}
