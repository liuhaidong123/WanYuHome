package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.home.wanyu.Icons.Device;
import com.home.wanyu.Icons.DeviceSettings;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.HomeDeviceSettingActivity;
import com.home.wanyu.activity.HomeSenceSettingActivity;
import com.home.wanyu.adapter.HomeDevicePagerItemListApdater;
import com.home.wanyu.bean.Bean_HomeRoomOnOff;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.lzhView.MyFloatingView;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/4.
 */
//设备的viewpager页面
public class HomeFragmentDevicePager extends Fragment implements HomeFragmentDevice.DeviceData{
    private String SceneName;//当前的房间名称
    private Unbinder unbinder;
    private int pos;//当前的情景的position
    @BindView(R.id.fragment_home_device_viewpager_item_MylistView)ListView myListView;
    @BindView(R.id.fragment_home_device_viewpager_item_MyFloating) MyFloatingView floatingView;
    @BindView(R.id.deviceSetting_emptyView)RelativeLayout deviceSetting_emptyView;
    private HomeDevicePagerItemListApdater adapter;
//    private String[]title={"客厅灯","电视","客厅插座"};
//    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};

    private Bean_SceneAndRoom.RoomListBean listBean;
    private  List<Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX> listBeanX;

    private okhttpTools tools;
    private Boolean isOnAndOff=false;//当前是否正在进行一键操作
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isOnAndOff=false;
                    mToast.ToastFaild(getActivity(),ToastType.FAILD);
                    break;
                case 1:
                    isOnAndOff=false;
                    try{
                        Bean_HomeRoomOnOff onOff= mGson.gson.fromJson(tools.mResponStr,Bean_HomeRoomOnOff.class);
                        if (onOff!=null){
                            if ("0".equals(onOff.getCode())){
                                setOnAndOff();
                            }
                            else {
                                ServerCode.showResponseMsg(getActivity(),onOff.getCode());
                            }
                        }
                        else {
                            mToast.ToastFaild(getActivity(),ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(getActivity(),ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_device_pager_fragment,null);
        unbinder=ButterKnife.bind(this,vi);
        initData();
        tools=new okhttpTools();
        floatingView.setClick(new MyFloatingView.ClickListener() {
            @Override
            public void click(boolean flag) {
                if (flag){
                    if (listBean==null){
                        return;
                    }
                    if (isOnAndOff){
                        mToast.Toast(getActivity(),"正在操作，请稍后");
                        return;
                    }
                    isOnAndOff=true;
                    boolean state=floatingView.getState();
                    HashMap<String,String>mp=new HashMap<>();
                    mp.put("id",listBean.getId()+"");mp.put("token", UserInfo.userToken);
                    if (state){//当前处于全部开启的状态
                        mp.put("state","1");//1关闭操作
                    }
                    else {//当前全部关闭状态
                        mp.put("state","0");//0开启操作
                    }
                    String url= Ip.serverPath+Ip.interface_HomeRoom_OpenAndOn;
                    tools.getServerData(handler,1,url,mp,"房间一键操作返回---");
                }

            }
        });
        return vi;
    }
    @OnClick({R.id.fragment_home_device_viewpager_item_toplayout,R.id.fragment_home_device_viewpager_item_MyFloating,R.id.deviceSetting_emptyView})
    public void click(View vi){
        switch (vi.getId()){
//            case R.id.fragment_home_device_viewpager_item_MyFloating:
//                break;
            case R.id.fragment_home_device_viewpager_item_toplayout:
                if (listBean.getId()==0L){
                    mToast.Toast(getActivity(),"不支持设置的房间类型");
                    return;
                }
                Intent intent=new Intent(getActivity(),HomeDeviceSettingActivity.class);
                intent.putExtra("id",listBean.getId()+"");
                intent.putExtra("name",SceneName);
                startActivity(intent);
                break;
            case R.id.deviceSetting_emptyView://没有添加设备时
                if (listBean.getId()==0L){
                    mToast.Toast(getActivity(),"不支持设置的房间类型");
                    return;
                }
                Intent intent2=new Intent(getActivity(),HomeDeviceSettingActivity.class);
                intent2.putExtra("id",listBean.getId()+"");
                intent2.putExtra("name",SceneName);
                startActivity(intent2);
                break;
        }
    }
    private void initData() {
        if (listBean!=null){
            listBeanX=listBean.getEquipmentList();
            SceneName=listBean.getRoomName();
//            list=new ArrayList<>();
            if (listBeanX!=null&&listBeanX.size()>0){
                adapter = new HomeDevicePagerItemListApdater(listBeanX, getActivity(),floatingView,listBean.getFamilyId()+"");
                myListView.setAdapter(adapter);
                floatingView.setVisibility(View.VISIBLE);
                deviceSetting_emptyView.setVisibility(View.GONE);
            }
            else {
                myListView.setEmptyView(deviceSetting_emptyView);
                floatingView.setVisibility(View.GONE);
            }
        }
    }
    //情景名称,id
    public void setSceneName(String title,int position){
        SceneName=title;
        pos=position;
    }
    //获取到刷新到数据源
    @Override
    public void sendMsg(Object msg) {
        listBean= (Bean_SceneAndRoom.RoomListBean) msg;
    }

    public void setOnAndOff(){
        boolean state=floatingView.getState();
        floatingView.setOnAndOff(!state);
        if (listBeanX!=null&&listBeanX.size()>0){
            int size=listBeanX.size();
            for (int i=0;i<size;i++){
                if (state){//0kaiqi,1关闭
                    listBeanX.get(i).setState(1L);
                }
                else {
                    listBeanX.get(i).setState(0L);
                    }
            }
            adapter.notifyDataSetChanged();
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
