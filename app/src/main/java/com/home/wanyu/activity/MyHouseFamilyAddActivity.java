package com.home.wanyu.activity;

import android.content.Intent;
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
import com.home.wanyu.adapter.Bean_sendPhoneMSM;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.lzhUtils.MyActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加家人页面
public class MyHouseFamilyAddActivity extends MyActivity {
    private boolean isClick=true;//获取验证码是否可以点击
    private int timeOut=59;//读秒器

    private String cooki="";
    private TextView myActivity_title;
    private TextView my_user_info_submmit;
    private String[]tel={"13717883005","13717883006","13717883007","13717883008","13717883009","13717883000"};
    @BindView(R.id.activity_my_house_family_add_getSmSCode)TextView activity_my_house_family_add_getSmSCode;//获取验证码的按钮
    @BindView(R.id.activity_my_house_family_add_edit)EditText activity_my_house_family_add_edit;//手机号输入框
    @BindView(R.id.activity_my_house_family_add_msmCode)EditText activity_my_house_family_add_msmCode;//验证码输入框
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what>0){
                setClickable(false);
                activity_my_house_family_add_getSmSCode.setText("剩余 "+timeOut+" s");
            }
            else if (msg.what==0){
                activity_my_house_family_add_getSmSCode.setText("获取验证码");
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
    };
    String resStr;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1://获取验证码失败
                    setClickable(true);
                    mToast.ToastFaild(con,ToastType.FAILD);
                    break;
                case 0:
                    mToast.ToastFaild(con,ToastType.FAILD);
                    break;
                case 1://获取验证码成功
                    try{
                        Bean_M b= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (b!=null){
                            if ("0".equals(b.getCode())){
                                setClickable(false);
                                activity_my_house_family_add_msmCode.setText(b.getResult());
                                timeOut=59;
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
                                setClickable(true);
                                mToast.Toast(con,b.getMessage());
                            }
                        }
                        else {
                            setClickable(true);
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        setClickable(true);
                        e.printStackTrace();
                        mToast.ToastFaild(con, ToastType.GSONFAILD);
                    }
                    break;
                case 2://提交申请
                    try{
                        Bean_sendPhoneMSM sms=mGson.gson.fromJson(resStr,Bean_sendPhoneMSM.class);
                        if (sms!=null){
                            if ("0".equals(sms.getCode())){
                                Intent intent=new Intent();
                                Bundle bun=new Bundle();
                                bun.putSerializable("data",sms.getHomePersonal());
                                intent.putExtra("data",bun);
                                intent.setClass(con,MyHouseFamilyEditor.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                               mToast.Toast(con,sms.getMessage());
                            }
                        }
                        else {
                            mToast.ToastFaild(con, ToastType.GSONEMPTY);
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
        myActivity_title.setText("添加家人");
        my_user_info_submmit.setText("确认添加");
        initChildView(R.layout.activity_my_house_family_add);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
    }
    public void setClickable(Boolean click){
        isClick=click;
        if (isClick){//可点击
            activity_my_house_family_add_getSmSCode.setBackground(getResources().getDrawable(R.drawable.editrighe));
        }
       else {//不可点击
            activity_my_house_family_add_getSmSCode.setBackground(getResources().getDrawable(R.drawable.smscode_un));
        }
    }

    @Override
    public void getSerVerData() {

    }
    @OnClick({R.id.activity_my_house_family_add_getSmSCode})
    public void click(View Vi){
        switch (Vi.getId()){
            case R.id.activity_my_house_family_add_getSmSCode://获取验证码
                if (isClick){
//                    Random ran=new Random();
//                    int te=ran.nextInt(6);
//                    activity_my_house_family_add_edit.setText(tel[te]);//测试用
                    String phone=activity_my_house_family_add_edit.getText().toString();
                    if (isPhoneNum(phone)){
                        setClickable(false);
                        getSmSCode();
                    }
                    else {
                        mToast.Toast(con,"您输入的手机号不正确");
                    }
                }

                break;
        }
    }
    //提交按钮http://192.168.1.55:8080/smarthome/mobileapi/myFamily/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    http://192.168.1.55:8080/smarthome/mobileapi/myFamily/save.do?telephone=18335277251&token=9DB2FD6FDD2F116CD47CE6C48B3047EE&addvcode=177428
    public void Submit(View vi){
        String phone=activity_my_house_family_add_edit.getText().toString();
        String smsCode=activity_my_house_family_add_msmCode.getText().toString();
        if (!"".equals(phone)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(smsCode)&&!"".equals(smsCode)){
            if (smsCode.length()==6){
                    if (isPhoneNum(phone)){
                        getUserData();//获取当前手机号的个人信息
//                        startActivity(new Intent(con,MyHouseFamilyEditor.class));
                    }
                else {
                        mToast.Toast(con,"手机号不正确");
                    }
            }
            else {
                mToast.Toast(con,"验证码不正确");
            }
        }
        else {
         mToast.Toast(con,"手机号或验证码不正确");
        }
    }

    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //获取验证码http://192.168.1.55:8080/smarthome/mobileapi/personal/addvcode.do?telephone=13717883005&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void getSmSCode() {
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("telephone",activity_my_house_family_add_edit.getText().toString());
        okhttp.getCall(Ip.serverPath+Ip.interface_addFamilyUserSms,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(-1);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
//                Headers headers=response.headers();
//                    for (String s:headers.names()){
//                        Log.i("键--"+s,"值--"+headers.get(s));
//                    }
                cooki=response.headers().get("Set-Cookie");
                Log.i("添加家人获取验证码-",resStr);
                hand.sendEmptyMessage(1);
            }
        });
    }
//    http://192.168.1.55:8080/smarthome/mobileapi/myFamily/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    http://192.168.1.55:8080/smarthome/mobileapi/myFamily/save.do?telephone=18335277251&token=9DB2FD6FDD2F116CD47CE6C48B3047EE&addvcode=177428
    public void getUserData() {
        String telephone=activity_my_house_family_add_edit.getText().toString();
        String addvcode=activity_my_house_family_add_msmCode.getText().toString();
        if (!"".equals(telephone)&&!TextUtils.isEmpty(addvcode)&&!TextUtils.isEmpty(addvcode)&&!"".equals(addvcode)
                &&isPhoneNum(telephone)){
            Map<String,String>mp=new HashMap<>();
            mp.put("token",UserInfo.userToken);
            mp.put("telephone",telephone);
            mp.put("addvcode",addvcode);
            okhttp.getCallCookie(Ip.serverPath+Ip.interface_sendPhoneSmsCode,mp,cooki).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    hand.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("提交添加申请--",resStr);
                    hand.sendEmptyMessage(2);
                }
            });
        }
       else {
            mToast.Toast(con,"您填写的信息不正确");
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        timeOut=-1;
    }

}
