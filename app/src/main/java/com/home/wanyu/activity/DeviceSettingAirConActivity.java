package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.bean.Bean_setDeviceData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.MySwitchButton;
import com.home.wanyu.lzhView.SeekBar;
import com.home.wanyu.lzhView.SwitchButton;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.mEmeu.ModeType;
import com.home.wanyu.mEmeu.WindType;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("scene0",beanX);
//        bundle.putSerializable("room1",bean);
//bundle.putSerializable("familyId",fmiId);
//type=0:情景中的设备，type=1房间中的设备,,beanX情景中获取的设备数据，bean：房间中国年获取的设备信息
//        bundle.putString("type",type+"");
//        intent.putExtra("data",bundle);
//空调设置页面
public class DeviceSettingAirConActivity extends MyActivity implements View.OnClickListener{
    private String title="空调";
    @BindView(R.id.deviceSetting_air_switch)SwitchButton deviceSetting_air_switch;//空调开关按钮
    @BindView(R.id.deviceSetting_air_wendu)TextView deviceSetting_air_wendu;//显示当前温度的textView
    @BindView(R.id.deviceSetting_air_wendu_plus)ImageView deviceSetting_air_wendu_plus;//减号
    @BindView(R.id.deviceSetting_air_wendu_add)ImageView deviceSetting_air_wendu_add;//加号

    @BindView(R.id.deviceSetting_air_moshi_par_layout)RelativeLayout deviceSetting_air_moshi_par_layout;//模式选项的layout布局
    @BindView(R.id.deviceSetting_air_fengsu_par_layout)RelativeLayout deviceSetting_air_fengsu_par_layout;//风速选项
    @BindView(R.id.deviceSetting_air_fengxiang_par_layout)RelativeLayout deviceSetting_air_fengxiang_par_layout;//风向选项
    @BindView(R.id.deviceSetting_air_dingshi_par_layout)RelativeLayout deviceSetting_air_dingshi_par_layout;//定时选项

    @BindView(R.id.deviceSetting_air_moshi_par_image)ImageView deviceSetting_air_moshi_par_image;//显示的当前模式的图标
    @BindView(R.id.deviceSetting_air_moshi_par_textV)TextView deviceSetting_air_moshi_par_textV;//显示当前选择的模式名字的view

    @BindView(R.id.deviceSetting_air_fengsu_par_image)ImageView deviceSetting_air_fengsu_par_image;//显示当前风速的图标（大，中，小）
    @BindView(R.id.deviceSetting_air_fengsu_par_textV)TextView deviceSetting_air_fengsu_par_textV;//显示当前风速的textview

    @BindView(R.id.deviceSetting_air_fengxiang_par_textV)TextView deviceSetting_air_fengxiang_par_textV;//显示当前风向的view

    @BindView(R.id.deviceSetting_air_bottomLayout)LinearLayout deviceSetting_air_bottomLayout;
    private ArrayList<RelativeLayout> listLayout;


    private View air_modesetting;//空调设置的view
    private View air_windspeed;//风速选择的view
    private View air_winddirect;//风向
    private View air_timesetting;//定时设置的view
    private List<View> listSettingView;

    RelativeLayout ari_modesetting_mode_auto;//自动模式
    RelativeLayout ari_modesetting_mode_cold;//制冷
    RelativeLayout ari_modesetting_mode_hot;//制热
    RelativeLayout ari_modesetting_mode_wet;//除湿
    RelativeLayout ari_modesetting_mode_wind;//送风

    private SeekBar mSeekBar;//风速控制按钮

    private RelativeLayout ari_winddirect_auto;//自动风向
    private RelativeLayout ari_winddirect_topAndBottom;//上下风向
    private RelativeLayout ari_winddirect_leftAndRight;//左右风向

