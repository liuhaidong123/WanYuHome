package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.Random;

/**
 * Created by wanyu on 2017/5/8.
 */

public class MyView extends View {
    private int startX;
    private int startY;
    private Paint paint;
    private Random random;
    private int[]Color={0xfbf20306,0xfbe6640e,0xfbf2d603,0xfb03f227,0xfb03eef2,0xfb0b03f2,0xfb8204f1};
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        random=new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int pos= random.nextInt(7);
        paint.setColor(Color[pos]);
        int wid=canvas.getWidth();
        int height=canvas.getHeight();
        int cirlce=wid>height?height:wid;
        canvas.drawCircle(cirlce/2,cirlce/2,cirlce/2,paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        setEnabled(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrX=(int)event.getX();
        int scrY=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                invalidate();
                startX=(int)event.getX();
                startY=(int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int  x=scrX-startY;
                int y=scrY-startY;
                layout(getLeft()+x,getTop()+y,getRight()+x,getBottom()+y);
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return false;
    }

}
