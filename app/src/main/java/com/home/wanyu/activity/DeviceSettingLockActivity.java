package com.home.wanyu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.DeviceSettingTvGridAdapter;
import com.home.wanyu.adapter.LockSettingGridViewAdapter;
import com.home.wanyu.adapter.LockShareListAdapter;
import com.home.wanyu.adapter.MyHouseFamilyManagerAdapter;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.bean.Bean_setDeviceData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.MyTextView;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("scene0",beanX);
//        bundle.putSerializable("room1",bean);
//bundle.putSerializable("familyId",fmiId);
//type=0:情景中的设备，type=1房间中的设备,,beanX情景中获取的设备数据，bean：房间中国年获取的设备信息
//        bundle.putString("type",type+"");
//        intent.putExtra("data",bundle);
//门锁设置
public class DeviceSettingLockActivity extends MyActivity implements LockShareListAdapter.SelectCurrent{
    @BindView(R.id.deviceSetting_air_LockState)TextView deviceSetting_air_LockState;//显示门锁状态点view

    @BindView(R.id.deviceSetting_lock_mima_par_layout)RelativeLayout deviceSetting_lock_mima_par_layout;//密码解锁
    @BindView(R.id.deviceSetting_lock_zhiwen_par_layout)RelativeLayout deviceSetting_lock_zhiwen_par_layout;//指纹解锁
    @BindView(R.id.deviceSetting_lock_share_par_layout)RelativeLayout deviceSetting_lock_share_par_layout;//分享钥匙
    @BindView(R.id.deviceSetting_lock_setting_par_layout)RelativeLayout deviceSetting_lock_setting_par_layout;//设置
    ArrayList<RelativeLayout>listLayout;//当前选择的view
    @BindView(R.id.deviceSetting_lock_bottomLayout)LinearLayout deviceSetting_lock_bottomLayout;//底部替换的view
    private List<View> listSettingView;
    private View LockPsd,LockFinger,LockShare,LockSetting;

    LockSettingGridViewAdapter adapter;

    @BindArray(R.array.lockStr)String[]lockStr;
    private int MAX=6;//密码位数
    MyTextView deviceSetting_lock_textV_psd;
    private List<String>listTime;//时限的数据源


    private Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX room;//房间中的设备信息
    private Bean_SceneAndRoom.SceneListBean.EquipmentListBean scene;//情境中的设备信息
    private String type;
    private String fimilyId;

    private String psd;//当前输入的密码
    private Boolean isLock=false;//是否正在进行密码解锁操作;

    PopupWindow popW;
    private int lockTime=0;//分享钥匙的时限
    private int lockCurrentpos=0;//时限选择的下表
    private int SelectPos=-1;//选择的下表
    LockShareListAdapter adapter2;
    private List<Bean_FamilyUserS.PersonalListBean> list;
    private boolean isChange=false;//当前是否正在调节
    private String resStr;

