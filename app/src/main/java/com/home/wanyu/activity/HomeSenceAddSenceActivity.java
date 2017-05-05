package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeSceneAddListAdapter;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.MyToast;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
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
                showWindowMode();//显示选择启动方式的view
                break;
        }
        }

    private void showWindowMode() {

        pop = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_homesenceaddsetting, null);

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.home_sence_Add_rela_condition_relaLayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.8f;
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
        pop.showAsDropDown(parent, 0, 20);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
    }
    @Override
    public void getSerVerData() {

    }
}
