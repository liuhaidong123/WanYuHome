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
//注册的fragment
public class registerFragment extends Fragment{
    Unbinder unbinder;
    @BindView(R.id.register_psd_edit_phone)EditText register_psd_edit_phone;//手机号输入框
    @BindView(R.id.login_sms_edit_psd_resigter)EditText login_sms_edit_psd_resigter;//验证码输入框
    @BindView(R.id.register_psd_edit_psd)EditText register_psd_edit_psd;//密码输入框
    @BindView(R.id.register_psd_edit_psd_2)EditText register_psd_edit_psd_2;//重复密码输入框
    @BindView(R.id.login_sms_text_resigter)TextView login_sms_text_resigter;//发送验证码的anniu
    private boolean isClick=true;//是否可以点击
    private int timeOut=59;//读秒器
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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_register,null);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }
    @OnClick({R.id.login_sms_text_resigter,R.id.login_btn_login_register})
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
                                mToast.Toast(getActivity(),"注册成功");
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
                    Random random=new Random();
                    int sms=random.nextInt(10)+100000;
                    login_sms_edit_psd_resigter.setText(sms+"");
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
                    mToast.Toast(getActivity(),"您输入的手机号不正确");
                }
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
}
