package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CarPoolingAda;
import com.home.wanyu.myview.MyListView;

public class CarPoolingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack, mMsg,mCar_post_img;
    private MyListView mListview;
    private CarPoolingAda mAdapter;
    private LinearLayout mCar_Now_ll, mCar_End_ll;
    private TextView mCar_now_tv, mCar_end_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling);
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.car_msg_back);
        mBack.setOnClickListener(this);
//发帖
        mCar_post_img=(ImageView) findViewById(R.id.car_post_activity);
        mCar_post_img.setOnClickListener(this);
        //消息
        mMsg = (ImageView) findViewById(R.id.car_news_img);
        mMsg.setOnClickListener(this);
        //变化背景
        mCar_Now_ll = (LinearLayout) findViewById(R.id.car_now_ll);
        mCar_End_ll = (LinearLayout) findViewById(R.id.car_end_ll);
        mCar_Now_ll.setOnClickListener(this);
        mCar_End_ll.setOnClickListener(this);
        mCar_now_tv = (TextView) findViewById(R.id.car_jin_tv);
        mCar_end_tv = (TextView) findViewById(R.id.car_end_tv);

//adapter
        mListview = (MyListView) findViewById(R.id.car_listview);
        mAdapter = new CarPoolingAda(this);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mMsg.getId()) {//跳转消息
            Intent in = new Intent(this, CircleGiveYouCommentActivity.class);
            startActivity(in);
        } else if (id == mCar_Now_ll.getId()) {//正在进行背景，文字
            mCar_Now_ll.setBackgroundResource(R.color.bg_rect);
            mCar_now_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mCar_End_ll.setBackgroundResource(R.color.white);
            mCar_end_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
        } else if (id == mCar_End_ll.getId()) {//已完成背景，文字
            mCar_Now_ll.setBackgroundResource(R.color.white);
            mCar_now_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            mCar_End_ll.setBackgroundResource(R.color.bg_rect);
            mCar_end_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        }else if (id==mCar_post_img.getId()){//拼车发帖
            Intent intent=new Intent(this,CarPoolingPostActivity.class);
            startActivity(intent);
        }
    }
}
