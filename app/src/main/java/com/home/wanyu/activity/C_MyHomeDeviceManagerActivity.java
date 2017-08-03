package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.C_Model.C_MyHomeDeviceManagerModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.C_View.C_MyHomeDeviceManagerPresenter;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerAdapter;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.zxing.app.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家-设备管理页面
public class C_MyHomeDeviceManagerActivity extends CMyActivity implements ILoading,C_MyHomeDeviceManagerModel.IAllDeviceModel{
    C_MyHomeDeviceManagerPresenter presenter;
    @BindView(R.id.titleButton1)TextView textBtn;//全选按钮
    @BindView(R.id.c_myhome_devicemanager_listview)ListView c_myhome_devicemanager_listview;
    @BindView(R.id.c_home_device_delete)TextView c_home_device_delete;//删除设备按钮
    @BindView(R.id.leftlayout)LinearLayout leftlayout;//删除按钮所在的layout
    C_MyHomeDeviceManagerAdapter adapter;
    List<Bean_AllDevice.EquipmentListBean>li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_device_manager);
        unbinder= ButterKnife.bind(this);
        presenter=new C_MyHomeDeviceManagerPresenter(this,this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        leftlayout.setVisibility(View.GONE);
        presenter.getAllDevice();
    }
    @OnClick({R.id.c_home_device_delete,R.id.c_home_device_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_device_delete://删除
                Intent intent=new Intent(this,C_MyHomeDeviceDeleteActivity.class);
                intent.putExtra("data",(ArrayList)li); //List<Bean_AllDevice.EquipmentListBean>li;
                startActivity(intent);
                break;
            case R.id.c_home_device_add://添加
                startActivity(new Intent(this,CaptureActivity.class));
                break;
        }
    }
//网络请求
    @Override
    public void onLoading() {

    }
    @Override
    public void onStopLoading() {

    }
    @Override
    public void onNetworkError(String interfaceName) {
        Log.e("C_MyHomeDeviceManagerAc","onNetworkError:"+interfaceName);
    }

    @Override
    public void onGsonError(String gsonStr) {
        Log.e("C_MyHomeDeviceManagerAc","数据异常-onGsonError:"+gsonStr);
    }
    //网络请求
    @Override
    public void onSuccess(Bean_AllDevice allDevice) {
        if (allDevice!=null){
            if ("0".equals(allDevice.getCode())){
                li=allDevice.getEquipmentList();
                if (li!=null&&li.size()>0){
                    leftlayout.setVisibility(View.VISIBLE);
                    adapter=new C_MyHomeDeviceManagerAdapter(this,li);
                    c_myhome_devicemanager_listview.setAdapter(adapter);
                }
            }
            else {
                ServerCode.showServerInfo(this,allDevice.getCode(),allDevice.getMessage()==null?allDevice.getResult():allDevice.getMessage());
            }
        }
    }
}
