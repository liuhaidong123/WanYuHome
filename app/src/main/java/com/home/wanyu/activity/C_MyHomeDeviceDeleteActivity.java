package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.C_Model.C_MyHomeDeviceDeleteModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.C_View.C_MyHomeDeviceDeletePresenter;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerDeleteAdapter;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.bean.Bean_DeleteDevice;
import com.home.wanyu.myUtils.CMyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//Intent intent=new Intent(this,C_MyHomeDeviceDeleteActivity.class);
//        intent.putExtra("data",(ArrayList)li); //List<Bean_AllDevice.EquipmentListBean>li;
//        startActivity(intent);
public class C_MyHomeDeviceDeleteActivity extends CMyActivity implements C_MyHomeDeviceManagerDeleteAdapter.Select,ILoading, C_MyHomeDeviceDeleteModel.IDeviceDelete {
//    interface_DeviceDelete
    C_MyHomeDeviceManagerDeleteAdapter adapter;
    @BindView(R.id.c_home_device_delete_listview)ListView c_home_device_delete_listview;
    @BindView(R.id.c_device_Image_select)TextView c_device_Image_select;
    List<Bean_AllDevice.EquipmentListBean>li;
    C_MyHomeDeviceDeletePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_device_delete);
        unbinder= ButterKnife.bind(this);
        initAdapter();
        presenter=new C_MyHomeDeviceDeletePresenter(this,this,this);
    }
    //测试用
    private void initAdapter() {
        li= (List<Bean_AllDevice.EquipmentListBean>) getIntent().getSerializableExtra("data");
        if (li!=null&&li.size()>0){
            for (int i=0;i<li.size();i++){
                li.get(i).setFlag(false);
            }
        }
        adapter=new C_MyHomeDeviceManagerDeleteAdapter(this,li,this);
        c_home_device_delete_listview.setAdapter(adapter);
    }

    @OnClick({R.id.c_home_device_delete_btn,R.id.c_device_Image_select})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_device_delete_btn://删除按钮
                presenter.deleteDevice(li);
                break;
            case R.id.c_device_Image_select://全选按钮
                    for (int i=0;i<li.size();i++){
                        if (c_device_Image_select.isSelected()){
                            li.get(i).setFlag(false);
                        }
                        else {
                            li.get(i).setFlag(true);
                        }
                    }
                adapter.notifyDataSetChanged();
                c_device_Image_select.setSelected(!c_device_Image_select.isSelected());
                break;
        }
    }

    @Override
    public void isAllSelect(Boolean select) {
        if (select){
            c_device_Image_select.setSelected(true);
        }
        else {
            c_device_Image_select.setSelected(false);
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
        Log.e("C_MyHomeDeviceDeleteAct","onNetworkError:"+interfaceName);

    }

    @Override
    public void onGsonError(String gsonStr) {
        Log.e("C_MyHomeDeviceDeleteAct","数据异常onGsonError:"+gsonStr);
    }

    @Override
    public void onSuccess(Bean_DeleteDevice bean_deleteDevice,List<Bean_AllDevice.EquipmentListBean> lts) {
        if (bean_deleteDevice!=null){
            if ("0".equals(bean_deleteDevice.getCode())){
                li.removeAll(lts);
                adapter.notifyDataSetChanged();
            }
            else {
                ServerCode.showServerInfo(this,bean_deleteDevice.getCode(),bean_deleteDevice.getMessage()==null?bean_deleteDevice.getResult():bean_deleteDevice.getMessage());
            }
        }
    }
    //网络请求
}
