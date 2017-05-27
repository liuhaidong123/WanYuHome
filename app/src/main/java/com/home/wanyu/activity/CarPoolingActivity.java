package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CarPoolingAda;
import com.home.wanyu.bean.carPoolingList.Result;
import com.home.wanyu.bean.carPoolingList.Root;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class CarPoolingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack, mMsg, mCar_post_img;
    private MyListView mListview;
    private CarPoolingAda mAdapter;
    private List<Result> mNowlist = new ArrayList<>();
    private List<Result> mEndlist = new ArrayList<>();
    private LinearLayout mCar_Now_ll, mCar_End_ll;
    private TextView mCar_now_tv, mCar_end_tv;
    private SwipeRefreshLayout mrefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;
    private int over = 1;//正在进行，已结束
    private int start = 0;
    private int limit = 10;

    private int flag = 1;//1刷新  2加载

    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 126) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (over==1){//正在进行

                        if (flag == 1) {//刷新
                            MyDialog.stopDia();
                            mrefresh.setRefreshing(false);
                            mNowlist = root.getResult();
                            mAdapter.setMlist(mNowlist, over);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            List<Result> list = new ArrayList<>();
                            list = root.getResult();
                            mNowlist.addAll(list);
                            mAdapter.setMlist(mNowlist, over);
                            mAdapter.notifyDataSetChanged();
                        }

                    }else {//结束活动

                        if (flag == 1) {//刷新
                            MyDialog.stopDia();
                            mrefresh.setRefreshing(false);
                            mEndlist = root.getResult();
                            mAdapter.setMlist(mEndlist, over);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            List<Result> list = new ArrayList<>();
                            list = root.getResult();
                            mEndlist.addAll(list);
                            mAdapter.setMlist(mEndlist, over);
                            mAdapter.notifyDataSetChanged();
                        }

                    }


                    if (root.getResult().size() < 10) {
                        mBar.setVisibility(View.INVISIBLE);
                        mMore_rl.setVisibility(View.GONE);
                    } else {
                        mBar.setVisibility(View.INVISIBLE);
                        mMore_rl.setVisibility(View.VISIBLE);
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling);
        mHttptools = HttpTools.getHttpToolsInstance();
       // mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.car_msg_back);
        mBack.setOnClickListener(this);

        mrefresh = (SwipeRefreshLayout) findViewById(R.id.car_refresh_layout);
        mrefresh.setRefreshing(true);
        mrefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 1;
                start = 0;
                mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页
            }
        });

        mMore_rl = (RelativeLayout) findViewById(R.id.many_relative);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.pbLocate);

//发帖
        mCar_post_img = (ImageView) findViewById(R.id.car_post_activity);
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
        mAdapter = new CarPoolingAda(this, mNowlist);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mMsg.getId()) {//跳转消息
            Intent in = new Intent(this, CircleGiveYouCommentActivity.class);
            in.putExtra("type",2);
            startActivity(in);
        } else if (id == mCar_Now_ll.getId()) {//正在进行背景，文字
            over = 1;
            if (mNowlist.size() != 0) {
                mAdapter.setMlist(mNowlist, over);
                mAdapter.notifyDataSetChanged();
                if (mNowlist.size()%10==0){
                    mBar.setVisibility(View.INVISIBLE);
                    mMore_rl.setVisibility(View.VISIBLE);
                }else {
                    mBar.setVisibility(View.INVISIBLE);
                    mMore_rl.setVisibility(View.GONE);
                }
            } else {
                start = 0;
                flag = 1;
                MyDialog.showDialog(this);
                mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页
            }



            mCar_Now_ll.setBackgroundResource(R.color.bg_rect);
            mCar_now_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mCar_End_ll.setBackgroundResource(R.color.white);
            mCar_end_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
        } else if (id == mCar_End_ll.getId()) {//已完成背景，文字
            over = 2;
            if (mEndlist.size() != 0) {
                mAdapter.setMlist(mEndlist, over);
                mAdapter.notifyDataSetChanged();

                if (mEndlist.size()%10==0){
                    mBar.setVisibility(View.INVISIBLE);
                    mMore_rl.setVisibility(View.VISIBLE);
                }else {
                    mBar.setVisibility(View.INVISIBLE);
                    mMore_rl.setVisibility(View.GONE);
                }
            } else {
                start = 0;
                flag = 1;
                MyDialog.showDialog(this);
                mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页
            }


            mCar_Now_ll.setBackgroundResource(R.color.white);
            mCar_now_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            mCar_End_ll.setBackgroundResource(R.color.bg_rect);
            mCar_end_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (id == mCar_post_img.getId()) {//拼车发帖
            Intent intent = new Intent(this, CarPoolingPostActivity.class);
            startActivity(intent);
        } else if (id == mMore_rl.getId()) {//加载更多
            mBar.setVisibility(View.VISIBLE);
            flag = 2;
            start += 10;
            mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        start = 0;
        flag = 1;
        mHttptools.getCarPoolingList(mHandler, UserInfo.userToken, over, start, limit);//社区平车首页

    }
}
