package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加设备的activity
public class HomeDeviceAddActivity extends MyActivity {
    @BindView(R.id.activity_home_device_add_textView)TextView activity_home_device_add_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_device_add);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("添加设备");
        ShowChildView(DEFAULTRESID);
    }
    @OnClick({R.id.activity_home_device_add_textView})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_home_device_add_textView:
                startActivity(new Intent(con,HomeDeviceAddWifiActivity.class));
                break;
        }
        }


    //初始化网络请求的方法
    @Override
    public void getSerVerData() {

    }
}