    //定时关闭控制
    private WheelView air_timesetting_wheelHour;
    private WheelView air_timesetting_wheelMini;
    private List<String>listHour;
    private List<String>listMini;
    private int SelectHourPos=0;
    private int SelectMiniPos=0;
    private TextView air_timesetting_textv;//显示关闭时间的view
    private TextView air_timesetting_submit;//提交的按钮

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
        initChildView(R.layout.activity_device_setting_air_con);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle(title);
        ShowChildView(DEFAULTRESID);
        getIntentData();//获取inten重的数据
        initData();
        initSettingView();//设置的view
        deviceSetting_air_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
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
                            deviceSetting_air_switch.setNorState(false);
                        }
                        else {
                            deviceSetting_air_switch.setNorState(true);
                            }
                    }
                else if ("0".equals(type)){
                        scene= (Bean_SceneAndRoom.SceneListBean.EquipmentListBean) b.getSerializable("scene0");
                        if (scene.getState()==1L){
                            deviceSetting_air_switch.setNorState(false);
                        }
                      else {
                            deviceSetting_air_switch.setNorState(true);
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
    @OnClick({R.id.deviceSetting_air_moshi_par_layout,R.id.deviceSetting_air_fengsu_par_layout,R.id.deviceSetting_air_fengxiang_par_layout,R.id.deviceSetting_air_dingshi_par_layout
    ,R.id.deviceSetting_air_wendu_add,R.id.deviceSetting_air_wendu_plus})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.deviceSetting_air_moshi_par_layout://模式
                setSelectSetting(0);
                ShowbottomView(0);
                break;
            case R.id.deviceSetting_air_fengsu_par_layout://风速
                setSelectSetting(1);
                ShowbottomView(1);
                break;
            case R.id.deviceSetting_air_fengxiang_par_layout://风向
                setSelectSetting(2);
                ShowbottomView(2);
                break;
            case R.id.deviceSetting_air_dingshi_par_layout://定时
                setSelectSetting(3);
                ShowbottomView(3);
                break;
            case R.id.deviceSetting_air_wendu_add://加最高
                int tem=Integer.parseInt(deviceSetting_air_wendu.getText().toString())<40?Integer.parseInt(deviceSetting_air_wendu.getText().toString()):39;
                deviceSetting_air_wendu.setText(""+(tem+1));

                break;
            case R.id.deviceSetting_air_wendu_plus://减
                int temp=Integer.parseInt(deviceSetting_air_wendu.getText().toString())>0?Integer.parseInt(deviceSetting_air_wendu.getText().toString()):1;
                deviceSetting_air_wendu.setText(""+(temp-1));
                break;
        }
    }
    private void ShowbottomView(int pos){
        if (deviceSetting_air_bottomLayout!=null){
            if (deviceSetting_air_bottomLayout.getChildCount()>0){
                for (int i=0;i<deviceSetting_air_bottomLayout.getChildCount();i++){
                    deviceSetting_air_bottomLayout.removeViewAt(i);
                }
            }
            deviceSetting_air_bottomLayout.addView(listSettingView.get(pos),0);
        }
    }
    private void initSettingView() {

        listSettingView=new ArrayList<>();

        air_modesetting= LayoutInflater.from(con).inflate(R.layout.air_modesetting,null);
        ari_modesetting_mode_auto= (RelativeLayout) air_modesetting.findViewById(R.id.ari_modesetting_mode_auto);//自动
        ari_modesetting_mode_auto.setOnClickListener(this);

        ari_modesetting_mode_cold= (RelativeLayout) air_modesetting.findViewById(R.id.ari_modesetting_mode_cold);//制冷
        ari_modesetting_mode_cold.setOnClickListener(this);

        ari_modesetting_mode_hot= (RelativeLayout) air_modesetting.findViewById(R.id.ari_modesetting_mode_hot);//制热
        ari_modesetting_mode_hot.setOnClickListener(this);

        ari_modesetting_mode_wet= (RelativeLayout) air_modesetting.findViewById(R.id.ari_modesetting_mode_wet);//除湿
        ari_modesetting_mode_wet.setOnClickListener(this);

        ari_modesetting_mode_wind= (RelativeLayout) air_modesetting.findViewById(R.id.ari_modesetting_mode_wind);//送风
        ari_modesetting_mode_wind.setOnClickListener(this);

        air_windspeed= LayoutInflater.from(con).inflate(R.layout.air_windspeed,null);
        mSeekBar= (SeekBar) air_windspeed.findViewById(R.id.air_windspeed_seekBar);
        mSeekBar.setOnSeekListener(new SeekBar.SeekListener() {
            @Override
            public void seek(float mark, int max, int state,int windState) {
                    switch (windState){
                        case 0:
                            setWinType(WindType.Small);
                            break;
                        case 1:
                            setWinType(WindType.Middle);
                            break;
                        case 2:
                            setWinType(WindType.Large);
                            break;
                    }
            }
        });

        //风向控制
        air_winddirect= LayoutInflater.from(con).inflate(R.layout.air_winddirect,null);
        //ZIDONG
        ari_winddirect_auto= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_auto);
        ari_winddirect_auto.setOnClickListener(this);
        //shangxia
        ari_winddirect_topAndBottom= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_topAndBottom);
        ari_winddirect_topAndBottom.setOnClickListener(this);
        //左右风向
        ari_winddirect_leftAndRight= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_leftAndRight);
        ari_winddirect_leftAndRight.setOnClickListener(this);


        //定时控制
        air_timesetting= LayoutInflater.from(con).inflate(R.layout.air_timesetting,null);
        air_timesetting_submit= (TextView) air_timesetting.findViewById(R.id.air_timesetting_submit);
        air_timesetting_submit.setOnClickListener(this);
        air_timesetting_textv= (TextView) air_timesetting.findViewById(R.id.air_timesetting_textv);
        air_timesetting_textv.setText(listHour.get(SelectHourPos)+" "+listMini.get(SelectMiniPos)+"后 将关闭该空调");
        air_timesetting_wheelHour= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelHour);
        air_timesetting_wheelHour.setTitle("小时");
        air_timesetting_wheelHour.setViewAdapter(new MyWheelAdapter50(con,listHour,"小时"));
        air_timesetting_wheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectHourPos=newValue;
                Toast.makeText(con,listHour.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时 "+listMini.get(SelectMiniPos)+"分钟后 将关闭该空调");
            }
        });
        air_timesetting_wheelMini= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelMini);
        air_timesetting_wheelMini.setTitle("分钟");
        air_timesetting_wheelMini.setViewAdapter(new MyWheelAdapter50(con,listMini,"分钟"));
        air_timesetting_wheelMini.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectMiniPos=newValue;
                Toast.makeText(con,listMini.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时 "+listMini.get(SelectMiniPos)+"分钟后 将关闭该空调");
            }
        });

        listSettingView.add(air_modesetting);
        listSettingView.add(air_windspeed);
        listSettingView.add(air_winddirect);
        listSettingView.add(air_timesetting);
    }

    private void initData() {
        listHour=new ArrayList<>();
        listMini=new ArrayList<>();
        for (int i=0;i<24;i++){
            listHour.add(i+"");
        }
       for (int i=1;i<60;i++){
           listMini.add(i+"");
       }

        listLayout=new ArrayList<>();
        listLayout.add(deviceSetting_air_moshi_par_layout);
        listLayout.add(deviceSetting_air_fengsu_par_layout);
        listLayout.add(deviceSetting_air_fengxiang_par_layout);
        listLayout.add(deviceSetting_air_dingshi_par_layout);
    }

    //当前选择的设置项目
    public void setSelectSetting(int pos){
        int size=listLayout.size();
        for (int i=0;i<size;i++){
            listLayout.get(i).setSelected(false);
        }
        listLayout.get(pos).setSelected(true);
    }
    //设置当前选择的模式
    public void setModeType(ModeType type){
                deviceSetting_air_moshi_par_textV.setText(type.getModeName(type));
                deviceSetting_air_moshi_par_image.setImageResource(type.getResId(type));
    }
    //设置风速图标以及显示的文字
    public void setWinType(WindType type){
                deviceSetting_air_fengsu_par_image.setImageResource(type.getResId(type));
                deviceSetting_air_fengsu_par_textV.setText(type.getWindName(type));
    }

    //设置风向
    public void setWindDirect(int pos){
        switch (pos){
            case 0:
                deviceSetting_air_fengxiang_par_textV.setText("自动");
                break;
            case 1:
                deviceSetting_air_fengxiang_par_textV.setText("上下风");
                break;
            case 2:
                deviceSetting_air_fengxiang_par_textV.setText("左右风");
                break;
        }
    }
    @Override
    public void getSerVerData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //以下模式控制
            case R.id.ari_modesetting_mode_auto://自动
                setModeType(ModeType.AUT);
                break;
            case R.id.ari_modesetting_mode_cold://制冷
                setModeType(ModeType.COLD);
                break;
            case R.id.ari_modesetting_mode_hot://制热
                setModeType(ModeType.HOT);
                break;
            case R.id.ari_modesetting_mode_wet://除湿
                setModeType(ModeType.WET);
                break;
            case R.id.ari_modesetting_mode_wind://送风
                setModeType(ModeType.WIND);
                break;

            //以下风向控制
            case R.id.ari_winddirect_auto://自动风向
                setWindDirect(0);
                break;
            case R.id.ari_winddirect_topAndBottom://上下风向
                setWindDirect(1);
                break;
            case R.id.ari_winddirect_leftAndRight://左右风向
                setWindDirect(2);
                break;
            //以下定时控制
            case R.id.air_timesetting_submit://定时控制的提交按钮
                Toast.makeText(con,"提交",Toast.LENGTH_SHORT).show();
                break;
        }
    }




    private void setDevice() {
        if (isChange){
            mToast.Toast(con,"正在操作，请稍后");
            return;
            }
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
            isChange=true;
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
            isChange=true;
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
