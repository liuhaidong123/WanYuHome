package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.activity.HomeSenceSettingActivity;
import com.home.wanyu.adapter.HomeScenePagerItemListApdater;
import com.home.wanyu.lzhView.MyFloatingView;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanyu on 2017/5/2.
 */
//情景的viewpager中的frgment
public class HomeFragmentScenePager extends Fragment{
//    private static int count=0;
    private String SceneName;//当前的情景名称
    private int pos;//当前的情景的position
    @BindView(R.id.fragment_home_scene_viewpager_item_MylistView)MyListView myListView;
    @BindView(R.id.fragment_home_scene_viewpager_item_MyFloating) MyFloatingView floatingView;
    private HomeScenePagerItemListApdater adapter;
    private ArrayList<Map<String,String>>list;
    private String[]title={"客厅灯","电视","卫生间插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_scene_pager_fragment,null);
        ButterKnife.bind(this,vi);
        initData();
        return vi;
    }

        @OnClick({R.id.fragment_home_scene_viewpager_item_MyFloating,R.id.fragment_home_scene_viewpager_item_toplayout})
        public void click(View vi){
            switch (vi.getId()){
                case R.id.fragment_home_scene_viewpager_item_MyFloating:
                    boolean state=floatingView.getState();
                    floatingView.setOnAndOff(!state);
                    int size=list.size();
                    for (int i=0;i<size;i++){
                        if (state){
                            list.get(i).put("state","0");
                        }
                        else {
                            list.get(i).put("state","1");
                        }
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.fragment_home_scene_viewpager_item_toplayout:
                    Intent intent=new Intent(getActivity(),HomeSenceSettingActivity.class);
                    intent.putExtra("name",SceneName);
                    startActivity(intent);
                    break;
            }
        }

    private void initData() {
        list=new ArrayList<>();
        int size=url.length;
        for (int i=0;i<size;i++){
            HashMap<String,String>m=new HashMap<>();
            m.put("title",title[i]);
            m.put("url",url[i]+"");
            m.put("state","1");
            list.add(m);
        }
        adapter = new HomeScenePagerItemListApdater(list, getActivity(),floatingView);
        myListView.setAdapter(adapter);
    }
    //情景名称,id
    public void setSceneName(String title,int position){
        SceneName=title;
        pos=position;
    }
}
