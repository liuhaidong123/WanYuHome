package com.home.wanyu.Ip;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wanyu on 2017/5/8.
 */
//服务器状态码
public class ServerCode {
//    @CodeMessage(msg="您还没有登录!")
//    public final static Long personal_10000 = 10000L;
//    @CodeMessage(msg="请重新登录！")
//    public final static Long personal_10001 = 10001L;
//    @CodeMessage(msg="指定编号的用户不存在！")
//    public final static Long personal_10002 = 10002L;
//    @CodeMessage(msg="请输入手机号码！")
//    public final static Long personal_10003 = 10003L;
//    @CodeMessage(msg="请输入一个有效的手机号码！")
//    public final static Long personal_10004 = 10004L;
    public static void showResponseMsg(Context context,String serverCode){
        if (!"".equals(serverCode)&&!TextUtils.isEmpty(serverCode)){
            switch (serverCode){
                case "10000":
                    mToast.Toast(context,"您还没有登录！");
                    break;
                case "10001"://token失效，需要从新处理
                    mToast.Toast(context,"请重新登录！");
                    break;
                case "10002":
                    mToast.Toast(context,"当前用户不存在！");
                    break;
                case "10003":
                    mToast.Toast(context,"请输入手机号码！");
                    break;
                case "10004":
                   mToast.Toast(context,"请输入一个有效的手机号码！");
                    break;
                default:
                    mToast.Toast(context,"操作失败");
                    break;
            }
        }
        else {
            Log.e("ServerCode----","传入的服务器响应码不正确，请检查");
        }
    }
}
