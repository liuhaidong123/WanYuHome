package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

//添加家人页面
public class MyHouseFamilyAddActivity extends MyActivity {
    private TextView myActivity_title;
    private TextView my_user_info_submmit;
    @BindView(R.id.activity_my_house_family_add_getSmSCode)TextView activity_my_house_family_add_getSmSCode;//获取验证码的按钮
    @BindView(R.id.activity_my_house_family_add_edit)EditText activity_my_house_family_add_edit;//手机号输入框
    @BindView(R.id.activity_my_house_family_add_msmCode)EditText activity_my_house_family_add_msmCode;//验证码输入框
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

    @Override
    public void getSerVerData() {

    }
    //提交按钮
    public void Submit(View vi){
        String phone=activity_my_house_family_add_edit.getText().toString();
        String smsCode=activity_my_house_family_add_msmCode.getText().toString();
        if (!"".equals(phone)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(smsCode)&&!"".equals(smsCode)){
            if (smsCode.length()==6){
                    if (isPhoneNum(phone)){
                        startActivity(new Intent(con,MyHouseFamilyEditor.class));
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
}
