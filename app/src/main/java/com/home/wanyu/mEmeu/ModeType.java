package com.home.wanyu.mEmeu;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/10.
 */

public enum  ModeType {
    //自动，制冷，炙热，除湿，送风
    AUT,COLD,HOT,WET,WIND;
    int[]resId={R.mipmap.self_motiona,R.mipmap.colda,R.mipmap.hota,R.mipmap.weta,R.mipmap.winda};
    String[]modeName={"自动","制冷","制热","除湿","送风"};
    public int getResId(ModeType type){
        switch (type){
            case AUT:
                return resId[0];
            case COLD:
                return resId[1];
            case HOT:
                return resId[2];
            case WET:
                return resId[3];
            case WIND:
                return resId[4];
            default:
                return 0;
        }
    }
    public String getModeName(ModeType type){
        switch (type){
            case AUT:
                return modeName[0];
            case COLD:
                return modeName[1];
            case HOT:
                return modeName[2];
            case WET:
                return modeName[3];
            case WIND:
                return modeName[4];
            default:
                return "未知";
        }
    }
}
