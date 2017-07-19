package com.home.wanyu.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.AddAddressActivity;
import com.home.wanyu.activity.CommercialActivity;
import com.home.wanyu.activity.DecorationActivity;
import com.home.wanyu.activity.ExpressActivity;
import com.home.wanyu.activity.HomeServiceActivity;
import com.home.wanyu.activity.HouseMsgActivity;
import com.home.wanyu.activity.HouseMsgActivity2;
import com.home.wanyu.activity.LifeMoneyActivity;
import com.home.wanyu.activity.LifeMoneyActivity2;
import com.home.wanyu.activity.OrderActivity;
import com.home.wanyu.activity.OrderMessageActivity;
import com.home.wanyu.activity.RepairActivity;
import com.home.wanyu.apater.MyExpandableAda;
import com.home.wanyu.apater.MyGridViewAda;
import com.home.wanyu.apater.PropertyViewPagerAda;
import com.home.wanyu.bean.MyGridBean;
import com.home.wanyu.bean.haveAddress.Root;
import com.home.wanyu.myview.MyExpandableListview;
import com.home.wanyu.myview.MyGridView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//物业管家
public class HousekeeperFrgment extends Fragment implements  View.OnClickListener {

    private RollPagerView mRollPagerView;

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mScrollRelative;

    private LinearLayout mRepair_ll;
    private LinearLayout mOrder_ll;
    private LinearLayout mLife_money_ll;
    private LinearLayout mExpress_ll;
    private LinearLayout mCommercial_ll;
    private LinearLayout mDecoration_ll;
    private LinearLayout mHomeService_ll;
    private LinearLayout mHouseMsg_ll;


    private HttpTools mHttptools;
    private Root mHaveRoot;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100) {//判断有没有地址
                Object o = msg.obj;
                mRefresh.setRefreshing(false);
                if (o != null && o instanceof Root) {
                    mHaveRoot = (Root) o;
                }
            }


        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window =  getActivity(). getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_housekeeper, null, false);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView(vi);
        return vi;
    }

    public void initView(View view) {
        mRollPagerView = (RollPagerView) view.findViewById(R.id.my_pagerview);
        mRollPagerView.setPlayDelay(3000);
        mRollPagerView.setAnimationDurtion(500);
        mRollPagerView.setAdapter(new RollAdapter(mRollPagerView));
        //设置指示器（顺序依次）
        mRollPagerView.setHintView(new ColorPointHintView(this.getActivity(), Color.BLACK, Color.WHITE));
        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "下标：" + position, Toast.LENGTH_SHORT).show();
            }
        });


        //将scrollview定位到顶部
        mScrollRelative = (RelativeLayout) view.findViewById(R.id.scroll_rl);
        mScrollRelative.setFocusable(true);
        mScrollRelative.setFocusableInTouchMode(true);
        mScrollRelative.requestFocus();
        //刷新

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.property_refresh);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHttptools.haveUserAddress(mHandler, UserInfo.userToken);
            }
        });

        //报事报修
        mRepair_ll = (LinearLayout) view.findViewById(R.id.property_repair);
        mRepair_ll.setOnClickListener(this);
        //物业账单
        mOrder_ll = (LinearLayout) view.findViewById(R.id.property_order);
        mOrder_ll.setOnClickListener(this);
        //生活缴费
        mLife_money_ll = (LinearLayout) view.findViewById(R.id.property_life_money);
        mLife_money_ll.setOnClickListener(this);
        //快递收发
        mExpress_ll = (LinearLayout) view.findViewById(R.id.property_express);
        mExpress_ll.setOnClickListener(this);

        //小区商户
        mCommercial_ll = (LinearLayout) view.findViewById(R.id.property_commercial);
        mCommercial_ll.setOnClickListener(this);
        //装修
        mDecoration_ll = (LinearLayout) view.findViewById(R.id.property_decoration);
        mDecoration_ll.setOnClickListener(this);
        //家政服务
        mHomeService_ll = (LinearLayout) view.findViewById(R.id.property_home_srevice);
        mHomeService_ll.setOnClickListener(this);

        //租房信息
        mHouseMsg_ll = (LinearLayout) view.findViewById(R.id.property_home_message);
        mHouseMsg_ll.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mRepair_ll.getId()) {//报事报修
            startActivity(new Intent(getActivity(), RepairActivity.class));
        } else if (id == mOrder_ll.getId()) {//物业账单
            if (mHaveRoot != null && mHaveRoot.getCode().equals("-1")) {//没有地址，显示需要添加地址的页面
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("order", 22);
                startActivity(intent);
            } else if (mHaveRoot != null && mHaveRoot.getCode().equals("0")) {//有地址的话，直接显示物业账单信息
                startActivity(new Intent(getActivity(), OrderMessageActivity.class));
            } else {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mLife_money_ll.getId()) {//生活缴费

            if (mHaveRoot != null && mHaveRoot.getCode().equals("-1")) {//没有地址，显示需要添加地址的页面
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("order", 11);
                startActivity(intent);
            } else if (mHaveRoot != null && mHaveRoot.getCode().equals("0")) {//有地址的话，直接显示物业账单信息
                startActivity(new Intent(getActivity(), LifeMoneyActivity2.class));
            } else {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mExpress_ll.getId()) {//快递收发
            startActivity(new Intent(getActivity(), ExpressActivity.class));
        } else if (id == mCommercial_ll.getId()) {//小区商户
            startActivity(new Intent(getActivity(), CommercialActivity.class));
        } else if (id == mDecoration_ll.getId()) {//装修
            startActivity(new Intent(getActivity(), DecorationActivity.class));
        } else if (id == mHomeService_ll.getId()) {//家政服务
            startActivity(new Intent(getActivity(), HomeServiceActivity.class));
        } else if (id == mHouseMsg_ll.getId()) {//租房信息
            startActivity(new Intent(getActivity(), HouseMsgActivity2.class));
        }

    }

    /**
     * 广告适配器
     */
    class RollAdapter extends LoopPagerAdapter {
        private int[] imgs = {R.mipmap.banner};

        public RollAdapter(RollPagerView viewPager) {
            super(viewPager);
        }


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mRollPagerView.pause();
        mRollPagerView.resume();
        mRollPagerView.isPlaying();
    }
}
