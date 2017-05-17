package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.home.wanyu.R;

public class CarPoolingMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private LinearLayout mCar_msg_comment_ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling_msg);
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.car_msg_back);
        mback.setOnClickListener(this);

        //评论
        mCar_msg_comment_ll= (LinearLayout) findViewById(R.id.car_msg_comment_ll);
        mCar_msg_comment_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
            //评论
        }else if (id==mCar_msg_comment_ll.getId()){
            Intent intent=new Intent(this,CarPoolingCommentActivity.class);
            startActivity(intent);
        }
    }
}
