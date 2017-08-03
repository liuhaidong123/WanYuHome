package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.home.wanyu.C_View.DeviceSettingC_LockPresenter;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//门锁设置DeviceSettingC_AirConActivity
public class DeviceSettingC_LockActivity extends CMyActivity implements DeviceSettingC_LockPresenter.Locking {
    DeviceSettingC_LockPresenter presenter;
    @BindView(R.id.locksetting_textv_lockstate)TextView locksetting_textv_lockstate;//门锁状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__device_setting_lock);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("门锁","--",true);
        unbinder= ButterKnife.bind(this);
        presenter=new DeviceSettingC_LockPresenter(this,this);
    }
    @OnClick({R.id.lockSetting_Locking,R.id.lockSetting_LockShare,R.id.lockSetting_LockSetting,R.id.lockSetting_fingerPrint})
    public void OnClick(View vi){
        switch (vi.getId()){
            case R.id.lockSetting_Locking://密码开锁
                presenter.ShowWindowLocking();
                break;
            case R.id.lockSetting_LockShare://分享钥匙
                startActivity(new Intent(this,DeviceSettingC_LockShareActivity.class));
                break;
            case R.id.lockSetting_LockSetting://设置
                startActivity(new Intent(this,DeviceSettingC_LockSettingActivity.class));
                break;
            case R.id.lockSetting_fingerPrint://指纹解锁
                break;
        }

    }

    @Override
    public void Success() {

    }

    @Override
    public void Failed() {

    }

    @Override
    public void onNetWorkFailed() {

    }

    @Override
    public void onLocking() {

    }
}
