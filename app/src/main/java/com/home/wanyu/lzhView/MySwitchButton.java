package com.home.wanyu.lzhView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.home.wanyu.R;

import java.lang.reflect.Type;

/**
 * Created by hp on 2016/9/12.
 * 选择开关
 */
public class MySwitchButton extends View {
    private switchChangeListener listener;
    private final String TAG=getClass().getSimpleName();
    private Boolean state;//开关的状态
    private int textColor;//字体的颜色
    private float textSize;
    private String message;
    private float scale=2.5f;//宽：高=2.5；
    private Paint paint;
    private int ballColor;
    private final String trueText="开";
    private final String falseText="关";
    private int switchColorFalse;//关闭状态下的颜色
    private int switchColorTrue;//开启状态下的颜色
    private float height;//高度（宽度是高度的2.2倍）
    private boolean isMove;//当前是否处于滑动状态
    private float eveX;//按下的点的x的坐标
    private float RadioX;//圆球的圆心所在位置
    private float movX;//x轴方向圆心移动的距离(可能是负数)
    private float wid;//画布的宽度
    public MySwitchButton(Context context) {
        super(context);
    }

    public MySwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.switchBtn);
        state=typedArray.getBoolean(R.styleable.switchBtn_sta,true);
        textColor=typedArray.getColor(R.styleable.switchBtn_switch_textColor, getResources().getColor(R.color.switchTextColor));
        switchColorFalse=typedArray.getColor(R.styleable.switchBtn_switch_false, getResources().getColor(R.color.switchFalseColor));
        switchColorTrue=typedArray.getColor(R.styleable.switchBtn_switch_true,getResources().getColor(R.color.switchTrueColor));
        ballColor=typedArray.getColor(R.styleable.switchBtn_ballCo,getResources().getColor(R.color.switchballColor));
        textSize=typedArray.getDimension(R.styleable.switchBtn_switch_textSize, getResources().getDimension(R.dimen.textSize));
        height=typedArray.getDimension(R.styleable.switchBtn_switchHeight, getResources().getDimension(R.dimen.switchWid));
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        RadioX=0;
        movX=0;
        isMove=false;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wid=canvas.getWidth();
        //-----------------------------------------开启状态绘制---------------------------------------
        if (state){//开启状态
            RadioX=canvas.getWidth() - height / 2;
            Log.i("state==true", "" + (state == true));
            if (movX<=0&&movX>height-canvas.getWidth()){
                paint.setColor(switchColorTrue);
            }
            else {
                paint.setColor(switchColorFalse);
            }
            RectF rectleft=new RectF(0,0,height,height);
            //左边半圆
            canvas.drawArc(rectleft,90,180,true,paint);
            RectF rectRight=new RectF(canvas.getWidth()-height,0,canvas.getWidth(),height);
            //右边半圆
            if (movX==0){
                paint.setColor(switchColorTrue);
            }
            else {
                paint.setColor(switchColorFalse);
            }
            canvas.drawArc(rectRight, 270, 180, true, paint);

            //右矩形
            paint.setColor(switchColorFalse);
            canvas.drawRect(canvas.getWidth() - height / 2 + movX, 0, canvas.getWidth() - height / 2+1, height, paint);
            //左矩形
//            if (movX<=3*height/2-canvas.getWidth()){
//                paint.setColor(switchColorFalse);
//            }
//            3*height/2-canvas.getWidth();
//            else {
//                paint.setColor(switchColorTrue);
//            }
            paint.setColor(switchColorTrue);
            canvas.drawRect(height/2, 0, canvas.getWidth() - height/2+movX, height, paint);

            //绘制圆球
            paint.setColor(ballColor);
            canvas.drawCircle(RadioX+movX, height / 2, height / 2-1, paint);
//            绘制文字
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            String text="开";
            Typeface typeface= Typeface.create(Typeface.DEFAULT_BOLD,Typeface.BOLD);
            paint.setTypeface(typeface);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics metrics=paint.getFontMetrics();
            float des=metrics.descent;
            float asent=metrics.ascent;
            float he=(des+asent)/2;

            if (movX<=-height/2){
                text=falseText;
                canvas.drawText(text,0,text.length(),(canvas.getWidth()-height)/2+height,
                        canvas.getHeight()/2-he,paint);
            }
            else {
                text=trueText;
                canvas.drawText(text,0,text.length(),(canvas.getWidth()-height)/2,
                        canvas.getHeight()/2-he,paint);
            }

        }
        //------------------------------------关闭时绘制---------------------------------------
        else {
//            canvas.getWidth()-3*height/2;
            RadioX=height / 2;
            Log.i("state==false",""+(state==true));
            //做半圆
            if (movX>0){
                paint.setColor(switchColorTrue);
            }
            else {
                paint.setColor(switchColorFalse);
            }
            RectF rectleft=new RectF(0,0,height,height);
            canvas.drawArc(rectleft, 90, 180, true, paint);
            //绘制右半圆
            if (movX>canvas.getWidth()-height){
                paint.setColor(switchColorTrue);
            }
            else {
                paint.setColor(switchColorFalse);
            }
            RectF rectRight=new RectF(canvas.getWidth()-height,0,canvas.getWidth(),height);
            canvas.drawArc(rectRight, 270, 180, true, paint);

            //绘制左矩形
            if (movX>0){
                paint.setColor(switchColorTrue);
            }
            else {
                paint.setColor(switchColorFalse);
            }
            canvas.drawRect(height / 2, 0,height / 2+movX, height, paint);
            //绘制右边矩形
            if (movX<canvas.getWidth()-height){
                paint.setColor(switchColorFalse);
            }
            else {
                paint.setColor(switchColorTrue);
            }
            canvas.drawRect(height /2+movX, 0,canvas.getWidth()-height/2+1, height, paint);
            paint.setColor(ballColor);
            canvas.drawCircle(RadioX+movX
                    , height / 2, height / 2 - 1, paint);
            //绘制文字
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            String text="关";
            Typeface typeface= Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD);
            paint.setTypeface(typeface);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics metrics=paint.getFontMetrics();
            float des=metrics.descent;
            float asent=metrics.ascent;
            float he=(des+asent)/2;
            if (movX>=height/2){
                text=trueText;
                canvas.drawText(text,0,text.length(),(canvas.getWidth()-height)/2,
                        canvas.getHeight()/2-he,paint);
            }
            else {
                text=falseText;
                canvas.drawText(text,0,text.length(),(canvas.getWidth()-height)/2+height,
                        canvas.getHeight()/2-he,paint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                movX=0;
                eveX=event.getX();
                float eventY=event.getY();
                if (eveX>=0&&eveX<=getWidth()&&eventY>=0&&eventY<=getHeight()){
                    isMove=true;
                }
                else {
                    isMove=false;
                }
                Log.i("isMove======",""+isMove);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMove){//按下去的点在当前空间范围内响应操作
                    movX=event.getX()-eveX;//X轴上移动的距离
                    if (state){//开启状态
                        if (movX<=0){
                            if (movX<height-getWidth()){
                                movX=height-getWidth();
                            }
                        }
                        else {
                            movX=0;
                        }

                    }
                    else {//关闭状态
                        if (movX>=0){
                            if (movX>getWidth()-height){
                                movX=getWidth()-height;
                            }
                        }
                        else {
                            movX=0;
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//                处理点击事件
                float X=event.getX();
                float Y=event.getY();
                float mvX=X-eveX;
                movX=0;
                if (isMove){//滑动事件
                    isMove=false;
                    if (state){//开启状态
                        if (mvX<=-height/2){
                            state=!state;
                            //关闭状态
                            if (listener!=null){
                                listener.change(state,falseText);
                            }
                        }
                        else if (mvX==0){//点击事件
                            state=!state;
                            //关闭状态
                            if (listener!=null){
                                if (state){
                                    listener.change(state,trueText);
                                }
                                else {
                                    listener.change(state,falseText);
                                }
                            }
                        }
                    }
                    else {//关闭状态
                        if (mvX>=height/2){
                            state=!state;
                            if (listener!=null){
                                listener.change(state,trueText);
                            }
                        }
                        else if (mvX==0){//点击事件
                            state=!state;
                            //关闭状态
                            if (listener!=null){
                                if (state){
                                    listener.change(state,trueText);
                                }
                                else {
                                    listener.change(state,falseText);
                                }
                            }
                        }
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hei= (int) height;
        Log.i(TAG, "高度------------" + height);
//        if (hei<50|hei>60){
//            hei=50;
//            height=hei;
//        }
        int wid= (int) (hei*scale);
        setMeasuredDimension(wid,hei);
    }


    //获取开关状态
    private Boolean getState(){
        return state;
    }
    //设置开关状态
    private void setState(Boolean st){
        this.state=st;
        if (listener!=null){
            if (st){
                listener.change(state,trueText);
            }
            else {
                listener.change(st,falseText);
            }
        }
        invalidate();
    }
    public void setSwitchChangerListener(switchChangeListener li){
        this.listener=li;
    }
    interface switchChangeListener{
        void change(Boolean sta,String text);
    }
}