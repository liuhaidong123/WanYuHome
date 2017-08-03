package com.home.wanyu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.MyCircleContactAdapter_c;
import com.home.wanyu.adapter.OtherPersonCircleAdapter;
import com.home.wanyu.apater.OtherPersonActivityAdapter;
import com.home.wanyu.bean.Bean_otherActivity;
import com.home.wanyu.bean.Bean_otherCircle;
import com.home.wanyu.bean.Bean_otherPersonInfo;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//他人主页
//intent.putExtra("id","1");
public class OtherPersonInfoActivity extends Activity {
    private Context con=OtherPersonInfoActivity.this;
    private String tataId;//当前的id
    String na="LIM";
    @BindView(R.id.fragment_mine_image_userphoto_1)RoundImageView fragment_mine_image_userphoto_1;//头像image
    @BindView(R.id.fragment_mine_image_username_1)TextView fragment_mine_image_username_1;//姓名
    @BindView(R.id.fragment_mine_image_usersex_1)ImageView fragment_mine_image_usersex_1;//性别的image

    @BindView(R.id.other_person_layout_acti)RelativeLayout other_person_layout_acti;//活动的layout
    @BindView(R.id.other_person_layout_circle)RelativeLayout other_person_layout_circle;//圈子的layout
    @BindView(R.id.other_listview)MyListView other_listview;

    @BindView(R.id.other_loading_layout)RelativeLayout other_loading_layout;
    @BindView(R.id.other_loading_text)TextView other_loading_text;
    @BindView(R.id.other_progressBar)ProgressBar other_progressBar;
    private int Select=0;//当前选择的是圈子还是活动
    private final int ACTIVITY=1;//活动
    private final int CIRCLE=0;//圈子

    private List<Bean_otherCircle.RowsBean> listCircle;//圈子的数据源
    private  List<Bean_otherActivity.RowsBean> listActi;//活动数据源
    private String[]data={"2017-3-21 12:12:40","2017-5-10 14:52:33","2016-5-16 17:00:00","2017-5-16 16:00:00"};//发布时间
    private int[] resId={R.mipmap.ph1,R.mipmap.ph2,R.mipmap.ph3,R.mipmap.ph4,R.mipmap.ph5};
    private String[]name={"刘明","刘文","王文","小明","阿三","HE"};
    private OtherPersonCircleAdapter adapter_c;//我的圈子的适配QI
    private OtherPersonActivityAdapter adapter_a;//我的活动的view

