package com.home.wanyu.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;

import com.home.wanyu.activity.DeviceSettingLockActivity;
import com.home.wanyu.activity.HomeDeviceAdRoomActivity;
import com.home.wanyu.activity.HomeDeviceAddRoomActivity;
import com.home.wanyu.activity.HomeSenceAddSenceActivity;

import com.home.wanyu.activity.LoginAndRegisterActivity;
import com.home.wanyu.activity.OtherPersonInfoActivity;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;

import com.home.wanyu.zxing.app.CaptureActivity;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//家
public class HomeFragment extends Fragment  {
    private int cur;//0情景，1设备
    private Unbinder unbinder;
    @BindView(R.id.fragment_home_top_qj) TextView fragment_home_top_qj;
    @BindView(R.id.fragment_home_top_shebei) TextView fragment_home_top_shebei;
    @BindView(R.id.fragment_home_top_add)ImageView fragment_home_top_add;

    private HomeFragmentDevice homeFragmentDevice;//设备
    private HomeFragmentScene homeFragmentScene;//情景
    private Fragment mFragment;

    private PopupWindow pop;
    private Bean_SceneAndRoom sceneAndRoom;
    private okhttpTools tools;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(getActivity(), ToastType.FAILD);
                    break;
                case 1:
                    try{
                        sceneAndRoom= mGson.gson.fromJson(tools.mResponStr,Bean_SceneAndRoom.class);
                        String code=sceneAndRoom.getCode();
                        if ("0".equals(code)){
                            homeFragmentDevice.sendMsg(sceneAndRoom.getRoomList());
                            homeFragmentScene.sendMsg(sceneAndRoom.getSceneList());
                            }
                        else {
                            ServerCode.showResponseMsg(getActivity(),code);
                        }
                        }
                    catch (Exception e){
                        mToast.ToastFaild(getActivity(), ToastType.GSONFAILD);
                        e.printStackTrace();
                        }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home,null);
        unbinder= ButterKnife.bind(this,vi);
        initView();
        return vi;
    }

    @Override
    public void onStart() {
        super.onStart();
        getSerVerData();//获取情景以及设备的全部信息接口
//        if (MainActivity.state==1){
//            getSerVerData();//获取情景以及设备的全部信息接口
//        }
    }

    private void initView() {
        fragment_home_top_qj.setSelected(true);
        fragment_home_top_shebei.setSelected(false);
        homeFragmentDevice=new HomeFragmentDevice();
        homeFragmentScene=new HomeFragmentScene();
        FragmentTransaction transition=getActivity().getSupportFragmentManager().beginTransaction();
        transition.add(R.id.fragment_home_bottom_layout,homeFragmentScene);
        transition.add(R.id.fragment_home_bottom_layout,homeFragmentDevice);
        mFragment=homeFragmentScene;
        cur=0;
        transition.hide(homeFragmentScene).hide(homeFragmentDevice).show(mFragment).commit();
    }
    @OnClick({R.id.fragment_home_top_qj,R.id.fragment_home_top_shebei,R.id.fragment_home_change,R.id.fragment_home_top_add})
    public void click(View v){
        switch (v.getId()){
            case R.id.fragment_home_top_qj:
                getActivity().getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
                mFragment=homeFragmentScene;
                fragment_home_top_qj.setSelected(true);
                fragment_home_top_shebei.setSelected(false);
                getActivity().getSupportFragmentManager().beginTransaction().show(mFragment).commit();
                cur=0;
                break;
            case R.id.fragment_home_top_shebei:
                getActivity().getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
                mFragment=homeFragmentDevice;
                fragment_home_top_qj.setSelected(false);
                fragment_home_top_shebei.setSelected(true);
                getActivity().getSupportFragmentManager().beginTransaction().show(mFragment).commit();
                cur=1;
                break;

            case R.id.fragment_home_change:
                mToast.DebugToast(getActivity(),"切换实景");
//              startActivity(new Intent(getActivity(), TestActivity.class));
                //他人的信息页面
//                Intent intent=new Intent(getActivity(),OtherPersonInfoActivity.class);
//                intent.putExtra("id","1");
//                startActivity(intent);
                //注册登录页面
                startActivity(new Intent(getActivity(),LoginAndRegisterActivity.class));
                break;
            case R.id.fragment_home_top_add:
                showWindow();
                break;
        }
    }
    //添加设备，添加情景，添加房间
    private void showWindow() {
        pop = new PopupWindow();
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pop_homeadd, null);
        TextView textv_add_scene= (TextView) v.findViewById(R.id.textv_add_scene);
        TextView  textv_add_room= (TextView) v.findViewById(R.id.textv_add_room);
        TextView textv_add_device= (TextView) v.findViewById(R.id.textv_add_device);
        textv_add_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                startActivity(new Intent(getActivity(), HomeSenceAddSenceActivity.class));
            }
        });
        textv_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                startActivity(new Intent(getActivity(), HomeDeviceAddRoomActivity.class));
//                Toast.makeText(getActivity(),"添加房间",Toast.LENGTH_SHORT).show();
            }
        });
        textv_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                startActivity(new Intent(getActivity(), CaptureActivity.class));
            }
        });
        getActivity(). getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params =   getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity(). getWindow().setAttributes(params);

        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(v);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup2_anim);
        pop.showAsDropDown(fragment_home_top_add, 0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params =   getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        if (tools!=null){
            tools=null;
        }
    }
//    获取情景及房间的接口
// http://192.168.1.55:8080/smarthome/mobileapi/scene/ findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void getSerVerData() {
        Log.i("--onStart--","getSerVerData()执行");
        tools=new okhttpTools();
        tools.getSceneAndRoom(handler,1);
    }
    public interface HomeData{
        void sendMsg(Object msg);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){//隐藏的时候

        }
        else {
//            getSerVerData();
        }
    }
}
