package com.home.wanyu.PermissionCheck;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by wanyu on 2017/7/5.
 */
//权限检查
public class PermissionCheck {
    //权限的名字参照PermissionName接口
    //检查存储权限
    public static boolean checkStroagePri(Activity activity,String[] pri){
        if (Build.VERSION.SDK_INT>=23){
            if (pri!=null&&pri.length>0){
                boolean isPriGet=true;
                for (int i=0;i<pri.length;i++){
                    if (ContextCompat.checkSelfPermission(activity, pri[i])!= PackageManager.PERMISSION_GRANTED
                           ){
                        isPriGet=false;
//                        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
                    }
                }
                return isPriGet;
            }
            return true;//请求的权限数组为空时默认获取了权限
        }
        return true;
    }

}
