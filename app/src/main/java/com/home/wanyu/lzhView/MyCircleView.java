package com.home.wanyu.lzhView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/10.
 */

public class MyCircleView extends View {
    private int strokeWidth;
    Paint paint;
    int startColor;
    int endColor;
    public MyCircleView(Context context) {
        super(context);
    }
//    <color name="select">#00bfff</color>
//    <color name="middleColor">#1ddff0</color>
//    <color name="unselect">#36fbe2</color>
    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        strokeWidth= (int) array.getDimension(R.styleable.CircleView_strokeWidth,getResources().getDimension(R.dimen.strokeWidth));
//        text=array.getString(R.styleable.CircleView_title)==null?"当前温度":array.getString(R.styleable.CircleView_title);
        paint=new Paint();
        startColor=getResources().getColor(R.color.select);
        endColor=getResources().getColor(R.color.unselect);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=canvas.getWidth();
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(startColor);
        Shader shader=new LinearGradient(0,width/2,width,width/2,startColor,endColor, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawCircle(width/2,width/2,width/2-strokeWidth/2,paint);

        paint.setStrokeWidth(strokeWidth/4);
        paint.setColor(startColor);
        Shader shader2=new LinearGradient(strokeWidth,width/2,width-strokeWidth,width/2,startColor,endColor, Shader.TileMode.MIRROR);
        paint.setShader(shader2);
        canvas.drawCircle(width/2,width/2,width/2-strokeWidth-strokeWidth/2,paint);
    }

}
