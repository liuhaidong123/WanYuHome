package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.JpushUtils.JPshAliasAndTags;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.MainActivity;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.bean.Bean_Register;
import com.squareup.okhttp.Callback;
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
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/22.
 */
//注册的fragment
public class registerFragment extends Fragment{
    Unbinder unbinder;
    @BindView(R.id.register_psd_edit_phone)EditText register_psd_edit_phone;//手机号输入框
    @BindView(R.id.login_sms_edit_psd_resigter)EditText login_sms_edit_psd_resigter;//验证码输入框
    @BindView(R.id.register_psd_edit_psd)EditText register_psd_edit_psd;//密码输入框
    @BindView(R.id.register_psd_edit_psd_2)EditText register_psd_edit_psd_2;//重复密码输入框
    @BindView(R.id.login_sms_text_resigter)TextView login_sms_text_resigter;//发送验证码的anniu

    @BindView(R.id.login_psd_image_select_register)ImageView login_psd_image_select_register;
    private boolean isClick=true;//是否可以点击
    private int timeOut=59;//读秒器
    private String resStr;
    private String cookie;
    private String tele;

    private boolean isRegist=false;//是否正在注册
    @BindView(R.id.login_btn_login_register)TextView login_btn_login_register;//注册按钮

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (login_sms_text_resigter!=null){
                if (msg.what>0){
                    setClickable(false);
                    login_sms_text_resigter.setText("剩余 "+timeOut+" s");
                }
                else if (msg.what==0){
                    login_sms_text_resigter.setText("获取验证码");
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
                case -2:
                    setClickable(true);
                    break;
                case -3://获取注册验证码
                    try{
                        Bean_M bean_m= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean_m!=null){
                            if ("0".equals(bean_m.getCode())){
                                login_sms_edit_psd_resigter.setText(bean_m.getResult());
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
                                setClickable(true);
                                mToast.Toast(getActivity(),bean_m.getMessage());
                                }
                        }
                        else {
                            setClickable(true);
                            }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        setClickable(true);
                        mToast.ToastFaild(getActivity(), ToastType.GSONFAILD);
                    }
                    break;
                case 2://注册失败
                    isRegist=false;
                    mToast.ToastFaild(getActivity(),ToastType.FAILD);
                    break;
                case 3://注册返回
                    isRegist=false;
                    try{
                        Bean_Register register=mGson.gson.fromJson(resStr,Bean_Register.class);
                        if (register!=null){
                            if ("0".equals(register.getCode())){
                                mToast.Toast(getActivity(),"注册成功");
                                UserInfo.savaLogin(tele,register.getToken(),Long.parseLong(register.getPersonalId()+""),getActivity());
                                //激光注册标签
                                JPshAliasAndTags.setAlias(getActivity(),UserInfo.userName);
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                            else {
                                mToast.Toast(getActivity(),register.getMessage());
                            }
                        }
                        else {
                            mToast.ToastFaild(getActivity(),ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(getActivity(),ToastType.FAILD);
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_register,null);
        unbinder= ButterKnife.bind(this,view);
        login_psd_image_select_register.setSelected(true);
        return view;
    }
    @OnClick({R.id.login_sms_text_resigter,R.id.login_btn_login_register,R.id.login_psd_image_select_register})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.login_btn_login_register://注册按钮
                if (isPhoneNum(register_psd_edit_phone.getText().toString())){
                    String sms=login_sms_edit_psd_resigter.getText().toString();
                    if (!"".equals(sms)&&!TextUtils.isEmpty(sms)&&sms.length()==6){
                        if (checkInput()){
                            String psd1=register_psd_edit_psd.getText().toString();
                            String psd2=register_psd_edit_psd_2.getText().toString();
                            if (psd1.equals(psd2)){
                                if (login_psd_image_select_register.isSelected()){
                                    register();
                                }
                              else {
                                    mToast.Toast(getActivity(),"您还没有同意隐私协议");
                                }
                            }
                            else {
                                mToast.Toast(getActivity(),"您输入的密码不一致");
                            }
                        }
                    }
                    else {
                        mToast.Toast(getActivity(),"验证码不正确");
                    }

                }
                else {
                    mToast.Toast(getActivity(),"您输入的手机号不正确");
                }
                break;
            case R.id.login_sms_text_resigter://获取验证码按钮
                if (isClick==false){
                    return;
                }
                if (isPhoneNum(register_psd_edit_phone.getText().toString())){
                    getRegisterSmSCode();
                }
                else {
                    mToast.Toast(getActivity(),"您输入的手机号不正确");
                }
                break;

            case R.id.login_psd_image_select_register://协议的image
                login_psd_image_select_register.setSelected(!login_psd_image_select_register.isSelected());
                break;
        }
    }

    private boolean checkInput() {
        String phone=register_psd_edit_phone.getText().toString();
        String psd1=register_psd_edit_psd.getText().toString();
        String psd2=register_psd_edit_psd_2.getText().toString();
        String sms=login_sms_edit_psd_resigter.getText().toString();
        if (!"".equals(phone)&&!TextUtils.isEmpty(phone)){
            if (!"".equals(psd1)&&!TextUtils.isEmpty(psd1)){
                if (!"".equals(sms)&&!TextUtils.isEmpty(sms)){
                    if (!"".equals(psd2)&&!TextUtils.isEmpty(psd2)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //设置是否可以继续点击获取验证码
    public void setClickable(Boolean click){
        if (login_sms_text_resigter==null){
            return;
        }
        isClick=click;
        if (isClick){//可点击
            login_sms_text_resigter.setBackground(getResources().getDrawable(R.drawable.editrighe));
        }
        else {//不可点击
            login_sms_text_resigter.setBackground(getResources().getDrawable(R.drawable.smscode_un));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //注册获取验证码
//    1.注册时发送验证码
//    http://192.168.1.55:8080/smarthome/mobilepub/personal/register.do?telephone=18782931350&password=123&vcode=578614
//    http://192.168.1.55:8080/smarthome/mobilepub/personal/vcode.do?telephone=18782931350
//    参数  参数名  参数类型  备注
//    telephone  Long  电话
    public void getRegisterSmSCode() {
        if (isClick == false) {
            mToast.Toast(getActivity(), "正在操作，请稍后");
            return;
        }
        isClick = false;
        Map<String, String> mp = new HashMap<>();
        mp.put("telephone", register_psd_edit_phone.getText().toString());
        okhttp.getCall(Ip.pths + Ip.interface_getSMSCODE_register, mp, okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(-2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Response res = response;
                resStr = response.body().string();
                cookie=response.headers().get("Set-Cookie");
                Log.i("注册获取验证码--", "000-" + resStr);
                hand.sendEmptyMessage(-3);
            }
        });
    }
    //注册
    public void register(){
        if (isRegist){
            mToast.Toast(getActivity(),"正在操作，请稍后");
            return;
        }
        isRegist=true;
//        2.注册
//        接口 http://192.168.1.55:8080/smarthome/mobilepub/personal/vcode.do?telephone=18782931350
//        参数  参数名  参数类型  备注
//        telephone  Long  电话
//        password  String  密码
//        vcode  Object  验证码
        Map<String,String> mp=new HashMap<>();
        tele=register_psd_edit_phone.getText().toString();
        mp.put("telephone",register_psd_edit_phone.getText().toString());
        mp.put("password",register_psd_edit_psd.getText().toString());
        mp.put("vcode",login_sms_edit_psd_resigter.getText().toString());
        okhttp.getCallCookie(Ip.pths+Ip.interface_get_register,mp,cookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("注册--",resStr);
                hand.sendEmptyMessage(3);
            }
        });
    }
}
