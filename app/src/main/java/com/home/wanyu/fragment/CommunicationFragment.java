package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CircleAdapter;
import com.home.wanyu.bean.CircleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */

//圈子
public class CommunicationFragment extends Fragment{
    public static CommunicationFragment mFragment;
    public static CommunicationFragment getInstance(){
        if (mFragment==null){
            mFragment=new CommunicationFragment();
        }
        return mFragment;
    }

    private ListView mListview;
    private CircleAdapter mAdapter;
    private List<CircleBean> mList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_communication,null);
        initView(vi);
        return vi;
    }

    public void initView(View view){
        mList.add(new CircleBean(R.mipmap.circle_friend,"友邻圈",1));
        mList.add(new CircleBean(R.mipmap.circle_area_activity,"社区活动",2));
        mList.add(new CircleBean(R.mipmap.circle_area_car,"社区拼车",3));
        mAdapter=new CircleAdapter(mList,this.getActivity());
        mListview= (ListView) view.findViewById(R.id.circle_listview);
        mListview.setAdapter(mAdapter);
    }
}
