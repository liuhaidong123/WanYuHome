package com.home.wanyu.C_Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_AllDevice;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wanyu on 2017/7/28.
 */

public class C_MyHomeDeviceManagerModel {
    IAllDeviceModel iAllDeviceModel;
    final String IMyDevice=Ip.interface_HomeScene_getAllDevice;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                try{
                    Bean_AllDevice allDevice= mGson.gson.fromJson(resStr,Bean_AllDevice.class);
                    iAllDeviceModel.onSuccess(allDevice);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    };
    ILoading iLoading;
    String resStr;
    public C_MyHomeDeviceManagerModel(ILoading iLoading,IAllDeviceModel iAllDeviceModel){
        this.iLoading=iLoading;
        this.iAllDeviceModel=iAllDeviceModel;
    }

    //获取所有设备
    public void getMyDevice(){
        iLoading.onLoading();
        HashMap<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+IMyDevice,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                iLoading.onStopLoading();
                iLoading.onNetworkError(IMyDevice);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                iLoading.onStopLoading();
                resStr=response.body().string();
                hand.sendEmptyMessage(1);
                Log.i("获取所有设备接口---",resStr);
            }
        });
    }

    public interface IAllDeviceModel{
        void onSuccess(Bean_AllDevice allDevice);
    }
}
