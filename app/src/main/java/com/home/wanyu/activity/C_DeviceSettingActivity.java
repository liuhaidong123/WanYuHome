package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设备设置页面
public class C_DeviceSettingActivity extends CMyActivity {
    @BindView(R.id.home_deviceWifi_setting_rela_condition_imageselectc)ImageView home_deviceWifi_setting_rela_condition_imageselectc;//下拉的image
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__device_setting);
        ButterKnife.bind(this);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        titleButton= (TextView) findViewById(R.id.titleButton);
        initTitle("设备设置","确定",false);
    }
    @OnClick(R.id.home_deviceWifi_setting_rela_condition_relaLayoutc)
    public void click(View vi){
        mToast.Toast(this,"选择房间");
    }
    @Override
    public void Submit(View vi) {
        super.Submit(vi);
        mToast.Toast(this,"提交");
    }

    @Override
    public void getServerData() {

    }
}
