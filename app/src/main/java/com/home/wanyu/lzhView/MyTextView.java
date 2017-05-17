package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wanyu on 2017/5/11.
 */

public class MyTextView extends TextView{
    private String psd;
    private final String pd="*";
    CommpeteListener listener;
    private int MaxSize=6;//最大位数
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (psd!=null&&!"".equals(psd)){
            if (psd.length()>0){
                Paint paint=new Paint();
                if (psd.length()==MaxSize){
                    if (listener!=null){
                        listener.complete(psd);
                    }
                }
            }
        }
    }

    public void setPsd(String psd){
        if (psd!=null&&!"".equals(psd)&&psd.length()<MaxSize){
            this.psd=psd;
            invalidate();
        }
    }
    public String getPsd(){
        return psd;
    }
    //设置密码长度
    public void setMaxSize(int max){
        this.MaxSize=max;
    }
    public void setCompleteListener(CommpeteListener listener){
        this.listener=listener;
    }
    public interface CommpeteListener{
        void complete(String psd);
    }
}
