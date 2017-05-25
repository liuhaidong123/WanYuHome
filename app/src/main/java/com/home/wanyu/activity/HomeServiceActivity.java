package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.apater.HomeServiceAda;
import com.home.wanyu.myview.MyListView;

public class HomeServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private MyListView myListView;
    private HomeServiceAda mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.HomeService_msg_back);
        mBack.setOnClickListener(this);
        myListView = (MyListView) findViewById(R.id.homeservice_listview);
        mAdapter = new HomeServiceAda(this);
        myListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }
}
