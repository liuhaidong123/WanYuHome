package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.LockRecordAdapter;
import com.home.wanyu.myUtils.CMyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceSettingC_LockRecordActivity extends CMyActivity {
    @BindView(R.id.lockmanager_record_listview)ListView lockmanager_record_listview;
    LockRecordAdapter adapter;//开锁记录的adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__lock_record);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("开锁记录","--",true);
        unbinder= ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        adapter=new LockRecordAdapter(this);
        lockmanager_record_listview.setAdapter(adapter);
    }
}
