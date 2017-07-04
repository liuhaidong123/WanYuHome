package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.adapter.C_HomeSceneGridAdapter;
import com.home.wanyu.myview.MyGridView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/6/30.
 */
//改动后的情景fragment
public class C_HomeSceneFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.c_homescene_btnAdd)TextView c_homescene_btnAdd;//添加按钮
    @BindView(R.id.c_homescene_gridView)MyGridView c_homescene_gridView;//显示情景的gridview
    C_HomeSceneGridAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.c_homescene_fragment,null);
        unbinder=ButterKnife.bind(this,vi);
        initAdapter();
        return vi;
    }
    //测试用
    private void initAdapter() {
        mGridAdapter=new C_HomeSceneGridAdapter(getActivity());
        c_homescene_gridView.setAdapter(mGridAdapter);
    }

    @OnClick(R.id.c_homescene_btnAdd)
    public void click(View vi){
        if (vi!=null){
            mToast.Toast(getActivity(),"添加情景");
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
