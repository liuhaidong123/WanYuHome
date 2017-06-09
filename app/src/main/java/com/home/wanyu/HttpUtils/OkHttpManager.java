package com.home.wanyu.HttpUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.haveAddress.Root;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * Created by liuhaidong on 2017/6/6.
 */

public class OkHttpManager {

    private static OkHttpManager mOkHttpManager;
    private Gson mGson;
    private OkHttpClient mOkhttpClient;
    private Handler mHandler;

    private OkHttpManager() {
        mOkhttpClient = new OkHttpClient();
        mOkhttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null) {
            mOkHttpManager = new OkHttpManager();
        }
        return mOkHttpManager;
    }

    /**
     * 普通get方式请求
     *
     * @param url     请求地址
     * @param msg     为了查看方便
     * @param handler
     * @param mwhat
     */
    public void getMethod(String url, final String msg, final Handler handler, final int mwhat) {
        Request request = buildRequest(url, msg, null, HttpMethodType.GET);
        Log.e("", msg + "的地址" + url);
        doRequest(request, msg, handler, mwhat);
    }

    /**
     * 普通post方式请求
     *
     * @param url     请求地址
     * @param msg     为了查看方便
     * @param handler
     * @param mwhat
     */
    public void postMethod(String url, final String msg, Map<String, String> map, final Handler handler, final int mwhat) {
        Request request = buildRequest(url, msg, map, HttpMethodType.POST);
        Log.e("", msg + "的地址" + url);
        doRequest(request, msg, handler, mwhat);

    }

    /**
     * cookie=response.headers().get("Set-Cookie");//这个是从网络上获取的cookie
     * 上传文件（需要添加cookie）request.header("cookie","token="+token)
     *
     * @param url     请求地址
     * @param msg     为了查看方便
     * @param map     参数集合
     * @param files   文件集合
     * @param handler
     * @param mwhat
     */
    public void postFileMethod(String url, final String msg, Map<String, String> map, File[] files, final Handler handler, final int mwhat) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加文件
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Log.e("files[i].getName()",files[i].getName());
                Log.e("files[i].getName()",files[i].getName());
                RequestBody fileBody = RequestBody.create(MediaType.parse(getMediaType(files[i].getName())), files[i]);

                builder.addFormDataPart("文件"+i, files[i].getName(), fileBody);
            }
        }
        //添加参数
        if (map != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=" + entry.getValue() + "&");
            }
            Log.e("url地址=", msg + stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
        }

        Request request = new Request.Builder().url(url).header("Cookie","token="+ UserInfo.userToken).post(builder.build()).build();
        mOkhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("失败原因",msg+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Log.e("成功结果", msg + string);
                    Message message = new Message();
                    message.what = mwhat;
                    message.obj = string;
                    handler.sendMessage(message);
                }
            }
        });

}

    /**
     * 根据文件的名称判断文件的Mine值
     */
    private String getMediaType(String fileName) {
        FileNameMap map = URLConnection.getFileNameMap();
        String contentTypeFor = map.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 网络请求回调
     *
     * @param request
     * @param msg
     * @param handler
     * @param mwhat
     */
    public void doRequest(Request request, final String msg, final Handler handler, final int mwhat) {

        mOkhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("失败原因", msg + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Log.e("成功", msg + string);
                    Message message = new Message();
                    message.what = mwhat;
                    message.obj = string;
                    handler.sendMessage(message);
                }
            }
        });
    }


    /**
     * 构建请求对象
     *
     * @param url
     * @param map
     * @param type
     * @return
     */
    private Request buildRequest(String url, String msg, Map<String, String> map, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {//get方法请求
            builder.get();
        } else if (type == HttpMethodType.POST) {//post方法请求
            builder.post(buildFormData(map, url, msg));
        }
        return builder.build();
    }

    //构建请求所需的参数表单
    private RequestBody buildFormData(Map<String, String> map, String url, String msg) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=" + entry.getValue() + "&");
            }
            Log.e("url地址=", msg + stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
        }
        return builder.build();
    }

}
