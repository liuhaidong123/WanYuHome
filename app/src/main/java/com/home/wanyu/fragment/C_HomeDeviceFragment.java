package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.C_HomeDeviceGridAdapter;
import com.home.wanyu.adapter.C_HomeSceneGridAdapter;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.zxing.app.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/6/30.
 */
//设备设置
public class C_HomeDeviceFragment extends Fragment{
    Unbinder unbinder;
    @BindView(R.id.c_homedevice_textName)TextView c_homedevice_textName;//显示当前房间的名字
    @BindView(R.id.c_homedevice_relaMore)RelativeLayout c_homedevice_relaMore;//下啦按钮所在的lagyou（切换房间）
    @BindView(R.id.c_homedevice_gridView)MyGridView c_homedevice_gridView;//显示情景的gridview
    C_HomeDeviceGridAdapter mGridAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.c_homedevicefrag,null);
        unbinder=ButterKnife.bind(this,vi);
        initAdapter();
        return vi;
    }

    private void initAdapter() {
        mGridAdapter=new C_HomeDeviceGridAdapter(getActivity());
        c_homedevice_gridView.setAdapter(mGridAdapter);
    }
    @OnClick({R.id.c_homedevice_btnAdd,R.id.c_homedevice_relaMore})
    void click(View vi){
        switch (vi.getId()){
            case R.id.c_homedevice_btnAdd://添加房间
                //添加设备，暂定
                startActivity(new Intent(getActivity(),CaptureActivity.class));
                break;
            case R.id.c_homedevice_relaMore://切换房间
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
