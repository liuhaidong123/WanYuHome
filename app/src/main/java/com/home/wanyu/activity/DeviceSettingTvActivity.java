package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.DeviceSettingTvGridAdapter;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.bean.Bean_setDeviceData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.SwitchButton;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myview.MyGridView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("scene0",beanX);
//        bundle.putSerializable("room1",bean);
//        bundle.putString("type",type+"");
//bundle.putSerializable("familyId",fmiId);
//type=0:情景中的设备，type=1房间中的设备,,beanX情景中获取的设备数据，bean：房间中国年获取的设备信息
//        intent.putExtra("data",bundle);
//电视设置
public class DeviceSettingTvActivity extends MyActivity {
    @BindView(R.id.deviceSetting_tv_switch)SwitchButton deviceSetting_tv_switch;//开关
    @BindView(R.id.deviceSetting_tv_timeTextV)TextView deviceSetting_tv_timeTextV;//显示时间的textview
    @BindView(R.id.deviceSetting_tv_timelayout)LinearLayout deviceSetting_tv_timelayout;//时间所在的layout
    @BindView(R.id.deviceSetting_tv_gridView)MyGridView deviceSetting_tv_gridView;//上部的gridView
    @BindArray(R.array.tvString)String[]tvString;//gridView数据源
    DeviceSettingTvGridAdapter adapter;

    //频道，静音，音量控制
    @BindView(R.id.deviceSetting_tv_channelAdd)ImageView deviceSetting_tv_channelAdd;//频道向前
    @BindView(R.id.deviceSetting_tv_channelMinus)ImageView deviceSetting_tv_channelMinus;//频道向后
    @BindView(R.id.deviceSetting_tv_VoiceMinus)ImageView deviceSetting_tv_VoiceMinus;//音量减
    @BindView(R.id.deviceSetting_tv_VoiceAdd)ImageView deviceSetting_tv_VoiceAdd;//音量加
    @BindView(R.id.deviceSetting_tv_VoiceMute)TextView deviceSetting_tv_VoiceMute;//静音

    private int currentChannel=0;
    private int maxChannel=10;
    private int currentVoice=0;
    private int maxVoice=10;

