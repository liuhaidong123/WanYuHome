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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
//登录的fragment
public class LoginFragment extends Fragment{
    Unbinder unbinder;
    private int loginType=0;//0密码登录，1验证码登录
    private final int TYPE_PSD=0;
    private final int TYPE_SMS=1;

    @BindView(R.id.login_psd_image_select)ImageView login_psd_image_select;//使用条款选择的按钮
    @BindView(R.id.login_psd_text_select)TextView login_psd_text_select;//使用条款的textview
    @BindView(R.id.login_psd_edit_phone)EditText login_psd_edit_phone;//手机号输入框
    @BindView(R.id.login_btn_login)TextView login_btn_login;//登录按钮

    @BindView(R.id.login_text_select)TextView login_text_select;//切换密码登录与验证码登录的view
    //密码登录方式
    @BindView(R.id.layout_psd)LinearLayout layout_psd;//密码登录所在的lyout
    @BindView(R.id.login_psd_edit_psd)EditText login_psd_edit_psd;//密码输入框
    //密码登录方式
    //验证码登录
    @BindView(R.id.login_sms_edit_psd)EditText login_sms_edit_psd;//验证码输入框
    @BindView(R.id.login_sms_text)TextView login_sms_text;//发送验证码的按钮
    @BindView(R.id.lyout_sms)LinearLayout lyout_sms;//验证码所在的layout

    private String tele;
    private boolean isClick=true;//是否可以点击
    private int timeOut=59;//读秒器

