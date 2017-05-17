package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/10.
 */

public class SeekBar extends View {
    private SeekListener listener;
    private int MAX=100;//最大刻度
    private float mark=0f;//当前刻度百分比
    private Paint paint;
    private int maxAlpha=100;//色值变化区间（100-色值）
    private int SelectColor;//左边选中的颜色值
    private int MiddleColor;//中间色值
    private int UnSelectColor;//右边选中的颜色值
    private int maxWidth;
    private float Rowx=0f;//滑动过程中x轴的坐标
    private int SeekState=0;//停止状态，1滑动状态
    private int maxParent=20;//色值变化的范围
    public SeekBar(Context context) {
        super(context);
    }

    public SeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setAntiAlias(true);
        SelectColor=getResources().getColor(R.color.select);
        MiddleColor=getResources().getColor(R.color.middleColor);
        UnSelectColor=getResources().getColor(R.color.unselect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        maxWidth=canvas.getWidth();
        int width=canvas.getWidth();
        int heitht=canvas.getHeight();
        float selectWidth=Math.abs(Rowx);
        float leftWidth=selectWidth<=heitht/2?0:selectWidth;//左juxing
        leftWidth=leftWidth>=width-heitht?width-heitht:leftWidth;
        //左半圆
        paint.setColor(SelectColor);
        RectF rectF=new RectF(heitht/4,heitht/4,heitht/2+heitht/4,heitht/2+heitht/4);
        canvas.drawArc(rectF,90,180,true,paint);
        paint.setColor(SelectColor);
        paint.setStyle(Paint.Style.FILL);
        Shader mShader = new LinearGradient(heitht/2,heitht/2-heitht/4,width-heitht/2,heitht/2-heitht/4,SelectColor,UnSelectColor, Shader.TileMode.REPEAT);
        //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，
        // 玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
        paint.setShader(mShader);
        canvas.drawRect(heitht/2,heitht/2-heitht/4,width-heitht/2,heitht/2+heitht/4,paint);

        //右半圆
        paint.setColor(UnSelectColor);
        paint.setShader(null);
        RectF rectF1=new RectF(width-heitht/2-heitht/4,heitht/2-heitht/4,width-heitht/2+heitht/4,heitht/2+heitht/4);
        canvas.drawArc(rectF1,270,180,true,paint);

        if (SeekState==3){//抬起状态
            Rowx=-3000;
             int winState=0;
                SeekState=0;
            if (leftWidth<width*0.3){
                Rowx=0;
                leftWidth=0;
                winState=0;
            }
            else if (leftWidth>=width*0.3&&leftWidth<=width*0.7){
                leftWidth=width/2-heitht/2;
                Rowx=leftWidth;
                winState=1;
            }
            else if (leftWidth>width*0.7){
                leftWidth=width-heitht;
                Rowx=leftWidth;
                winState=2;
            }
            Log.e("state---","333333333");
            if (listener!=null){
                listener.seek(leftWidth/(width-heitht),MAX,SeekState,winState);
            }
        }
        //空心圆环
        paint.setColor(SelectColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.cilrWidth));
        canvas.drawCircle(leftWidth+heitht/2,heitht/2,heitht/2-getResources().getDimension(R.dimen.cilrWidth),paint);
        //圆环
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(leftWidth+heitht/2,heitht/2,heitht/2-getResources().getDimension(R.dimen.cilrWidth2),paint);
        paint.setColor(SelectColor);
        canvas.drawCircle(leftWidth+heitht/2,heitht/2,heitht/2-getResources().getDimension(R.dimen.cilrWidth3),paint);
    }

    public void setMark(float mark){
        if (mark>=0&&mark<=1){
            if (mark>=0&&mark<0.3){
                this.mark=0f;
            }
           else if (mark>=0.3&&mark<=0.6){
                this.mark=0.5f;
            }
            else if (mark>0.6){
                this.mark=1f;
            }
            Rowx=mark*maxWidth;
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                SeekState=1;
                Rowx=event.getRawX()<0?0:event.getRawX()>maxWidth?maxWidth:event.getRawX();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Rowx=event.getRawX()<0?0:event.getRawX()>maxWidth?maxWidth:event.getRawX();
                SeekState=2;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                SeekState=3;
                Rowx=event.getRawX()<0?0:event.getRawX()>maxWidth?maxWidth:event.getRawX();
                invalidate();
                break;
        }

        return true;
    }

    public void setOnSeekListener(SeekListener listener){
        this.listener=listener;
    }
    //mark当前百分比位置（左边宽度／总宽度）
    public interface SeekListener{
        //mark刻度占的百分比，max最大刻度值，state是否处于抬起状态（0初始状态，1按下，2滑动,3抬起后的状态）
        void seek(float mark,int max,int state,int windState);//windState：0慢，1中，2高
    }
}
