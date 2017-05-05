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

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



/**
 * Created by wanyu on 2017/5/2.
 */
//情景的fragment
public class HomeFragmentScene extends Fragment{
    private Unbinder unbinder;
    private MyAdapter adapter;
    public static HomeFragmentScene mFragment;
    @BindView(R.id.scroll)HorizontalScrollView scroll;
    @BindView(R.id.fragment_home_scene_viewpager)  ViewPager fragment_home_scene_viewpager;
    @BindView(R.id.fragment_home_scene_tablayout)
    TabLayout fragment_home_scene_tablayout;
    @BindArray(R.array.homeSceneString) String[]Sence;
    private ArrayList<Fragment>listFragment;
    public static HomeFragmentScene getInstance(){
        if (mFragment==null){
            mFragment=new HomeFragmentScene();
        }
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_scene,null);
        unbinder= ButterKnife.bind(this,vi);
        initData();
        return vi;
    }

    private void initData() {
        int length=Sence.length;
        for (int i=0;i<length;i++){
            fragment_home_scene_tablayout.addTab(fragment_home_scene_tablayout.newTab().setText(Sence[i]));
        }

        listFragment=new ArrayList<>();
        for (int i=0;i<length;i++){
            HomeFragmentScenePager mFrag=new HomeFragmentScenePager();
            listFragment.add(mFrag);
        }

        adapter=new MyAdapter(getChildFragmentManager());
        fragment_home_scene_viewpager.setAdapter(adapter);
        fragment_home_scene_tablayout.setupWithViewPager(fragment_home_scene_viewpager,true);



        fragment_home_scene_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("left---",""+fragment_home_scene_tablayout.getPaddingLeft()+"=="+fragment_home_scene_tablayout.getPaddingRight());
                int wid=fragment_home_scene_tablayout.getWidth();
                int itemWidth=wid/fragment_home_scene_tablayout.getTabCount();//item的宽度
                int pos=tab.getPosition();
//                Log.i("itemWidth--"+itemWidth,"pos=="+pos+"====wid--"+wid);
//                TabLayout.Tab vi=tablayout.getTabAt(pos);
                int mx=(pos)*itemWidth;
                scroll.scrollTo(mx-wid/2-itemWidth,0);
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
