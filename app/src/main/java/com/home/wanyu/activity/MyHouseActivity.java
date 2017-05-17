package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家
public class MyHouseActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_my_house);
        setTitle("我的家");
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
    }
    @OnClick({R.id.my_house_layout_Housemsg,R.id.my_house_layout_familyImage,R.id.my_house_layout_Device})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.my_house_layout_Housemsg://我的房屋信息
                startActivity(new Intent(MyHouseActivity.this,MyHouseInfoActivity.class));
            break;
            case R.id.my_house_layout_familyImage://我的家人管理
//                mToast.Toast(con,"我的设备管理");
                startActivity(new Intent(con,MyHouseFamilyManagerActivity.class));
                break;
            case R.id.my_house_layout_Device://我的设备管理
//                mToast.Toast(con,"我的设备管理");
                Intent intent=new Intent();
                intent.putExtra("type","all");
                intent.setClass(con,MyHouseDeviceManagerActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void getSerVerData() {

    }
}
