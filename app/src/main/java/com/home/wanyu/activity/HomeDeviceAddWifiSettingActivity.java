package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.HomeDeviceAddWifiSettingGridViewAdapter;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.bean.Bean_DeviceAdd_Change;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyGridView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//修改信息的时候需要传递过来设备的信息，添加的试试不传,type:add（添加设备）,change(修改设备)
//bundle.putSerializable("data",list.get(position));
//        intent.putExtra("data",bundle);
//        intent.putExtra("type","add");
//设备设置页面
public class HomeDeviceAddWifiSettingActivity extends MyActivity {
    private String type;//add添加，change修改
    private int roomId;//当前的roomID；
    private int iconId;//iconId
    private Bean_AllDevice.EquipmentListBean device;//当前设备的信息
    @BindView(R.id.home_deviceWifi_setting_rela_eidtext)EditText home_deviceWifi_setting_rela_eidtext;//设备名称
    @BindView(R.id.home_deviceWifi_setting_rela_condition_guishu)TextView home_deviceWifi_setting_rela_condition_guishu;//设备所属的房间名称
    @BindView(R.id.home_deviceWifi_setting_rela_condition_relaLayout)RelativeLayout home_deviceWifi_setting_rela_condition_relaLayout;//所属房间名所在的view
    @BindView(R.id.home_deviceWifi_setting_rela_condition_imageselect)ImageView home_deviceWifi_setting_rela_condition_imageselect;//所属房间的image下拉按钮
    @BindView(R.id.textView3)TextView textView3;//设备名称的view
    @BindView(R.id.device_image)ImageView device_image;

//    @BindView(R.id.home_deviceWifi_setting_gridview)MyGridView home_deviceWifi_setting_gridview;//图标显示的gridview
//    camera door light3 light2 line music socket1 sound square sun tv1 more3
    int[]ResId={R.mipmap.camera,R.mipmap.door,R.mipmap.light2,R.mipmap.light3,R.mipmap.line,R.mipmap.music,
                R.mipmap.socket1,R.mipmap.sound,R.mipmap.square,R.mipmap.sun,R.mipmap.tv1,R.mipmap.more3};
    private ArrayList<HashMap<String,String>>list;
    private HomeDeviceAddWifiSettingGridViewAdapter adapter;
    //所属房间的添加按钮（在没有添加过房间时不显示下拉菜单：后台获取到的房间为空时）
    private boolean isRoomEmpty=true;//用户还没有添加过房间
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_DeviceAdd_Change add_change= mGson.gson.fromJson(resStr,Bean_DeviceAdd_Change.class);
                        if (add_change!=null){
                            if ("0".equals(add_change.getCode())){
                                mToast.DebugToast(con,"成功");
                            }
                            else {
                                ServerCode.showResponseMsg(con,add_change.getCode());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_device_add_wifi_setting);
        setTitle("设备设置");
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        initData();
        setRoomState();
    }


    public void setRoomState(){
        if (isRoomEmpty){//用户没有添加过房间时
            home_deviceWifi_setting_rela_condition_imageselect.setVisibility(View.GONE);
            home_deviceWifi_setting_rela_condition_guishu.setSelected(true);
            home_deviceWifi_setting_rela_condition_guishu.setText("添加房间");
        }
        else
        {
            home_deviceWifi_setting_rela_condition_imageselect.setVisibility(View.VISIBLE);
            home_deviceWifi_setting_rela_condition_guishu.setSelected(false);
//            home_deviceWifi_setting_rela_condition_guishu.setText("客厅");
            home_deviceWifi_setting_rela_condition_guishu.setText(device.getRoomId()+"");//未做处理暂时，需要与获取到的roomId对比
        }
    }
    private void initData() {
        type=getIntent().getStringExtra("type");
        if ("add".equals(type)){//未处理暂时====================
            isRoomEmpty=true;
        }
        else if ("change".equals(type)){
            isRoomEmpty=false;
            Bundle b=getIntent().getBundleExtra("data");
            device= (Bean_AllDevice.EquipmentListBean) b.getSerializable("data");
            textView3.setText(icon.getDeviceName(device.getIconId()));
            ViewGroup.LayoutParams params=device_image.getLayoutParams();
            params.width=getWindowManager().getDefaultDisplay().getWidth()/4;
            params.height=getWindowManager().getDefaultDisplay().getWidth()/4;
            device_image.setLayoutParams(params);
            device_image.setImageResource(device.getIconId()>icon.mIconRes.length-1?R.mipmap.errorphoto:icon.mIconRes[device.getIconId()]);
            roomId=device.getRoomId();
            iconId=device.getIconId();
            home_deviceWifi_setting_rela_eidtext.setText(device.getName());
            Log.e("-------",device.getRoomId()+"---------");

        }
        adapter=new HomeDeviceAddWifiSettingGridViewAdapter(list,con);
    }



