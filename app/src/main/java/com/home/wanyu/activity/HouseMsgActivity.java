package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.HouseMsgListAda;
import com.home.wanyu.myview.MyListView;

public class HouseMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private HouseMsgListAda msgListAda;
    private MyListView myListView;

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mLocation_search_address_rl;
    private TextView location_tv;
    private RelativeLayout mLocation_search_area_rl;
    private ImageView mPost_Card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_msg);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_mag_back);
        mBack.setOnClickListener(this);
        myListView = (MyListView) findViewById(R.id.house_location_area_refresh_listview);
        msgListAda = new HouseMsgListAda(this);
        myListView.setAdapter(msgListAda);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HouseMsgActivity.this, HouseLookMessageActivity.class);
                startActivity(intent);
            }
        });

        //定位地址
        mLocation_search_address_rl = (RelativeLayout) findViewById(R.id.house_location_address);
        mLocation_search_address_rl.setOnClickListener(this);
        location_tv = (TextView) findViewById(R.id.location_msg);

        //搜索小区名称
        mLocation_search_area_rl = (RelativeLayout) findViewById(R.id.input_area_name);
        mLocation_search_area_rl.setOnClickListener(this);

        //发帖
        mPost_Card = (ImageView) findViewById(R.id.house_post_card);
        mPost_Card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mLocation_search_address_rl.getId()) {//跳转到搜索定位地址
            Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
            intent.putExtra("type", 1);
            startActivityForResult(intent, 123);//搜索城市会城市传回
        } else if (id == mLocation_search_area_rl.getId()) {//跳转到搜索小区
            Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("city", location_tv.getText().toString());
            startActivity(intent);
        }else if(id==mPost_Card.getId()){//发帖
            startActivity(new Intent(this,HousePostCardActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
