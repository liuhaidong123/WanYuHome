package com.home.wanyu.PermissionCheck;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;
import java.io.IOException;

/**
 * Created by wanyu on 2017/7/24.
 */

public class FilePath {
    static FilePath filePath;
    Context context;
    private FilePath(){

    }
    public static FilePath getInstance(){
        if (filePath==null){
            filePath=new FilePath();
        }
        return filePath;
    }

    public  File getPath(Context context,String fileName){
        File f;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getSDFreeSize()>10){//sd卡存在,容量大于10M
           f=new File(Environment.getExternalStoragePublicDirectory(
                   Environment.DIRECTORY_PICTURES),fileName);
        }
        else {//sd卡不能使用或者容量较小
            f = new File(context.getFilesDir().getAbsolutePath(),fileName);
            }
        if (f.exists()) {
            f.delete();
            }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;

    }

    public long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }

}
