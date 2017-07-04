package com.home.wanyu.C_Utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/7/3.
 */

public class PopupSettings {
    static PopupSettings settings;
    private PopupSettings(){

    }
    public static PopupSettings getInstance(){
        if (settings==null){
            settings=new PopupSettings();
        }
        return settings;
    }
    //底部弹窗效果设置参数：pop-窗口，parentView-父容器，con-依附的Activity,contentView-显示的view
    public  void windowBottomSet(PopupWindow pop, View parentView, final Activity con, View contentView){
        con.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = con.getWindow().getAttributes();
        params.alpha = 0.6f;
        con.getWindow().setAttributes(params);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parentView, Gravity.BOTTOM,0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                con.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = con.getWindow().getAttributes();
                params.alpha = 1f;
                con.getWindow().setAttributes(params);
            }
        });
    }
}
