package com.home.wanyu.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wanyu on 2017/5/2.
 */

public class MainBottomControl {
    private TextView textView;
    private ImageView imageView;
    public MainBottomControl(TextView textView,ImageView imageView){
        this.imageView=imageView;
        this.textView=textView;
    }
    public void setSelect(boolean select){
        imageView.setSelected(select);
        textView.setSelected(select);
    }
}
