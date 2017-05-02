package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.wanyu.R;

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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_communication,null);
        ButterKnife.bind(this,vi);
        return vi;
    }
}
