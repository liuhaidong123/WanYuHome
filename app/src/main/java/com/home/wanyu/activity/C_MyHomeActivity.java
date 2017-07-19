package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.C_View.C_MyHomePresenter;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家
public class C_MyHomeActivity extends CMyActivity {
    C_MyHomePresenter presenter;
    @BindView(R.id.c_myHome_image)ImageView c_myHome_image;//下拉的image
    @BindView(R.id.c_myHome_houseName)TextView c_myHome_houseName;//显示小区名字的view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("我的家",",,",true);
        unbinder= ButterKnife.bind(this);
    }
    @OnClick({R.id.c__my_home_LayoutName,R.id.c_myHome_layout_HouseInfo,R.id.c_myHome_layout_FamilyManager,
            R.id.c_myHome_layout_DeviceManager,R.id.c_myHome_layout_SceneManger,R.id.c_myHome_layout_RoomManager})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c__my_home_LayoutName:
                c_myHome_image.setSelected(!c_myHome_image.isSelected());
                break;
            case R.id.c_myHome_layout_HouseInfo://我的房屋信息
                startActivity(new Intent(this,MyHouseInfoActivity.class));
                break;
            case R.id.c_myHome_layout_FamilyManager://家人管理
                startActivity(new Intent(this,MyHouseFamilyManagerActivity.class));
                break;
            case R.id.c_myHome_layout_DeviceManager://设备管理
                startActivity(new Intent(this,C_MyHomeDeviceManagerActivity.  class));
                break;
            case R.id.c_myHome_layout_SceneManger://情景管理
                startActivity(new Intent(this,C_MyHomeSceneManagerActivity.class));
                break;
            case R.id.c_myHome_layout_RoomManager://房间管理
                startActivity(new Intent(this,C_MyHomeRoomManagerActivity.class));
                break;
        }
    }
    @Override
    public void getServerData() {

    }
}
