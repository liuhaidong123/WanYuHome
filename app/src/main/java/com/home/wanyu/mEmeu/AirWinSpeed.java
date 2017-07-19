package com.home.wanyu.mEmeu;

/**
 * Created by wanyu on 2017/7/12.
 */
//空调风速
public enum  AirWinSpeed {
    LOW,MIDDLE,LARGE;//低速，中速，快速
    public String getSpeedName(AirWinSpeed speed){
        String name="未知";
        switch(speed){
            case LOW:
                name="低速";
              break;
            case MIDDLE:
                name="中速";
                break;
            case LARGE:
                name="快速";
                break;
        }
        return name;
    }
}
