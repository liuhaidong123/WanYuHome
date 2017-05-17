package com.home.wanyu.Ip;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanyu on 2017/5/2.
 */

public class mToast {
    public static void DebugToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void Toast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    //服务器连接失败，网络问题
    public static void ToastFaild(Context context,ToastType type){
        switch (type){
            case FAILD://网络异常
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
                break;
            case GSONEMPTY://数据为空，服务器返回数据为空
                Toast.makeText(context,"查询不到数据",Toast.LENGTH_SHORT).show();
                break;
            case GSONFAILD://服务器返回数据格式错误，无法解析
                Toast.makeText(context,"数据异常",Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
