package com.home.wanyu.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

import com.home.wanyu.bean.MainBottomControl;
import com.home.wanyu.fragment.CommunicationFragment;
import com.home.wanyu.fragment.HomeFragment;
import com.home.wanyu.fragment.HousekeeperFrgment;
import com.home.wanyu.fragment.MineFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


import static android.R.attr.fragment;

public class MainActivity extends FragmentActivity {
    private int listControlSize;
    private Unbinder unbinder;
    //家，物业管家，个人，圈子的textview
    @BindViews({R.id.main_bottomlayout_home_text,R.id.main_bottomlayout_Property_text,R.id.main_bottomlayout_Communication_text,R.id.main_bottomlayout_Mine_text}) List<TextView>listTextView;

    @BindViews({R.id.main_bottomlayout_home_image,R.id.main_bottomlayout_Property_image,R.id.main_bottomlayout_Communication_image,R.id.main_bottomlayout_Mine_image}) List<ImageView>listImage;

    @BindView(R.id.main_top_frament) RelativeLayout main_top_frament;//fragment布局
    private ArrayList<MainBottomControl> listControl;
    private HomeFragment mHomeFragment;//家
    private HousekeeperFrgment mHousekeeperFrgment;//物业管家
    private CommunicationFragment mCommunicationFragment;//圈子
    private MineFragment mMineFragment;//个人
    private ArrayList<Fragment>listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder=ButterKnife.bind(MainActivity.this);
        initData();
        setSelection(0);
        initFragment();
    }

    //初始化fragment
    private void initFragment() {
        mHomeFragment=HomeFragment.getInstance();
        mHousekeeperFrgment=HousekeeperFrgment.getInstance();
        mCommunicationFragment=CommunicationFragment.getInstance();
        mMineFragment=MineFragment.getInstance();
        listFragment=new ArrayList<>();
        listFragment.add(mHomeFragment);
        listFragment.add(mHousekeeperFrgment);
        listFragment.add(mCommunicationFragment);
        listFragment.add(mMineFragment);
        mHomeFragment=null;mHousekeeperFrgment=null;
        mCommunicationFragment=null;mMineFragment=null;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_top_frament,listFragment.get(0)).commit();
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
        switch (view.getId()){
            case R.id.main_bottomlayout_Communication://圈子
                setSelection(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_top_frament,listFragment.get(2)).commit();
            break;
            case R.id.main_bottomlayout_Home://家
                setSelection(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_top_frament,listFragment.get(0)).commit();
            break;
            case R.id.main_bottomlayout_Mine://个人
                setSelection(3);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_top_frament,listFragment.get(3)).commit();
            break;
            case R.id.main_bottomlayout_Property://物业管家
                setSelection(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_top_frament,listFragment.get(1)).commit();
            break;
        }
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
}
