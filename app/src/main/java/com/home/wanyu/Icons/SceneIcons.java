package com.home.wanyu.Icons;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/7/25.
 */

public class SceneIcons {
    //定义所有情景图标
    public static int[] SceneIcons={R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e,R.mipmap.f};
    //根据id获取名字
    public static String getSceneIconSName(int pos){
        String name="";
        switch (pos){
            case 0:
                name="a";
                break;
            case 1:
                name="b";
                break;
            case 2:
                name="c";
                break;
            case 3:
                name="d";
                break;
            case 4:
                name="e";
                break;
            case 5:
                name="f";
                break;
        }
        return name;
    }
    //根据名字获取mipmap
    public static int getSceneIcon(String name){
        int pos=0;
        switch (name){
            case "a":
               pos=0;
                break;
            case "b":
                pos=1;
                break;
            case "c":
                pos=2;
                break;
            case "d":
                pos=3;
                break;
            case "e":
                pos=4;
                break;
            case "f":
                pos=5;
                break;
        }

        return SceneIcons[pos];
    }
}
