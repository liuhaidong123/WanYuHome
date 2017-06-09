package com.home.wanyu.lzhView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/6/2.
 */

public class MyVi extends View{
    Paint paint;
    public MyVi(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.white));
        paint.setAntiAlias(true);
    }
    public MyVi(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        Path path=new Path();
        path.moveTo(0,height/2);
        path.lineTo(width,height/4);
        path.lineTo(width,3*height/4);
        path.close();
        canvas.drawPath(path,paint);
    }
}
