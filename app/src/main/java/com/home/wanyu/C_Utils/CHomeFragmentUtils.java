package com.home.wanyu.C_Utils;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.activity.HomeSenceSettingActivity;
import com.home.wanyu.adapter.C_HomeScene_PopListAdapter;
import com.home.wanyu.fragment.C_HomeFragment;
import com.home.wanyu.lzhUtils.ListVIewHeightSetting;

/**
 * Created by wanyu on 2017/7/3.
 */
//主页面相关utils
public class CHomeFragmentUtils implements View.OnClickListener,AdapterView.OnItemClickListener{
    PopupWindow popScene;//情景弹窗
    PopupSettings popupSettings;
    View parentView;//弹出窗口依附的view（当前底部弹窗用到的）
    CHomeFragmentUtils utils;
    Activity con;
    C_HomeFragment frag;
    //情景弹窗
    ListView c_homescene_pop_listView;//情景的listview
    TextView c_homescene_pop_addScene;//添加新情景
    TextView c_homescene_pop_cancle;//取消按钮
    C_HomeScene_PopListAdapter sceneAdapter;//情景弹窗的适配器
    //情景弹窗
    public CHomeFragmentUtils(C_HomeFragment frag){
        this.frag=frag;
        con=frag.getActivity();
        popupSettings=PopupSettings.getInstance();
        parentView= con.findViewById(R.id.activity_main);
    }
    //弹出情景编辑窗口
    public void ShowSceneSettingWindow(){
            if (popScene==null){
                popScene=new PopupWindow();
            }
            View v = LayoutInflater.from(con).inflate(R.layout.c_pop_homescene, null);
            RelativeLayout c_homescene_pop_toplayout= (RelativeLayout) v.findViewById(R.id.c_homescene_pop_toplayout);
            TextView c_home_fg1= (TextView) v.findViewById(R.id.c_home_fg1);
            c_homescene_pop_listView= (ListView) v.findViewById(R.id.c_homescene_pop_listView);
            c_homescene_pop_addScene= (TextView) v.findViewById(R.id.c_homescene_pop_addScene);
            c_homescene_pop_addScene.setOnClickListener(this);
            c_homescene_pop_cancle= (TextView) v.findViewById(R.id.c_homescene_pop_cancle);
            c_homescene_pop_cancle.setOnClickListener(this);
            sceneAdapter=new C_HomeScene_PopListAdapter(con);
            c_homescene_pop_listView.setAdapter(sceneAdapter);
            c_homescene_pop_listView.setOnItemClickListener(this);
            popupSettings.windowBottomSet(popScene,parentView,con,v);
            ViewGroup.LayoutParams params=c_homescene_pop_toplayout.getLayoutParams();
            ViewGroup.LayoutParams par1=c_homescene_pop_addScene.getLayoutParams();
            if (sceneAdapter!=null){
               if (sceneAdapter.getCount()<3){
                   params.height=par1.height+ListVIewHeightSetting.getListViewHeight(c_homescene_pop_listView)+2+(c_homescene_pop_listView.getDividerHeight() * (c_homescene_pop_listView.getCount() - 1));
                    if (sceneAdapter.getCount()==0){
                        c_home_fg1.setVisibility(View.GONE);
                    }
               }
            }
        else {
                params.height=par1.height;
                c_home_fg1.setVisibility(View.GONE);
            }
        c_homescene_pop_toplayout.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.c_homescene_pop_addScene://添加新情景
                frag.addSceneOrDevice();
                break;
            case R.id.c_homescene_pop_cancle:
                popScene.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mToast.Toast(con,"当前:"+position);
        con.startActivity(new Intent(con, HomeSenceSettingActivity.class));
    }

    public interface iCHomeFragmentUtils{
        void addSceneOrDevice();//添加情景或者设备（根据当前的CHomeFragment中的select判断）
    }
}
