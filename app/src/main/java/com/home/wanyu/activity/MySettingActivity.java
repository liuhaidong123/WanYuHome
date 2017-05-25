package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置页面
public class MySettingActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_my_setting);
        setTitle("设置");
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
    }
    @OnClick({R.id.activity_my_setting_delete,R.id.activity_my_setting_top,R.id.activity_my_setting_bottom})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_setting_delete://退出登陆
                break;
            case R.id.activity_my_setting_top://修改绑定手机号
                startActivity(new Intent(con,MySettingChangePhoneActivity.class));
                break;
            case R.id.activity_my_setting_bottom://修改密码
                startActivity(new Intent(con,MySettingChangePsdActivity.class));
                break;
        }
        }
    @Override
    public void getSerVerData() {

    }
}
