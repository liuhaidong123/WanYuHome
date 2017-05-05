package com.home.wanyu.lzhUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by wanyu on 2017/5/4.
 */
//网络相关的工具
public class NetWorkUtils {
    public static String getWifiInfo(Context context){
        String wifiName="请先连接到wifi";
        if (isWifiConnected(context)){
            WifiManager wifiManager= (WifiManager) context.getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifiName=wifiInfo.getSSID();
        }
       return wifiName;
    }

    //检查是否已经裂解到了wifi
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }

        return false ;
    }

}
