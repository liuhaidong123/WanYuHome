package com.home.wanyu.Ip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.home.wanyu.Applications.MyApplication;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.LoginAndRegisterActivity;

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
    private static Context con;
    public static void showResponseMsg(final Context context, String serverCode) {
        con = context;
        if (!"".equals(serverCode) && !TextUtils.isEmpty(serverCode)) {
            switch (serverCode) {
                case "10000":
                    ShowLoginWindow(con);
                    break;
                case "10001"://token失效，需要从新处理
                    ShowLoginWindow(con);
                    break;
                case "10002":
                    mToast.Toast(context, "当前用户不存在！");
                    break;
                case "10003":
                    mToast.Toast(context, "请输入手机号码！");
                    break;
                case "10004":
                    mToast.Toast(context, "请输入一个有效的手机号码！");
                    break;
                case "-1":
                    AlertDialog dialog = new AlertDialog.Builder(context).setTitle("操作失败").setMessage("该账号在其他设备登录，请重新登录").setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    UserInfo.clearLogin(con);
                                    MyApplication.removeActivity();
                                    con.startActivity(new Intent(con, LoginAndRegisterActivity.class));
                                }
                            }).show();
                    break;
                default:
                    mToast.Toast(context, "操作失败");
                    break;
            }
        } else {
            Log.e("ServerCode----", "传入的服务器响应码不正确，请检查");
        }
    }
    public static void showServerInfo(final Context con, String serverCode, String serverMsg){
        switch (serverCode){
            case "10000":
                ShowLoginWindow(con);
                break;
            case "10001":
                ShowLoginWindow(con);
                break;
            case "-1":
                AlertDialog dialog = new AlertDialog.Builder(con).setTitle("操作失败").setMessage("该账号在其他设备登录，请重新登录").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                UserInfo.clearLogin(con);
                                MyApplication.removeActivity();
                                con.startActivity(new Intent(con, LoginAndRegisterActivity.class));
                            }
                        }).show();
                break;
            default:
               mToast.Toast(con,serverMsg);
                break;
        }
    }


    public static void ShowLoginWindow(final Context context) {
//        R.layout.logindialog//对话框的view
        if (UserInfo.isShowLoginDialog == false) {//已经提示过一次登录,buzaitishi
            mToast.Toast(context, "登录失效，请重新登录");
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setCancelable(false);
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.setTitle("重要提示");
            alertDialog.setMessage("您还没有登录／您的登录状态已失效(账号在其他设备登录),请重新登录");
            alertDialog.setCancelable(false);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    UserInfo.isShowLoginDialog=false;
//                    dialog.dismiss();
//                    MyApplication.removeActivity();
//                }
//            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserInfo.isShowLoginDialog=false;
                    dialog.dismiss();
                    context.startActivity(new Intent(context,LoginAndRegisterActivity.class));
                }
            });
            alertDialog.show();
        }
    }
}
