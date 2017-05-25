package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.DeviceManagerAdapter;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.bean.Bean_getRoomData;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.zxing.app.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//根据名字判断当前设备是否已经添加过
//mToast.DebugToast(con,"添加设备");
//        Intent intent=new Intent();
//        intent.putExtra("type","roomAdd");roomAdd房间中添加设备,sceneAdd情景中添加设备，all获取所有设备列表
//Bundle bundle=new Bundle();
//        ArrayList<String>listName=new ArrayList<>();
//        if (list!=null&&list.size()>0){
//        for (int i=0;i<list.size();i++){
//        listName.add(list.get(i).getName());
//        }
//        }
//        bundle.putSerializable("data",listName);
//        intent.putExtra("data",bundle);
//        intent.setClass(con,MyHouseDeviceManagerActivity.class);
//        startActivity(intent);
//设备列表页面
public class MyHouseDeviceManagerActivity extends MyActivity {
    private String type;
    @BindView(R.id.activity_device_manager_listview)ListView activity_device_manager_listview;
    TextView myActivity_deviceManager_title,my_deviceManager_submmit;

    private DeviceManagerAdapter adapter;
    private List<Bean_AllDevice.EquipmentListBean> list;
    private ArrayList<String>listName;//粗放上文获取到的已经添加过的设备的名称
    private List<String>li;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con,ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_AllDevice allDevice= mGson.gson.fromJson(mTools.mResponStr,Bean_AllDevice.class);
                        if (allDevice!=null){
                            if ("0".equals(allDevice.getCode())){
                                if (allDevice.getEquipmentList()!=null&&allDevice.getEquipmentList().size()>0){
                                    if (!"".equals(type)&&!TextUtils.isEmpty(type)){
                                        switch (type){
                                            case "all"://获取所有设备
                                                list.addAll(allDevice.getEquipmentList());
                                                break;
                                            case "roomAdd"://获取当前房间中不存在的设备
                                                List<Bean_AllDevice.EquipmentListBean> lt=new ArrayList<>();
                                                for (int i=0;i<allDevice.getEquipmentList().size();i++){
//                                                    Log.i("roomid=2=",allDevice.getEquipmentList().get(i).getRoomId()+"----");
                                                    if ("0".equals(allDevice.getEquipmentList().get(i).getRoomId()+"")){
                                                        if (allDevice.getEquipmentList().get(i).getIconId()>icon.mIconId[0]&&
                                                                allDevice.getEquipmentList().get(i).getIconId()<=icon.mIconId[icon.mIconId.length-1]){
//                                                            Log.i("roomid==",allDevice.getEquipmentList().get(i).getRoomId()+"----");
                                                            boolean isTrue=true;
                                                            if (listName!=null&&listName.size()>0){
                                                                for (int j=0;j<listName.size();j++){
                                                                    if (listName.get(j).equals(allDevice.getEquipmentList().get(i).getName())){
                                                                        isTrue=false;
                                                                    }
                                                                }
                                                            }
                                                            if (isTrue){
                                                                lt.add(allDevice.getEquipmentList().get(i));
                                                            }
                                                        }
                                                    }
                                                }
                                                list.addAll(lt);
                                                adapter.notifyDataSetChanged();
                                                break;
                                            case "sceneAdd"://获取当前情景中不存在的设备
                                                List<Bean_AllDevice.EquipmentListBean> lt2=new ArrayList<>();
                                                for (int i=0;i<allDevice.getEquipmentList().size();i++){
//                                                      //当前设备在可支持的设备列表内
                                                        if (allDevice.getEquipmentList().get(i).getIconId()>icon.mIconId[0]&&
                                                                allDevice.getEquipmentList().get(i).getIconId()<=icon.mIconId[icon.mIconId.length-1]){
//                                                            Log.i("roomid==",allDevice.getEquipmentList().get(i).getRoomId()+"----");
                                                            boolean isTrue=true;
                                                            if (listName!=null&&listName.size()>0){
                                                                for (int j=0;j<listName.size();j++){
                                                                    if (listName.get(j).equals(allDevice.getEquipmentList().get(i).getName())){
                                                                        isTrue=false;
                                                                    }
                                                                }
                                                            }
                                                            if (isTrue){
                                                                lt2.add(allDevice.getEquipmentList().get(i));
                                                            }
                                                        }

                                                }
                                                list.addAll(lt2);
                                                adapter.notifyDataSetChanged();
                                                break;
                                        }
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                ServerCode.showResponseMsg(con,allDevice.getCode());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.devicemanager_title);
        myActivity_deviceManager_title= (TextView) findViewById(R.id.myActivity_deviceManager_title);
        my_deviceManager_submmit= (TextView) findViewById(R.id.my_deviceManager_submmit);
        initChildView(R.layout.activity_device_manager);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        initData();
        mTools=new okhttpTools();
        getSerVerData();
    }

    private void initData() {
        type=getIntent().getStringExtra("type");
        if (!"".equals(type)&&!TextUtils.isEmpty(type)){
            switch (type){
                case "all"://获取所有设备
                    break;
                case "roomAdd"://获取当前房间中不存在的设备(li存放已经添加到情景／房间中的设备的id)
                    myActivity_deviceManager_title.setText("选择设备");
                    my_deviceManager_submmit.setVisibility(View.GONE);
                    listName= (ArrayList<String>) getIntent().getBundleExtra("data").getSerializable("data");
                    break;
                case "sceneAdd"://获取当前情景中不存在的设备
                    myActivity_deviceManager_title.setText("选择设备");
                    my_deviceManager_submmit.setVisibility(View.GONE);
                    Bundle bundle2=getIntent().getBundleExtra("data");
                    listName= (ArrayList<String>) bundle2.getSerializable("data");
                    break;
            }
        }
        list=new ArrayList<>();
        adapter=new DeviceManagerAdapter(con,list);
        activity_device_manager_listview.setAdapter(adapter);
        activity_device_manager_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (type){
                    case "all"://获取所有设备
                        Intent intent=new Intent();
                        intent.setClass(con,HomeDeviceAddWifiSettingActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("data",list.get(position));
                        intent.putExtra("data",bundle);
                        intent.putExtra("type","change");
                        startActivity(intent);
                        break;
                    case "roomAdd"://获取当前房间中不存在的设备
                        Intent intent1=new Intent();
//                        intent1.setClass(con,HomeDeviceSettingActivity.class);
                        Bundle bundle1=new Bundle();
                        Bean_getRoomData.RoomBean.EquipmentListBean bean=new Bean_getRoomData.RoomBean.EquipmentListBean();
                        Bean_AllDevice.EquipmentListBean bean1=list.get(position);
                        bean.setIconId(bean1.getIconId());
                        bean.setCreateTimeString(bean1.getCreateTimeString());
                        bean.setUpdateTimeString(bean1.getUpdateTimeString());
                        bean.setSerialNumber(bean1.getSerialNumber());
                        bean.setExtendState(bean1.getExtendState());
                        bean.setRoomId(bean1.getRoomId());
                        bean.setFamilyId(bean1.getFamilyId());
                        bean.setSceneTaskId(bean1.getSceneTaskId());
                        bean.setToState(bean1.getToState());
                        bean.setToExtendState(bean1.getToExtendState());
                        bean.setName(bean1.getName());
                        bean.setIconUrl(bean1.getIconUrl());
                        bean.setId(bean1.getId());
                        bean.setState(bean1.getState());
                        bundle1.putSerializable("data",bean);
                        intent1.putExtra("data",bundle1);
                        setResult(200,intent1);
                        finish();
                        break;
                    case "sceneAdd"://获取当前情景中不存在的设备
                        Intent intent2=new Intent();
//                        intent2.setClass(con,HomeSenceSettingActivity.class);
                        Bundle bundle2=new Bundle();
                        Bean_getSceneData.EquipmentListBean bean2=new  Bean_getSceneData.EquipmentListBean();
                        Bean_AllDevice.EquipmentListBean bean3=list.get(position);
                        bean2.setIconId(bean3.getIconId());
                        bean2.setCreateTimeString(bean3.getCreateTimeString());
                        bean2.setUpdateTimeString(bean3.getUpdateTimeString());
                        bean2.setSerialNumber(bean3.getSerialNumber());
                        bean2.setExtendState(bean3.getExtendState());
                        bean2.setRoomId(bean3.getRoomId());
                        bean2.setFamilyId(bean3.getFamilyId());
                        bean2.setSceneTaskId(bean3.getSceneTaskId());
                        bean2.setToState(bean3.getToState());
                        bean2.setToExtendState(bean3.getToExtendState());
                        bean2.setName(bean3.getName());
                        bean2.setIconUrl(bean3.getIconUrl());
                        bean2.setId(bean3.getId());
                        bean2.setState(bean3.getState());
                        bundle2.putSerializable("data",bean2);
                        intent2.putExtra("data",bundle2);
                        setResult(200,intent2);
                        finish();
                        break;
                }

            }
        });

    }
//http://192.168.1.55:8080/smarthome/mobileapi/equipment/findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取所有设备列表
    @Override
    public void getSerVerData() {
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mTools.getServerData(hand,1, Ip.serverPath+Ip.interface_HomeScene_getAllDevice,mp,"获取所有设备---");
     }
    public void Submit(View view) {
       startActivity(new Intent(con, CaptureActivity.class));
    }
}
