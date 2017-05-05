package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/3.
 */

public class MyFloatingView extends View {
    private boolean mState;//当前状态
    private Paint paint;
    public MyFloatingView(Context context) {
        super(context);
    }

    public MyFloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setAntiAlias(true);
    }

        @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int wid=canvas.getWidth();
        int height=canvas.getHeight();
        paint.setColor(getResources().getColor(R.color.mainBottomTextSelectColor));
        canvas.drawCircle(wid/2,height/2,wid>height?height/2:wid/2,paint);
        String text="";
        if (mState){//全部开启
            text="一键关闭";
        }
        else {
            text="一键开启";
            }
//            Log.i("MyFloatingView--onDraw-","state==="+mState);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextSize(getResources().getDimension(R.dimen.textSize15));
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text,getMeasuredWidth() / 2 -rect.width() / 2, baseline+2, paint);
    }

    //设置全部关闭或者开启
    public void setOnAndOff(boolean state){
                mState=state;
                postInvalidate();
    }
    //获取当前的开启状态
    public boolean getState(){
        return mState;
    }
    public void setState(boolean state){
        mState=state;
    }
}
