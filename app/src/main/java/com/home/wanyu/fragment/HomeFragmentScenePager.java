package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeScenePagerItemListApdater;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */
//情景的viewpager中的frgment
public class HomeFragmentScenePager extends Fragment{
    @BindView(R.id.fragment_home_scene_viewpager_item_MylistView)MyListView myListView;
    private HomeScenePagerItemListApdater adapter;
    private ArrayList<Map<String,String>>list;
    private String[]title={"客厅灯","电视","卫生间插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_scene_pager_fragment,null);
        ButterKnife.bind(this,vi);
        initData();
        return vi;
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i=0;i<3;i++){
            Map<String,String>m=new HashMap<>();
            m.put("title",title[i]);
            m.put("url",url[i]+"");
            list.add(m);
        }
        adapter=new HomeScenePagerItemListApdater(list,getActivity());
        myListView.setAdapter(adapter);
    }
}
