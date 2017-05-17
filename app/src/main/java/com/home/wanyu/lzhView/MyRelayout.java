package com.home.wanyu.lzhView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.home.wanyu.R;
/**
 * Created by wanyu on 2017/5/11.
 */
public class MyRelayout extends View {
    private int SelectColor;//左边选中的颜色值
     private int UnSelectColor;//右边选中的颜色值
    private Paint paint;
    public MyRelayout(Context context) {
        super(context);
    }
    public MyRelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setAntiAlias(true);
        SelectColor=getResources().getColor(R.color.sele);
        UnSelectColor=getResources().getColor(R.color.unsele);
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int width=canvas.getWidth();
        int heigth=canvas.getHeight();
        paint.setColor(SelectColor);
        Shader mShader = new LinearGradient(width/2,heigth/2,width/2,heigth,SelectColor,UnSelectColor, Shader.TileMode.CLAMP);
        paint.setShader(mShader);
        canvas.drawRect(0,0,width,heigth,paint);
    }
}
