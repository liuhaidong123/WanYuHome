package com.home.wanyu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;

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
    private boolean isClick=true;//是否可以点击
    private int timeOut=59;//读秒器
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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.frag_login_psd,null);
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }
    @OnClick({R.id.login_btn_login,R.id.login_psd_image_select,R.id.login_text_select,R.id.login_sms_text})
    public void click(View view){
        switch (view.getId()){
            case R.id.login_btn_login://登录按钮
                if(login_psd_image_select.isSelected()){
                    login();
                }
                else {
                    mToast.Toast(getActivity(),"您还没有阅读或者同意隐私条款");
                    }
                break;
            case R.id.login_psd_image_select://使用条款的选择按钮
                login_psd_image_select.setSelected(!login_psd_image_select.isSelected());
                break;
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
                if (isClick==false){
                    return;
                }
                Random random=new Random();
                int sms=random.nextInt(10)+100000;
                login_sms_edit_psd.setText(sms+"");
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
                break;
        }
    }


    //登录操作
    private void login() {
        if (isPhoneNum(login_psd_edit_phone.getText().toString())){
                if (loginType==TYPE_SMS){//验证码denglu
                    String smsCode=login_sms_edit_psd.getText().toString();
                    if (!"".equals(smsCode)&&!TextUtils.isEmpty(smsCode)&&smsCode.length()==6){
                            mToast.Toast(getActivity(),"验证码登录");
                    }
                    else {
                        mToast.Toast(getActivity(),"验证码不正确");
                    }

                }
             else if (loginType==TYPE_PSD){//密码登录
                        String psd=login_psd_edit_psd.getText().toString();
                    if (!"".equals(psd)&&!TextUtils.isEmpty(psd)){
                        mToast.Toast(getActivity(),"密码登录");
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
}
