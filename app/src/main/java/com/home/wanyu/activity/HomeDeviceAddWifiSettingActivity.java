package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeDeviceAddWifiSettingGridViewAdapter;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设备设置页面
public class HomeDeviceAddWifiSettingActivity extends MyActivity {
    @BindView(R.id.home_deviceWifi_setting_rela_eidtext)EditText home_deviceWifi_setting_rela_eidtext;//设备名称
    @BindView(R.id.home_deviceWifi_setting_rela_condition_guishu)TextView home_deviceWifi_setting_rela_condition_guishu;//设备所属的房间名称
    @BindView(R.id.home_deviceWifi_setting_rela_condition_relaLayout)RelativeLayout home_deviceWifi_setting_rela_condition_relaLayout;//所属房间名所在的view
    @BindView(R.id.home_deviceWifi_setting_rela_condition_imageselect)ImageView home_deviceWifi_setting_rela_condition_imageselect;//所属房间的image下拉按钮
    @BindView(R.id.home_deviceWifi_setting_gridview)MyGridView home_deviceWifi_setting_gridview;//图标显示的gridview
//    camera door light3 light2 line music socket1 sound square sun tv1 more3
    int[]ResId={R.mipmap.camera,R.mipmap.door,R.mipmap.light2,R.mipmap.light3,R.mipmap.line,R.mipmap.music,
                R.mipmap.socket1,R.mipmap.sound,R.mipmap.square,R.mipmap.sun,R.mipmap.tv1,R.mipmap.more3};
    private ArrayList<HashMap<String,String>>list;
    private HomeDeviceAddWifiSettingGridViewAdapter adapter;
    //所属房间的添加按钮（在没有添加过房间时不显示下拉菜单：后台获取到的房间为空时）
    private boolean isRoomEmpty=true;//用户还没有添加过房间

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
            home_deviceWifi_setting_rela_condition_guishu.setText("客厅");
        }
    }
    private void initData() {
        list=new ArrayList<>();
        int size=ResId.length;
        for (int i=0;i<size;i++){
            HashMap<String,String>mp=new HashMap<>();
            mp.put("select","0");
            mp.put("image",ResId[i]+"");
            list.add(mp);
        }
        list.get(0).put("select","1");
        adapter=new HomeDeviceAddWifiSettingGridViewAdapter(list,con);
        home_deviceWifi_setting_gridview.setAdapter(adapter);
    }



    @OnClick({R.id.activity_homescene_deviceWifi_setting_submit,R.id.home_deviceWifi_setting_rela_condition_relaLayout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homescene_deviceWifi_setting_submit://提交按钮点击事件
                Toast.makeText(con,"确定",Toast.LENGTH_SHORT).show();

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
}
