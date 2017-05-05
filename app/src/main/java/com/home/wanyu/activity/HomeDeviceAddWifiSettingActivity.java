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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_device_add_wifi_setting);
        setTitle("设备设置");
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        initData();
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
//        home_deviceWifi_setting_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position!=adapter.getCount()-1){
//                    setSelct(position);
//                }
//                else {
//                    Toast.makeText(con,"更多图标，请期待",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }



    @OnClick({R.id.activity_homescene_deviceWifi_setting_submit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homescene_deviceWifi_setting_submit://提交按钮点击事件
                break;
        }
    }
    @Override
    public void getSerVerData() {

    }
}
