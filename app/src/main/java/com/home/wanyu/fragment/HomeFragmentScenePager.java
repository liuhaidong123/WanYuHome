package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Icons.Device;
import com.home.wanyu.Icons.DeviceSettings;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.HomeSenceSettingActivity;
import com.home.wanyu.adapter.HomeDevicePagerItemListApdater;
import com.home.wanyu.adapter.HomeScenePagerItemListApdater;
import com.home.wanyu.bean.Bean_HomeSceneOnOff;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.lzhView.MyFloatingView;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.id.list;

/**
 * Created by wanyu on 2017/5/2.
 */
//情景的viewpager中的frgment
public class HomeFragmentScenePager extends Fragment implements HomeFragmentScene.SceneData{
//    private static int count=0;
    Unbinder unbinder;
    private String SceneName;//当前的情景名称
    private int pos;//当前的情景的position
    @BindView(R.id.fragment_home_scene_viewpager_item_MylistView)ListView myListView;
    @BindView(R.id.fragment_home_scene_viewpager_item_MyFloating) MyFloatingView floatingView;
    private HomeScenePagerItemListApdater adapter;
//    private ArrayList<Map<String,String>>list;
    private String[]title={"客厅灯","电视","卫生间插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};

    private Bean_SceneAndRoom.SceneListBean listBean;
    private List<Bean_SceneAndRoom.SceneListBean.EquipmentListBean>listEqui;
    private Boolean isOnORoff=false;//当前是否正在进行一键开启／关闭操作

    private okhttpTools tools;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isOnORoff=false;
                    mToast.ToastFaild(getActivity(),ToastType.FAILD);
                    break;
                case 1:
                    isOnORoff=false;
                    try{
                        Bean_HomeSceneOnOff onOff= mGson.gson.fromJson(tools.mResponStr,Bean_HomeSceneOnOff.class);
                        if (onOff!=null){
                            if ("0".equals(onOff.getCode())){
                                    if (onOff.getScene().getSceneState()==1L){
                                        mToast.Toast(getActivity(),"情景关闭成功");
                                        floatingView.setOnAndOff(false);
                                    }
                                else if (onOff.getScene().getSceneState()==0L){
                                        floatingView.setOnAndOff(true);
                                        mToast.Toast(getActivity(),"情景开启成功");
                                    }
                                }
                            else {
                                ServerCode.showResponseMsg(getActivity(),onOff.getCode());
                                }
                        }
                        else {
                           mToast.ToastFaild(getActivity(),ToastType.GSONEMPTY);
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(getActivity(), ToastType.GSONFAILD);
                    }

                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home_scene_pager_fragment,null);
        unbinder=ButterKnife.bind(this,vi);
        initData();
        tools=new okhttpTools();
        floatingView.setClick(new MyFloatingView.ClickListener() {
            @Override
            public void click(boolean flag) {
                if (flag){
                    //                    http://192.168.1.55:8080/smarthome/mobileapi/scene/scenectrl.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
                    if (listBean==null){
                        return;
                    }
                    if (isOnORoff==true){
                        Toast.makeText(getActivity(),"正在操作，请稍后",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isOnORoff=true;
                    HashMap<String,String>mp=new HashMap<>();
                    mp.put("id",listBean.getId()+"");
                    mp.put("token", UserInfo.userToken);
                    tools.getServerData(handler,1, Ip.serverPath+Ip.interface_HomeScene_OpenAndOn,mp,"情景一键开启---");
                }
            }
        });
        return vi;
    }

        @OnClick({R.id.fragment_home_scene_viewpager_item_MyFloating,R.id.fragment_home_scene_viewpager_item_toplayout})
        public void click(View vi){
            switch (vi.getId()){
//                case R.id.fragment_home_scene_viewpager_item_MyFloating:
//
//                    break;
                case R.id.fragment_home_scene_viewpager_item_toplayout:
                    Intent intent=new Intent(getActivity(),HomeSenceSettingActivity.class);
                    intent.putExtra("id",listBean.getId()+"");
                    intent.putExtra("name",SceneName);
                    startActivity(intent);
                    break;
            }
        }

    private void initData() {
        if (listBean!=null){
            listEqui=listBean.getEquipmentList();
            SceneName=listBean.getSceneName();
//            list=new ArrayList<>();
            if (listEqui!=null&&listEqui.size()>0){
                floatingView.setVisibility(View.VISIBLE);
                adapter = new HomeScenePagerItemListApdater(listEqui, getActivity(),listBean.getFamilyId()+"");
                myListView.setAdapter(adapter);
                Long state=listBean.getSceneState();
                floatingView.setVisibility(View.VISIBLE);
                if (state==0L){//开启状态
                    floatingView.setOnAndOff(true);
                }
                else if (state==1L){
                    floatingView.setOnAndOff(false);
                }
            }
        }
        else {
            floatingView.setVisibility(View.GONE);
            }
    }
    //情景名称,id
    public void setSceneName(String title,int position){
        SceneName=title;
        pos=position;
    }

    @Override
    public void sendMsg(Object msg) {
        if (msg!=null){
            listBean= (Bean_SceneAndRoom.SceneListBean) msg;
//            initData();
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void setOnAndOff(){
        boolean state=floatingView.getState();
        floatingView.setOnAndOff(!state);
        if (listEqui!=null&&listEqui.size()>0){
            int size=listEqui.size();
            for (int i=0;i<size;i++){
                if (state){//0kaiqi,1关闭
                    listEqui.get(i).setState(1L);
                }
                else {
                    listEqui.get(i).setState(0L);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
