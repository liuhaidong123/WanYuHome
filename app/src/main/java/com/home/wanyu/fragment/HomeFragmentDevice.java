package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.lzhUtils.WindowUtils;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//home中的设备fragment
public class HomeFragmentDevice extends Fragment implements HomeFragment.HomeData {
//
private Unbinder unbinder;
    private MyAdapter adapter;
    @BindView(R.id.fragment_home_device_viewpager)
    ViewPager fragment_home_device_viewpager;
    @BindView(R.id.fragment_home_device_tablayout)
    TabLayout fragment_home_device_tablayout;
//    @BindArray(R.array.homeDeviceString) String[]Sence;
    private ArrayList<String>listTable;//获取到的标题个数
    private ArrayList<HomeFragmentDevicePager> listFragment;

    private ArrayList<Bean_SceneAndRoom.RoomListBean>listRoom;//设备数据源
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_device,null);
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }

    private void initData() {
        if (listRoom!=null&&listRoom.size()>0){
            int size=listRoom.size();
            listTable=new ArrayList<>();
            listFragment=new ArrayList<>();
            for (int i=0;i<size;i++){
                listTable.add(listRoom.get(i).getRoomName()==null|"".equals(listRoom.get(i).getRoomName())?"未命名":listRoom.get(i).getRoomName());
                HomeFragmentDevicePager mFrag=new HomeFragmentDevicePager();
                mFrag.sendMsg(listRoom.get(i));
                listFragment.add(mFrag);
                fragment_home_device_tablayout.addTab(fragment_home_device_tablayout.newTab().setText(listTable.get(i)));
            }
        }
            adapter=new MyAdapter(getChildFragmentManager());
            fragment_home_device_viewpager.setAdapter(adapter);
            fragment_home_device_tablayout.setupWithViewPager(fragment_home_device_viewpager,true);
            listFragment.get(0).setSceneName(listTable.get(0),0);

            fragment_home_device_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String title=listTable.get(tab.getPosition());
                listFragment.get(tab.getPosition()).setSceneName(title,tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }



    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment==null?0:listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTable.get(position);
        }
    }
    //刷新数据用
    @Override
    public void sendMsg(Object msg) {
        if (msg!=null){
            listRoom= (ArrayList<Bean_SceneAndRoom.RoomListBean>) msg;
            if (listRoom!=null&&listRoom.size()>0){
                initData();
            }
        }
    }
    //homeFragmentDevicePager的数据传递
    public interface DeviceData{
        void sendMsg(Object msg);
    }
}
