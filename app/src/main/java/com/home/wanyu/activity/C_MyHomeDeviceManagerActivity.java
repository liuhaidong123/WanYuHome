package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.C_View.C_MyHomeDeviceManagerPresenter;
import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerAdapter;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.zxing.app.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家-设备管理页面
public class C_MyHomeDeviceManagerActivity extends CMyActivity {
    C_MyHomeDeviceManagerPresenter presenter;
    @BindView(R.id.titleButton1)TextView textBtn;//全选按钮
    @BindView(R.id.c_myhome_devicemanager_listview)ListView c_myhome_devicemanager_listview;
    C_MyHomeDeviceManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_device_manager);
        unbinder= ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        adapter=new C_MyHomeDeviceManagerAdapter(this);
        c_myhome_devicemanager_listview.setAdapter(adapter);
    }
    @OnClick({R.id.c_home_device_delete,R.id.c_home_device_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_device_delete:
                startActivity(new Intent(this,C_MyHomeDeviceDeleteActivity.class));
                break;
            case R.id.c_home_device_add:
                startActivity(new Intent(this,CaptureActivity.class));
                break;
        }
    }
}
