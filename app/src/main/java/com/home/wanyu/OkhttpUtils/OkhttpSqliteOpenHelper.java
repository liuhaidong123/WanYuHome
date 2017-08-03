package com.home.wanyu.OkhttpUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanyu on 2017/7/27.
 */
//网络请求缓存
public class OkhttpSqliteOpenHelper extends SQLiteOpenHelper {

    public OkhttpSqliteOpenHelper(Context context) {
        super(context, "network.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table network(name varchar(200) PRIMARY KEY AUTOINCREMENT,values varchar(2000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
