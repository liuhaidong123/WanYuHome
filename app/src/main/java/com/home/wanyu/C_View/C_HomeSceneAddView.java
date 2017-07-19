package com.home.wanyu.C_View;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.adapter.PopDataGridViewAdapter;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.mEmeu.SceneStartMode;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.myview.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/7/5.
 */
//添加情景
public class C_HomeSceneAddView {
    final String DEFAULT="-1";//默认的接口SceneModel中的参数值
    PopupSettings popupSettings;
    CMyActivity activity;
    Unbinder unbinder;
    PopupWindow pop;//选择启动方式的pop
    //-----定时启动
    PopupWindow popW;//定时启动
    String repeatMode="";//重复的shijian
    int currentTimeHour=0;//小时
    int  currentTimeMinus=0;//分钟
    PopDataGridViewAdapter adapterGridView;
    private List<String> listHours;//轮滚的小时数据源
    private List<String>listTime;//轮滚的分钟数据源
    private List<Map<String,String>> listGridview;//定时启动弹出窗口中的gridview的数据源
    String[]str; // @BindArray(R.array.week)
    //---定时启动
    //------定位启动
    PopupWindow popWx;
    String[]listKm;//距离定位中的千米@BindArray(R.array.KM)
    private int currentKm;//当前定位选中的千米数
    String[]listM;//距离定位中@BindArray(R.array.M)
    private int currentM;//当前定位选中的米数
    private int sceneDistance;//定位启动的距离
    //------定位启动
    SceneModelSelect modelSelect;//启动方式选择后回掉的类
    //view
    ImageView c_homescenesetting_imageSelect;//图片选择
    EditText home_sence_scene_rela_eidtext;//情景名称
    TextView home_sence_scene_rela_condition_textv_name;//启动方式名称
    ImageView home_sence_scene_rela_condition_imageselect;//下啦的view（下啦状态 select=true）   //view
    TextView titleTextv;
    TextView btnTextView;
    MyGridView home_sence_listview;
    private List<Bean_getSceneData.EquipmentListBean> list;
    private HomeSceneSettingListAdapter adapter;
    //设备删除
    PopupWindow popX;
    int deleteId;
    public C_HomeSceneAddView(CMyActivity act,SceneModelSelect modelSelect){
        this.activity=act;
        this.modelSelect=modelSelect;
        popupSettings=PopupSettings.getInstance();
        initView();
        initData();
    }
    //初始化view
    private void initView() {
        titleTextv= (TextView) activity.findViewById(R.id.titleTextView);
        titleTextv.setText("添加情景");
        btnTextView= (TextView) activity.findViewById(R.id.titleButton);
        btnTextView.setText("确定");
        c_homescenesetting_imageSelect= (ImageView) activity.findViewById(R.id.c_homescenesetting_imageSelect);
        home_sence_scene_rela_eidtext= (EditText) activity.findViewById(R.id.home_sence_scene_rela_eidtext);
        home_sence_scene_rela_condition_textv_name= (TextView) activity.findViewById(R.id.home_sence_scene_rela_condition_textv_name);
        home_sence_scene_rela_condition_imageselect= (ImageView) activity.findViewById(R.id.home_sence_scene_rela_condition_imageselect);
        str=activity.getResources().getStringArray(R.array.week);
        listKm=activity.getResources().getStringArray(R.array.KM);
        listM=activity.getResources().getStringArray(R.array.M);


        home_sence_listview= (MyGridView) activity.findViewById(R.id.home_sence_listview);
        adapter=new HomeSceneSettingListAdapter(list,activity);
        home_sence_listview.setAdapter(adapter);
        home_sence_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position!=adapter.getCount()-1){
                    showWindow(position);
                }
                else {
                    addDevice();//添加设备
                    mToast.Toast(activity,""+position);
                }
                return false;
            }
        });
    }

    //添加设备
    private void addDevice() {

    }
    //初始化定时启动,定位启动中用到的一些数据源
    private void initData() {
        listGridview=new ArrayList<>();
        int length=str.length;
        for (int i=0;i<length;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("name",str[i]);
            mp.put("select","0");
            listGridview.add(mp);
        }
        adapterGridView=new PopDataGridViewAdapter(listGridview,activity);
        listHours=new ArrayList<>();
        for (int i=0;i<24;i++){
            listHours.add(i+"");
        }
        listTime=new ArrayList<>();
        for (int i=0;i<60;i++){
            listTime.add(i+"");
        }
    }

    //选择情景启动方式
    public void showWindowMode() {
        c_homescenesetting_imageSelect.setSelected(true);
        pop = new PopupWindow();
        View v = LayoutInflater.from(activity).inflate(R.layout.pop_homesenceaddsetting, null);
        TextView pop_text1= (TextView) v.findViewById(R.id.pop_text1);//一键启动
        TextView pop_text2= (TextView) v.findViewById(R.id.pop_text2);//定时启动
        TextView pop_text3= (TextView) v.findViewById(R.id.pop_text3);//定位启动
        //一键启动
        pop_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                selectSetting(SceneStartMode.BUTTON);
                modelSelect.modelSelect(SceneStartMode.BUTTON,DEFAULT,DEFAULT,DEFAULT,DEFAULT);
            }
        });
        //定时启动
        pop_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//定时启动
                pop.dismiss();
                selectSetting(SceneStartMode.TIME);
                modelSelect.modelSelect(SceneStartMode.TIME,DEFAULT,DEFAULT,DEFAULT,DEFAULT);
                showWindowData();
            }
        });
        //定位启动
        pop_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//定位启动
                pop.dismiss();
                selectSetting(SceneStartMode.LOCATION);
                modelSelect.modelSelect(SceneStartMode.LOCATION,DEFAULT,DEFAULT,DEFAULT,DEFAULT);
                showWindowAddress();
            }
        });
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.home_sence_scene_rela_condition_relaLayout_c);
        popupSettings.windowBottomDownSet(pop,parent,activity,v);
    }
    //定时启动的
    private void showWindowData(){
        repeatMode="";
        currentTimeHour=0;
        currentTimeMinus=0;
        popW=new PopupWindow();
        View v = LayoutInflater.from(activity).inflate(R.layout.pop_homesencesetting_data, null);
        final TextView pop_data_submit= (TextView) v.findViewById(R.id.pop_data_submit);
        //提交设置
        pop_data_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popW.dismiss();
                modelSelect.modelSelect(SceneStartMode.TIME,currentTimeHour+"",currentTimeMinus+"",getRepeatTime(),DEFAULT);
            }
        });
        WheelView pop_wheelview_data= (WheelView) v.findViewById(R.id.pop_wheelview_data);//小时
        pop_wheelview_data.setTitle("点");
        WheelView pop_wheelview_time= (WheelView) v.findViewById(R.id.pop_wheelview_time);//分钟
        pop_wheelview_time.setTitle("分");
        pop_wheelview_data.setViewAdapter(new MyWheelAdapter(activity,listHours));
        pop_wheelview_time.setViewAdapter(new MyWheelAdapter(activity,listTime));
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
                Toast.makeText(activity,listTime.get(newValue),Toast.LENGTH_SHORT).show();
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

                }
                adapterGridView.notifyDataSetChanged();
            }
        });
        LinearLayout parent = (LinearLayout) activity.findViewById(R.id.c_homescene_settinglayout);
        popupSettings.windowBottomUpSet(popW,parent,activity,v);
    }
    //定位启动
    private void showWindowAddress() {
        sceneDistance=0;
        currentKm=0;
        currentM=0;
        popWx = new PopupWindow();
        View v = LayoutInflater.from(activity).inflate(R.layout.pop_homesenceasetting_address, null);

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
                modelSelect.modelSelect(SceneStartMode.LOCATION,DEFAULT,DEFAULT,DEFAULT,sceneDistance+"");
            }
        });
        WheelView pop_wheelview_address_M= (WheelView) v.findViewById(R.id.pop_wheelview_address_M);
        pop_wheelview_address_M.setTitle("m");
        WheelView pop_wheelview_address_KM= (WheelView) v.findViewById(R.id.pop_wheelview_address_KM);
        pop_wheelview_address_KM.setTitle("km");
        pop_wheelview_address_KM.setViewAdapter(new MyWheelViewAdapterArray(activity,listKm));
        pop_wheelview_address_M.setViewAdapter(new MyWheelViewAdapterArray(activity,listM));
        pop_wheelview_address_KM.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(activity,listKm[newValue],Toast.LENGTH_SHORT).show();
                currentKm=Integer.parseInt(listKm[newValue]);
            }
        });
        pop_wheelview_address_M.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(activity,listM[newValue],Toast.LENGTH_SHORT).show();
                currentM=Integer.parseInt(listM[newValue]);
            }
        });

        LinearLayout parent = (LinearLayout) activity.findViewById(R.id.c_homescene_settinglayout);
        popupSettings.windowBottomUpSet(popWx,parent,activity,v);
    }

    //删除设备的按钮
    private void showWindow(int pos) {
        deleteId=pos;
        popX = new PopupWindow();
        View v = LayoutInflater.from(activity).inflate(R.layout.pop_device_delete, null);
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
        LinearLayout parent = (LinearLayout)  activity.findViewById(R.id.c_homescene_settinglayout);
        popupSettings.windowActivityCenter(popX,parent,activity,v);
    }

    //选择启动方式后填写启动方式的名字等操作处理
    public void selectSetting(SceneStartMode mo){
        c_homescenesetting_imageSelect.setSelected(false);
        home_sence_scene_rela_condition_textv_name.setText(mo.getModelName(mo));
    }
    //是否全部重复定时启动的弹窗）
    public boolean isAll(){
        for (int i=0;i<listGridview.size()-1;i++){
            if ("0".equals(listGridview.get(i))){
                return false;
            }
        }
        return true;
    }
    //获取到重复的时间中间用，分割，（0,1，2，3，4，5，6）0不重复，1-7对应周一到周日
    public String getRepeatTime(){
        String repeat="";
        int size=listGridview.size();
        for (int i=0;i<size-1;i++){
            if ("1".equals(listGridview.get(i).get("select"))){
                repeat+=((i+1)+",");
            }
        }
        if (!"".equals(repeat)&&!TextUtils.isEmpty(repeat)){
            repeat=repeat.substring(0,repeat.length()-1);
            return repeat;
        }
        return "0";
    }
    /**
     * 启动方式选择后的回调
     * model：启动方式BUTTON，TIME，LOCATION，UNKONE
     *一键启动方式下其余四个参数的值均为-1
     * TIME（定时启动方式下）参数hours，minute表示重复的小时与分钟，参数repeat表示重复的天数（周一，周二，周三，周四，周五，周六，周末等），location参数为-1
     * LOCATION启动方式下参数hours，minute，repeat均为-1，参数location表示启动的距离
     * */
    public interface SceneModelSelect{
        void modelSelect(SceneStartMode mode,String hours,String Minute,String repeat,String location);
    }
}
