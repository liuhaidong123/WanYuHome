package com.home.wanyu.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Icons.Device;
import com.home.wanyu.Icons.DeviceSettings;
import com.home.wanyu.Icons.icon;
import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_SceneAndRoom;
import com.home.wanyu.bean.Bean_setDeviceData;
import com.home.wanyu.lzhView.MyFloatingView;
import com.home.wanyu.lzhView.SwitchButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/5/2.
 */
public class HomeScenePagerItemListApdater extends BaseAdapter{
    private int currentPos;
    private Boolean isChange=false;//是否正在改变设备状态
    private List<Bean_SceneAndRoom.SceneListBean.EquipmentListBean>listEqui;
    private Context context;
//    private MyFloatingView floatingView;
private String fmiId;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isChange=false;
                    mToast.ToastFaild(context, ToastType.FAILD);
                    break;
                case 1:
                    isChange=false;
                    try{
                        Bean_setDeviceData deviceData= mGson.gson.fromJson(resStr,Bean_setDeviceData.class);
                        if (deviceData!=null){
                            if ("0".equals(deviceData.getCode())){
                                mToast.Toast(context,"设置成功");
                                if (deviceData.getEquipment().getState()==1){
                                    listEqui.get(currentPos).setState(1L);
                                }
                                else {
                                    listEqui.get(currentPos).setState(0L);
                                }
                                mToast.Toast(context,"操作成功");
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            mToast.ToastFaild(context,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(context,ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    public HomeScenePagerItemListApdater(List<Bean_SceneAndRoom.SceneListBean.EquipmentListBean>listEqui,Context context,String fmiId) {
        this.listEqui = listEqui;
        this.context = context;
        this.fmiId=fmiId;
//        this.floatingView=floatingView;
    }
    @Override
    public int getCount() {
        return listEqui==null?0:listEqui.size();
    }

    @Override
    public Object getItem(int position) {
        return listEqui.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.homescenepageritemlist_item,null);
            viewHodler=new ViewHodler();
            viewHodler.fragment_home_scene_viewpager_listitem_switch= (SwitchButton) convertView.findViewById(R.id.fragment_home_scene_viewpager_listitem_switch);
            viewHodler.fragment_home_scene_viewpager_listitem_imageSettings= (ImageView) convertView.findViewById(R.id.fragment_home_scene_viewpager_listitem_imageSettings);
            viewHodler.fragment_home_scene_viewpager_listitem_textVsetting= (TextView) convertView.findViewById(R.id.fragment_home_scene_viewpager_listitem_textVsetting);
            viewHodler.fragment_home_scene_viewpager_listitem_toplayout= (RelativeLayout) convertView.findViewById(R.id.fragment_home_scene_viewpager_listitem_toplayout);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.fragment_home_scene_viewpager_listitem_imageSettings.setImageResource(listEqui.get(position).getIconId()>=icon.mIconRes.length?R.mipmap.errorphoto:icon.mIconRes[listEqui.get(position).getIconId()]);
//        Picasso.with(context).load(Ip.imagePath+listEqui.get(position).getIconUrl()).error(R.mipmap.light).into(viewHodler.fragment_home_scene_viewpager_listitem_imageSettings);
//      viewHodler.fragment_home_scene_viewpager_listitem_imageSettings.setImageResource(Integer.parseInt(listEqui.get(position).get("url")));
        viewHodler.fragment_home_scene_viewpager_listitem_textVsetting.setText(listEqui.get(position).getName()==null|"".equals(listEqui.get(position).getName())?"未命名":listEqui.get(position).getName());
         Long state=listEqui.get(position).getState();
        if (state==1L){//关闭
            viewHodler.fragment_home_scene_viewpager_listitem_switch.setNorState(false);
        }
        else if (state==0L){//开启
            viewHodler.fragment_home_scene_viewpager_listitem_switch.setNorState(true);
        }
        viewHodler.fragment_home_scene_viewpager_listitem_switch.setTag(position);
        viewHodler.fragment_home_scene_viewpager_listitem_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
            @Override
            public void change(Boolean sta, String text, SwitchButton vi) {
                if (isChange){
                    return;
                }
                isChange=true;
                int pos= (int) vi.getTag();
                currentPos=pos;
                setDevice();
            }
        });
        viewHodler.fragment_home_scene_viewpager_listitem_toplayout.setTag(position);
        viewHodler.fragment_home_scene_viewpager_listitem_toplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                Device device=Device.getDevice(listEqui.get(pos).getIconId());
                DeviceSettings.moveToSettingActivity(context,device,fmiId,listEqui.get(pos),null, DeviceSettings.DeviceType.SceneDevice0);
            }
        });
        return convertView;
    }

    class ViewHodler{
        ImageView fragment_home_scene_viewpager_listitem_imageSettings;
        TextView fragment_home_scene_viewpager_listitem_textVsetting;
        SwitchButton fragment_home_scene_viewpager_listitem_switch;
        RelativeLayout fragment_home_scene_viewpager_listitem_toplayout;
    }

    public boolean getState(){

        boolean flag=true;
        for (int i=0;i<listEqui.size();i++){//0开启状态，1关闭状态
            if (listEqui.get(i).getState()==1L){
                flag=false;
            }
        }
        return flag;
    }


    //
//    http://192.168.1.55:8080/smarthome/mobileapi/equipment/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |id           |Long     |N    |编号
//    |name         |String   |N    |设备名称
//    |iconId       |Long     |Y    |设备图标编号
//    |iconUrl      |String   |Y    |设备图标URL
//    |state        |Integer  |N    |状态0=开1=关
//    |roomId       |Long     |N    |房间编号外键(可以为0，即不指定房间编号)
//    |serialNumber |String   |Y    |设备序列号唯一标识符
//    |extendState  |String   |Y    |扩展状态控制，如灯的亮度，空调的温度等
//    |familyId     |Long     |N    |家庭编号
    //设置单个设备的当前状态
    private void setDevice() {
        HashMap<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("id",listEqui.get(currentPos).getId()+"");
        mp.put("name",listEqui.get(currentPos).getName());
        mp.put("iconId",listEqui.get(currentPos).getIconId()+"");
        mp.put("iconUrl",listEqui.get(currentPos).getIconUrl());
        String state="";
        if (listEqui.get(currentPos).getState()==1L){
            state="0";
        }
        else if (listEqui.get(currentPos).getState()==0L){
            state="1";
        }
        mp.put("state",state);
        mp.put("roomId",listEqui.get(currentPos).getRoomId()+"");
        mp.put("serialNumber",listEqui.get(currentPos).getSerialNumber());
        mp.put("extendState",listEqui.get(currentPos).getToExtendState());
        mp.put("familyId",fmiId);
        okhttp.getCall(Ip.serverPath+Ip.interface_HomeScene_setDevice,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("房间中单个设备状态设置---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

}
