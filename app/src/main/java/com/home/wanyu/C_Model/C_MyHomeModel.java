package com.home.wanyu.C_Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_getMyFamily;
import com.home.wanyu.bean.Bean_saveMyFamily;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/7/27.
 */
//我的家页面
public class C_MyHomeModel {

    public C_MyHomeModel( IC_MyHome ic_myHome,ILoading iLoading){
        this.ic_myHome=ic_myHome;
        this.iLoading=iLoading;
    }

    final String mAllMyHome=Ip.interface_getAllFamily;//获取我所有的小区接口
    Bean_getMyFamily.MapListBean bean;
    String resStr;
    IC_MyHome ic_myHome;
    ILoading iLoading;//网络请求
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://获取所有的家
                    try {
                        Bean_getMyFamily allFamily =mGson.gson.fromJson(resStr,Bean_getMyFamily.class);
                        ic_myHome.onSuccessAllHome(allFamily);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        iLoading.onGsonError(resStr);
                    }
                    break;
                case 2://切换家
                    try{
                        Bean_saveMyFamily fily=mGson.gson.fromJson(resStr,Bean_saveMyFamily.class);
                        ic_myHome.onSuccessChangeMyHome(fily,bean);
                    }
                    catch (Exception e){
                        iLoading.onGsonError(resStr);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    //获取我所有的家的地址
    public void getAllMyArea(){
        iLoading.onLoading();
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+mAllMyHome,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                iLoading.onStopLoading();
                iLoading.onNetworkError(Ip.interface_getAllFamily);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                iLoading.onStopLoading();
                resStr=response.body().string();
                Log.i("获取我的所有家--",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
    //切换家操作
    public void saveFamiley(final Bean_getMyFamily.MapListBean bean){
        this.bean=bean;
        iLoading.onLoading();
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("familyId",bean.getFamilyId()+"");
        mp.put("personalId",bean.getPersonalId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_saveMyFamily,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                iLoading.onStopLoading();
                iLoading.onNetworkError(Ip.interface_saveMyFamily);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                iLoading.onStopLoading();
                resStr=response.body().string();
                Log.i("关联选择的家--",resStr+"---"+bean.getFamilyName());
                handler.sendEmptyMessage(2);
            }
        });
    }


    public interface IC_MyHome{
        void onSuccessAllHome(Bean_getMyFamily allFamily);//请求成功
        void onSuccessChangeMyHome(Bean_saveMyFamily fily,Bean_getMyFamily.MapListBean bean);//切换家参数fily：服务器返回的bean，参数2：bean：切换后的家的bean
    }
}
