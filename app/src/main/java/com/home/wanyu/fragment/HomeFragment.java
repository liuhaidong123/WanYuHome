package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.activity.HomeDeviceAddActivity;
import com.home.wanyu.activity.HomeDeviceAddWifiActivity;
import com.home.wanyu.activity.HomeSenceAddSenceActivity;
import com.home.wanyu.activity.TestActivity;
import com.home.wanyu.lzhUtils.MyToast;
import com.home.wanyu.lzhView.MyFloatingView;
import com.home.wanyu.zxing.app.CaptureActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//家
public class HomeFragment extends Fragment {
    private int cur;//0情景，1设备
    private Unbinder unbinder;
    @BindView(R.id.fragment_home_top_qj) TextView fragment_home_top_qj;
    @BindView(R.id.fragment_home_top_shebei) TextView fragment_home_top_shebei;
    private HomeFragmentDevice homeFragmentDevice;//设备
    private HomeFragmentScene homeFragmentScene;//情景
    private Fragment mFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home,null);
        unbinder= ButterKnife.bind(this,vi);
        initView();
        return vi;
    }
    private void initView() {

        fragment_home_top_qj.setSelected(true);
        fragment_home_top_shebei.setSelected(false);
        homeFragmentDevice=new HomeFragmentDevice();
        homeFragmentScene=new HomeFragmentScene();


        FragmentTransaction transition=getActivity().getSupportFragmentManager().beginTransaction();
        transition.add(R.id.fragment_home_bottom_layout,homeFragmentScene);
        transition.add(R.id.fragment_home_bottom_layout,homeFragmentDevice);

        mFragment=homeFragmentScene;
        cur=0;
        transition.hide(homeFragmentScene).hide(homeFragmentDevice).show(mFragment).commit();
    }
    @OnClick({R.id.fragment_home_top_qj,R.id.fragment_home_top_shebei,R.id.fragment_home_change,R.id.fragment_home_top_add})
    public void click(View v){
        switch (v.getId()){
            case R.id.fragment_home_top_qj:
                getActivity().getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
                fragment_home_top_qj.setSelected(true);
                fragment_home_top_shebei.setSelected(false);
                mFragment=homeFragmentScene;
                getActivity().getSupportFragmentManager().beginTransaction().show(mFragment).commit();
                cur=0;
                break;
            case R.id.fragment_home_top_shebei:
                getActivity().getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
                fragment_home_top_qj.setSelected(false);
                fragment_home_top_shebei.setSelected(true);
                mFragment=homeFragmentDevice;
                getActivity().getSupportFragmentManager().beginTransaction().show(mFragment).commit();
                cur=1;
                break;

            case R.id.fragment_home_change:
                MyToast.DebugToast(getActivity(),"切换实景");
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;

            case R.id.fragment_home_top_add:
                if (fragment_home_top_qj.isSelected()){//当前显示的是情景，点击后进入添加情景页面
                    MyToast.DebugToast(getActivity(),"添加情景");
                    startActivity(new Intent(getActivity(), HomeSenceAddSenceActivity.class));
                }
                else {//当前显示的是设备页面，进入添加设备页面
                    startActivity(new Intent(getActivity(), CaptureActivity.class));
                }
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

}