    private String cookie;
    private boolean isLogin=false;//是否正在执行登录操作
    //验证码登录
//    activity_my_house_family_add_getSmSCode.setBackground(getResources().getDrawable(R.drawable.editrighe));
//}
//else {//不可点击
//        activity_my_house_family_add_getSmSCode.setBackground(getResources().getDrawable(R.drawable.smscode_un));
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (login_sms_text!=null){
                if (msg.what>0){
                    setClickable(false);
                    login_sms_text.setText("剩余 "+timeOut+" s");
                }
                else if (msg.what==0){
                    login_sms_text.setText("获取验证码");
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
    private String resStr;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1://获取验证码失败
                    setClickable(true);
                    mToast.ToastFaild(getActivity(), ToastType.FAILD);
                    break;
                case 1://获取验证码成功
                    try{
                        Bean_M bean_m= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (bean_m!=null){
                            if ("0".equals(bean_m.getCode())){
                                login_sms_edit_psd.setText(bean_m.getResult());
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
                                login_psd_edit_phone.setText(bean_m.getMessage());
                                }
                        }
                        else {
                            setClickable(true);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        setClickable(true);
                    }
                    break;
                case -2:
                    isLogin=false;
                    mToast.ToastFaild(getActivity(),ToastType.FAILD);
                    break;
                case 2:
                    isLogin=false;
                    try{
                        Bean_Register login=mGson.gson.fromJson(resStr,Bean_Register.class);
                        if (login!=null){
                        if ("0".equals(login.getCode())){
                            UserInfo.savaLogin(tele,login.getToken(),login.getPersonalId(),getActivity());
                            //激光注册标签
                            JPshAliasAndTags.setAlias(getActivity(),UserInfo.userName);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            mToast.Toast(getActivity(),"登录成功");
                            getActivity().finish();
                        }
                            else {
                            mToast.Toast(getActivity(),login.getMessage());
                            }
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(getActivity(),ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.frag_login_psd,null);
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }
    @OnClick({R.id.login_btn_login,R.id.login_text_select,R.id.login_sms_text})
    public void click(View view){
        switch (view.getId()){
            case R.id.login_btn_login://登录按钮
                    login();
                break;
//            case R.id.login_psd_image_select://使用条款的选择按钮
//                login_psd_image_select.setSelected(!login_psd_image_select.isSelected());
//                break;
            case R.id.login_text_select://切换密码登录与验证码登录的view
                if (loginType==TYPE_PSD){
                    loginType=TYPE_SMS;
                    login_text_select.setText("密码登录");
                    layout_psd.setVisibility(View.GONE);//隐藏密码登录
                    lyout_sms.setVisibility(View.VISIBLE);//隐藏密码登录
                }
                else {
                    layout_psd.setVisibility(View.VISIBLE);//显示密码登录
                    lyout_sms.setVisibility(View.GONE);//隐藏验证码登录
                    login_text_select.setText("手机验证码登录");
                    loginType=TYPE_PSD;
                }
                break;
            case R.id.login_sms_text://发送验证码的按钮
                String ph=login_psd_edit_phone.getText().toString();
                if ("".equals(ph)|TextUtils.isEmpty(ph)|!isPhoneNum(ph)){
                    mToast.Toast(getActivity(),"您输入的手机号不正确");
                    return;
                }
                if (isClick==false){
                    return;
                }
                getSmsCode();
                break;
        }
    }


    //登录操作
    private void login() {
        if (isPhoneNum(login_psd_edit_phone.getText().toString())){
                if (loginType==TYPE_SMS){//验证码denglu
                    String smsCode=login_sms_edit_psd.getText().toString();
                    if (!"".equals(smsCode)&&!TextUtils.isEmpty(smsCode)&&smsCode.length()==6){
//                            mToast.Toast(getActivity(),"验证码登录");
                        sendLogin();
                    }
                    else {
                        mToast.Toast(getActivity(),"验证码不正确");
                    }
                }
             else if (loginType==TYPE_PSD){//密码登录
                        String psd=login_psd_edit_psd.getText().toString();
                    if (!"".equals(psd)&&!TextUtils.isEmpty(psd)){
//                        mToast.Toast(getActivity(),"密码登录");
                        sendLogin();
                    }
                    else {
                        mToast.Toast(getActivity(),"密码不正确");
                    }
                }
        }
        else {
            mToast.Toast(getActivity(),"手机号不正确");
        }
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
        if (login_sms_text==null){
            return;
        }
        isClick=click;
        if (isClick){//可点击
            login_sms_text.setBackground(getResources().getDrawable(R.drawable.editrighe));
        }
        else {//不可点击
            login_sms_text.setBackground(getResources().getDrawable(R.drawable.smscode_un));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //获取验证码http://localhost:8080/smarthome/mobilepub/personal/loginvcode.do?telephone=18782931356
    public void getSmsCode(){
        if (isClick==false){
            return;
        }
        isClick=false;
        Map<String,String> mp=new HashMap<>();
        mp.put("telephone",login_psd_edit_phone.getText().toString());
        okhttp.getCall(Ip.pths+Ip.interface_LoginSMSCODE,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    cookie=response.headers().get("Set-Cookie");
                    Log.i("登录获取验证码-",resStr);
                    hand.sendEmptyMessage(1);
            }
        });
    }


    public void sendLogin(){
        if (isLogin){
            mToast.Toast(getActivity(),"正在操作，请稍后");
            return;
        }
        isLogin=true;
//        http://localhost:8080/smarthome/mobilepub/personal/login.do?telephone=18782931356&password=123
//        telephone    Long      用户电话
//        password     String      密码
        Map<String,String>map=new HashMap<>();
        tele=login_psd_edit_phone.getText().toString();
        map.put("telephone",tele);
        switch (loginType){
            case TYPE_SMS://验证码登录
                map.put("password",login_sms_edit_psd.getText().toString());
                okhttp.getCallCookie(Ip.pths+Ip.interface_Login,map,cookie).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hand.sendEmptyMessage(-2);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("登录返回--"+loginType,resStr);
                        hand.sendEmptyMessage(2);
                    }
                });
                break;
            case TYPE_PSD://密码登录
                map.put("password",login_psd_edit_psd.getText().toString());
                okhttp.getCall(Ip.pths+Ip.interface_Login,map,okhttp.OK_POST).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hand.sendEmptyMessage(-2);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("登录返回--"+loginType,resStr);
                        hand.sendEmptyMessage(2);
                    }
                });
                break;
        }

    }
}
