package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;

public class HouseSearchAddessAndAreaActivity2 extends AppCompatActivity {
    private ImageView mBack;
    private TextView mLocation_tv, mBeijin_tv, mBaoding_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_search_addess_and_area2);
        mBack = (ImageView) findViewById(R.id.address_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取传过开的定位城市
        mLocation_tv = (TextView) findViewById(R.id.location_city_msg);
        if (getIntent().getStringExtra("city").equals("")) {
            mLocation_tv.setText("未定位");
        } else {
            mLocation_tv.setText(getIntent().getStringExtra("city"));
        }
        //北京
        mBeijin_tv = (TextView) findViewById(R.id.beijin_city_tv);
        mBeijin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = getIntent();
                it.putExtra("code", "110100");//这个是北京城区的编码，而不是北京市的编码
                it.putExtra("city", "北京市");
                setResult(RESULT_OK, it);
                finish();
            }
        });

        //保定
        mBaoding_tv = (TextView) findViewById(R.id.baoding_city_tv);
        mBaoding_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = getIntent();
                it.putExtra("code", "130600");//这个是北京城区的编码，而不是北京市的编码
                it.putExtra("city", "保定市");
                setResult(RESULT_OK, it);
                finish();
            }
        });
    }
}
