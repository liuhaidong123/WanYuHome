package com.home.wanyu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.HouseAdapter;
import com.home.wanyu.adapter.PopFamilyListAdapter;
import com.home.wanyu.bean.Bean_getMyFamily;
import com.home.wanyu.bean.Bean_saveMyFamily;
import com.home.wanyu.lzhUtils.MyActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的家
public class MyHouseActivity extends MyActivity {
    @BindView(R.id.activity_my_house_rela_select_name) TextView activity_my_house_rela_select_name;//当前显示的我所选择的家的名字
    @BindView(R.id.activity_my_house_rela_select)RelativeLayout activity_my_house_rela_select;//家的名字所在的layout
    @BindView(R.id.more)ImageView more;

    @BindView(R.id.rela)RelativeLayout rela;
    private PopupWindow pop;
    List<Bean_getMyFamily.MapListBean> li;
    private int current;
    private String resStr;
    private boolean isFlag=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //不处理
                    break;
                case 1:
                    try{
                        Bean_getMyFamily fami= mGson.gson.fromJson(resStr,Bean_getMyFamily.class);
                        if (fami!=null){
                            if ("0".equals(fami.getCode())){
                                li= fami.getMapList();
                                if (li!=null&&li.size()>0){
                                    activity_my_house_rela_select_name.setText("切换家");
                                    int ids=li.get(0).getCurrentFamilyId();
                                    for (int i=0;i<li.size();i++){
                                        if (li.get(i).getFamilyId()==ids){
                                            activity_my_house_rela_select_name.setText(li.get(i).getFamilyName());
                                        }
                                    }
                                    activity_my_house_rela_select.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2://关联家时联网失败
                    isFlag=false;
                    mToast.Toast(con,"网络异常，关联失败");
                    break;
                case 3:
                    isFlag=false;
                    try{
                        Bean_saveMyFamily fily=mGson.gson.fromJson(resStr,Bean_saveMyFamily.class);
                        if (fily!=null){
                            if ("0".equals(fily.getCode())){
                                mToast.Toast(con,"关联成功");
                                activity_my_house_rela_select_name.setText(li.get(current).getFamilyName());
                            }
                            else {
                                mToast.Toast(con,fily.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con, ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.Toast(con,"关联失败，数据异常");
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_my_house);
        setTitle("我的家");
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        activity_my_house_rela_select.setVisibility(View.GONE);
        getFamilyData();
    }


    @OnClick({R.id.my_house_layout_Housemsg,R.id.my_house_layout_familyImage,R.id.my_house_layout_Device,R.id.activity_my_house_rela_select})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.my_house_layout_Housemsg://我的房屋信息
                startActivity(new Intent(MyHouseActivity.this,MyHouseInfoActivity.class));
            break;
            case R.id.my_house_layout_familyImage://我的家人管理
                startActivity(new Intent(con,MyHouseFamilyManagerActivity.class));
                break;
            case R.id.my_house_layout_Device://我的设备管理
                Intent intent=new Intent();
                intent.putExtra("type","all");
                intent.setClass(con,MyHouseDeviceManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_my_house_rela_select://我创建的家或者我加入的家选项
                showWindow();//弹出可选择的家的列表
                break;
        }
    }

    private void showWindow() {
        if (li!=null&&li.size()>0){
            pop = new PopupWindow();
            rela.setSelected(true);
            View v = LayoutInflater.from(con).inflate(R.layout.pop_house, null);
            ListView pop_family_list= (ListView) v.findViewById(R.id.pop_family_list_1);
            HouseAdapter adapter=new HouseAdapter(con,li);
            pop_family_list.setAdapter(adapter);
            pop_family_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    saveFamiley(position);
                    pop.dismiss();
                }
            });
            RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_my_house_rela_select);

            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setWidth(parent.getMeasuredWidth());

            pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            pop.setContentView(v);
            pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            pop.setTouchable(true);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);

            pop.setAnimationStyle(R.style.popup4_anim);
            pop.showAsDropDown(parent, 0, 2);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    rela.setSelected(false);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
                }
            });
        }

    }

    @Override
    public void getSerVerData() {

    }
    //关联选择的家http://192.168.1.55:8080/smarthome/mobileapi/personal/setFamily.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&familyId=123&personalId=123234
//    |token        |String   |Y    |令牌
//    |familyId     |Long     |Y    |要关联的家的编号
//    |personalId   |Long     |Y    |要关联的家的主人的编号
    public void saveFamiley(int pos){
        if (li==null|li.size()==0){
            return;
        }
        if (isFlag){
            mToast.Toast(con,"正在操作，请稍后");
            return;
        }
        isFlag=true;
        current=pos;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("familyId",li.get(current).getFamilyId()+"");
        mp.put("personalId",li.get(current).getPersonalId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_saveMyFamily,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("关联选择的家--",resStr+"---"+li.get(current).getFamilyName());
                handler.sendEmptyMessage(3);
            }
        });
    }
    //获取存在的家的名字http://192.168.1.55:8080/smarthome/mobileapi/family/findMyRelativeHome.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void getFamilyData() {
       Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+Ip.interface_getAllFamily,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取我的所有家--",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
