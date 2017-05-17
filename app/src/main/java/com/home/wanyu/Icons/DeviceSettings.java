package com.home.wanyu.Icons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.home.wanyu.activity.DeviceSettingAirConActivity;
import com.home.wanyu.activity.DeviceSettingCurtainActivity;
import com.home.wanyu.activity.DeviceSettingLightActivity;
import com.home.wanyu.activity.DeviceSettingLockActivity;
import com.home.wanyu.activity.DeviceSettingSwitchActivity;
import com.home.wanyu.activity.DeviceSettingTvActivity;
import com.home.wanyu.bean.Bean_SceneAndRoom;

/**
 * Created by wanyu on 2017/5/9.
 */

public class DeviceSettings {
    //0=无设备
//    1=开关
//    2=灯
//    3=电视
//    4=窗帘
//    5=空调
//    6=门锁
//    NONE,SWITCH,LIGHT,TV,CURTAIN,AC,LOCK
    public static void moveToSettingActivity(Context context,Device IconId,String fmiId,Bean_SceneAndRoom.SceneListBean.EquipmentListBean bean,
                                             Bean_SceneAndRoom.RoomListBean.EquipmentListBeanX beanX,DeviceType type){
//        type=0:情景中的设备，type=1房间中的设备,,bean情景中获取的设备数据，beanX：房间中国年获取的设备信息
        if (IconId==Device.NONE){
            Toast.makeText(context,"不支持的设备类型",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable("scene0",bean);
        bundle.putSerializable("room1",beanX);
        if (type==DeviceType.RoomDevice1){
            bundle.putString("type","1");
        }
        else if (type==DeviceType.SceneDevice0){
                bundle.putString("type","0");
            }
        bundle.putSerializable("familyId",fmiId);
        intent.putExtra("data",bundle);
            switch (IconId){
//                case NONE:
//                    Toast.makeText(context,"不支持的设备类型",Toast.LENGTH_SHORT).show();
//                    break;
                case SWITCH://开
                    intent.setClass(context, DeviceSettingSwitchActivity.class);
                    context.startActivity(intent);
                    break;
                case LIGHT://灯
                    intent.setClass(context, DeviceSettingLightActivity.class);
                    context.startActivity(intent);
                    break;
                case TV://电视
//
                    intent.setClass(context, DeviceSettingTvActivity.class);
                    context.startActivity(intent);
//                    Toast.makeText(context,"电视机设置",Toast.LENGTH_SHORT).show();
                    break;
                case CURTAIN://窗帘
                    intent.setClass(context, DeviceSettingCurtainActivity.class);
                    context.startActivity(intent);
                    break;
                case AC://空调
                    intent.setClass(context, DeviceSettingAirConActivity.class);
                    context.startActivity(intent);
//                    Toast.makeText(context,"空调设置",Toast.LENGTH_SHORT).show();
                    break;
                case LOCK://门锁
                    intent.setClass(context, DeviceSettingLockActivity.class);
                    context.startActivity(intent);
//                    Toast.makeText(context,"门锁设置",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    public enum DeviceType{
        SceneDevice0,RoomDevice1;
    }
}
