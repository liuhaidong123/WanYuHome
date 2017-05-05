package com.home.wanyu.lzhUtils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by wanyu on 2017/5/3.
 */

public class WindowUtils {
    public static int getWinowWidth(Context context){
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }
}