    @OnClick({R.id.activity_homescene_deviceWifi_setting_submit,R.id.home_deviceWifi_setting_rela_condition_relaLayout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homescene_deviceWifi_setting_submit://提交按钮点击事件
//                Toast.makeText(con,"确定",Toast.LENGTH_SHORT).show();
                if (!"".equals(home_deviceWifi_setting_rela_eidtext.getText().toString())&&!TextUtils.isEmpty(home_deviceWifi_setting_rela_eidtext.getText().toString())){
                    sendMsg();
                }
                else {
                    mToast.Toast(con,"房间名称不正确");
                }
                break;
            case R.id.home_deviceWifi_setting_rela_condition_relaLayout:

                if (!isRoomEmpty){
                    Toast.makeText(con,"选择房间",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(con,"添加房间",Toast.LENGTH_SHORT).show();
                    isRoomEmpty=false;
                    setRoomState();

                }
                break;
        }
    }
    @Override
    public void getSerVerData() {

    }
    //提交更改／添加信息http://192.168.1.55:8080/smarthome/mobileapi/equipment/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    post
//    |token        |String   |Y    |令牌
//    |id           |Long     |N    |编号
//    |name         |String   |N    |设备名称
//    |iconId       |Long     |Y    |设备图标编号
//    |iconUrl      |String   |Y    |设备图标URL
//    |state        |Integer  |N    |状态0=开1=关
//    |roomId       |Long     |N    |房间编号外键(可以为0，即不指定房间编号)
//    |serialNumber |String   |Y    |设备序列号唯一标识符
//    |extendState  |String   |Y    |扩展状态控制，如灯的亮度，空调的温度等
//    |familyId     |Long     |N    |家庭编号
    public void sendMsg(){
        HashMap<String,String>mp=new HashMap<>();
//        mp.put("token", UserInfo.userToken);
        if ("change".equals(type)){
            mp.put("id",device.getId()+"");
            mp.put("name",home_deviceWifi_setting_rela_eidtext.getText().toString());
            mp.put("iconId",device.getIconId()+"");
            mp.put("iconUrl",device.getIconUrl());
            mp.put("state",device.getState()+"");
            mp.put("roomId",roomId+"");
            mp.put("serialNumber",device.getSerialNumber());
            mp.put("extendState",device.getExtendState());
            mp.put("familyId",device.getFamilyId()+"");
        }
        else if("add".equals(type)){
            mp.put("id","");
            mp.put("name",home_deviceWifi_setting_rela_eidtext.getText().toString());
            mp.put("iconId",iconId+"");
            mp.put("iconUrl","");
            mp.put("state","0");
            mp.put("roomId","0");
            mp.put("serialNumber","");
            mp.put("extendState","");
            mp.put("familyId","");
        }
        Log.e("roomId----",roomId+"---");
        okhttp.getCallCookie(Ip.serverPath+Ip.interface_DeviceAdd_Change,mp,"token="+UserInfo.userToken).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("添加／修改设备返回--",resStr);
                handler.sendEmptyMessage(1);
            }
        });
//        mTools.getServerDataPost(handler,1,Ip.serverPath+Ip.interface_HomeRoom_OpenAndOn,mp,"添加／修改设备----");
    }
}
