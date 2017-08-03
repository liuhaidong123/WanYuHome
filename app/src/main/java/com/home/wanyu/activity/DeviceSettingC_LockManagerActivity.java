package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.DeviceSettingC_LockManagerAdapter;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceSettingC_LockManagerActivity extends CMyActivity {
    @BindView(R.id.lockmanager_listview)ListView lockmanager_listview;
    DeviceSettingC_LockManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__lock_manager);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("钥匙管理","--",true);
        unbinder= ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        adapter=new DeviceSettingC_LockManagerAdapter(this);
        lockmanager_listview.setAdapter(adapter);
    }
}
