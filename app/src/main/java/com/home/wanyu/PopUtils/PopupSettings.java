package com.home.wanyu.PopUtils;

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
    //底部向上弹窗效果设置参数：pop-窗口，parentView-父容器，con-依附的Activity,contentView-显示的view
    public   void windowBottomUpSet(PopupWindow pop, View parentView, final Activity con, View contentView){
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
    //底部向下弹出效果：pop-窗口，parentView-父容器，con-依附的Activity,contentView-显示的view
    public   void windowBottomDownSet(PopupWindow pop, View parentView, final Activity con, View contentView){
        con.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params =con.getWindow().getAttributes();
        params.alpha = 0.9f;
        con.getWindow().setAttributes(params);

        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(parentView.getMeasuredWidth());

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup2_anim);
        pop.showAsDropDown(parentView, 0, 5);
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


    //activity中部弹窗效果（缩放弹窗）：pop-窗口，parentView-父容器，con-依附的Activity,contentView-显示的view
    public   void windowActivityCenter(PopupWindow popX, View parentView, final Activity activity, View contentView){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params =  activity.getWindow().getAttributes();
        params.alpha = 0.6f;
        activity.getWindow().setAttributes(params);

        popX.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popX.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popX.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popX.setContentView(contentView);
        popX.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popX.setTouchable(true);
        popX.setFocusable(true);
        popX.setOutsideTouchable(true);

        popX.setAnimationStyle(R.style.popup2_anim);
        popX.showAtLocation(parentView, Gravity.CENTER,0,0);
        popX.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params =  activity.getWindow().getAttributes();
                params.alpha = 1f;
                activity.getWindow().setAttributes(params);
            }
        });
    }
}
