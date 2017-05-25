package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.bean.Bean_deleteFamilyUser;
import com.home.wanyu.bean.Bean_getFamilyUserPri;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("data",list.get(pos));
//        intent.putExtra("data",bundle);
//我的家人信息（权限管理，删除等）
public class MyHouseFamilyInfoActivity extends MyActivity {
    @BindView(R.id.activity_my_house_family_in_image)RoundImageView activity_my_house_family_info_image;//头像
    @BindView(R.id.activity_my_house_family_in_name)TextView activity_my_house_family_info_name;//姓名
    @BindView(R.id  .activity_my_house_family_in_pri_1)TextView activity_my_house_family_info_pri;//身份
    @BindView(R.id.activity_my_house_family_in_tele)TextView activity_my_house_family_info_tele;//电话号
    @BindView(R.id.activity_my_house_family_se_shebei)ImageView activity_my_house_family_se_shebei;//控制我的设备的权限
    @BindView(R.id.activity_my_house_family_se_mensuo)ImageView activity_my_house_family_se_mensuo;//控制我的门锁
    @BindView(R.id.activity_my_house_family_se_tianjia)ImageView activity_my_house_family_se_tianjia;//添加设备到我的家
    @BindView(R.id.activity_my_house_family_se_shanchu)ImageView activity_my_house_family_se_shanchu;//从我的家删除设备
    Bean_FamilyUserS.PersonalListBean bean;
    private Boolean isSave=false;
    private boolean isChange=false;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isSave=false;
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_deleteFamilyUser de= mGson.gson.fromJson(resStr,Bean_deleteFamilyUser.class);
                        if (de!=null){
                            if ("0".equals(de.getCode())){
                                mToast.Toast(con,"删除成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,de.getResult()==null?"删除失败":de.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 2:
                    try{
                        Bean_getFamilyUserPri pri=mGson.gson.fromJson(resStr,Bean_getFamilyUserPri.class);
                        if (pri!=null){
                            if ("0".equals(pri.getCode())){
                                Bean_getFamilyUserPri.MyFamilyBean be=pri.getMyFamily();
                                if (be!=null){
                                    setState(be.getPmsnCtrlDevice(),activity_my_house_family_se_shebei);
                                    setState(be.getPmsnCtrlDoor(),activity_my_house_family_se_mensuo);
                                    setState(be.getPmsnCtrlAdd(),activity_my_house_family_se_tianjia);
                                    setState(be.getPmsnCtrlDel(),activity_my_house_family_se_shanchu);
                                }
                            }
                            else {
                                mToast.Toast(con,pri.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 3://权限修改
                    try{
                        Bean_M b=mGson.gson.fromJson(resStr,Bean_M.class);
                        if (b!=null){
                                if ("0".equals(b.getCode())){
                                    mToast.Toast(con,"权限修改成功");
                                }
                        }
                        else {
                            mToast.Toast(con,"权限修改失败");
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Log.e("修改权限失败000",e.toString());
                    }
                    isSave=false;
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("家人信息");
        initChildView(R.layout.activity_my_house_family_info);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        try{
            bean= (Bean_FamilyUserS.PersonalListBean) getIntent().getBundleExtra("data").getSerializable("data");
            Picasso.with(con).load(Ip.imagePath+bean.getAvatar()).error(R.mipmap.errorphoto).into(activity_my_house_family_info_image);
            activity_my_house_family_info_name.setText(bean.getUserName());
            activity_my_house_family_info_pri.setText(bean.getComment());
            activity_my_house_family_info_tele.setText(bean.getTelephone()+"");
            getUserPri(bean.getMyFamilyId()+"");
        }
       catch (Exception e){
           bean=null;
           e.printStackTrace();
        }
    }
    @OnClick({R.id.activity_my_house_family_se_shebei,R.id.activity_my_house_family_se_mensuo,R.id.activity_my_house_family_se_tianjia
            ,R.id.activity_my_house_family_se_shanchu,R.id.activity_my_house_family_se_delete})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_house_family_se_shebei://控制我的设备的权限
                isChange=true;
                activity_my_house_family_se_shebei.setSelected(!activity_my_house_family_se_shebei.isSelected());
                break;
            case R.id.activity_my_house_family_se_mensuo://控制我的门锁
                isChange=true;
                activity_my_house_family_se_mensuo.setSelected(!activity_my_house_family_se_mensuo.isSelected());
                break;
            case R.id.activity_my_house_family_se_tianjia://添加设备到我的家
                isChange=true;
                activity_my_house_family_se_tianjia.setSelected(!activity_my_house_family_se_tianjia.isSelected());
                break;
            case R.id.activity_my_house_family_se_shanchu://从我的家删除设备
                isChange=true;
                activity_my_house_family_se_shanchu.setSelected(!activity_my_house_family_se_shanchu.isSelected());
                break;
            case R.id.activity_my_house_family_se_delete:
                mToast.DebugToast(con,"删除");
                if (bean!=null){
                   deleteUser();
                }
                break;
        }
    }
//    http://192.168.1.55:8080/smarthome/mobileapi/myFamily/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    private void deleteUser() {
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("ids",bean.getMyFamilyId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_deleteFamilyUser,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("删除家人---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //    删除家人接口
//    http://192.168.1.55:8080/smarthome/mobileapi/homeUser/delete.do?homePersonalId=&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名          |类型      |必需  |描述
//    |-----          |----     |---- |----
//            |token          |String   |Y    |令牌
//    |homePersonalId |Long |Y |家人个人用户编号
    @Override
    public void getSerVerData() {

    }
//    |pmsnCtrlDevice |Integer  |N    |控制我的设备，0=无权限，1=有权限
//    |pmsnCtrlDoor   |Integer  |N    |控制我的门锁，0=无权限，1=有权限
//    |pmsnCtrlAdd    |Integer  |N    |添加设备到我的家，0=无权限，1=有权限
//    |pmsnCtrlDel    |Integer  |N    |从我的家删除设备，0=无权限，1=有权限

    //获取权限接口http://192.168.1.55:8080/smarthome/mobileapi/myFamily/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
    public void getUserPri(String ids){
    Map<String,String> mp=new HashMap<>();
        mp.put("id",ids);mp.put("token",UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+Ip.interface_getFamilyUserPri,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取家人权限---",resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isChange){
                savaData();
            }
            else {
                finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void TitleReturn(View view) {
        if (isChange){
            savaData();
        }
        else {
            finish();
        }
    }


//    http://192.168.1.55:8080/smarthome/mobileapi/myFamily/setPermission.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名          |类型      |必需  |描述
//    |-----          |----     |---- |----
//            |token          |String   |Y    |令牌
//    |id             |Long     |N    |与家人关联的编号
//    |pmsnCtrlDevice |Integer  |N    |控制我的设备，0=无权限，1=有权限
//    |pmsnCtrlDoor   |Integer  |N    |控制我的门锁，0=无权限，1=有权限
//    |pmsnCtrlAdd    |Integer  |N    |添加设备到我的家，0=无权限，1=有权限
//    |pmsnCtrlDel    |Integer  |N    |从我的家删除设备，0=无权限，1=有权限
//    |pmsnCtrl001    |Integer  |N    |扩展字段，0=无权限，1=有权限
//    |pmsnCtrl002    |Integer  |N    |扩展字段，0=无权限，1=有权限
//    |pmsnCtrl003    |Integer  |N    |扩展字段，0=无权限，1=有权限
//    |pmsnCtrl004    |Integer  |N    |扩展字段，0=无权限，1=有权限
//    |pmsnCtrl005    |Integer  |N    |扩展字段，0=无权限，1=有权限
    //提交家人权限
    private void savaData() {
        if (isSave){
            mToast.Toast(con,"正在保存");
            return;
        }
        isSave=true;
        Map<String,String>mp=new HashMap<>();
//        @BindView(R.id.activity_my_house_family_se_shebei)ImageView activity_my_house_family_se_shebei;//控制我的设备的权限
//        @BindView(R.id.activity_my_house_family_se_mensuo)ImageView activity_my_house_family_se_mensuo;//控制我的门锁
//        @BindView(R.id.activity_my_house_family_se_tianjia)ImageView activity_my_house_family_se_tianjia;//添加设备到我的家
//        @BindView(R.id.activity_my_house_family_se_shanchu)ImageView activity_my_house_family_se_shanchu;//从我的家删除设备
        mp.put("pmsnCtrlDevice",getState(activity_my_house_family_se_shebei));
        mp.put("pmsnCtrlDoor",getState(activity_my_house_family_se_mensuo));
        mp.put("pmsnCtrlAdd",getState(activity_my_house_family_se_tianjia));
        mp.put("pmsnCtrlDel",getState(activity_my_house_family_se_shanchu));
        mp.put("id",bean.getMyFamilyId()+"");
        mp.put("token",UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+Ip.interface_changeFamilyUserPri,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
                finish();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("家人权限修改---",resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }
    public String getState(ImageView vi){
        if (vi.isSelected()){
            return "1";//有权限
        }
            return "0";//无权限
    }
    public void setState(int tp,ImageView vi){
        switch (tp){
            case 0:
                vi.setSelected(false);
                break;
            case 1:
                vi.setSelected(true);
                break;
                default:
                    Log.e("家人权限----","获取到的权限字段格式错误，非0或者1"+"===值="+tp);
                    vi.setSelected(false);
                    break;
        }
    }
}
