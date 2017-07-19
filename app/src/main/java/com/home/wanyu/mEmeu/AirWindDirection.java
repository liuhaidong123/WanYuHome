package com.home.wanyu.mEmeu;

/**
 * Created by wanyu on 2017/7/12.
 */
//空调风向
public enum AirWindDirection {
    AUTO,TOP_BOTTOM,LEFT_RIGHT;//自动，上下，左右
    public String getWindDirectionName(AirWindDirection direction){
        String name="未命名";
        switch (direction){
            case AUTO:
                name="自动";
                break;
            case TOP_BOTTOM:
                name="上下风";
                break;
            case LEFT_RIGHT:
                name="左右风";
                break;
        }
        return name;
    }
}
