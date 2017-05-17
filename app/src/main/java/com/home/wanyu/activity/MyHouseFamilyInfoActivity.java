package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.bean.Bean_deleteFamilyUser;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("data",list.get(pos));
//        intent.putExtra("data",bundle);
//我的家人信息（权限管理，删除等）
public class MyHouseFamilyInfoActivity extends MyActivity {
    @BindView(R.id.activity_my_house_family_in_image)RoundImageView activity_my_house_family_info_image;//头像
    @BindView(R.id.activity_my_house_family_in_name)TextView activity_my_house_family_info_name;//姓名
    @BindView(R.id  .activity_my_house_family_in_pri_1)TextView activity_my_house_family_info_pri;//身份
    @BindView(R.id.activity_my_house_family_in_tele)TextView activity_my_house_family_info_tele;//电话号
    @BindView(R.id.activity_my_house_family_se_shebei)ImageView activity_my_house_family_se_shebei;//控制我的设备的权限
    @BindView(R.id.activity_my_house_family_se_mensuo)ImageView activity_my_house_family_se_mensuo;//控制我的门锁
    @BindView(R.id.activity_my_house_family_se_tianjia)ImageView activity_my_house_family_se_tianjia;//添加设备到我的家
    @BindView(R.id.activity_my_house_family_se_shanchu)ImageView activity_my_house_family_se_shanchu;//从我的家删除设备
    Bean_FamilyUserS.PersonalListBean bean;
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
                        Bean_deleteFamilyUser de= mGson.gson.fromJson(resStr,Bean_deleteFamilyUser.class);
                        if (de!=null){
                            if ("0".equals(de.getCode())){
                                mToast.Toast(con,"删除成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,de.getResult()==null?"删除失败":de.getResult());
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
        setTitle("家人信息");
        initChildView(R.layout.activity_my_house_family_info);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        try{
            bean= (Bean_FamilyUserS.PersonalListBean) getIntent().getBundleExtra("data").getSerializable("data");
        }
       catch (Exception e){
           bean=null;
           e.printStackTrace();
       }
    }
    @OnClick({R.id.activity_my_house_family_se_shebei,R.id.activity_my_house_family_se_mensuo,R.id.activity_my_house_family_se_tianjia
            ,R.id.activity_my_house_family_se_shanchu,R.id.activity_my_house_family_se_delete})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_house_family_se_shebei://控制我的设备的权限
                activity_my_house_family_se_shebei.setSelected(!activity_my_house_family_se_shebei.isSelected());
                break;
            case R.id.activity_my_house_family_se_mensuo://控制我的门锁
                activity_my_house_family_se_mensuo.setSelected(!activity_my_house_family_se_mensuo.isSelected());
                break;
            case R.id.activity_my_house_family_se_tianjia://添加设备到我的家
                activity_my_house_family_se_tianjia.setSelected(!activity_my_house_family_se_tianjia.isSelected());
                break;
            case R.id.activity_my_house_family_se_shanchu://从我的家删除设备
                activity_my_house_family_se_shanchu.setSelected(!activity_my_house_family_se_shanchu.isSelected());
                break;
            case R.id.activity_my_house_family_se_delete:
                mToast.DebugToast(con,"删除");
                if (bean!=null){
                   deleteUser();
                }
                break;
        }
    }

    private void deleteUser() {
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("homePersonalId",bean.getId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_deleteFamilyUser,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("删除家人---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //    删除家人接口
//    http://192.168.1.55:8080/smarthome/mobileapi/homeUser/delete.do?homePersonalId=&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名          |类型      |必需  |描述
//    |-----          |----     |---- |----
//            |token          |String   |Y    |令牌
//    |homePersonalId |Long |Y |家人个人用户编号
    @Override
    public void getSerVerData() {

    }
}
