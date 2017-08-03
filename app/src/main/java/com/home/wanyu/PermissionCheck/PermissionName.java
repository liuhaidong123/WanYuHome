package com.home.wanyu.PermissionCheck;

import android.Manifest;

/**
 * Created by wanyu on 2017/7/5.
 */

public interface PermissionName {
    String READ_EXTERNAL_STORAGE= Manifest.permission.READ_EXTERNAL_STORAGE;//读取存储卡
    String WRITE_EXTERNAL_STORAGE=Manifest.permission.WRITE_EXTERNAL_STORAGE;//写入存储卡
    String CAMERA=Manifest.permission.CAMERA;//相机
}
