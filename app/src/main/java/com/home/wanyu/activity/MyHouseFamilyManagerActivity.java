package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.MyHouseFamilyManagerAdapter;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.lzhUtils.MyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的家人管理页面
public class MyHouseFamilyManagerActivity extends MyActivity {
    @BindView(R.id.activity_my_house_family_manager_listView)ListView activity_my_house_family_manager_listView;
    private MyHouseFamilyManagerAdapter adapter;
    private List<Bean_FamilyUserS.PersonalListBean> list;
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
                        Bean_FamilyUserS userS= mGson.gson.fromJson(mTools.mResponStr,Bean_FamilyUserS.class);
                        if (userS!=null){
                            if ("0".equals(userS.getCode())){
                                userS.getPersonalList();
                                if (userS.getPersonalList()!=null&&userS.getPersonalList().size()>0){
                                    list.clear();
                                    list.addAll(userS.getPersonalList());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                mToast.Toast(con,userS.getMessage());
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
        initTitleView(R.layout.myhousefamilymanager_title);
        initChildView(R.layout.activity_my_house_family_manager);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        initData();
        mTools=new okhttpTools();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSerVerData();
    }

    private void initData() {
        list=new ArrayList<>();
        adapter=new MyHouseFamilyManagerAdapter(con,list);
        activity_my_house_family_manager_listView.setAdapter(adapter);
    }

    //http://192.168.1.55:8080/smarthome/mobileapi/homeUser/findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取家人列表
    @Override
    public void getSerVerData() {
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mTools.getServerData(handler,1, Ip.serverPath+Ip.interface_getFamilyUsers,mp,"获取家人列表--");
    }

    public void Submit(View view) {
//        mToast.DebugToast(con,"添加");
        startActivity(new Intent(con,MyHouseFamilyAddActivity.class));
    }
}
