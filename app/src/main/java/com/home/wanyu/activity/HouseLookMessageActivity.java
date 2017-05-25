package com.home.wanyu.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.HouseLookConfigureAda;
import com.home.wanyu.apater.HouseViewpagerAda;
import com.home.wanyu.bean.HouseLookConfigure;
import com.home.wanyu.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class HouseLookMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ViewPager mViewpager;
    private HouseViewpagerAda mViewPagerAda;
    private List<Integer> mImgList = new ArrayList<>();
    private TextView mImg_num;

    private MyGridView myGridView;
    private HouseLookConfigureAda lookConfigureAda;
    private List<HouseLookConfigure> mList = new ArrayList<>();

    private Button mCall_btn;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_look_message);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_look_back);
        mBack.setOnClickListener(this);

        //轮播图
        mViewpager = (ViewPager) findViewById(R.id.look_msg_viewpager);
        mImgList.add(R.mipmap.main_background4);
        mImgList.add(R.mipmap.main_background5);
        mImgList.add(R.mipmap.main_background6);
        mViewPagerAda = new HouseViewpagerAda(mImgList, this);
        mViewpager.setAdapter(mViewPagerAda);

        mImg_num = (TextView) findViewById(R.id.img_num);
        if (mImgList.size() == 1) {
            mImg_num.setText("");
        } else {
            //viewpager滑动监听
            mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mImg_num.setText(position + 1 + "" + "/8");

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        myGridView = (MyGridView) findViewById(R.id.look_configure_gridview);
        mList.add(new HouseLookConfigure("电视", R.mipmap.television, 1));
        mList.add(new HouseLookConfigure("宽带", R.mipmap.broadband, 2));
        mList.add(new HouseLookConfigure("洗衣机", R.mipmap.washer, 3));
        mList.add(new HouseLookConfigure("床", R.mipmap.bed, 4));
        mList.add(new HouseLookConfigure("热水器", R.mipmap.heater, 5));
        mList.add(new HouseLookConfigure("供暖", R.mipmap.heating, 6));
        mList.add(new HouseLookConfigure("柜子", R.mipmap.cabinet, 7));
        mList.add(new HouseLookConfigure("沙发", R.mipmap.sofa, 8));
        mList.add(new HouseLookConfigure("冰箱", R.mipmap.fridge, 9));
        mList.add(new HouseLookConfigure("空调", R.mipmap.air_conditioner, 10));

        lookConfigureAda = new HouseLookConfigureAda(this, mList);
        myGridView.setAdapter(lookConfigureAda);

        //预约
        mCall_btn = (Button) findViewById(R.id.look_house_btn);
        mCall_btn.setOnClickListener(this);

        view = LayoutInflater.from(this).inflate(R.layout.look_house_call_alert, null);
        builder = new AlertDialog.Builder(this);
        alert = builder.create();
        alert.setView(view);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
            //预约
        } else if (id == mCall_btn.getId()) {
            alert.show();

        }
    }
}
