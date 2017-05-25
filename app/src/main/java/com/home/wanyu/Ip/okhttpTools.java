package com.home.wanyu.Ip;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.User.UserInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/5/8.
 */

public class okhttpTools {
    private Handler hand;
    private int code;
    public String mResponStr;
    public String logStr;
    private String uri;
    //获取情景及房间的接口
    // http://192.168.1.55:8080/smarthome/mobileapi/scene/ findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void getSceneAndRoom( Handler handler, int RequestCode){
        this.hand=handler;
        this.code=RequestCode;
        HashMap<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        String url=Ip.serverPath+Ip.interface_Home_getSceneAndRoom;
        okhttp.getCall(url,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponStr=response.body().string();
                Log.i("获取情景以及房间返回----",mResponStr);
                hand.sendEmptyMessage(code);
            }
        });
    }

    //家》设备页面当前选中的房间一键开启功能接口
    // 家》设备页面当前选中的房间一键关闭功能接口state=0：开启，1关闭
    public void setRoomOnAndOff(final Handler handler, int RequestCode, HashMap<String,String>mp){
        this.hand=handler;
        this.code=RequestCode;
        okhttp.getCall(Ip.serverPath+Ip.interface_HomeRoom_OpenAndOn,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponStr=response.body().string();
                Log.i("房间设备开关操作返回--",mResponStr);
                hand.sendEmptyMessage(code);
            }
        });
    }



    public void getServerData(Handler handler, int RequestCode, String url,HashMap<String,String>mp, String logSt){
        this.hand=handler;
        this.code=RequestCode;
        this.logStr=logSt;
        this.uri=url;
        okhttp.getCall(uri,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponStr=response.body().string();
                Log.i(logStr,mResponStr);
                hand.sendEmptyMessage(code);
            }
        });
    }
    public void getServerDataPost(Handler handler, int RequestCode, String url,HashMap<String,String>mp, String logSt){
        this.hand=handler;
        this.code=RequestCode;
        this.logStr=logSt;
        this.uri=url;
        okhttp.getCall(uri,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponStr=response.body().string();
                Log.i(logStr,mResponStr);
                hand.sendEmptyMessage(code);
            }
        });
    }
}
