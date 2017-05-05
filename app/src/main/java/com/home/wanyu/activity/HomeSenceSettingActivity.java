package com.home.wanyu.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeDevicePagerItemListApdater;
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
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/4.
 */
//情景设置的activity
//    intent.putExtra("name",SceneName);
public class HomeSenceSettingActivity extends MyActivity{
    @BindView(R.id.home_sence_scene_rela_eidtext) EditText home_sence_scene_rela_eidtext;
    @BindView(R.id.home_sence_scene_rela_condition_imageselect)ImageView home_sence_scene_rela_condition_imageselect;
    @BindView(R.id.home_sence_listview)MyListView home_sence_listview;
    private ArrayList<Map<String,String>> list;
    private String[]title={"客厅灯","电视","客厅插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    private HomeSceneSettingListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_sence_setting);
        unbinder=ButterKnife.bind(this,ChildView);
        setTitle("情景设置");
        ShowChildView(DEFAULTRESID);
        initView();
        initData();
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
        adapter = new HomeSceneSettingListAdapter(list,HomeSenceSettingActivity.this);
        home_sence_listview.setAdapter(adapter);
    }

    private void initView() {
        String name=getIntent().getStringExtra("name");
        home_sence_scene_rela_eidtext.setText(name);
    }
    @OnClick({R.id.home_sence_scene_rela_condition_relaLayout,R.id.activity_homeSceneSetting_rela_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.home_sence_scene_rela_condition_relaLayout:
                home_sence_scene_rela_condition_imageselect.setSelected(!home_sence_scene_rela_condition_imageselect.isSelected());
                break;
            case R.id.activity_homeSceneSetting_rela_add:
                MyToast.DebugToast(con,"添加设备");
                break;
        }
        }


    //初始化网络请求
    @Override
    public void getSerVerData() {

    }

}
