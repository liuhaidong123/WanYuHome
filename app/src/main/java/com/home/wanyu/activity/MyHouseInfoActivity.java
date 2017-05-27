package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_MyHouseInf;
import com.home.wanyu.bean.Bean_changeHouseInfo;
import com.home.wanyu.bean.Bean_getCity;
import com.home.wanyu.bean.Bean_getHouseInfo;
import com.home.wanyu.bean.Bean_getXQ;
import com.home.wanyu.bean.cityList.BaseAreaList;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.PhoneUtils;
import com.home.wanyu.lzhView.wheelView.MyAdapter;
import com.home.wanyu.lzhView.wheelView.MyAreaAdapter;
import com.home.wanyu.lzhView.wheelView.MyProAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.MyWheelArr50;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.MyXqAdapter;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的房屋信息
public class MyHouseInfoActivity extends MyActivity {

    @BindView(R.id.city_rl_1)RelativeLayout city_rl;//城市所在的layout
    @BindView(R.id.city_tv_msg_1)TextView city_tv_msg;//城市的名字
    String city;

//    @BindView(R.id.area_rl)RelativeLayout area_rl;//小区的layout
    @BindView(R.id.area_tv_msg_1)TextView area_tv_msg;//显示的小区名字
    String areaName;

    @BindView(R.id.name_tv_msg_1)EditText name_tv_msg;//姓名1
    String userName;

    @BindView(R.id.phone_tv_msg_1)EditText phone_tv_msg;//手机号1
    String telephone;

    @BindView(R.id.phone_rl_pri_1)RelativeLayout phone_rl_pri;//身份选择1
    @BindView(R.id.phone_tv_msg_pri_1)TextView phone_tv_msg_pri;//显示身份的TextView1
    String pri;

    @BindView(R.id.lou_tv_msg_1)EditText lou_tv_msg;//楼号1
    String louName;

    @BindView(R.id.ceng_tv_msg_1)EditText ceng_tv_msg;//楼层
    String cengName;

    @BindView(R.id.unit_tv_msg_1)EditText unit_rl;//单元号1
    String unitName;

    @BindView(R.id.hourse_tv_msg_1)EditText hourse_tv_msg;//房间号
    String roomName;

    private PopupWindow popupW;
    private TextView activity_address_title;
    //三级列表
//    private WheelView my_address_pop_wheelV_Provice,my_address_pop_wheelV_City,my_address_pop_wheelV_Areas;
//    private List<Map<String,String>> mProvince;//省
//    private List<Map<String,String>>listCity;
//    private List<Map<String,String>>listArea;
//    private String provice;
//    private String city;
//    private int currentPro,currentCity,currentArea;
//    private MyProAdapter adapterPro,adapterCity,adapterAres;
//    private Map<String,List<Map<String,String>>> mCity;
//    private Map<String,List<Map<String,String>>>mArea;
//    private String selectAddress;//被选中等address（省市县）
//    private String pro,cit,area;
//    private String selectPostCode;//被选中的postCode（三级集合）
//    private String codeP,codeC,codeA;//被选中的省市县三级的code
    //三级列表
    private String resultStr;

    private String id;

    int selectId;//当前滑动view中选定的用户身份
    private List<Bean_getCity.BaseAreaListBean> list;//存放城市的bena
    private String areaCode;//城市编号
    private int SelectAreaPos;//当前选择的城市的下表
    PopupWindow popArea;

    private PopupWindow popXQ;//小区
    private int SelectXQPos;//小区的下下表
    private String xqCode;//小区的编码
    private List<Bean_getXQ.ResultBean>listXQ;

