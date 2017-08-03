package com.home.wanyu.OkhttpUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.zxing.qrcode.decoder.Mode;

/**
 * Created by wanyu on 2017/7/27.
 */

public class NetWorkDBUtils {
//    "create table network(_id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(200),values text)"
   static NetWorkDBUtils dbUtils;
    OkhttpSqliteOpenHelper helper;
    private NetWorkDBUtils(){
    }
    public static NetWorkDBUtils getInstance(){
        if (dbUtils==null){
            dbUtils=new NetWorkDBUtils();
        }
        return dbUtils;
    }

    //根据url获取到缓存的网络数据
    public String getOkhttpString(Context context, String url){
        helper=new OkhttpSqliteOpenHelper(context);
        SQLiteDatabase db =helper.getReadableDatabase();
        String netString="";
        Cursor cursor=db.rawQuery("select values from network where name=?",new String[]{url});
        if (cursor!=null){
            while (cursor.moveToNext()){
                netString=cursor.getString(cursor.getColumnIndex("values"));
            }
        }
        db.close();
        Log.i("NetWorkDBUtils","getOkhttpString获取缓存数据--："+url+"==="+netString);
        return netString;
    }
    //插入／替换
    public boolean saveOkhttpString(Context context ,String url,String netString){
        helper=new OkhttpSqliteOpenHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",url);
        contentValues.put("values",netString);
        long l=db.replace("newWork",null,contentValues);
        if (l>-1){
            Log.i("NetWorkDBUtils","保存网络数据-成功-："+url+"==="+netString);
            return true;
        }
        Log.i("NetWorkDBUtils","saveOkhttpString保存网络数据-失败-："+url+"==="+netString);
        return false;
    }
}
