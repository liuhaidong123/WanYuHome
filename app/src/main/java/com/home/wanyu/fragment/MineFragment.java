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

public class MineFragment extends Fragment{
    public static MineFragment mFragment;
    public static MineFragment getInstance(){
        if (mFragment==null){
            mFragment=new MineFragment();
        }
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_mine,null);
        ButterKnife.bind(this,vi);
        return vi;
    }
}
