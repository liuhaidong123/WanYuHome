package com.home.wanyu.lzhUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wanyu on 2017/7/5.
 */

public class BitmapUtils {

    /**
     * @param filePath   要加载的图片路径
     * @param destWidth  显示图片的控件宽度
     * @param destHeight 显示图片的控件的高度
     * @return
     */
    public static Bitmap getBitmap(String filePath, int destWidth, int destHeight) {
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        BitmapFactory.decodeFile(filePath, options);
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //加载图片并返回
        return BitmapFactory.decodeFile(filePath, options);
    }
    //保存bitmap到制定文件下
    public static boolean savaBitmap(File file,Bitmap bm){
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("BitmapUtils保存bitmap", "已经保存成功");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("BitmapUtils保存bitmap", "保存失败1：FileNotFoundException");
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("BitmapUtils保存bitmap", "保存失败2：IOException");
            return false;
        }
        return true;
    }
}
