package com.home.wanyu.lzhUtils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanyu on 2017/5/2.
 */

public class MyToast {
    public static void DebugToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
