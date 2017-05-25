package com.home.wanyu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Icons.SceneMode;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.HomeSceneAddListAdapter;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.adapter.PopDataGridViewAdapter;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.bean.Bean_sceneSetting;
import com.home.wanyu.lzhUtils.MyActivity;

import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSenceAddSenceActivity extends MyActivity {
    private PopupWindow popWx;
    private PopupWindow popW;
    private PopupWindow popX;
    private int deleteId;
    @BindView(R.id.home_sence_Add_rela_eidtext)EditText home_sence_Add_rela_eidtext;//情景名称

    @BindView(R.id.home_sence_Add_rela_condition_textVname)TextView home_sence_Add_rela_condition_textVname;//显示启动条件的textview
    @BindView(R.id.home_sence_Add_rela_condition_imageselect)ImageView home_sence_Add_rela_condition_imageselect;//下拉，收起的imageview
    @BindView(R.id.home_sence_Add_rela_condition_relaLayout)RelativeLayout home_sence_Add_rela_condition_relaLayout;//启动条件所在的layout布局

    @BindView(R.id.home_senceAdd_listview)MyListView home_senceAdd_listview;//显示设备的listview
    private PopupWindow pop;
//    private ArrayList<Map<String,String>> list;
//    private String[]title={"客厅灯","电视","客厅插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    private HomeSceneAddListAdapter adapter;
    @BindArray(R.array.mode)String[]mode;
    private int SelectPositon=0;//当前选中的启动方式对应mode中的下标

    private List<Map<String,String>> listGridview;//定时启动弹出窗口中的gridview的数据源
    @BindArray(R.array.week)String[]str;
    PopDataGridViewAdapter adapterGridView;
    private List<String>listHours;//轮滚的小时数据源
    private List<String>listTime;//轮滚的分钟数据源

    private int currentTimeHour;//当前选中的启动时间的小时
    private int currentTimeMinus;//当前选中的启动时间的分钟
    private String sceneTime;//定时开启设置的时间
    private String repeatMode;//重复方式
    List<Bean_getSceneData.EquipmentListBean>list;//添加的设备
    @BindArray(R.array.KM)String[]listKm;//距离定位中的千米
    private int currentKm;//当前定位选中的千米数
    @BindArray(R.array.M)String[]listM;//距离定位中
    private int currentM;//当前定位选中的米数
    private int sceneDistance;//定位启动的距离
    //sceneMode:1一键，2定时，3定位
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 2://情景修改
                    try{
                        Bean_sceneSetting setting=mGson.gson.fromJson(mTools.mResponStr,Bean_sceneSetting.class);
                        if (setting!=null){
                            if ("0".equals(setting.getCode())){
                                mToast.Toast(con,"添加成功");
                            }
                            else {
                                ServerCode.showResponseMsg(con,setting.getCode());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
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
        initChildView(R.layout.activity_home_sence_add_sence);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("添加情景");
        ShowChildView(DEFAULTRESID);
        initData();
        mTools=new okhttpTools();

    }
    @OnClick({R.id.activity_homeSceneSetting_rela_sence_add,R.id.activity_homeSceneAdd_listitem_relaAdd_submit,R.id.home_sence_Add_rela_condition_relaLayout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homeSceneSetting_rela_sence_add://添加设备的按钮
                Intent intent=new Intent();
                intent.putExtra("type","sceneAdd");
                Bundle bundle=new Bundle();
                ArrayList<String>listName=new ArrayList<>();
                if (list!=null&&list.size()>0){
                    for (int i=0;i<list.size();i++){
                        listName.add(list.get(i).getName());
                    }
                }
                bundle.putSerializable("data",listName);
                intent.putExtra("data",bundle);
                intent.setClass(con,MyHouseDeviceManagerActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.activity_homeSceneAdd_listitem_relaAdd_submit://提交按钮
//                mToast.DebugToast(con,"确定");
                sendSceneSetting();
                break;
            case R.id.home_sence_Add_rela_condition_relaLayout://显示可选的启动方式
                home_sence_Add_rela_condition_imageselect.setSelected(true);
                showWindowMode();//显示选择启动方式的view
                break;
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==200){
                Bundle bundle=data.getBundleExtra("data");
                Bean_getSceneData.EquipmentListBean bean= (Bean_getSceneData.EquipmentListBean) bundle.getSerializable("data");
                list.add(bean);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void showWindowMode() {
        pop = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesenceaddsetting, null);
        TextView pop_text1= (TextView) v.findViewById(R.id.pop_text1);//一键启动
        TextView pop_text2= (TextView) v.findViewById(R.id.pop_text2);//定时启动
        TextView pop_text3= (TextView) v.findViewById(R.id.pop_text3);//定位启动

        pop_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPositon=0;
                home_sence_Add_rela_condition_textVname.setText(mode[0]);
                pop.dismiss();
            }
        });

        pop_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//定时启动
                SelectPositon=1;
                home_sence_Add_rela_condition_textVname.setText(mode[1]);
                pop.dismiss();
                showWindowData();

            }
        });

        pop_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//定位启动
                SelectPositon=2;
                home_sence_Add_rela_condition_textVname.setText(mode[2]);
                pop.dismiss();
                showWindowAddress();
            }
        });
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.home_sence_Add_rela_condition_relaLayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);

        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(parent.getMeasuredWidth());

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(v);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup2_anim);
        pop.showAsDropDown(parent, 0, 5);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                home_sence_Add_rela_condition_imageselect.setSelected(false);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

        //定位启动的view
    private void showWindowAddress() {
        sceneDistance=0;
        currentKm=0;
        currentM=0;
        popWx = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesenceasetting_address, null);
        WheelView pop_wheelview_address_M= (WheelView) v.findViewById(R.id.pop_wheelview_address_M);

        TextView pop_wheelview_address_Submit= (TextView) v.findViewById(R.id.pop_wheelview_address_Submit);

        pop_wheelview_address_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentKm==0&&currentM==0){
                    sceneDistance=100;//计算当前的数值
                }
                else {
                    sceneDistance=currentKm*1000+currentM;
                }
                popWx.dismiss();
            }
        });
        WheelView pop_wheelview_address_KM= (WheelView) v.findViewById(R.id.pop_wheelview_address_KM);

        pop_wheelview_address_KM.setViewAdapter(new MyWheelViewAdapterArray(con,listKm));
        pop_wheelview_address_KM.setTitle("km");
        pop_wheelview_address_M.setViewAdapter(new MyWheelViewAdapterArray(con,listM));
        pop_wheelview_address_M.setTitle("m");
        pop_wheelview_address_KM.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(con,listKm[newValue],Toast.LENGTH_SHORT).show();
                currentKm=Integer.parseInt(listKm[newValue]);
            }
        });
        pop_wheelview_address_M.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(con,listM[newValue],Toast.LENGTH_SHORT).show();
                currentM=Integer.parseInt(listM[newValue]);
            }
        });


        RelativeLayout parent = (RelativeLayout) findViewById(R.id.home_sence_Add_parentLayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        popWx.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWx.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popWx.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWx.setContentView(v);
        popWx.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popWx.setTouchable(true);
        popWx.setFocusable(true);
        popWx.setOutsideTouchable(true);

        popWx.setAnimationStyle(R.style.popup3_anim);
        popWx.showAtLocation(parent, Gravity.BOTTOM,0,0);
        popWx.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });


    }
    //定时启动的view
    private void showWindowData(){
        repeatMode="";
        sceneTime="";
        currentTimeHour=0;
        currentTimeMinus=0;
        popW = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesencesetting_data, null);
        WheelView pop_wheelview_data= (WheelView) v.findViewById(R.id.pop_wheelview_data);//小时
        WheelView pop_wheelview_time= (WheelView) v.findViewById(R.id.pop_wheelview_time);//分钟
        TextView pop_data_submit= (TextView) v.findViewById(R.id.pop_data_submit);
        pop_data_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sceneTime=listHours.get(currentTimeHour)+":"+listTime.get(currentTimeMinus);
                if ("1".equals(listGridview.get(listGridview.size()-1).get("select"))){
                    repeatMode=0+"";
                }
                else {
                    for (int i=0;i<listGridview.size()-1;i++){
                        if ("1".equals(listGridview.get(i).get("select"))){
                            repeatMode+=(i+",");
                        }
                    }
                    if (!"".equals(repeatMode)&&!TextUtils.isEmpty(repeatMode)){
                        repeatMode=repeatMode.substring(0,repeatMode.length()-1);
                    }
                    else {
                        repeatMode="";
                    }

                }
                popW.dismiss();
            }
        });
        pop_wheelview_data.setViewAdapter(new MyWheelAdapter(con,listHours));
        pop_wheelview_data.setTitle("点");
        pop_wheelview_time.setViewAdapter(new MyWheelAdapter(con,listTime));
        pop_wheelview_time.setTitle("分");
        pop_wheelview_data.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentTimeHour=newValue;
            }
        });
        pop_wheelview_time.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentTimeMinus=newValue;
            }
        });
        GridView pop_data_gridview= (GridView) v.findViewById(R.id.pop_data_gridview);
        pop_data_gridview.setAdapter(adapterGridView);
        pop_data_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select=listGridview.get(position).get("select");
                if (position!=listGridview.size()-1){
                    if ("1".equals(select)){
                        listGridview.get(position).put("select","0");
                    }
                    else {
                        listGridview.get(position).put("select","1");
                    }
                    if (isAll()){
                        listGridview.get(listGridview.size()-1).put("select","1");
                    }
                    else {
                        listGridview.get(listGridview.size()-1).put("select","0");
                    }
                }
                else {
                    if ("1".equals(listGridview.get(listGridview.size()-1))){
                        for (int i=0;i<listGridview.size();i++){
                            listGridview.get(i).put("select","0");
                        }
                    }
                    else {
                        for (int i=0;i<listGridview.size();i++){
                            listGridview.get(i).put("select","1");
                        }
                    }

//                    Toast.makeText(con,"全部重复",Toast.LENGTH_SHORT).show();
                }
                adapterGridView.notifyDataSetChanged();
            }
        });


        RelativeLayout parent = (RelativeLayout) findViewById(R.id.home_sence_Add_parentLayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        popW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popW.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popW.setContentView(v);
        popW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popW.setTouchable(true);
        popW.setFocusable(true);
        popW.setOutsideTouchable(true);

        popW.setAnimationStyle(R.style.popup3_anim);
        popW.showAtLocation(parent, Gravity.BOTTOM,0,0);
        popW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
    private void initData() {
        list=new ArrayList<>();
        adapter = new HomeSceneAddListAdapter(list,con);
        home_senceAdd_listview.setAdapter(adapter);
        home_senceAdd_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showWindow(position);
                return false;
            }
        });
        //定时启动弹窗中gridview的数据源以及适配器
        listGridview=new ArrayList<>();
        int length=str.length;
        for (int i=0;i<length;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("name",str[i]);
            mp.put("select","0");
            listGridview.add(mp);
        }
        adapterGridView=new PopDataGridViewAdapter(listGridview,con);
        listHours=new ArrayList<>();
        for (int i=0;i<24;i++){
            listHours.add(i+"");
        }
        listTime=new ArrayList<>();
        for (int i=0;i<60;i++){
            listTime.add(i+"");
        }
    }

    //删除设备的按钮
    private void showWindow(int pos) {
        deleteId=pos;
        popX = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_device_delete, null);
        TextView pop_delete= (TextView) v.findViewById(R.id.pop_delete);
        TextView pop_cancle= (TextView) v.findViewById(R.id.pop_cancle);
        pop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(deleteId);
                adapter.notifyDataSetChanged();
                popX.dismiss();
            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popX.dismiss();
            }
        });
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.home_sence_setting_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        popX.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popX.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popX.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popX.setContentView(v);
        popX.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popX.setTouchable(true);
        popX.setFocusable(true);
        popX.setOutsideTouchable(true);

        popX.setAnimationStyle(R.style.popup2_anim);
        popX.showAtLocation(parent, Gravity.CENTER,0,0);
        popX.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }


    @Override
    public void getSerVerData() {

    }


    //提交情景设置http://192.168.1.55:8080/smarthome/mobileapi/scene/save.do?id=123&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public void sendSceneSetting(){
        if(CheckInput()){//检查信息是否完整
            HashMap<String,String>mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
//            mp.put("id",sceneData.getId()+"");
            mp.put("sceneName",home_sence_Add_rela_eidtext.getText().toString());
            if(list!=null&&list.size()>0){
                String gson=mGson.gson.toJson(list);
                Log.i("gson----",gson);
                mp.put("equipmentList",gson);
            }
            else {
                mp.put("equipmentList","");
            }
            mp.put("sceneModel",(SelectPositon+1)+"");
//            定时开启关闭，3=根据位置开启关闭
//                    |sceneTime             |String   |n    |定时开启关闭时间
//                    |repeatMode             |String   |n    |重复方式，空或0代表全部，1=周一，2=周二，等等，多个选项使用逗号分隔，如：1,2,3,4,5,6,7
//                    |sceneDistance             |String   |n    |根据位置开启关闭的距离
            switch (SelectPositon){
                case 0://手动启动

                    break;
                case 1://定时启动
                    if (!"".equals(sceneTime)&&!TextUtils.isEmpty(sceneTime)){
                        mp.put("sceneTime",sceneTime);
                        mp.put("repeatMode",repeatMode);
                    }
                    else {
                        mToast.Toast(con,"您还没有设置启动时间");
                        return;
                    }
                    break;
                case 2://定位启动
                    if (sceneDistance!=0L){
                        mp.put("sceneDistance",sceneDistance+"");
                    }
                    else {
                        mToast.Toast(con,"您还没有设置启动距离");
                        return;
                    }
                    break;
                default:
                    mToast.Toast(con,"信息错误");
                    Log.e("无法识别提交的启动模式","===HomeSenceSettingActivity======");
                    return;
            }
            mTools.getServerDataPost(handler,2, Ip.serverPath+Ip.interface_HomeScene_setScene,mp,"情景修改---");
        }
        else {
            mToast.Toast(con,"情景名称不能为空");
        }
    }

    private boolean CheckInput() {
        String name=home_sence_Add_rela_eidtext.getText().toString();
        if (!"".equals(name)&&!TextUtils.isEmpty(name)){//情景名称不为空
            return true;
        }
        return false;
    }

    public boolean isAll(){
        for (int i=0;i<listGridview.size()-1;i++){
            if ("0".equals(listGridview.get(i))){
                return false;
            }
        }
        return true;
    }
}
