package com.home.wanyu.PermissionCheck;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.home.wanyu.lzhUtils.WindowUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wanyu on 2017/7/5.
 */

public class IntentAction {
    public static IntentAction action;
    private IntentAction(){

    }
    public static IntentAction getInstance(){
        if (action==null){
            action=new IntentAction();
        }
        return action;
    }
    //从图库中获取图片
    public  void getImage(Activity activity, File outputImage,int RequestCode){
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        //此处调用了图片选择器
        //如果直接写intent.setDataAndType("image/*");
        //调用的是系统图库
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        activity.startActivityForResult(intent, RequestCode);
    }

    //图片的裁剪
    public void curtImage(Intent data,int width,int height,File outputImage,Activity activity,int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        //此处注释掉的部分是针对android 4.4路径修改的一个测试
        //有兴趣的读者可以自己调试看看
        intent.setDataAndType(data.getData(), "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.5);
        intent.putExtra("outputX", WindowUtils.getWinowWidth(activity));//宽度
//        intent.putExtra("outputY", height);//高度
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        activity.startActivityForResult(intent, requestCode);
    }
}
