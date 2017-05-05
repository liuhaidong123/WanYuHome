package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.home.wanyu.R;
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
public class HomeFragmentDevice extends Fragment{
//
private Unbinder unbinder;
    private MyAdapter adapter;
    @BindView(R.id.fragment_home_device_viewpager)
    ViewPager fragment_home_device_viewpager;
    @BindView(R.id.fragment_home_device_tablayout)
    TabLayout fragment_home_device_tablayout;
    @BindArray(R.array.homeDeviceString) String[]Sence;
    private ArrayList<HomeFragmentDevicePager> listFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_device,null);
        unbinder= ButterKnife.bind(this,vi);
        initData();
        return vi;
    }

    private void initData() {
        listFragment=new ArrayList<>();
        int length=Sence.length;
        for (int i=0;i<length;i++){
            HomeFragmentDevicePager mFrag=new HomeFragmentDevicePager();
            listFragment.add(mFrag);
            fragment_home_device_tablayout.addTab(fragment_home_device_tablayout.newTab().setText(Sence[i]));
        }
        adapter=new MyAdapter(getChildFragmentManager());
        fragment_home_device_viewpager.setAdapter(adapter);
        fragment_home_device_tablayout.setupWithViewPager(fragment_home_device_viewpager,true);

        listFragment.get(0).setSceneName(Sence[0],0);
        fragment_home_device_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String title=Sence[tab.getPosition()];
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




    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Sence[position];
        }
    }


}
