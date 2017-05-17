package com.home.wanyu.Icons;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/8.
 */

public class icon {
    //0=无设备,无法识别的设备
//    1=开关
//    2=灯
//    3=电视
//    4=窗帘
//    5=空调
//    6=门锁
    public static int[]mIconRes={R.mipmap.line,R.mipmap.socket,R.mipmap.light,R.mipmap.tv1,R.mipmap.square,R.mipmap.sun,R.mipmap.door};
    public static int[]mIconId={0,1,2,3,4,5,6};
    public static String[]mDeviceName={"未识别的设备","开关","灯","电视","窗帘","空调","门锁"};
    public static String getDeviceName(int dev){
        Device device=Device.getDevice(dev);
        switch (device){
            case NONE:
                return mDeviceName[0];
            case SWITCH:
               return mDeviceName[1];
            case LIGHT:
                return mDeviceName[2];
            case TV:
                return mDeviceName[3];
            case CURTAIN:
                return mDeviceName[4];
            case AC:
                return mDeviceName[5];
            case LOCK:
                return mDeviceName[6];
            default:
                return mDeviceName[0];
        }
    }

}
