package com.home.wanyu.ViewSettingUtils;

import android.widget.TextView;

import com.home.wanyu.mEmeu.HomeSelect;

import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/6/30.
 */

public class TextViewSetting {
    static String SCENE="我的情景";
    static String DEVICE="我的设备";
    static String SELECT;
    static String UNSELECT;
    public static void ChangeTextSie(TextView textViewLarge,TextView textViewSmall,HomeSelect select){
        switch (select){
            case SCENE:
                SELECT=SCENE;
                UNSELECT=DEVICE;
                break;
            case DEVICE:
                SELECT=DEVICE;
                UNSELECT=SCENE;
                break;
        }
        textViewLarge.setTextSize(24);
        textViewLarge.setText(SELECT);
        textViewSmall.setTextSize(16);
        textViewSmall.setText(UNSELECT);
    }
}
