package com.home.wanyu.mEmeu;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/10.
 */

public enum  WindType {
    //小，中，大
    Small,Middle,Large;
    int[]resId={R.mipmap.slowb,R.mipmap.mediumb,R.mipmap.fastb};
    String[]windName={"慢速","中速","快速"};
    public int getResId(WindType type){
        switch (type){
            case Small:
                return resId[0];
            case Middle:
                return resId[1];
            case Large:
                return resId[2];
            default:
                return 0;
        }
    }
    public String getWindName(WindType type){
        switch (type){
            case Small:
                return windName[0];
            case Middle:
                return windName[1];
            case Large:
                return windName[2];
            default:
                return windName[1];
        }
    }
}
