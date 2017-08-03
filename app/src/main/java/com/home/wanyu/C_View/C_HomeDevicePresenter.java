package com.home.wanyu.C_View;

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
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.activity.C_HomeRoomSettingActivity;
import com.home.wanyu.activity.C_HomeSceneSettingActivity;
import com.home.wanyu.adapter.C_HomeRoom_PopListAdapter;
import com.home.wanyu.adapter.C_HomeScene_PopListAdapter;
import com.home.wanyu.lzhUtils.ListVIewHeightSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/7/25.
 */

public class C_HomeDevicePresenter implements View.OnClickListener,AdapterView.OnItemClickListener{
    Activity activity;
    IAddRoom iAddRoom;
    PopupWindow pop;
    //情景弹窗
    ListView c_homescene_pop_listView;//情景的listview
    TextView c_homescene_pop_addScene;//添加新情景
    TextView c_homescene_pop_cancle;//取消按钮
    C_HomeRoom_PopListAdapter sceneAdapter;//情景弹窗的适配器
    //一下数据测试用
    String[]sceneName={"客厅","卧室","LIU房间","厨房"};
    private List<String> listRooms;
    //--------------
    //情景弹窗
    public C_HomeDevicePresenter(Activity activity,IAddRoom iAddRoom){
        this.activity=activity;
        this.iAddRoom=iAddRoom;
        listRooms=new ArrayList<>();
        for (int i=0;i<sceneName.length;i++){
            listRooms.add(sceneName[i]);
        }
    }

    //切换房间
    public void ShowSceneSettingWindow(){
        if (pop==null){
            pop=new PopupWindow();
        }
        View v = LayoutInflater.from(activity).inflate(R.layout.c_pop_homescene, null);
        RelativeLayout c_homescene_pop_toplayout= (RelativeLayout) v.findViewById(R.id.c_homescene_pop_toplayout);
        TextView c_home_fg1= (TextView) v.findViewById(R.id.c_home_fg1);
        c_homescene_pop_listView= (ListView) v.findViewById(R.id.c_homescene_pop_listView);
        c_homescene_pop_addScene= (TextView) v.findViewById(R.id.c_homescene_pop_addScene);
        c_homescene_pop_addScene.setText("添加新房间");
        c_homescene_pop_addScene.setOnClickListener(this);
        c_homescene_pop_cancle= (TextView) v.findViewById(R.id.c_homescene_pop_cancle);
        c_homescene_pop_cancle.setOnClickListener(this);
        sceneAdapter=new C_HomeRoom_PopListAdapter(activity,listRooms);
        c_homescene_pop_listView.setAdapter(sceneAdapter);
        c_homescene_pop_listView.setOnItemClickListener(this);
        View parentView=activity.findViewById(R.id.activity_main);
        PopupSettings.getInstance().windowBottomUpSet(pop,parentView,activity,v);

        ViewGroup.LayoutParams params=c_homescene_pop_toplayout.getLayoutParams();
        ViewGroup.LayoutParams par1=c_homescene_pop_addScene.getLayoutParams();
        if (sceneAdapter!=null){
            if (sceneAdapter.getCount()<3){
                params.height=par1.height+ ListVIewHeightSetting.getListViewHeight(c_homescene_pop_listView)+
                        2+(c_homescene_pop_listView.getDividerHeight() * (c_homescene_pop_listView.getCount() - 1));
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
//        mToast.Toast(activity,"-------");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.c_homescene_pop_addScene://添加新房间
                iAddRoom.addNewRoom();
                break;
            case R.id.c_homescene_pop_cancle:
                pop.dismiss();
                break;
        }
    }

    public interface IAddRoom{
        void addNewRoom();//添加房间
        void changeRoom(String roomName);//切换房间
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.c_homescene_pop_listView:
                mToast.Toast(activity,"当前:"+position);
                iAddRoom.changeRoom(listRooms.get(position));
                pop.dismiss();
                break;
        }
    }
}
