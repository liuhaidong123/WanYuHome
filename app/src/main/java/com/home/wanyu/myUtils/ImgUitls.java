package com.home.wanyu.myUtils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by liuhaidong on 2017/5/3.
 */

public class ImgUitls {


    public static int getWith(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width3 = dm.widthPixels;
        return width3;

    }


}
