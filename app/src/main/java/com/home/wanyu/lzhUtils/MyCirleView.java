package com.home.wanyu.lzhUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/26.
 */

public class MyCirleView extends View{
    Paint paint;
    private int color;
    public MyCirleView(Context context) {
        super(context);
    }

    public MyCirleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        color=getResources().getColor(R.color.test1);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int width=canvas.getWidth();
        canvas.drawCircle(width/2,width/2,width/2,paint);
    }
}
