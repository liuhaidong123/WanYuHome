package com.home.wanyu.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

/**
 * Created by wanyu on 2017/5/8.
 */

public class UserInfo {
        public static String userName;//用户名
        public static String userPsd;//密码
        public static long personalId = 11l;
        public static String userToken = "9DB2FD6FDD2F116CD47CE6C48B3047EE";//登陆后获取的token
        //检查是否已经登陆
        public static boolean checkLogin(Context context) {
                return false;
        }

        public static boolean isLogin(Context context) {
                SharedPreferences preference = context.getSharedPreferences("USER", Context.MODE_APPEND);
                if (preference.contains("userName") && preference.contains("userToken") && preference.contains("personalId")) {//此三个都是登陆后返回的
                        userName = preference.getString("userName", "0");
                        userToken = preference.getString("userToken", "0");
                        personalId = preference.getLong("personalId", -1l);
                        if (!"0".equals(userName) && !"0".equals(userToken) && personalId != -1l) {
                                return true;
                        }
                }
                return false;
        }

        public static void clearLogin(Context context) {
                SharedPreferences preference = context.getSharedPreferences("USER", Context.MODE_APPEND);
                SharedPreferences.Editor editor = preference.edit();
                editor.remove("userName");
                editor.remove("userToken");
                editor.remove("personalId");
                editor.commit();
        }
        //保存登陆后返回的信息（目前保存：userName,userToken,personalId字段）
        public static void savaLogin() {

        }
}

