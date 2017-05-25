package com.home.wanyu.lzhUtils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanyu on 2017/5/17.
 */

public class PhoneUtils {
    public static boolean isPhoneNum(String num){
        if ("".equals(num)| TextUtils.isEmpty(num)){
            return false;
        }
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(num);
        return m.matches();
    }
}