    private boolean flagArea=false;//获取到的省市是否是空的，true空的
    private PopupWindow pop;
    @BindArray(R.array.addressStr)String[]addressStr;//存放 用户身份
    Bean_MyHouseInf info;//房屋信息
    private int selectPosAdd=0;//选择的业主身份
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1://获取房屋信息
                    try{
                        info= mGson.gson.fromJson(resStr,Bean_MyHouseInf.class);
                        if (info!=null){
                                if ("0".equals(info.getCode())){
                                    Bean_MyHouseInf.FamilyBean familyBean=info.getFamily();
                                    if (familyBean==null){
                                        return;
                                    }
                                    name_tv_msg.setText(familyBean.getOwnerName());
                                    phone_tv_msg.setText(familyBean.getOwnerTelephone()+"");
                                    lou_tv_msg.setText(familyBean.getBuildingNumber());//楼号
                                     ceng_tv_msg.setText(familyBean.getFloor());//楼层
                                    city_tv_msg.setText(familyBean.getAreaName());
                                    areaCode=familyBean.getAreaCode()+"";
                                    area_tv_msg.setText(familyBean.getResidentialQuartersName());
                                    xqCode=familyBean.getResidentialQuartersId()+"";

                                    unit_rl.setText(familyBean.getUnitNumber());//单元号
                                    hourse_tv_msg.setText(familyBean.getRoomNumber());//房间号
                                if (familyBean!=null){
                                    int type=familyBean.getUserType();
                                    String tp="";
                                    switch (type){
                                        case 0:
                                            selectPosAdd=0;
                                            tp="业主";
                                            break;
                                        case 1:
                                            selectPosAdd=1;
                                            tp="租客";
                                            break;
                                        case 2:
                                            selectPosAdd=2;
                                            tp="访客";
                                            break;
                                        default:
                                            selectPosAdd=type;
                                            tp="未知身份";
                                            break;
                                    }
                                    phone_tv_msg_pri.setText(tp);
//                                    areaCode=familyBean.getAreaCode()+"";//城市编号
//                                    name_tv_msg.setText(familyBean.getOwnerName());
//                                    phone_tv_msg.setText(familyBean.getOwnerTelephone()+"");
//                                    lou_tv_msg.setText(familyBean.getBuildingNumber());
//                                    ceng_tv_msg.setText(familyBean.get);

//                                    city_tv_msg.setText(familyBean.getCity());//城市
//                                    area_tv_msg.setText(familyBean.getAreaName());
                                }
                            }
                            else {
                                mToast.Toast(con,info.getResult());
                                }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
//                            mToast.Toast(con,"0000000");
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 2:
                    try{
                        Bean_changeHouseInfo info=mGson.gson.fromJson(resStr,Bean_changeHouseInfo.class);
                        if (info!=null){
                            if ("0".equals(info.getCode())){
                                mToast.Toast(con,"提交成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,info.getResult());
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
                case 3://获取城市
                    try{
                        Bean_getCity bean=mGson.gson.fromJson(resStr,Bean_getCity.class);
                        if (bean!=null){
                            if ("0".equals(bean.getCode())){
                                list= bean.getBaseAreaList();
                                if (list!=null&&list.size()>0){
//                                        -----
                                }
                                else {
                                    flagArea=true;
                                    mToast.ToastFaild(con,ToastType.GSONEMPTY);
                                }
                            }
                            else {
                                mToast.Toast(con,bean.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                            flagArea=true;
                        }
                    }
                    catch (Exception e){
                            e.printStackTrace();
                            mToast.ToastFaild(con,ToastType.FAILD);
                    }
                    break;
                case 4://获取小区成功
                    try{
                        Bean_getXQ xq=mGson.gson.fromJson(resStr,Bean_getXQ.class);
                        if (xq!=null){
                            if ("0".equals(xq.getCode())){
                                listXQ=xq.getResult();
                                if (listXQ!=null&&listXQ.size()>0){
                                    showWindowXq();
                                }
                                else {
                                    mToast.ToastFaild(con,ToastType.GSONEMPTY);
                                }
                            }
                            else {
                                mToast.Toast(con,"获取小区失败");
                            }

                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.FAILD);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.activity_my_house_info_title);
        initChildView(R.layout.activity_my_house_info);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        phone_tv_msg_pri.setText(addressStr[selectPosAdd]);
        //        pullXml();
        getSerVerData();//获取我的房屋信息
        getCityData();
    }
    public void Submit(View vi){
        if (vi.getId()!=R.id.my_user_info_submmit_t){
            return;
        }
        Log.i("--------------","--------------");
            if (checkInput()){//检查电话号码，联系电话，身份，选择的地址，详细地址是否为空
                if (PhoneUtils.isPhoneNum(phone_tv_msg.getText().toString())){

//                    |userType       |Integer  |N    |业主身份；0=业主，1=租客，2=访客
                    HashMap<String,String>mp=new HashMap<>();
                    mp.put("token",UserInfo.userToken);
                    mp.put("residentialQuartersId",xqCode);
                    mp.put("areaCode",areaCode);
                    mp.put("areaName",city);
                    mp.put("buildingNumber",louName);
                    mp.put("unitNumber",unitName);
                    mp.put("roomNumber",roomName);
                    mp.put("ownerName",userName);
                    mp.put("ownerTelephone",telephone);
                    mp.put("floor",cengName);
                    mp.put("residentialQuartersName",areaName);
                    mp.put("userType",selectPosAdd+"");
                    okhttp.getCall(Ip.serverPath+Ip.interface_setHouseInfo,mp,okhttp.OK_POST).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            resStr=response.body().string();
                            Log.i("房屋信息修改/tianji---",resStr);
                            handler.sendEmptyMessage(2);
                        }
                    });
                }
                else {
                    mToast.Toast(con,"电话号码不正确");
                }
            }
        else {
                mToast.Toast(con,"您填写的信息不完整");
            }
    }

    private boolean checkInput() {
        city=city_tv_msg.getText().toString();
        areaName=area_tv_msg.getText().toString();
        userName=name_tv_msg.getText().toString();
        telephone=phone_tv_msg.getText().toString();
        pri=phone_tv_msg_pri.getText().toString();
        louName=lou_tv_msg.getText().toString();
        cengName=ceng_tv_msg.getText().toString();
        unitName=unit_rl.getText().toString();
        roomName=hourse_tv_msg.getText().toString();
            if (!"".equals(city)&&!TextUtils.isEmpty(city)){
                        if (!"".equals(areaName)&&!TextUtils.isEmpty(areaName)){
                            if (!"".equals(userName)&&!TextUtils.isEmpty(userName)){
                                if (!"".equals(telephone)&&!TextUtils.isEmpty(telephone)){
                                    if (!"".equals(pri)&&!TextUtils.isEmpty(pri)){
                                        if (!"".equals(louName)&&!TextUtils.isEmpty(louName)){
                                            if (!"".equals(cengName)&&!TextUtils.isEmpty(cengName)){
                                                if (!"".equals(unitName)&&!TextUtils.isEmpty(unitName)){
                                                    if (!"".equals(roomName)&&!TextUtils.isEmpty(roomName)){
                                                    return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
            }
        return false;
    }

    @OnClick({R.id.phone_rl_pri_1,R.id.city_rl_1,R.id.area_rl_1})
    public void click(View view){
        switch (view.getId()){
            case R.id.phone_rl_pri_1://身份
                showwWindow();
                break;
            case R.id.city_rl_1://选择城市
                showwWindowArea();
                break;
            case R.id.area_rl_1://选择小区
                if (!"".equals(areaCode)&&!TextUtils.isEmpty(areaCode)){
                    getAreaData();
                }
               else {
                    mToast.Toast(con,"您还没有选择城市");
                    }
                break;
                    }
    }
    //城市选择
    private void showwWindowArea() {//0城市列表，1小区列表
        if (list!=null&&list.size()>0){
            View vi=getLayoutInflater().inflate(R.layout.pop_address, null);
            TextView my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle_2);
            my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popArea.dismiss();
                }
            });
            TextView my_address_pop_submit_2= (TextView) vi.findViewById(R.id.my_address_pop_submit_2);

            my_address_pop_submit_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    areaCode=list.get(SelectAreaPos).getAreaCode();
                    xqCode="";
                    area_tv_msg.setText("");
                    city_tv_msg.setText(list.get(SelectAreaPos).getAreaName());
                    popArea.dismiss();
                }
            });
            WheelView my_address_wheel= (WheelView) vi.findViewById(R.id.my_address_wheel);
            MyAreaAdapter adapter=new MyAreaAdapter(MyHouseInfoActivity.this,list);
            my_address_wheel.setViewAdapter(adapter);
            SelectAreaPos=0;
            my_address_wheel.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    SelectAreaPos=newValue;
                }
            });
            popArea=new PopupWindow();

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params=getWindow().getAttributes();
            params.alpha=0.6f;
            getWindow().setAttributes(params);
            popArea.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popArea.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popArea.setContentView(vi);
            popArea.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            popArea.setTouchable(true);
            popArea.setFocusable(true);
            popArea.setOutsideTouchable(true);
            RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
            popArea.setAnimationStyle(R.style.popup3_anim);
            popArea.showAtLocation(parent, Gravity.BOTTOM, 0,0);
            popArea.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams params=getWindow().getAttributes();
                    params.alpha=1f;
                    getWindow().setAttributes(params);
                }
            });
        }
        else {
            mToast.Toast(con,"正在重新加载城市列表，请稍后重试");
            getCityData();
            return;
        }

    }
    //选择小区
    private void showWindowXq(){
        View vi=getLayoutInflater().inflate(R.layout.pop_address, null);
        TextView my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle_2);
        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popXQ.dismiss();
            }
        });
        TextView my_address_pop_submit_2= (TextView) vi.findViewById(R.id.my_address_pop_submit_2);

        my_address_pop_submit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_tv_msg.setText(listXQ.get(SelectXQPos).getRname());
                xqCode=listXQ.get(SelectXQPos).getId()+"";
                popXQ.dismiss();
            }
        });
        WheelView my_address_wheel= (WheelView) vi.findViewById(R.id.my_address_wheel);
        MyXqAdapter adapter=new MyXqAdapter(MyHouseInfoActivity.this,listXQ);
        my_address_wheel.setViewAdapter(adapter);
        SelectXQPos=0;
        my_address_wheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
               SelectXQPos=newValue;
            }
        });
        popXQ=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popXQ.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popXQ.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popXQ.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popXQ.setContentView(vi);
        popXQ.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popXQ.setTouchable(true);
        popXQ.setFocusable(true);
        popXQ.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
        popXQ.setAnimationStyle(R.style.popup3_anim);
        popXQ.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        popXQ.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }
    //选择身份
    private void showwWindow() {
        selectId=0;
        View vi=getLayoutInflater().inflate(R.layout.pop_address, null);
        TextView my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle_2);
        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        TextView my_address_pop_submit_2= (TextView) vi.findViewById(R.id.my_address_pop_submit_2);

        my_address_pop_submit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosAdd=selectId;
                phone_tv_msg_pri.setText(addressStr[selectPosAdd]);
                pop.dismiss();
            }
        });
        WheelView my_address_wheel= (WheelView) vi.findViewById(R.id.my_address_wheel);
        List<String>li=new ArrayList<>();
        for (int i=0;i<addressStr.length;i++){
            li.add(addressStr[i]);
        }
        MyWheelAdapter50 adapter=new MyWheelAdapter50(MyHouseInfoActivity.this,li,"");
        my_address_wheel.setViewAdapter(adapter);
        my_address_wheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectId=newValue;
            }
        });
        pop=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(vi);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }

    //http://192.168.1.55:8080/smarthome/mobileapi/family/get.do?token=9DB2FD6FDD2F116CD47CE6C48B3047E获取房屋信息
    public void getData(){
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+Ip.interface_getHouseInfo,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取房屋信息---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
    @Override
    public void getSerVerData() {
            getData();//获取我的房屋信息
    }
    //获取城市列表http://192.168.1.55:8080/smarthome/mobilepub/baseArea/findList.do
    public void getCityData() {
        Map<String,String>mp=new HashMap<>();
        okhttp.getCall(Ip.pths+Ip.interface_getCity,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取城市列表---",resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }
    //获取小区
    public void getAreaData(){
        Map<String,String>mp=new HashMap<>();
        mp.put("AreaCode",list.get(SelectAreaPos).getAreaCode());
        okhttp.getCall(Ip.pths+Ip.interface_getArea,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取小区---"+areaCode,"------"+resStr);
                handler.sendEmptyMessage(4);
            }
        });
    }

    //地址选择器
//    private void showWindowSelect() {
//        pro=mProvince.get(0).get("name");
//        codeP=mProvince.get(0).get("code");
//        cit=mCity.get(pro).get(0).get("name");
//        codeC=mCity.get(pro).get(0).get("code");
//        area=mArea.get(cit).get(0).get("name");
//        codeA=mArea.get(cit).get(0).get("code");
//        currentArea=0;
//        currentCity=0;
//        currentPro=0;
//        View vi=getLayoutInflater().inflate(R.layout.my_address_popup, null);
//        my_address_pop_wheelV_Provice= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Provice);
//        my_address_pop_wheelV_City= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_City);
//        my_address_pop_wheelV_Areas= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Areas);
//
//
//        adapterPro=new MyProAdapter(MyHouseInfoActivity.this,mProvince);
//        my_address_pop_wheelV_Provice.setViewAdapter(adapterPro);
//        my_address_pop_wheelV_Provice.setVisibleItems(5);
//
//        adapterCity=new MyProAdapter(MyHouseInfoActivity.this,mCity.get(mProvince.get(0).get("name")));
//        my_address_pop_wheelV_City.setViewAdapter(adapterCity);
//        my_address_pop_wheelV_City.setVisibleItems(5);
//        List<Map<String,String>> li=mCity.get(mProvince.get(0).get("name"));
//        for (int i=0;i<li.size();i++){
//            Log.i("-------------",li.get(i).get("name"));
//        }
//        adapterAres=new MyProAdapter(MyHouseInfoActivity.this,mArea.get(li.get(0).get("name")));
//        my_address_pop_wheelV_Areas.setViewAdapter(adapterAres);
//        my_address_pop_wheelV_Areas.setVisibleItems(5);
//
//        TextView  my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle);
//        TextView    my_address_pop_submit= (TextView) vi.findViewById(R.id.my_address_pop_submit);
//
//        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupW.dismiss();
//            }
//        });
//
//        my_address_pop_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectPostCode=codeA;
//                selectAddress=pro+" "+cit+" "+area;
//                Log.e("code=="+selectPostCode,"name---"+selectAddress);
//                my_address_addressSelect.setText(selectAddress);
//                popupW.dismiss();
//            }
//        });
//
//
//        my_address_pop_wheelV_Provice.addChangingListener(new OnWheelChangedListener() {
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                currentPro=newValue;
//                my_address_pop_wheelV_City.setViewAdapter(new MyProAdapter(MyHouseInfoActivity.this,mCity.get(mProvince.get(currentPro).get("name"))));
//                my_address_pop_wheelV_City.setCurrentItem(0);
//                List<Map<String,String>>lit=mCity.get(mProvince.get(currentPro).get("name"));
//                my_address_pop_wheelV_Areas.setViewAdapter(new MyProAdapter(MyHouseInfoActivity.this,mArea.get(lit.get(0).get("name"))));
//                my_address_pop_wheelV_Areas.setCurrentItem(0);
//                pro=mProvince.get(newValue).get("name");
//                codeP=mProvince.get(newValue).get("code");
//
//                cit=mCity.get(mProvince.get(newValue).get("name")).get(0).get("name");
//                codeC=mCity.get(mProvince.get(newValue).get("name")).get(0).get("code");
//
//                List<Map<String,String>>li=mCity.get(mProvince.get(currentPro).get("name"));
//                area=mArea.get(li.get(0).get("name")).get(0).get("name");
//                codeA=mArea.get(li.get(0).get("name")).get(0).get("code");
//                currentCity=0;
//                currentArea=0;
//            }
//        });
//        my_address_pop_wheelV_City.addChangingListener(new OnWheelChangedListener() {
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                currentCity=newValue;
//                List<Map<String,String>>li=mCity.get(mProvince.get(currentPro).get("name"));
//                my_address_pop_wheelV_Areas.setViewAdapter(new MyProAdapter(MyHouseInfoActivity.this,mArea.get(li.get(newValue).get("name"))));
//                my_address_pop_wheelV_Areas.setCurrentItem(0);
//
//                cit=li.get(newValue).get("name");
//                codeC=li.get(newValue).get("code");
//
//                area=mArea.get(li.get(newValue).get("name")).get(0).get("name");
//                codeA=mArea.get(li.get(newValue).get("name")).get(0).get("code");
//                currentArea=0;
//            }
//        });
//
//        my_address_pop_wheelV_Areas.addChangingListener(new OnWheelChangedListener() {
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                  String proName=mProvince.get(currentPro).get("name");
//                    String citNam=mCity.get(proName).get(currentCity).get("name");
//                area=mArea.get(citNam).get(newValue).get("name");
//                codeA=mArea.get(citNam).get(newValue).get("code");
//            }
//        });
//
//        popupW=new PopupWindow();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        WindowManager.LayoutParams params=getWindow().getAttributes();
//        params.alpha=0.6f;
//        getWindow().setAttributes(params);
//        popupW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupW.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        popupW.setContentView(vi);
//        popupW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
//        popupW.setTouchable(true);
//        popupW.setFocusable(true);
//        popupW.setOutsideTouchable(true);
//        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
//        popupW.setAnimationStyle(R.style.popup3_anim);
//        popupW.showAtLocation(parent, Gravity.BOTTOM, 0,0);
//        popupW.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                WindowManager.LayoutParams params=getWindow().getAttributes();
//                params.alpha=1f;
//                getWindow().setAttributes(params);
//            }
//        });
//    }


    //解析数据
//    public boolean pullXml(){
//        try{
//            InputStream is=getResources().getAssets().open("city.xml");
//            //    创建XmlPullParserFactory解析工厂
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            //    通过XmlPullParserFactory工厂类实例化一个XmlPullParser解析类
//            XmlPullParser parser = factory.newPullParser();
//            //    根据指定的编码来解析xml文档
//            parser.setInput(is, "utf-8");
//            //    得到当前的事件类型
//            int eventType = parser.getEventType();
//            //    只要没有解析到xml的文档结束，就一直解析
//            while(eventType != XmlPullParser.END_DOCUMENT)
//            {
//                switch (eventType)
//                {
//                    //    解析到文档开始的时候
//                    case XmlPullParser.START_DOCUMENT:
//                        mProvince = new ArrayList<>();//省份集合
//                        mCity=new HashMap<>();//市区集合
//                        mArea=new HashMap<>();//县区集合
//                        break;
//                    //    解析到xml标签的时候
//                    case XmlPullParser.START_TAG:
//                        switch (parser.getName()){
//                            case "province":
//                                Map<String,String>mp=new HashMap<>();
//                                mp.put("name",parser.getAttributeValue(0));
//                                mp.put("code",parser.getAttributeValue(1));
//                                mProvince.add(mp);
//                                listCity=new ArrayList<>();
//                                provice=parser.getAttributeValue(0);
//                                break;
//                            case "city":
//                                Map<String,String>mp1=new HashMap<>();
//                                mp1.put("name",parser.getAttributeValue(0));
//                                mp1.put("code",parser.getAttributeValue(1));
//                                listCity.add(mp1);
//                                city=parser.getAttributeValue(0);
//                                listArea=new ArrayList<>();
//                                break;
//                            case "area":
//                                Map<String,String>mp2=new HashMap<>();
//                                mp2.put("name",parser.getAttributeValue(0));
//                                mp2.put("code",parser.getAttributeValue(1));
//                                listArea.add(mp2);
//                                break;
//                        }
//                        break;
//                    //    解析到xml标签结束的时候
//                    case XmlPullParser.END_TAG:
//                        switch (parser.getName()){
//                            case "province":
//                                mCity.put(provice,listCity);
//                                break;
//                            case "city":
//                                mArea.put(city,listArea);
//                                break;
//                        }
//                        break;
//                }
//                //    通过next()方法触发下一个事件
//                eventType = parser.next();
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

}
