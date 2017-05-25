package com.home.wanyu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.bean_My_settings_feadus;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.wheelView.ArrayWheelAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//意见反馈
public class MyContactUs extends Activity {
    private Context con=MyContactUs.this;
    private EditText my_settings_idea_editIdea,my_settings_idea_editContact;
    private TextView my_settings_idea_textNum;
    private String resultStr;
    private Boolean isSend=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isSend=false;
                    mToast.ToastFaild(con,ToastType.FAILD);
                    break;
                case 1:
                    isSend=false;
                    try{
                        bean_My_settings_feadus feadus= mGson.gson.fromJson(resultStr,bean_My_settings_feadus.class);
                        if ("0".equals(feadus.getCode())){
                            mToast.Toast(con,"提交成功");
                            finish();
                        }
                        else {
                            mToast.Toast(con,"提交失败："+feadus.getMessage());
                        }
//                        String code=feadus.getCode();
//                        if ("-1".equals(code)){
//                            Toast.makeText(My_settings_feedbackIdea_Activity.this,"当前登陆状态失效，请重新登陆",Toast.LENGTH_SHORT).show();
//                            user.clearLogin(My_settings_feedbackIdea_Activity.this);
//                        }
//                        else if ("0".equals(code)){
//                            Toast.makeText(My_settings_feedbackIdea_Activity.this,"提交成功",Toast.LENGTH_SHORT).show();
//                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con, ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_us);
        initView();
    }


    private void initView() {

        my_settings_idea_editIdea= (EditText) findViewById(R.id.my_settings_idea_editIdea);
        my_settings_idea_editContact= (EditText) findViewById(R.id.my_settings_idea_editContact);
        my_settings_idea_textNum= (TextView) findViewById(R.id.my_settings_idea_textNum);

        my_settings_idea_editIdea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                if (!"".equals(s)&&!TextUtils.isEmpty(text)){
                    int length=text.length();
                    my_settings_idea_textNum.setText(length+"/"+200);
                }
                else {
                    my_settings_idea_textNum.setText(0+"/"+200);
                }
            }
        });

    }


    public void goBack(View view){
        if (view!=null){
            if (view.getId()==R.id.activity_idea_include_imageReturn){
                finish();
            }
        }
    }
    //提交意见和联系方式http://192.168.1.55:8080/smarthome/mobileapi/feedback/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void submitIdea(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_idea_submit){
                if (isSend){
                    mToast.Toast(con,"正在提交，请稍后");
                    return;
                }
                String idea=my_settings_idea_editIdea.getText().toString();
                String contact=my_settings_idea_editContact.getText().toString();
                if (!"".equals(idea)&&!"".equals(contact)&&!TextUtils.isEmpty(idea)&&!TextUtils.isEmpty(contact)){
                    isSend=true;
//                    http://localhost:8080/yuyi/feedback//save.do?content=“”&contact=192873637&token=2E8B4C79121FBC6CB1377B190C663F52
                    Map<String,String> mp=new HashMap<>();
                    mp.put("content",idea);
                    mp.put("token", UserInfo.userToken);
                    mp.put("contact",contact);
                    okhttp.getCall(Ip.serverPath+Ip.interface_feadUs,mp,okhttp.OK_POST).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            resultStr=response.body().string();
                            handler.sendEmptyMessage(1);
                            Log.i("意见反馈返回的数据---",""+resultStr);
                        }
                    });
                }
                else {
                    mToast.Toast(con,"信息不完整");
                }
            }
        }

    }
}
