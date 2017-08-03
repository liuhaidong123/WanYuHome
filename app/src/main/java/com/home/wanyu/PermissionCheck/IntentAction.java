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

    public void takePhoto(Activity activity, File outputImage,int RequestCode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(outputImage);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//根据uri保存照片
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//保存照片的质量
        activity.startActivityForResult(intent,RequestCode);//启动相机拍照
    }

    //图片的裁剪
    public void curtImage(Intent data,int width,int height,File outputImage,Activity activity,int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data.getData(), "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.5);
//        intent.putExtra("outputX", width);//宽度
//        intent.putExtra("outputY", height);//高度
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        activity.startActivityForResult(intent, requestCode);
    }
    //图片的裁剪(拍照后)
    public void curtImageTakePhoto(Intent data,int width,int height,File outputImage,Activity activity,int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(outputImage), "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的view可裁剪
        intent.putExtra("crop","true");
        // 当图片的宽高不足时，会出现黑边，去除黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.5);

//        intent.putExtra("outputX", width);//宽度
//        intent.putExtra("outputY", height);//高度

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
        activity.startActivityForResult(intent, requestCode);
    }
}
