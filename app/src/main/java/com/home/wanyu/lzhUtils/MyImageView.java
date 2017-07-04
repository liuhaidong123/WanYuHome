package com.home.wanyu.lzhUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/26.
 */

public class MyImageView extends ImageView{
    private boolean isRead=false;
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRead){
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.test1));
            int width=canvas.getWidth();
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);
        }

    }

    public void setRead(boolean read){
        this.isRead=read;
        invalidate();
    }
}
