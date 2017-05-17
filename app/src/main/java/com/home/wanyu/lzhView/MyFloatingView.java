package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/3.
 */

public class MyFloatingView extends View {
    private boolean mState;//当前状态
    private Paint paint;
    private int startX;
    private int startY;
    float stx=0f;
    float sty=0f;
    private ClickListener listener;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrX = (int) event.getX();
        int scrY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stx=event.getRawX();
                sty=event.getRawY();
                startX = (int) event.getX();
                startY = (int) event.getY();
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_MOVE:
                int x = scrX - startX;
                int y = scrY - startY;
                layout(getLeft() + x, getTop() + y, getRight() + x, getBottom() + y);
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_UP:
                Log.e("----"+(event.getRawY() - sty),(event.getRawX() - stx)+"--");
                if (Math.abs(event.getRawX() - stx) > 50 | Math.abs(event.getRawY() - sty) > 50) {
                    if (listener != null) {
                        listener.click(false);
                        Log.e("Float-onclck---","false");
                    }
                } else {
                    if (listener != null) {
                        Log.e("Float-onclck---","true");
                        listener.click(true);
                        }
                }
                    return super.onTouchEvent(event);
                    default:
                        return super.onTouchEvent(event);
                }
        }

    public interface ClickListener{
        void click(boolean flag);
    }

    public void setClick(ClickListener listener){
        this.listener=listener;
    }
}
