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
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//修改密码
public class MySettingChangePsdActivity extends MyActivity {
    private TextView myActivity_title;
    private TextView my_user_info_submmit;
    @BindView(R.id.activity_my_setting_change_Edit_phone_1)EditText activity_my_setting_change_Edit_phone;//愿密码
    @BindView(R.id.activity_my_setting_change_Edit_psd_1)EditText activity_my_setting_change_Edit_psd;//新密码
    @BindView(R.id.activity_my_setting_change_Edit_psd_more_1)EditText activity_my_setting_change_Edit_psd_more;//在次输入新密码

    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try{
                        Bean_M nb= mGson.gson.fromJson(resStr,Bean_M.class);
                        if (nb!=null){
                            if ("0".equals(nb.getCode())){
                                mToast.Toast(con,"修改成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,nb.getMessage());
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
        initTitleView(R.layout.activity_my_user_title);
        initTitle();
        initChildView(R.layout.activity_my_setting_change_psd);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
    }

    private void initTitle() {
        myActivity_title= (TextView) findViewById(R.id.myActivity_title);
        myActivity_title.setText("修改密码");
        my_user_info_submmit= (TextView) findViewById(R.id.my_user_info_submmit);
        my_user_info_submmit.setText("确认更改");
    }



    public void Submit(View vi){
        if (checkInput()){
            Map<String,String>mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
            mp.put("oldPwd",activity_my_setting_change_Edit_phone.getText().toString());
            mp.put("newPwd",activity_my_setting_change_Edit_psd.getText().toString());
            okhttp.getCall(Ip.serverPath+Ip.interface_changePsd,mp,okhttp.OK_POST).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("修改密码---",resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }

    //http://192.168.1.55:8080/smarthome/mobileapi/personal/mdfPasswd.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&oldPwd=123&newPwd=123234
//    Method:POST
    private boolean checkInput() {
        String psd=activity_my_setting_change_Edit_phone.getText().toString();
        String psdA=activity_my_setting_change_Edit_psd.getText().toString();
        String psdB=activity_my_setting_change_Edit_psd_more.getText().toString();
        if (!"".equals(psd)&&!TextUtils.isEmpty(psd)&&
                !"".equals(psdA)&&!TextUtils.isEmpty(psdA)&&
                !"".equals(psdB)&&!TextUtils.isEmpty(psdB)){
            if (psdA.equals(psdB)){
               return true;
            }
            else {
                mToast.Toast(con,"您输入的新密码不一致");
            }
        }
        else {
            mToast.Toast(con,"您的输入不正确");
        }
        return false;
    }

    @Override
    public void getSerVerData() {

    }
}
