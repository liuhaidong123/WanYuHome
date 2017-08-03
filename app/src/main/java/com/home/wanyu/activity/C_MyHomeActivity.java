package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.C_Model.C_MyHomeModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.C_View.C_MyHomePresenter;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_getMyFamily;
import com.home.wanyu.bean.Bean_saveMyFamily;
import com.home.wanyu.myUtils.CMyActivity;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家
public class C_MyHomeActivity extends CMyActivity implements C_MyHomeModel.IC_MyHome ,ILoading,C_MyHomePresenter.IPresneter{
    C_MyHomePresenter presenter;
    Bean_getMyFamily benAll;//所有的家
    List<Bean_getMyFamily.MapListBean> li;
    int currentFamilyId=-1;//当前选定的家的id
    @BindView(R.id.c_myHome_image)ImageView c_myHome_image;//下拉的image
    @BindView(R.id.c_myHome_houseName)TextView c_myHome_houseName;//显示小区名字的view
    @BindView(R.id.c__my_home_LayoutName)RelativeLayout c__my_home_LayoutName;
    @BindView(R.id.rela2)RelativeLayout rela2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("我的家",",,",true);
        unbinder= ButterKnife.bind(this);
        presenter=new C_MyHomePresenter(this,this,this,this);
        c__my_home_LayoutName.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getAllMyFamily();
    }

    @OnClick({R.id.c__my_home_LayoutName,R.id.c_myHome_layout_HouseInfo,R.id.c_myHome_layout_FamilyManager,
            R.id.c_myHome_layout_DeviceManager,R.id.c_myHome_layout_SceneManger,R.id.c_myHome_layout_RoomManager})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c__my_home_LayoutName:
                c_myHome_image.setSelected(!c_myHome_image.isSelected());
                if (li!=null&&li.size()>0){
                    rela2.setSelected(true);
                }
                presenter.showWindow(li);
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
    //弹窗消失时处理
    @Override
    public void onDissmiss() {
        rela2.setSelected(false);
        c_myHome_image.setSelected(!c_myHome_image.isSelected());
    }

    //网络请求处理--------
    //开始网络请求
    @Override
    public void onLoading() {

    }
    //停止网络请求
    @Override
    public void onStopLoading() {

    }
    //url：接口名（网络异常）
    @Override
    public void onNetworkError(String url) {
        Log.e("C_MyHomeActivity网络异常：","onNetworkError:"+url);
        if (url.equals(Ip.interface_getAllFamily)){//获取我所有的家

        }
        else if (url.equals(Ip.interface_saveMyFamily)){//切换家

        }
    }
    //数据异常
    @Override
    public void onGsonError(String json) {
        Log.e("C_MyHomeActivity-数据异常","onGsonError："+json);
    }
    //网络请求处理--------


    //请求我全部的家成功
    @Override
    public void onSuccessAllHome(Bean_getMyFamily allFamily) {
        this.benAll=allFamily;
        if (allFamily!=null){
            if ("0".equals(allFamily.getCode())){
                li=allFamily.getMapList();
                if (li!=null&&li.size()>0){
                    c__my_home_LayoutName.setVisibility(View.VISIBLE);
                    currentFamilyId=li.get(0).getCurrentFamilyId();//获取当前家的id
                    for (int i=0;i<li.size();i++){
                        if (li.get(i).getFamilyId()==currentFamilyId){
                            c_myHome_houseName.setText(li.get(i).getFamilyName());
                        }
                    }
                }
            }
            else {
                ServerCode.showResponseMsg(this,allFamily.getCode());
            }
        }
    }

    //切换家成功
    @Override
    public void onSuccessChangeMyHome(Bean_saveMyFamily fily,Bean_getMyFamily.MapListBean bean) {
        if (fily!=null){
            if ("0".equals(fily.getCode())){
                c_myHome_houseName.setText(bean.getFamilyName());
            }
            else {
                ServerCode.showServerInfo(this,fily.getCode(),fily.getResult());
            }
        }
        else {
            mToast.ToastFaild(this, ToastType.GSONEMPTY);
        }
    }
}