    //定时相关
    private List<String>listHour;
    private List<String>listMini;
    private int SelectHourPos=0;
    private int SelectMiniPos=0;
    TextView air_timesetting_submit,air_timesetting_textv;
    WheelView air_timesetting_wheelHour,air_timesetting_wheelMini;



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
        initChildView(R.layout.activity_device_setting_tv);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("电视");
        ShowChildView(DEFAULTRESID);
        initData();
        getIntentData();
        deviceSetting_tv_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
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
                        deviceSetting_tv_switch.setNorState(false);
                    }
                    else {
                        deviceSetting_tv_switch.setNorState(true);
                    }
                }
                else if ("0".equals(type)){
                    scene= (Bean_SceneAndRoom.SceneListBean.EquipmentListBean) b.getSerializable("scene0");
                    if (scene.getState()==1L){
                        deviceSetting_tv_switch.setNorState(false);
                    }
                    else {
                        deviceSetting_tv_switch.setNorState(true);
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
    private void initData() {

        listHour=new ArrayList<>();
        listMini=new ArrayList<>();
        for (int i=0;i<24;i++){
            if (i<10){
                listHour.add("0"+i);
            }
           else {
                listHour.add(i+"");
            }
        }
        for (int i=1;i<60;i++){
            if (i<10){
                listMini.add("0"+i);
            }
            else {
                listMini.add(i+"");
            }
        }

        adapter=new DeviceSettingTvGridAdapter(tvString,con);
        deviceSetting_tv_gridView.setAdapter(adapter);
        deviceSetting_tv_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时
    }

    @OnClick({R.id.deviceSetting_tv_channelAdd,R.id.deviceSetting_tv_channelMinus,R.id.deviceSetting_tv_VoiceMinus
            ,R.id.deviceSetting_tv_VoiceAdd,R.id.deviceSetting_tv_VoiceMute,R.id.deviceSetting_tv_timelayout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.deviceSetting_tv_channelAdd://频道向前
                if (currentChannel!=maxChannel){
                    currentChannel+=1;
                }
               else {
                    currentChannel=0;
                }
                mToast.DebugToast(con,"当前频道："+currentChannel);
                break;
            case R.id.deviceSetting_tv_channelMinus://频道向后
                if (currentChannel>0){
                    currentChannel-=1;
                }
                else {
                    currentChannel=maxChannel;
                }
                mToast.DebugToast(con,"当前频道："+currentChannel);
                break;
            case R.id.deviceSetting_tv_VoiceMinus://音量减
                if (currentVoice==0){
                    deviceSetting_tv_VoiceMute.setSelected(true);
                }
                if (currentVoice>0){
                    currentVoice-=1;
                    if (currentVoice==0){
                        deviceSetting_tv_VoiceMute.setSelected(true);
                    }
                }
                mToast.DebugToast(con,"当前音量："+currentVoice);
                break;
            case R.id.deviceSetting_tv_VoiceAdd://音量加
                if (currentVoice!=maxVoice){
                    if (currentVoice==0){
                        deviceSetting_tv_VoiceMute.setSelected(false);
                    }
                    currentVoice+=1;
                }
                mToast.DebugToast(con,"当前音量："+currentVoice);
                break;
            case R.id.deviceSetting_tv_VoiceMute://静音
                deviceSetting_tv_VoiceMute.setSelected(!deviceSetting_tv_VoiceMute.isSelected());
                mToast.DebugToast(con,"静音");
                break;
            case R.id.deviceSetting_tv_timelayout://定时设置
                showWindowData();
                break;
        }
        }
    //定时启动的view
    private void showWindowData(){
        PopupWindow popW=new PopupWindow();
        popW = new PopupWindow();
        View air_timesetting = LayoutInflater.from(con).inflate(R.layout.air_timesetting, null);
        air_timesetting_submit= (TextView) air_timesetting.findViewById(R.id.air_timesetting_submit);
        air_timesetting_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceSetting_tv_timeTextV.setText(listHour.get(SelectHourPos)+":"+listMini.get(SelectMiniPos));
            }
        });
        air_timesetting_textv= (TextView) air_timesetting.findViewById(R.id.air_timesetting_textv);
        air_timesetting_textv.setText(listHour.get(SelectHourPos)+"点"+listMini.get(SelectMiniPos)+"分 将关闭该空调");
        air_timesetting_wheelHour= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelHour);
        air_timesetting_wheelHour.setTitle("点");
        air_timesetting_wheelHour.setViewAdapter(new MyWheelAdapter50(con,listHour,"小时"));
        air_timesetting_wheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectHourPos=newValue;
                Toast.makeText(con,listHour.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"点"+listMini.get(SelectMiniPos)+"分 将关闭该空调");
            }
        });
        air_timesetting_wheelMini= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelMini);
        air_timesetting_wheelMini.setTitle("分");
        air_timesetting_wheelMini.setViewAdapter(new MyWheelAdapter50(con,listMini,"分钟"));
        air_timesetting_wheelMini.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectMiniPos=newValue;
                Toast.makeText(con,listMini.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"点"+listMini.get(SelectMiniPos)+"分 将关闭该空调");
            }
        });

        LinearLayout parent = (LinearLayout) findViewById(R.id.activity_device_setting_tv);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        popW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popW.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popW.setContentView(air_timesetting);
        popW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popW.setTouchable(true);
        popW.setFocusable(true);
        popW.setOutsideTouchable(true);

        popW.setAnimationStyle(R.style.popup3_anim);
        popW.showAtLocation(parent, Gravity.BOTTOM,0,0);
        popW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
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
