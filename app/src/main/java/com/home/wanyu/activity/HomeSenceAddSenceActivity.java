package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeSceneAddListAdapter;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.adapter.PopDataGridViewAdapter;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.MyToast;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSenceAddSenceActivity extends MyActivity {
    @BindView(R.id.home_sence_Add_rela_eidtext)EditText home_sence_Add_rela_eidtext;//情景名称

    @BindView(R.id.home_sence_Add_rela_condition_textVname)TextView home_sence_Add_rela_condition_textVname;//显示启动条件的textview
    @BindView(R.id.home_sence_Add_rela_condition_imageselect)ImageView home_sence_Add_rela_condition_imageselect;//下拉，收起的imageview
    @BindView(R.id.home_sence_Add_rela_condition_relaLayout)RelativeLayout home_sence_Add_rela_condition_relaLayout;//启动条件所在的layout布局

    @BindView(R.id.home_senceAdd_listview)MyListView home_senceAdd_listview;//显示设备的listview
    private PopupWindow pop;
    private ArrayList<Map<String,String>> list;
    private String[]title={"客厅灯","电视","客厅插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    private HomeSceneAddListAdapter adapter;
    private String[]mode={"一键启动","定时启动","定位启动"};
    private int SelectPositon=0;//当前选中的启动方式对应mode中的下标

    private List<Map<String,String>> listGridview;//定时启动弹出窗口中的gridview的数据源
    private String[]str={"周一","周二","周三","周四","周五","周六","周日","全部",};
    PopDataGridViewAdapter adapterGridView;
    private List<String>listHours;//轮滚的小时数据源
    private List<String>listTime;//轮滚的分钟数据源
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_sence_add_sence);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("添加情景");
        ShowChildView(DEFAULTRESID);
        initData();
    }
    @OnClick({R.id.activity_homeSceneSetting_rela_sence_add,R.id.activity_homeSceneAdd_listitem_relaAdd_submit,R.id.home_sence_Add_rela_condition_relaLayout})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_homeSceneSetting_rela_sence_add://添加设备的按钮
                MyToast.DebugToast(con,"添加设备");
                break;
            case R.id.activity_homeSceneAdd_listitem_relaAdd_submit://提交按钮
                MyToast.DebugToast(con,"确定");
                break;
            case R.id.home_sence_Add_rela_condition_relaLayout://显示可选的启动方式
                home_sence_Add_rela_condition_imageselect.setSelected(true);
                showWindowMode();//显示选择启动方式的view
                break;
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
        PopupWindow popW=new PopupWindow();
        popW = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesenceasetting_address, null);

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
    //定时启动的view
    private void showWindowData(){
        PopupWindow popW=new PopupWindow();
        popW = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesencesetting_data, null);
        WheelView pop_wheelview_data= (WheelView) v.findViewById(R.id.pop_wheelview_data);//小时
        WheelView pop_wheelview_time= (WheelView) v.findViewById(R.id.pop_wheelview_time);//分钟
        pop_wheelview_data.setViewAdapter(new MyWheelAdapter(con,listHours));
        pop_wheelview_time.setViewAdapter(new MyWheelAdapter(con,listTime));
        pop_wheelview_time.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(con,listTime.get(newValue),Toast.LENGTH_SHORT).show();
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
                }
              else {
                    for (int i=0;i<listGridview.size()-1;i++){
                        listGridview.get(i).put("select","1");
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
        int size=title.length;
        for (int i=0;i<size;i++){
            Map<String,String>m=new HashMap<>();
            m.put("title",title[i]);
            m.put("url",url[i]+"");
            m.put("state","0");
            list.add(m);
        }
        adapter = new HomeSceneAddListAdapter(list,con);
        home_senceAdd_listview.setAdapter(adapter);


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
            listHours.add(i+"时");
        }
        listTime=new ArrayList<>();
        for (int i=0;i<60;i++){
            listTime.add(i+"分");
        }
    }
    @Override
    public void getSerVerData() {

    }
}
