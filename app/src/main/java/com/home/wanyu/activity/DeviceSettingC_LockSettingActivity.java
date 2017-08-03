package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

//门锁设置
public class DeviceSettingC_LockSettingActivity extends CMyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__lock_setting);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("门锁设置","--",true);
        unbinder= ButterKnife.bind(this);
    }
    @OnClick({R.id.locksetting_setting_changePsd,R.id.locksetting_setting_keyManager,R.id.locksetting_setting_record})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.locksetting_setting_changePsd://修改密码
                startActivity(new Intent(this,DeviceSettingLockChangePSDActivity.class));
                break;
            case R.id.locksetting_setting_keyManager://钥匙管理
                startActivity(new Intent(this,DeviceSettingC_LockManagerActivity.class));
                break;
            case R.id.locksetting_setting_record://开锁记录查询
                startActivity(new Intent(this,DeviceSettingC_LockRecordActivity.class));
                break;
        }
    }
}
