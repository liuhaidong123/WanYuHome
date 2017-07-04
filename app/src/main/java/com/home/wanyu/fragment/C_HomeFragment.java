package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.home.wanyu.C_Utils.CHomeFragmentUtils;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.ViewSettingUtils.TextViewSetting;
import com.home.wanyu.mEmeu.HomeSelect;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/6/30.
 */
//改动后的主页面
public class C_HomeFragment extends Fragment implements CHomeFragmentUtils.iCHomeFragmentUtils {
    Unbinder unbinder;
    @BindView(R.id.c_homefrag_select_scene)TextView c_homefrag_select_scene;//情景
    @BindView(R.id.c_homefrag_select_deice)TextView c_homefrag_select_deice;//
    @BindView(R.id.c_homefrag_select_edit)TextView 设备c_homefrag_select_edit;//编辑按钮
    float maxSize,normalSize;
    Fragment mFrgment;
    C_HomeSceneFragment sceneFragment;//情景的fragment
    C_HomeDeviceFragment deviceFragment;//设备的fragment
    HomeSelect mHomeselect;//当前选中的项目
    HomeSelect select;

    CHomeFragmentUtils chomeUtils;//弹出窗口设置的view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.c_homefrag,null);
        unbinder= ButterKnife.bind(this,vi);
        initFragment();
        return vi;
    }

    private void initFragment() {
        sceneFragment=new C_HomeSceneFragment();
        deviceFragment=new C_HomeDeviceFragment();
        FragmentTransaction trans=getChildFragmentManager().beginTransaction();
        trans.add(R.id.c_homefrag_select_fraglayout,sceneFragment);
        trans.add(R.id.c_homefrag_select_fraglayout,deviceFragment);
        mFrgment=sceneFragment;
        select=HomeSelect.SCENE;
        mHomeselect=HomeSelect.SCENE;//当前选中的是情景（默认选中）
        trans.hide(sceneFragment).hide(deviceFragment).show(mFrgment).commit();
        TextViewSetting.ChangeTextSie(c_homefrag_select_scene,c_homefrag_select_deice,select);
    }

    @OnClick({R.id.c_homefrag_select_scene,R.id.c_homefrag_select_deice,R.id.c_homefrag_select_edit})
    public void click(View v){
        switch (v.getId()){
            case R.id.c_homefrag_select_scene://不做处理
                break;
            case R.id.c_homefrag_select_deice:
                getChildFragmentManager().beginTransaction().hide(mFrgment).commit();
                if (select==HomeSelect.SCENE){//当前选中的是情景，点击后切换到设备
                    select=HomeSelect.DEVICE;
                    mFrgment=deviceFragment;
                }
                else {//当前选中的是设备，点击后切换到情景
                    select=HomeSelect.SCENE;
                    mFrgment=sceneFragment;
                    }
                TextViewSetting.ChangeTextSie(c_homefrag_select_scene,c_homefrag_select_deice,select);
                getChildFragmentManager().beginTransaction().show(mFrgment).commit();
                break;
            case R.id.c_homefrag_select_edit://编辑按钮
                if (chomeUtils==null){
                    chomeUtils=new CHomeFragmentUtils(this);
                    }

                switch (select){
                    case SCENE://情景编辑
                        chomeUtils.ShowSceneSettingWindow();
                        break;
                    case DEVICE://设备编辑

                        break;
                }
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

    //弹窗中添加情景／设备的选项
    @Override
    public void addSceneOrDevice() {
        switch (select){
            case SCENE://添加情景
                mToast.Toast(getActivity(),"添加情景");
                break;
            case DEVICE://添加房间
                mToast.Toast(getActivity(),"添加房间");
                break;
        }
    }

}
