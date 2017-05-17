package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.bean.Bean_setDeviceData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.MySeekBar;
import com.home.wanyu.lzhView.SwitchButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
//bundle.putSerializable("scene0",beanX);
//        bundle.putSerializable("room1",bean);
//bundle.putSerializable("familyId",fmiId);
//type=0:情景中的设备，type=1房间中的设备,,beanX情景中获取的设备数据，bean：房间中国年获取的设备信息
//        bundle.putString("type",type+"");
//        intent.putExtra("data",bundle);
//窗帘设置页面
public class DeviceSettingCurtainActivity extends MyActivity {
    String title="窗帘";
    @BindView(R.id.deviceSetting_curtain_switch)SwitchButton deviceSetting_curtain_switch;//开关按钮
    @BindView(R.id.deviceSetting_curtain_mySeekbar)MySeekBar deviceSetting_curtain_mySeekbar;//调节按钮

    private Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX room;//房间中的设备信息
    private Bean_SceneAndRoom.SceneListBean.EquipmentListBean scene;//情境中的设备信息
    private String type;
    private String fimilyId;

    private boolean isChange=false;//当前是否正在调节
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isChange=false;
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    isChange=false;
                    try {
                        Bean_setDeviceData deviceData = mGson.gson.fromJson(resStr, Bean_setDeviceData.class);
                        if (deviceData != null) {
                            if ("0".equals(deviceData.getCode())) {
                                mToast.Toast(con, "设置成功");
                            } else {
                                mToast.ToastFaild(con, ToastType.GSONEMPTY);
                            }
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
        initChildView(R.layout.activity_device_setting_curtain2);
        setTitle(title);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        getIntentData();
        deviceSetting_curtain_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
            @Override
            public void change(Boolean sta, String text, SwitchButton vi) {
                setDevice();
            }
        });
    }


    public void getIntentData() {
        Bundle b=getIntent().getBundleExtra("data");
        if (b!=null){
            type=b.getString("type");
            if (!"".equals(type)&&!TextUtils.isEmpty(type)){
                if ("1".equals(type)){
                    room= (Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX) b.getSerializable("room1");
                    if (room.getState()==1L){
                        deviceSetting_curtain_switch.setNorState(false);
                    }
                    else {
                        deviceSetting_curtain_switch.setNorState(true);
                    }
                }
                else if ("0".equals(type)){
                    scene= (Bean_SceneAndRoom.SceneListBean.EquipmentListBean) b.getSerializable("scene0");
                    if (scene.getState()==1L){
                        deviceSetting_curtain_switch.setNorState(false);
                    }
                    else {
                        deviceSetting_curtain_switch.setNorState(true);
                    }
                }
                fimilyId=b.getString("familyId");
            }
            else {
                mToast.Toast(con,"获取设备当前信息错误");
                Log.e(TAG,"设备设置上文没有传递过来设备的相关信息");
            }
        }
        else {
            mToast.Toast(con,"获取设备当前信息错误");
            Log.e(TAG,"设备设置上文没有传递过来设备的相关信息");
        }
    }
    @Override
    public void getSerVerData() {

    }



    private void setDevice() {
        if (isChange){
            mToast.Toast(con,"正在操作，请稍后");
            return;
        }
        isChange=true;
        //bundle.putSerializable("scene0",beanX);
//        bundle.putSerializable("room1",bean);
//bundle.putSerializable("familyId",fmiId);
//type=0:情景中的设备，type=1房间中的设备,,beanX情景中获取的设备数据，bean：房间中国年获取的设备信息
//        bundle.putString("type",type+"");
//        intent.putExtra("data",bundle);
        if ("0".equals(type)){
            if (scene==null){
                return;
            }
            HashMap<String,String> mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
            mp.put("id",scene.getId()+"");
            mp.put("name",scene.getName());
            mp.put("iconId",scene.getIconId()+"");
            mp.put("iconUrl",scene.getIconUrl());
            String state="";
            if (scene.getState()==1L){
                state="0";
            }
            else if (scene.getState()==0L){
                state="1";
            }
            mp.put("state",state);
            mp.put("roomId",scene.getRoomId()+"");
            mp.put("serialNumber",scene.getSerialNumber());
            mp.put("extendState",scene.getToExtendState());
            mp.put("familyId",fimilyId);
            okhttp.getCall(Ip.serverPath+Ip.interface_HomeScene_setDevice,mp,okhttp.OK_POST).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("房间中单个设备状态设置---",resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }
        else if ("1".equals(type)){
            if (room==null){
                return;
            }
            HashMap<String,String> mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
            mp.put("id",room.getId()+"");
            mp.put("name",room.getName());
            mp.put("iconId",room.getIconId()+"");
            mp.put("iconUrl",room.getIconUrl());
            String state="";
            if (room.getState()==1L){
                state="0";
            }
            else if (room.getState()==0L){
                state="1";
            }
            mp.put("state",state);
            mp.put("roomId",room.getRoomId()+"");
            mp.put("serialNumber",room.getSerialNumber());
            mp.put("extendState",room.getToExtendState());
            mp.put("familyId",fimilyId);
            okhttp.getCall(Ip.serverPath+Ip.interface_HomeScene_setDevice,mp,okhttp.OK_POST).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("房间中单个设备状态设置---",resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }
}