    private Long DeviceId=-1L;//设备的id
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
                case 2://获取家人列表
                    try{
                        Bean_FamilyUserS userS= mGson.gson.fromJson(mTools.mResponStr,Bean_FamilyUserS.class);
                        if (userS!=null){
                            if ("0".equals(userS.getCode())){
                                userS.getPersonalList();
                                if (userS.getPersonalList()!=null&&userS.getPersonalList().size()>0){
                                    list.clear();
                                    list.addAll(userS.getPersonalList());
                                    adapter2.notifyDataSetChanged();
                                }
                            }
                            else {
                                mToast.Toast(con,userS.getMessage());
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
                case 3://分享钥匙
                    try{
                            Bean_M bean_m=mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean_m!=null){
                            if ("0".equals(bean_m.getCode())){
                                mToast.Toast(con,"分享成功");
                            }
                            else {
                                mToast.Toast(con,bean_m.getMessage());
                            }
                        }
                        else {
                            mToast.Toast(con,"分享失败");
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 4://密码解锁联网失败
                    isLock=false;
                    mToast.ToastFaild(con,ToastType.FAILD);
                    break;
                case 5://密码解锁成功
                    isLock=false;
                    try{
                        Bean_M bean_m=mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean_m!=null){
                            if ("0".equals(bean_m.getCode())){
                                mToast.Toast(con,"解锁成功");
                            }
                            else {
                                mToast.Toast(con,bean_m.getMessage());
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
        initChildView(R.layout.activity_device_setting_lock);
        setTitle("门锁");
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        initData();
        list=new ArrayList<>();
        initSettingView();
        setSelectSetting(0);
        ShowbottomView(0);
        getIntentData();
        mTools=new okhttpTools();
        getFamilyUser();//获取家人列表
    }
    public void getIntentData() {
        Bundle b=getIntent().getBundleExtra("data");
        if (b!=null){
            type=b.getString("type");
            if (!"".equals(type)&&!TextUtils.isEmpty(type)){
                if ("1".equals(type)){
                    room= (Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX) b.getSerializable("room1");
                    if (room!=null){
                        DeviceId=room.getId();
                    }
                }
                else if ("0".equals(type)){
                    scene= (Bean_SceneAndRoom.SceneListBean.EquipmentListBean) b.getSerializable("scene0");
                    if (scene!=null){
                        DeviceId=scene.getId();
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
    //密码解锁，指纹解锁，分享钥匙，设置的view
    private void initSettingView() {
        listSettingView=new ArrayList<>();
        //--------------密码解锁--------------
        LockPsd= LayoutInflater.from(con).inflate(R.layout.devicesetting_lock_psd,null);
        deviceSetting_lock_textV_psd= (MyTextView) LockPsd.findViewById(R.id.deviceSetting_lock_textV_psd);//显示密码的输入框
        MyGridView deviceSetting_lock_gridView= (MyGridView) LockPsd.findViewById(R.id.deviceSetting_lock_gridView);
        adapter=new LockSettingGridViewAdapter(lockStr,con);
        deviceSetting_lock_gridView.setAdapter(adapter);
        deviceSetting_lock_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时
        deviceSetting_lock_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isLock){//正在解锁的时候不允许输入
                    mToast.Toast(con,"正在解锁，请稍后操作");
                    return;
                }
                switch (position){
                    case 9://删除1位操作
                        if (psd!=null&&!"".equals(psd)&&psd.length()>0){
                            psd=psd.substring(0,psd.length()-1);
                        }
                        break;
                    case 10://0
                        if (psd!=null&&!"".equals(psd)){
                            if (psd.length()<MAX){
                                psd+="0";
                            }
                            }
                        else {
                            psd="0";
                            }
                        break;
                    case 11://清除操作
                        psd="";
                        break;
                    default://0-8(1,2,3,4,5,6,7,8,9)
                        if (psd!=null&&!"".equals(psd)){
                            if (psd.length()<MAX){
                                psd+=lockStr[position];
                            }
                        }
                        else {
                            psd=lockStr[position];
                            }
                        break;
                }
                deviceSetting_lock_textV_psd.setText(psd);
            }
        });


        deviceSetting_lock_textV_psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null){
                    String str=s.toString();
                    if (!"".equals(str)&&!TextUtils.isEmpty(str)&&str.length()==MAX){
//                        Toast.makeText(con,"输入完成:"+psd,Toast.LENGTH_SHORT).show();
//                        setDevice();
                        openLockPsd();//密码解锁从左
                    }
                }
            }
        });

        //--------------密码解锁------以上--------
        //--------------指纹解锁------以下--------
        LockFinger=LayoutInflater.from(con).inflate(R.layout.devicesetting_lock_finger,null);
        //--------------指纹解锁------以上--------
        //--------------分享钥匙------以下--------
        LockShare=LayoutInflater.from(con).inflate(R.layout.devicesetting_lock_share,null);
        MyListView devicesetting_lock_share_listview= (MyListView) LockShare.findViewById(R.id.devicesetting_lock_share_listview);
        listTime=new ArrayList<>();
        for (int i=1;i<60;i++){
            listTime.add(""+i);
        }
        adapter2=new LockShareListAdapter(con,list,this);
        devicesetting_lock_share_listview.setAdapter(adapter2);
        TextView lockshare_btn_submit= (TextView) LockShare.findViewById(R.id.lockshare_btn_submit);//确定按钮
        lockshare_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectPos==-1){
                    mToast.Toast(con,"您还没有选择要分享的人");
                    return;
                }
                if (lockTime==0){
                    mToast.Toast(con,"您还没有选择钥匙的时限");
                    return;
                }
                ShareLock(list.get(SelectPos).getId(),lockTime);
            }
        });
        TextView lockshare_btn_select= (TextView) LockShare.findViewById(R.id.lockshare_btn_select);//选择时限的按钮
        lockshare_btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindowTime();
            }
        });
        //--------------分享钥匙------以上--------
        //--------------设置------以下--------
        LockSetting=LayoutInflater.from(con).inflate(R.layout.share_setting,null);
        RelativeLayout share_setting_psd= (RelativeLayout) LockSetting.findViewById(R.id.share_setting_psd);//密码管理
        RelativeLayout share_setting_finger= (RelativeLayout) LockSetting.findViewById(R.id.share_setting_finger);//指纹管理
        RelativeLayout share_setting_record= (RelativeLayout) LockSetting.findViewById(R.id.share_setting_record);//开锁记录
        share_setting_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeviceId!=-1L){
                    Intent intent=new Intent(con,DeviceSettingLockRecordActivity.class);
                    intent.putExtra("id",DeviceId+"");
                    startActivity(intent);
                }
               else {
                    Log.e("设备id错误--","无法查询开锁记录");
                }
            }
        });
        share_setting_psd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(con,DeviceSettingLockChangePSDActivity.class);
                intent.putExtra("id",DeviceId+"");
                startActivity(intent);
            }
        });
        //--------------设置------以上--------
        listSettingView.add(LockPsd);
        listSettingView.add(LockFinger);
        listSettingView.add(LockShare);
        listSettingView.add(LockSetting);
    }

    private void initData() {
        listLayout=new ArrayList<>();
        listLayout.add(deviceSetting_lock_mima_par_layout);
        listLayout.add(deviceSetting_lock_zhiwen_par_layout);
        listLayout.add(deviceSetting_lock_share_par_layout);
        listLayout.add(deviceSetting_lock_setting_par_layout);
        setSelectSetting(0);
    }


    @OnClick({R.id.deviceSetting_lock_mima_par_layout,R.id.deviceSetting_lock_zhiwen_par_layout,R.id.deviceSetting_lock_share_par_layout,R.id.deviceSetting_lock_setting_par_layout})
    public void click(View vi) {
        switch (vi.getId()) {
            case R.id.deviceSetting_lock_mima_par_layout://密码解锁
                setSelectSetting(0);
                ShowbottomView(0);
                break;
            case R.id.deviceSetting_lock_zhiwen_par_layout://指纹解锁
                setSelectSetting(1);
                ShowbottomView(1);
                break;
            case R.id.deviceSetting_lock_share_par_layout://分享钥匙
                setSelectSetting(2);
                ShowbottomView(2);
                break;
            case R.id.deviceSetting_lock_setting_par_layout://设置
                setSelectSetting(3);
                ShowbottomView(3);
                break;
        }
    }
    private void ShowbottomView(int pos){
        if (deviceSetting_lock_bottomLayout!=null){
            if (deviceSetting_lock_bottomLayout.getChildCount()>0){
                for (int i=0;i<deviceSetting_lock_bottomLayout.getChildCount();i++){
                    deviceSetting_lock_bottomLayout.removeViewAt(i);
                }
            }
            deviceSetting_lock_bottomLayout.addView(listSettingView.get(pos),0);
        }
    }
            @Override
    public void getSerVerData() {

    }

    //当前选择的设置项目
    public void setSelectSetting(int pos){
        int size=listLayout.size();
        for (int i=0;i<size;i++){
            listLayout.get(i).setSelected(false);
        }
        listLayout.get(pos).setSelected(true);
    }


    private void showWindowTime() {
            lockCurrentpos=0;
            popW=new PopupWindow();
            popW = new PopupWindow();
            View air_timesetting = LayoutInflater.from(con).inflate(R.layout.share_time, null);
            TextView pop_submit= (TextView) air_timesetting.findViewById(R.id.pop_submit);//提交选择的时限
            WheelView pop_wheelView= (WheelView) air_timesetting.findViewById(R.id.pop_wheelView);
            pop_wheelView.setTitle("分钟");
            pop_wheelView.setViewAdapter(new MyWheelAdapter50(con,listTime,"af"));
            pop_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockTime=Integer.parseInt(listTime.get(lockCurrentpos));
                popW.dismiss();
                }
            });
            lockTime=0;//每次弹出重置时限为0
            pop_wheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                mToast.DebugToast(con,listTime.get(newValue)+"分钟");
                lockCurrentpos=newValue;
            }
            });
            RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_device_setting_lock);
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


    //设备设置操作
    private void setDevice() {
        if (isChange){
            mToast.Toast(con,"正在操作，请稍后");
            return;
        }
        isChange=true;
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

    //获取我的家人
    public void getFamilyUser() {
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mTools.getServerData(handler,2, Ip.serverPath+Ip.interface_getFamilyUsers,mp,"获取家人列表--");
    }

    @Override
    public void sele(int pos) {
        lockTime=0;//每次选中的时候重置
        SelectPos=pos;
    }
    //分享钥匙
//    ttp://192.168.1.55:8080/smarthome/mobileapi/equipment/doorShareKey.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |equipmentId  |Long     |Y    |设备编号
//    |targetId     |Long     |Y    |分享给谁？用户编号
//    |expires      |int      |Y    |有效时间（分钟）
    public void ShareLock(int ids,int time){
        Map<String,String> mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        mp.put("equipmentId",DeviceId+"");
        mp.put("targetId",ids+"");
        mp.put("expires",time+"");
        Log.i("用户下表--"+SelectPos,"时间--"+time);
        okhttp.getCall(Ip.serverPath+Ip.interface_shareLock,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("分享钥匙--",resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }


//    http://192.168.1.55:8080/smarthome/mobileapi/equipment/doorOpenPasswd.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&equipmentId=6&pwd=123
//    Method:POST
//    参数列表:
//            |参数名          |类型      |必需  |描述
//    |-----          |----     |---- |----
//            |equipmentId     |String   |Y    |设备编号
//    |pwd           |String   |Y    |密码
    //密码解锁操作
    public void openLockPsd(){
        if (isLock){
            mToast.Toast(con,"正在操作，请稍后");
            return;
            }
        if (DeviceId!=null&&DeviceId!=-1L){
            isLock=true;
            Map<String,String>mp=new HashMap<>();
            mp.put("token",UserInfo.userToken);
            mp.put("equipmentId",DeviceId+"");
            mp.put("pwd",psd);
            okhttp.getCall(Ip.serverPath+Ip.interface_openDoorPsd,mp,okhttp.OK_POST).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(4);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("密码解锁操作--",resStr);
                    handler.sendEmptyMessage(5);
                }
            });
        }
        else {
            isLock=false;
            mToast.Toast(con,"无法获取设备信息，解锁失败");
        }

    }
}
