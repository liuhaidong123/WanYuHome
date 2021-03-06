package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

import com.home.wanyu.bean.MainBottomControl;
import com.home.wanyu.fragment.C_HomeFragment;
import com.home.wanyu.fragment.C_MineFragment;
import com.home.wanyu.fragment.CommunicationFragment;
import com.home.wanyu.fragment.HomeFragment;
import com.home.wanyu.fragment.HousekeeperFrgment;
import com.home.wanyu.fragment.MineFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;


import static android.R.attr.fragment;

public class MainActivity extends FragmentActivity {
    private final int ResultCode=100;
    public static int state;//是否刷新的标志
    private Fragment mFragment;
    private int listControlSize;
    private Unbinder unbinder;
    //家，物业管家，个人，圈子的textview
    @BindViews({R.id.main_bottomlayout_home_text,R.id.main_bottomlayout_Property_text,R.id.main_bottomlayout_Communication_text,R.id.main_bottomlayout_Mine_text}) List<TextView>listTextView;

    @BindViews({R.id.main_bottomlayout_home_image,R.id.main_bottomlayout_Property_image,R.id.main_bottomlayout_Communication_image,R.id.main_bottomlayout_Mine_image}) List<ImageView>listImage;

    @BindView(R.id.main_top_frament) RelativeLayout main_top_frament;//fragment布局

    private FragmentManager manager;
    private ArrayList<MainBottomControl> listControl;
//    private HomeFragment mHomeFragment;//家
    C_HomeFragment mHomeFragment;
    private HousekeeperFrgment mHousekeeperFrgment;//物业管家
    private CommunicationFragment mCommunicationFragment;//圈子
    private C_MineFragment mMineFragment;//个人
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        unbinder=ButterKnife.bind(MainActivity.this);
        initData();
        setSelection(0);
        initFragment();
    }

    //初始化fragment
    private void initFragment() {
        manager=getSupportFragmentManager();
        mHomeFragment=new C_HomeFragment();
        mHousekeeperFrgment=new HousekeeperFrgment();
        mCommunicationFragment=new CommunicationFragment();
        mMineFragment=new C_MineFragment();
        mFragment=mHomeFragment;
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.main_top_frament,mHomeFragment);
        transaction.add(R.id.main_top_frament,mHousekeeperFrgment);
        transaction.add(R.id.main_top_frament,mCommunicationFragment);
        transaction.add(R.id.main_top_frament,mMineFragment);
        transaction.hide(mHomeFragment).hide(mHousekeeperFrgment).hide(mCommunicationFragment).hide(mMineFragment).show(mFragment).commit();
    }

    private void initData() {
        listControl=new ArrayList<>();
        listControlSize=listTextView.size();
        MainBottomControl control;
        for (int i=0;i<listControlSize;i++){
            control=new MainBottomControl(listTextView.get(i),listImage.get(i));
            listControl.add(control);
        }
        control=null;
    }
    @OnClick({R.id.main_bottomlayout_Communication,R.id.main_bottomlayout_Home,R.id.main_bottomlayout_Mine,R.id.main_bottomlayout_Property})
    public void Click(View view){
        manager.beginTransaction().hide(mFragment).commit();
        switch (view.getId()){
            case R.id.main_bottomlayout_Communication://圈子
                setSelection(2);
                mFragment=mCommunicationFragment;
                break;
            case R.id.main_bottomlayout_Home://家
                setSelection(0);
                mFragment= mHomeFragment;
                break;
            case R.id.main_bottomlayout_Mine://个人
                setSelection(3);
                mFragment=mMineFragment;
                break;
            case R.id.main_bottomlayout_Property://物业管家
                setSelection(1);
                mFragment= mHousekeeperFrgment;
                break;
        }
        manager.beginTransaction().show(mFragment).commit();
    }
    //设置当前点中点项目
    public void setSelection(int pos){
        for (int i=0;i<listControlSize;i++){
            listControl.get(i).setSelect(false);
        }
        listControl.get(pos).setSelect(true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (listControl!=null&&listControl.size()>0){
            listControl.clear();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==ResultCode){
            switch (requestCode){
                case 200:
                    state=1;
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        state=0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}