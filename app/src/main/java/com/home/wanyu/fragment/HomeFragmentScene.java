package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.lzhView.MyFloatingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



/**
 * Created by wanyu on 2017/5/2.
 */
//情景的fragment
public class HomeFragmentScene extends Fragment implements HomeFragment.HomeData{
    private Unbinder unbinder;
    private MyAdapter adapter;
    @BindView(R.id.fragment_home_scene_viewpager)  ViewPager fragment_home_scene_viewpager;
    @BindView(R.id.fragment_home_scene_tablayout)
    TabLayout fragment_home_scene_tablayout;
//    @BindArray(R.array.homeSceneString) String[]Sence;
    private ArrayList<String>listTable;//获取到的标题个数
    private ArrayList<HomeFragmentScenePager>listFragment;

    private List<Bean_SceneAndRoom.SceneListBean> listScene;//
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_scene,null);
//        initData();
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }

    private void initData() {
        if (listScene!=null&&listScene.size()>0){
            int size=listScene.size();
            listFragment=new ArrayList<>();
            listTable=new ArrayList<>();
            for (int i=0;i<size;i++){
                listTable.add(listScene.get(i).getSceneName());
                HomeFragmentScenePager mFrag=new HomeFragmentScenePager();
                mFrag.sendMsg(listScene.get(i));
                listFragment.add(mFrag);
            }
        }
        adapter=new MyAdapter(getChildFragmentManager());
        fragment_home_scene_viewpager.setAdapter(adapter);
        fragment_home_scene_tablayout.setupWithViewPager(fragment_home_scene_viewpager,true);

        listFragment.get(0).setSceneName(listTable.get(0),0);
        fragment_home_scene_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


    //刷新数据，在homeFragment获取数据后刷新
    @Override
    public void sendMsg(Object msg) {
        if (msg!=null){
            listScene= (List<Bean_SceneAndRoom.SceneListBean>) msg;
            if (listScene!=null&&listScene.size()>0){
                initData();
            }
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
            return listTable.get(position);
        }
        }


    //homeFragmentDevicePager的数据传递
    public interface SceneData{
        void sendMsg(Object msg);
    }
}
