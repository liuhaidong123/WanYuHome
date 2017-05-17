package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CommunityListViewAda;
import com.home.wanyu.myview.MyListView;

public class CommunityMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private MyListView myListView;
    private CommunityListViewAda mAdapter;

    private ImageView mPost_img;
    private LinearLayout mMy_start_ll, mMy_end_ll;
    private TextView mStart_tv, mEnd_tv;

    private ImageView mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_message);
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.community_msg_back);
        mBack.setOnClickListener(this);
        //adaper
        myListView = (MyListView) findViewById(R.id.community_listview);
        mAdapter = new CommunityListViewAda(this);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommunityMessageActivity.this, CommunityCommentActivity.class);
                startActivity(intent);
            }
        });
        //发贴
        mPost_img = (ImageView) findViewById(R.id.community_post_activity);
        mPost_img.setOnClickListener(this);

//正在进行，活动结束
        mMy_start_ll = (LinearLayout) findViewById(R.id.community_now_ll);
        mMy_start_ll.setOnClickListener(this);
        mMy_end_ll = (LinearLayout) findViewById(R.id.community_ago_ll);
        mMy_end_ll.setOnClickListener(this);
        mStart_tv = (TextView) findViewById(R.id.order_year_tv);
        mEnd_tv = (TextView) findViewById(R.id.order_month_tv);

//消息
        mMsg = (ImageView) findViewById(R.id.community_news_img);
        mMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mPost_img.getId()) {
            startActivity(new Intent(this, CommunityPostActivity.class));
            //正在进行，
        } else if (id == mMy_start_ll.getId()) {
            mMy_start_ll.setBackgroundResource(R.color.bg_rect);
            mStart_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMy_end_ll.setBackgroundResource(R.color.white);
            mEnd_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            //活动结束
        } else if (id == mMy_end_ll.getId()) {
            mMy_start_ll.setBackgroundResource(R.color.white);
            mStart_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            mMy_end_ll.setBackgroundResource(R.color.bg_rect);
            mEnd_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (id == mMsg.getId()) {//消息
            Intent intent = new Intent(this, CircleGiveYouCommentActivity.class);
            startActivity(intent);
        }
    }


}
