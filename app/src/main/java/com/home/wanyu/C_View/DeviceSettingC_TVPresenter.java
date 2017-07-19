package com.home.wanyu.C_View;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.activity.DeviceSettingC_TVActivity;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/7/12.
 */

public class DeviceSettingC_TVPresenter implements View.OnClickListener{
    DeviceSettingC_TVActivity activity;
    TvSetting tvSetting;


    int SelectHourPos=0;
    int SelectMiniPos=0;
    private List<String> listHour;
    private List<String>listMini;
    PopupWindow popupTime;
    String tvState;//当前的开启／关闭状态
    public DeviceSettingC_TVPresenter(DeviceSettingC_TVActivity activity,TvSetting tvSetting){
        this.activity=activity;
        this.tvSetting=tvSetting;
        listHour=new ArrayList<>();
        listMini=new ArrayList<>();
        for (int i=0;i<24;i++){
            listHour.add(i+"");
        }
        for (int i=1;i<60;i++){
            listMini.add(i+"");
        }
    }
    //台数调节
    public void setTV(int max,int min ,int curent,TvType type){
        switch (type){
            case ADD://台数加(向后)
                if (curent!=max){
                    tvSetting.setTV(curent+1);
                }
                else {
                    tvSetting.setTV(min);
                }
                break;
            case MINUS://台数减（向前）
                if (curent!=min){
                    tvSetting.setTV(curent-1);
                }
                else {
                    tvSetting.setTV(max);
                    }
                break;
        }
    }
    //音量调节
    public void setVoice(int max,int min,int current,VoiceType type){
        switch (type){
            case ADD:
                if (current!=max){
                    tvSetting.setVoice(current+1);
                }
                else {
                    tvSetting.setVoice(max);
                }
                break;
            case MINUS:
                if (current!=min){
                    tvSetting.setVoice(current-1);
                }
                else {
                    tvSetting.setVoice(min);
                }
                break;
        }
    }
    //定时选择state：0当前处于关闭状态，1当前处于开启状态
    public void showWindTimeSelect(int state){
        tvState="";
        switch (state){
            case 0://当前处于关闭状态，需要的是定时开启
                tvState="开启";
                break;
            case 1://当前处于开启状态，需要的是定时关闭
                tvState="关闭";
                break;
        }
        if (popupTime==null){
            popupTime=new PopupWindow();
        }
        SelectHourPos=0;
        SelectMiniPos=0;
        //定时控制
        View air_timesetting= LayoutInflater.from(activity).inflate(R.layout.air_timesetting,null);
        TextView air_setting_windtime_cancle= (TextView) air_timesetting.findViewById(R.id.air_setting_windtime_cancle);
        air_setting_windtime_cancle.setOnClickListener(this);
        TextView air_timesetting_submit= (TextView) air_timesetting.findViewById(R.id.air_timesetting_submit);
        air_timesetting_submit.setOnClickListener(this);
        final TextView air_timesetting_textv= (TextView) air_timesetting.findViewById(R.id.air_timesetting_textv);
        air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时"+listMini.get(SelectMiniPos)+"分钟后将"+tvState);
        WheelView air_timesetting_wheelHour= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelHour);
        air_timesetting_wheelHour.setTitle("小时");
        air_timesetting_wheelHour.setViewAdapter(new MyWheelAdapter50(activity,listHour,"小时"));
        air_timesetting_wheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectHourPos=newValue;
                Toast.makeText(activity,listHour.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时"+listMini.get(SelectMiniPos)+"分钟后将"+tvState);
            }
        });
        WheelView  air_timesetting_wheelMini= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelMini);
        air_timesetting_wheelMini.setTitle("分钟");
        air_timesetting_wheelMini.setViewAdapter(new MyWheelAdapter50(activity,listMini,"分钟"));
        air_timesetting_wheelMini.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectMiniPos=newValue;
                Toast.makeText(activity,listMini.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时"+listMini.get(SelectMiniPos)+"分钟后将"+tvState);
            }
        });

        View parent=activity.findViewById(R.id.activity_device_setting_c__tv);
        PopupSettings.getInstance().windowBottomUpSet(popupTime,parent,activity,air_timesetting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.air_setting_windtime_cancle:
                popupTime.dismiss();
                break;
            case R.id.air_timesetting_submit:
                popupTime.dismiss();
                tvSetting.setTiming(Integer.parseInt(listHour.get(SelectHourPos)),Integer.parseInt(listMini.get(SelectMiniPos)));
                break;
        }
    }


    public interface TvSetting{
        void setTV(int current);//台数向前／向后操作，current：返回当前的电台
        void setVoice(int current);//音量加减曹祖欧，current:当前音量
        void setTiming(int hours,int minute);//定时操作，hours定时的小时数，minute定时的分钟数
    }

    public enum TvType{
        ADD,MINUS;
    }
    public enum VoiceType{
        ADD,MINUS;
    }
}
