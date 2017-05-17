package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Map<String,String> mp=new HashMap<>();
//        List<File> list=new ArrayList<>();
//        okhttp.getOkhttpFileCall(mp,list, "00000").enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                        String resStr=response.body().string();
//                        han
//            }
//        });
   }
}
