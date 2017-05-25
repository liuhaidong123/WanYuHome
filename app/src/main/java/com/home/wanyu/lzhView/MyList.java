package com.home.wanyu.lzhView;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wanyu on 2017/5/19.
 */

public class MyList extends ListView implements NestedScrollingChild {
    public MyList(Context context) {
        super(context);
    }

    public MyList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
