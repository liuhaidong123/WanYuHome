package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.NetWorkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//添加设备后连接wifi的页面
//intent.putExtra("code",rawResult.getText());
public class HomeDeviceAddWifiActivity extends MyActivity {
    @BindView(R.id.activity_homedeviceaddwifi_rela_contactWifi_wifiPsd)EditText activity_homedeviceaddwifi_rela_contactWifi_wifiPsd;//wifi密码输入框
    @BindView(R.id.activity_homedeviceaddwifi_rela_contactWifi_wifiName)TextView activity_homedeviceaddwifi_rela_contactWifi_wifiName;//wifi名字的view

    @BindView(R.id.activity_homedeviceaddwifi_rela_contactWifi)RelativeLayout activity_homedeviceaddwifi_rela_contactWifi;//连接前显示的view
    @BindView(R.id.activity_homedeviceaddwifi_rela_contactWifi_success)RelativeLayout activity_homedeviceaddwifi_rela_contactWifi_success;//连接成功时显示的view
    @BindView(R.id.activity_homedeviceaddwifi_rela_contactWifi_error)RelativeLayout activity_homedeviceaddwifi_rela_contactWifi_error;//连接失败时显示的view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_device_add_wifi);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("连接wifi");
        ShowChildView(DEFAULTRESID);
        initView();
    }

    private void initView() {
        String name= NetWorkUtils.getWifiInfo(con);//获取wifi到ssid
        name=name.replace("\"","");
        activity_homedeviceaddwifi_rela_contactWifi_wifiName.setText(name);

        activity_homedeviceaddwifi_rela_contactWifi_wifiPsd.setText(getIntent().getStringExtra("code"));
    }

    @OnClick({R.id.activity_homedeviceaddwifi_rela_contactWifi_submit,R.id.activity_homedeviceaddwifi_rela_contactWifi_success_submit,R.id.activity_homedeviceaddwifi_contactWifi_error_submit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homedeviceaddwifi_rela_contactWifi_submit://连接wifi时的提交按钮
               if (checkWifiState()){//检查连接问题，这里许改动，没有处理连接的问题
                   activity_homedeviceaddwifi_rela_contactWifi.setVisibility(View.GONE);
                   activity_homedeviceaddwifi_rela_contactWifi_success.setVisibility(View.VISIBLE);
               }
                else {
                   Toast.makeText(con,"请检查wifi信息是否正确",Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.activity_homedeviceaddwifi_rela_contactWifi_success_submit://连接成功时的设置按钮
//                mToast.DebugToast(con,"去设置");
                //测试用，点击后进入连接失败页面
                Intent intent=new Intent(con,C_DeviceSettingActivity.class);
                startActivity(intent);
//                activity_homedeviceaddwifi_rela_contactWifi_success.setVisibility(View.GONE);
//                activity_homedeviceaddwifi_rela_contactWifi_error.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_homedeviceaddwifi_contactWifi_error_submit://连接失败时继续连接的按钮
                activity_homedeviceaddwifi_rela_contactWifi.setVisibility(View.VISIBLE);
                activity_homedeviceaddwifi_rela_contactWifi_error.setVisibility(View.GONE);
                break;
        }
    }


    public boolean checkWifiState(){
        String name=activity_homedeviceaddwifi_rela_contactWifi_wifiName.getText().toString();
        String psd=activity_homedeviceaddwifi_rela_contactWifi_wifiPsd.getText().toString();
        if (!"".equals(name)&&!TextUtils.isEmpty(name)&&
                !"".equals(psd)&&!TextUtils.isEmpty(psd)){
            return true;
        }
        return false;
    }
    @Override
    public void getSerVerData() {

    }
}
