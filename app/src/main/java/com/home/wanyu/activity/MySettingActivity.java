package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.home.wanyu.Applications.MyApplication;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.lzhUtils.MyActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置页面
public class MySettingActivity extends MyActivity {
    private String resStr;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_M bean_m= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean_m!=null){
                            if ("0".equals(bean_m.getCode())){
                                UserInfo.clearLogin(con);
                                MyApplication.removeActivity();
                                startActivity(new Intent(con,LoginAndRegisterActivity.class));
//                                mToast.Toast(con,"退出成功");
                            }
                            else {
                                mToast.Toast(con,bean_m.getMessage());
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
            }
        }
    };
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
                clearLogin();
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
//    http://localhost:8080/smarthome/mobilepub/personal/Exitlogin.do?token=ACDCE729BCE6FABC50881A867CAFC1BC
//
//    参数：       参数名    参数类型    备注
//    token                     String                        令牌
//    返回值         返回值名称  返回值类型    备注
//    code          0=成功     —1表示失败
    public void clearLogin(){
        Map<String,String> mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        okhttp.getCall(Ip.pths+Ip.interface_ClearLogin,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("退出登录--",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