    private int start=0;
    private int limit=10;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    other_loading_layout.setClickable(true);
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1://获取他人信息
                    try{
                        Bean_otherPersonInfo info= mGson.gson.fromJson(resStr,Bean_otherPersonInfo.class);
                        if (info==null){
                            return;
                        }
                        if ("0".equals(info.getCode())){
                           Bean_otherPersonInfo.PersonalBean personalBean= info.getPersonal();
                            if (personalBean!=null){
                                Picasso.with(con).load(Ip.imagePath+personalBean.getAvatar()).error(R.mipmap.errorphoto).into(fragment_mine_image_userphoto_1);
                                fragment_mine_image_username_1.setText(personalBean.getUserName()==null?"未填写":personalBean.getUserName());
                                int gender= personalBean.getGender();
                                fragment_mine_image_usersex_1.setSelected(gender==1);//1男2女
                                }
                        }
                        else {
                            mToast.Toast(con,info.getMessage());
                            }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 2://获取他人的圈子
                    other_loading_layout.setVisibility(View.GONE);
                    other_loading_layout.setClickable(true);
                    try{
                        Bean_otherCircle circle=mGson.gson.fromJson(resStr,Bean_otherCircle.class);
                        if (circle==null){
                            return;
                        }
                        List<Bean_otherCircle.RowsBean>li=circle.getRows();
                        if (li!=null&&li.size()>0){
                            listCircle.addAll(li);
                            adapter_c.notifyDataSetChanged();
                            start+=li.size();
                            if (li.size()==limit){
                                other_loading_layout.setVisibility(View.VISIBLE);
                                setLoadingState(0);
                            }
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    other_loading_layout.setVisibility(View.GONE);
                    other_loading_layout.setClickable(true);
                    try{
                        Bean_otherActivity activity=mGson.gson.fromJson(resStr,Bean_otherActivity.class);
                        if (activity==null){
                            return;
                        }
                        List<Bean_otherActivity.RowsBean>li=activity.getRows();
                        if (li!=null&&li.size()>0){
                            listActi.addAll(li);
                            adapter_a.notifyDataSetChanged();
                            if (li.size()==limit){
                                other_loading_layout.setVisibility(View.VISIBLE);
                                setLoadingState(0);
                            }
                            start+=li.size();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_person_info);
        ButterKnife.bind(this);
        other_person_layout_circle.setSelected(true);
        tataId=getIntent().getStringExtra("id");
        listCircle=new ArrayList<>();
        Select=CIRCLE;
        other_loading_layout.setVisibility(View.GONE);
        adapter_c=new OtherPersonCircleAdapter(listCircle,con);
        other_listview.setAdapter(adapter_c);
        if (!"".equals(tataId)&&!TextUtils.isEmpty(tataId)){
            getPersonInfo();//获取他人信息
            getCircleData(start,limit);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @OnClick({R.id.other_person_layout_circle,R.id.other_person_layout_acti,R.id.other_loading_layout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.other_person_layout_circle://他的圈子
                Select=CIRCLE;
                other_person_layout_circle.setSelected(true);
                other_person_layout_acti.setSelected(false);
                start=0;
                listCircle=new ArrayList<>();
                other_loading_layout.setClickable(true);
                other_loading_layout.setVisibility(View.GONE);
                adapter_c=new OtherPersonCircleAdapter(listCircle,con);
                other_listview.setAdapter(adapter_c);
                getCircleData(start,limit);
                break;
            case R.id.other_person_layout_acti://他的活动
                Select=ACTIVITY;
                other_person_layout_circle.setSelected(false);
                other_person_layout_acti.setSelected(true);
                start=0;
                listActi=new ArrayList<>();
                other_loading_layout.setClickable(true);
                other_loading_layout.setVisibility(View.GONE);
                adapter_a=new OtherPersonActivityAdapter(listActi,con);
                other_listview.setAdapter(adapter_a);
                getActData(start,limit);
                break;
            case R.id.other_loading_layout://加载更多的view点击事件
                setLoadingState(1);
                other_loading_layout.setClickable(false);
                start+=limit;
                switch (Select){
                    case ACTIVITY:
                        getActData(start,limit);
                        break;
                    case CIRCLE:
                        getCircleData(start,limit);
                        break;
                }
                break;
        }
    }
    //获取他人的信息
//    获取他人的个人页个人信息接口
//    http://192.168.1.55:8080/smarthome/mobileapi/personal/gettata.do?tataId=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |tataId       |Long     |Y    |他她的个人用户编号
    public void getPersonInfo(){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("tataId",tataId);
        okhttp.getCall(Ip.serverPath+Ip.interface_getOtherPersonMsg,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取他人的信息--",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }


//    分页查询他人的圈子状态信息列表
//    http://192.168.1.55:8080/smarthome/mobileapi/state/findPageTata.do?tataId=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    //获取他人圈子数据
    public void getCircleData(int st,int limi){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("tataId",tataId);
        mp.put("start",st+"");
        mp.put("limit",limi+"");
        Log.i("start=="+st,"limit=="+limi);
        okhttp.getCall(Ip.serverPath+Ip.interface_getOtherPersonCircle,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取他人的圈子--",resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }
    //获取他人活动
    public void getActData(int st,int limi){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("tataId",tataId);
        mp.put("start",st+"");
        mp.put("limit",limi+"");
        Log.i("start=="+st,"limit=="+limi);
        okhttp.getCall(Ip.serverPath+Ip.interface_getOtherPersonActivity,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取他人的活动--",resStr);
                handler.sendEmptyMessage(3);
            }
    });
}
    public void setLoadingState(int state){
        switch (state){
            case 0://显示加载更多
                other_loading_layout.setClickable(true);
                other_loading_text.setText("加载更多");
                other_progressBar.setVisibility(View.GONE);
                break;
            case 1:
                other_loading_layout.setClickable(false);
                other_loading_text.setText("正在加载。。。");
                other_progressBar.setVisibility(View.VISIBLE);
                break;
        }

    }
    public void retu(View vi){
        finish();
    }
}