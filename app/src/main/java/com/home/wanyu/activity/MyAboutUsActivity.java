package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_AboutUs;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.XCRoundRectImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MyAboutUsActivity extends MyActivity {
    @BindView(R.id.my_settings_aboutOurs_imageview)XCRoundRectImageView my_settings_aboutOurs_imageview;
    @BindView(R.id.my_settings_aboutOurs_name)TextView my_settings_aboutOurs_name;//宇家名字
    @BindView(R.id.my_settings_aboutOurs_pro)TextView my_settings_aboutOurs_pro;//宇家说明
    @BindView(R.id.my_settings_aboutOurs_versionCode)TextView my_settings_aboutOurs_versionCode;//宇家版本号说明

    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_AboutUs aboutUs= mGson.gson.fromJson(resStr,Bean_AboutUs.class);
                        if (aboutUs!=null){
                            Bean_AboutUs.AboutUsBean bean=aboutUs.getAboutUs();
                            if (bean!=null){
                                Picasso.with(con).load(Ip.imagePath+bean.getPicture()).error(R.mipmap.errorphoto).into(my_settings_aboutOurs_imageview);
                                my_settings_aboutOurs_name.setText(bean.getTitle());
                                my_settings_aboutOurs_pro.setText(bean.getContent());
                                my_settings_aboutOurs_versionCode.setText(bean.getVersion());
                            }
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
        initChildView(R.layout.activity_my_about_us);
        ShowChildView(DEFAULTRESID);
        getSerVerData();
    }

    //http://192.168.1.55:8080/smarthome/mobilepub/aboutUs/get.do关于我们
    @Override
    public void getSerVerData() {
        Map<String,String> mp=new HashMap<>();
        okhttp.getCall(Ip.pths+Ip.interface_aboutUs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("关于我们---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
