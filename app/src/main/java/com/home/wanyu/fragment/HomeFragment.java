package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//家
public class HomeFragment extends Fragment {
    private Unbinder unbinder;
    @BindView(R.id.fragment_home_top_qj) TextView fragment_home_top_qj;
    @BindView(R.id.fragment_home_top_shebei) TextView fragment_home_top_shebei;
    public static HomeFragment mFragment;
    private HomeFragmentDevice homeFragmentDevice;
    private HomeFragmentScene homeFragmentScene;
    private ArrayList<Fragment>listFragment;
    public static HomeFragment getInstance(){
        if (mFragment==null){
            mFragment=new HomeFragment();
        }
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home,null);
        unbinder= ButterKnife.bind(this,vi);
        initView();
        return vi;
    }

    private void initView() {
        fragment_home_top_qj.setSelected(true);
        fragment_home_top_shebei.setSelected(false);
        homeFragmentDevice=HomeFragmentDevice.getInstance();
        homeFragmentScene=HomeFragmentScene.getInstance();
        listFragment=new ArrayList<>();
        listFragment.add(homeFragmentScene);
        listFragment.add(homeFragmentDevice);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_bottom_layout,listFragment.get(0)).commit();

    }



    @OnClick({R.id.fragment_home_top_qj,R.id.fragment_home_top_shebei,R.id.fragment_home_change,R.id.fragment_home_top_add})
    public void click(View v){
        switch (v.getId()){
            case R.id.fragment_home_top_qj:
                fragment_home_top_qj.setSelected(true);
                fragment_home_top_shebei.setSelected(false);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_bottom_layout,listFragment.get(0)).commit();
                break;
            case R.id.fragment_home_top_shebei:
                fragment_home_top_qj.setSelected(false);
                fragment_home_top_shebei.setSelected(true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_bottom_layout,listFragment.get(1)).commit();
                break;
            case R.id.fragment_home_change:
                MyToast.DebugToast(getActivity(),"切换实景");
                break;
            case R.id.fragment_home_top_add:
                MyToast.DebugToast(getActivity(),"添加");
                break;

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
