package com.home.wanyu.lzhView;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by wanyu on 2017/5/19.
 */

public class MyRela extends RelativeLayout implements NestedScrollingChild {
    public MyRela(Context context) {
        super(context);
    }

    public MyRela(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
