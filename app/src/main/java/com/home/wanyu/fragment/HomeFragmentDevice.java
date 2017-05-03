package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.wanyu.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//home中的设备fragment
public class HomeFragmentDevice extends Fragment{
    private Unbinder unbinder;
    public static HomeFragmentDevice mFragment;
    public static HomeFragmentDevice getInstance(){
        if (mFragment==null){
            mFragment=new HomeFragmentDevice();
        }
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_device,null);
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
