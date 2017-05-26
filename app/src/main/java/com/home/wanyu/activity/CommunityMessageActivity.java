package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CommunityListViewAda;
import com.home.wanyu.bean.getAreaActivityList.Result;
import com.home.wanyu.bean.getAreaActivityList.Root;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

//社区活动列表
public class CommunityMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private MyListView myListView;
    private CommunityListViewAda mAdapter;
    private List<Result> mNowList = new ArrayList<>();
    private List<Result> mEndList = new ArrayList<>();

    private ImageView mPost_img;
    private LinearLayout mMy_start_ll, mMy_end_ll;
    private TextView mStart_tv, mEnd_tv;

    private ImageView mMsg;//消息

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;
private long userId;
    private int start = 0, limit = 10, over = 1;//over=1,正在进行，over=2活动结束
    private int check = 1;//1是刷新，2是加载更多
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 118) {//活动列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mRefresh.setRefreshing(false);
                    MyDialog.stopDia();
                    Root root = (Root) o;
                    List<Result> list = new ArrayList<>();
                    list = root.getResult();
                    if (check == 1) {//刷新
                        if (over == 1) {
                            userId=root.getUserid();
                            mNowList.clear();
                            mNowList.addAll(list);
                            Log.e("mNowList 的大小",mNowList.size()+"");
                            mAdapter.setmList(mNowList, over,userId);
                            mAdapter.notifyDataSetChanged();


                        } else if (over == 2) {
                            mEndList.clear();
                            mEndList.addAll(list);
                            mAdapter.setmList(mEndList, over,userId);
                            mAdapter.notifyDataSetChanged();
                        }

                    } else if (check == 2) {//加载更多
                        if (over == 1) {
                            mNowList.addAll(list);
                            mAdapter.setmList(mNowList, over,userId);
                            mAdapter.notifyDataSetChanged();
                        } else if (over == 2) {
                            mEndList.addAll(list);
                            mAdapter.setmList(mEndList, over,userId);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                        if (list.size() < 10) {
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        } else {
                            mMore_rl.setVisibility(View.VISIBLE);
                            mBar.setVisibility(View.INVISIBLE);
                        }


                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_message);
        mHttptools = HttpTools.getHttpToolsInstance();
        //mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.community_msg_back);
        mBack.setOnClickListener(this);
        //adaper
        myListView = (MyListView) findViewById(R.id.community_listview);
        mAdapter = new CommunityListViewAda(this, mNowList);
        myListView.setAdapter(mAdapter);
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
//下拉刷新
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.community_refresh_layout);
        mRefresh.setRefreshing(true);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                check = 1;
                start = 0;
                mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);//获取活动列表
            }
        });
//加载更多
        mMore_rl = (RelativeLayout) findViewById(R.id.many_relative);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.pbLocate);
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
            over = 1;
            if (mNowList.size()==0) {
                Log.e("mNowList 点击的大小",mNowList.size()+"");
                MyDialog.showDialog(this);
                start = 0;
                mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);//获取活动列表
            } else {
                Log.e("mNowList 点击的大小",mNowList.size()+"");
                mAdapter.setmList(mNowList, over,userId);
                mAdapter.notifyDataSetChanged();
                if (mNowList.size()%10==0){
                    mMore_rl.setVisibility(View.VISIBLE);
                    mBar.setVisibility(View.INVISIBLE);
                }else {
                    mMore_rl.setVisibility(View.GONE);
                    mBar.setVisibility(View.INVISIBLE);
                }
            }

            mMy_start_ll.setBackgroundResource(R.color.bg_rect);
            mStart_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMy_end_ll.setBackgroundResource(R.color.white);
            mEnd_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            //活动结束
        } else if (id == mMy_end_ll.getId()) {
            over = 2;

            if (mEndList.size()==0) {
                Log.e("mEndList 点击的大小",mEndList.size()+"");
                MyDialog.showDialog(this);
                start = 0;
                mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);//获取活动列表
            } else {
                Log.e("mEndList 点击的大小",mEndList.size()+"");
                mAdapter.setmList(mEndList, over,userId);
                mAdapter.notifyDataSetChanged();
                if (mEndList.size()%10==0){
                    mMore_rl.setVisibility(View.VISIBLE);
                    mBar.setVisibility(View.INVISIBLE);
                }else {
                    mMore_rl.setVisibility(View.GONE);
                    mBar.setVisibility(View.INVISIBLE);
                }
            }

            mMy_start_ll.setBackgroundResource(R.color.white);
            mStart_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mMy_end_ll.setBackgroundResource(R.color.bg_rect);
            mEnd_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (id == mMsg.getId()) {//消息
            Intent intent = new Intent(this, CircleGiveYouCommentActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
        } else if (id == mMore_rl.getId()) {//加载更多
            check =2;
            start += 10;
            mBar.setVisibility(View.VISIBLE);
            mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);//获取活动列表
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        start=0;
        check = 1;
        //获取活动列表
        mHttptools.getAreaActivityList(mHandler, UserInfo.userToken, over, start, limit);//获取活动列表

    }
}
