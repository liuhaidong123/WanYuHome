package com.home.wanyu.C_Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.bean.Bean_DeleteDevice;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/7/28.
 */

public class C_MyHomeDeviceDeleteModel {
    final String IDeleteDevice=Ip.interface_DeviceDelete;
    ILoading iLoading;
    IDeviceDelete iDeviceDelete;
    String resStr;
    List<Bean_AllDevice.EquipmentListBean> lts;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                try{
                    Bean_DeleteDevice bean_deleteDevice= mGson.gson.fromJson(resStr,  Bean_DeleteDevice.class);
                    iDeviceDelete.onSuccess(bean_deleteDevice,lts);
                }
                catch (Exception e){
                    iLoading.onGsonError(resStr);
                    e.printStackTrace();
                }
            }
        }
    };

    public C_MyHomeDeviceDeleteModel(ILoading iLoading, IDeviceDelete iDeviceDelete){
        this.iLoading=iLoading;
        this.iDeviceDelete=iDeviceDelete;
    }

    public void delete(List<Bean_AllDevice.EquipmentListBean> lt){
        lts=lt;
        String str="";
        for (int i=0;i<lt.size();i++){
                str+=lt.get(i).getId()+",";
        }
        if (!"".equals(str)){
            iLoading.onLoading();
            str=str.substring(0,str.length()-1);
            Map<String,String> mp=new HashMap<>();
            Log.i("ids---",str);
            mp.put("ids",str);
            mp.put("token", UserInfo.userToken);
            okhttp.getCall(Ip.serverPath+IDeleteDevice,mp,okhttp.OK_GET).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                        iLoading.onStopLoading();
                        iLoading.onNetworkError(IDeleteDevice);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    iLoading.onStopLoading();
                    resStr=response.body().string();
                    Log.i("删除设备--",resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }
    public interface IDeviceDelete{
        void onSuccess(Bean_DeleteDevice bean_deleteDevice,List<Bean_AllDevice.EquipmentListBean> lt);
    }
}
