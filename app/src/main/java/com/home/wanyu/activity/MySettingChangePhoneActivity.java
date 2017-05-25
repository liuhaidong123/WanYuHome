package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.PhoneUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//修改绑定手机号
public class MySettingChangePhoneActivity extends MyActivity {
    private TextView myActivity_title,my_user_info_submmit;
    @BindView(R.id.activity_my_setting_change_nowPhone)TextView activity_my_setting_change_nowPhone;//当前手机号
    @BindView(R.id.activity_my_setting_change_Edit_phone)EditText activity_my_setting_change_Edit_phone;//新手机号输入框
    @BindView(R.id.activity_my_setting_change_Edit_psd)EditText activity_my_setting_change_Edit_psd;//密码输入框
    @BindView(R.id.activity_my_setting_change_Edit_smsCode)EditText activity_my_setting_change_Edit_smsCode;//验证码输入框
    @BindView(R.id.activity_my_setting_change_text_smsCode)TextView activity_my_setting_change_text_smsCode;//发送验证码的按钮
    private int timeOut=59;//读秒器
    private String resStr;
    private String cookie;
    private boolean isClick=true;//是否可以点击
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity_my_setting_change_text_smsCode!=null){
                if (msg.what>0){
                    setClickable(false);
                    activity_my_setting_change_text_smsCode.setText("剩余 "+timeOut+" s");
                }
                else if (msg.what==0){
                    activity_my_setting_change_text_smsCode.setText("获取验证码");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                handler.sendEmptyMessage(-100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else if (msg.what==-100){
                    setClickable(true);
                }
            }

        }
    };
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isClick=true;

                    break;
                case 1://获取验证码
                    try{
                        Bean_M bean= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean!=null){
                            if ("0".equals(bean.getCode())){
                                activity_my_setting_change_Edit_smsCode.setText(bean.getResult());
                                timeOut=59;
                        setClickable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (timeOut>-1){
                                    try {
                                        handler.sendEmptyMessage(timeOut);
                                        Thread.sleep(1000);
                                        timeOut--;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                            }
                            else {
                                isClick=true;
                                mToast.Toast(con,bean.getMessage());
                            }
                        }
                        else {
                            isClick=true;
                        }
                    }
                    catch (Exception e){
                        isClick=true;
                        e.printStackTrace();
                        mToast.ToastFaild(con, ToastType.GSONFAILD);
                    }
                    break;
                case 2://修改绑定网络异常
                    mToast.Toast(con,"网络异常");
                    break;
                case 3://修改绑定手机号返回
                    try{
                        Bean_M bean= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean!=null){
                            if ("0".equals(bean.getCode())){
                                mToast.Toast(con,"修改成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,"修改失败："+bean.getMessage());
                                }
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
        initTitleView(R.layout.activity_my_user_title);
        myActivity_title= (TextView) findViewById(R.id.myActivity_title);
        my_user_info_submmit= (TextView) findViewById(R.id.my_user_info_submmit);
        my_user_info_submmit.setText("确认更改");
        myActivity_title.setText("修改绑定手机号");
        initChildView(R.layout.activity_my_setting_change_phone);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
    }

    @Override
    public void getSerVerData() {

    }
        @OnClick(R.id.activity_my_setting_change_text_smsCode)
        public void click(View vi){
            switch (vi.getId()){
                case R.id.activity_my_setting_change_text_smsCode://获取验证码
                    if (isClick==false){
                        return;
                    }
                    if (PhoneUtils.isPhoneNum(activity_my_setting_change_Edit_phone.getText().toString())){
                        isClick=false;
                        getSmsCode();
                    }
                    else {
                        mToast.Toast(con,"您输入的手机号不正确");
                    }
                    break;
            }
        }

    public void Submit(View vi){
        if (checkInput()){
            String phone=  activity_my_setting_change_Edit_phone.getText().toString();
           if (PhoneUtils.isPhoneNum(phone)){
               changePhone();
            }
            else {
               mToast.Toast(con,"您输入的手机号不正确");
           }
        }
        else {
            mToast.Toast(con,"您的输入不正确，请检查");
        }
    }
    //修改手机号
//    http://192.168.1.55:8080/smarthome/mobileapi/personal/mdfTelephone.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&telephone=13717883005&pwd=123456
//    Method:POST
//    |token        |String   |Y    |令牌
//    |telephone    |Long     |Y    |要绑定的手机号
//    |bindvcode    |String   |Y    |手机验证码
//    |pwd          |String   |Y    |密码
    private void changePhone() {
        activity_my_setting_change_Edit_psd.setText("123123");//测试用
        String phone=  activity_my_setting_change_Edit_phone.getText().toString();
        String psd=activity_my_setting_change_Edit_psd.getText().toString();
        String sms=activity_my_setting_change_Edit_smsCode.getText().toString();
        Map<String,String> mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        mp.put("telephone",phone);
        mp.put("bindvcode",sms);
        mp.put("pwd",psd);
        okhttp.getCallCookie(Ip.serverPath+Ip.interface_changePhone,mp,cookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("修改绑定手机号--",resStr);
                hand.sendEmptyMessage(3);
            }
        });
    }

    private boolean checkInput() {
        String phone=  activity_my_setting_change_Edit_phone.getText().toString();
        String psd=activity_my_setting_change_Edit_psd.getText().toString();
        String sms=activity_my_setting_change_Edit_smsCode.getText().toString();
        if (!"".equals(phone)&&!TextUtils.isEmpty(phone)&&
                !"".equals(psd)&&!TextUtils.isEmpty(psd)&&
                !"".equals(sms)&&!TextUtils.isEmpty(sms)&&sms.length()==6){
            return true;
        }
        return false;
    }

    //获取验证码
//    修改绑定手机号发送验证码接口post
//    http://192.168.1.55:8080/smarthome/mobileapi/personal/bindvcode.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&telephone=13717883005
    public void getSmsCode(){
        String phone=  activity_my_setting_change_Edit_phone.getText().toString();
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("telephone",phone);
        okhttp.getCall(Ip.serverPath+Ip.interface_changePhoneSmsCode,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    hand.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    cookie=response.headers().get("Set-Cookie");
                    if (cookie!=null){
                        Log.i("cookie---",cookie);
                    }
                    else {
                        Log.e("cookie---","空指针cookie为空");
                    }
                    Log.i("修改手机号验证码-",resStr);
                    hand.sendEmptyMessage(1);
            }
        });
    }



    //设置是否可以继续点击获取验证码
    public void setClickable(Boolean click){
        if (activity_my_setting_change_text_smsCode==null){
            return;
        }
        isClick=click;
        if (isClick){//可点击
            activity_my_setting_change_text_smsCode.setBackground(getResources().getDrawable(R.drawable.editrighe));
        }
        else {//不可点击
            activity_my_setting_change_text_smsCode.setBackground(getResources().getDrawable(R.drawable.smscode_un));
        }
    }
}
