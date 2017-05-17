package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/9.
 */
//三角
public class Mytri extends View {
    private int color=0xafeafe;
    private Paint paint;
    public Mytri(Context context) {
        super(context);
    }

    public Mytri(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        color=getResources().getColor(R.color.tri);
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        Path path=new Path();
        path.moveTo(0,height);
        path.lineTo(width/2-3,3);
        path.lineTo(width/2,2);
        path.lineTo(width/2+3,3);
        path.lineTo(width,height);
        path.close();
        canvas.drawPath(path,paint);
    }
}
