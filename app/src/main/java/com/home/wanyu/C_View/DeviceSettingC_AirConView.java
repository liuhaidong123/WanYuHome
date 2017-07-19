package com.home.wanyu.C_View;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.activity.DeviceSettingC_AirConActivity;
import com.home.wanyu.adapter.LockShareListAdapter;
import com.home.wanyu.lzhView.SeekBar;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.mEmeu.AirModel;
import com.home.wanyu.mEmeu.AirWinSpeed;
import com.home.wanyu.mEmeu.AirWindDirection;


import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/7/10.
 */
//空调设置
public class DeviceSettingC_AirConView implements View.OnClickListener,SeekBar.SeekListener{
    DeviceSettingC_AirConView_WindowUtils windowUtils;//控制弹窗

    DeviceSettingC_AirConActivity activity;
    WindowSelect windowSelect;
    PopupWindow popModel,popWindSpeed,popWindDirection,popupTime;

    int SelectHourPos=0;
    int SelectMiniPos=0;
    private List<String> listHour;
    private List<String>listMini;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                if (popWindSpeed!=null){
                    popWindSpeed.dismiss();
                }
            }
        }
    };
    public DeviceSettingC_AirConView(DeviceSettingC_AirConActivity activity,WindowSelect windowSelect){
        this.activity=activity;
        this.windowSelect=windowSelect;//弹窗操作回调
        listHour=new ArrayList<>();
        listMini=new ArrayList<>();
        for (int i=0;i<24;i++){
            listHour.add(i+"");
        }
        for (int i=1;i<60;i++){
            listMini.add(i+"");
        }
    }


    //显示模式选择
    public void showModelSelect(){
        if (popModel==null){
            popModel=new PopupWindow();
        }
       View air_modesetting= LayoutInflater.from(activity).inflate(R.layout.c_airsetting_model,null);
        TextView air_modelsetting_cancle= (TextView) air_modesetting.findViewById(R.id.air_modelsetting_cancle);
//        zidong air_modelsetting_Layout_cood
        LinearLayout air_modelsetting_Layout_auto= (LinearLayout) air_modesetting.findViewById(R.id.air_modelsetting_Layout_auto);
        air_modelsetting_Layout_auto.setOnClickListener(this);
//        zhileng
        LinearLayout air_modelsetting_Layout_cood= (LinearLayout) air_modesetting.findViewById(R.id.air_modelsetting_Layout_cood);
        air_modelsetting_Layout_cood.setOnClickListener(this);

        //        制热
        LinearLayout air_modelsetting_Layout_hot= (LinearLayout) air_modesetting.findViewById(R.id.air_modelsetting_Layout_hot);
        air_modelsetting_Layout_hot.setOnClickListener(this);

        //        除湿
        LinearLayout air_modelsetting_Layout_wet= (LinearLayout) air_modesetting.findViewById(R.id.air_modelsetting_Layout_wet);
        air_modelsetting_Layout_wet.setOnClickListener(this);

        //        送风
        LinearLayout air_modelsetting_Layout_wind= (LinearLayout) air_modesetting.findViewById(R.id.air_modelsetting_Layout_wind);
        air_modelsetting_Layout_wind.setOnClickListener(this);


        air_modelsetting_cancle.setOnClickListener(this);
        View parent=activity.findViewById(R.id.activity_device_setting_c__air_con);
        PopupSettings.getInstance().windowBottomUpSet(popModel,parent,activity,air_modesetting);

    }
    //选择风速
    public void showWindSpeedSelect(){
        if (popWindSpeed==null){
            popWindSpeed=new PopupWindow();
        }
        View air_windspeed= LayoutInflater.from(activity).inflate(R.layout.air_windspeed,null);
        TextView air_setting_windspeed_cancle= (TextView) air_windspeed.findViewById(R.id.air_setting_windspeed_cancle);
        air_setting_windspeed_cancle.setOnClickListener(this);
        SeekBar seekBar= (SeekBar) air_windspeed.findViewById(R.id.air_windspeed_seekBar);
        seekBar.setOnSeekListener(this);
        View parent=activity.findViewById(R.id.activity_device_setting_c__air_con);
        PopupSettings.getInstance().windowBottomUpSet(popWindSpeed,parent,activity,air_windspeed);
    }
    //选择风向
    public void showWindDirectionSelect(){
        if (popWindDirection==null){
            popWindDirection=new PopupWindow();
        }
        //风向控制
        View air_winddirect= LayoutInflater.from(activity).inflate(R.layout.air_winddirect,null);
        TextView air_setting_winddir_cancle= (TextView) air_winddirect.findViewById(R.id.air_setting_winddir_cancle);
        air_setting_winddir_cancle.setOnClickListener(this);
        //ZIDONG
        RelativeLayout ari_winddirect_auto= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_auto);
        ari_winddirect_auto.setOnClickListener(this);
        //shangxia
        RelativeLayout ari_winddirect_topAndBottom= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_topAndBottom);
        ari_winddirect_topAndBottom.setOnClickListener(this);
        //左右风向
        RelativeLayout  ari_winddirect_leftAndRight= (RelativeLayout) air_winddirect.findViewById(R.id.ari_winddirect_leftAndRight);
        ari_winddirect_leftAndRight.setOnClickListener(this);

        View parent=activity.findViewById(R.id.activity_device_setting_c__air_con);
        PopupSettings.getInstance().windowBottomUpSet(popWindDirection,parent,activity,air_winddirect);
    }
    //定时选择
    public void showWindTimeSelect(){
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
        air_timesetting_textv.setText(listHour.get(SelectHourPos)+" "+listMini.get(SelectMiniPos)+"后 将关闭该空调");
        WheelView   air_timesetting_wheelHour= (WheelView) air_timesetting.findViewById(R.id.air_timesetting_wheelHour);
        air_timesetting_wheelHour.setTitle("小时");
        air_timesetting_wheelHour.setViewAdapter(new MyWheelAdapter50(activity,listHour,"小时"));
        air_timesetting_wheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                SelectHourPos=newValue;
                Toast.makeText(activity,listHour.get(newValue),Toast.LENGTH_SHORT).show();
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时 "+listMini.get(SelectMiniPos)+"分钟后 将关闭该空调");
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
                air_timesetting_textv.setText(listHour.get(SelectHourPos)+"小时 "+listMini.get(SelectMiniPos)+"分钟后 将关闭该空调");
            }
        });

        View parent=activity.findViewById(R.id.activity_device_setting_c__air_con);
        PopupSettings.getInstance().windowBottomUpSet(popupTime,parent,activity,air_timesetting);
    }
    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
      //---------------------模式------------------------------------------------------------
                case R.id.air_modelsetting_cancle://取消
                    popModel.dismiss();
                    break;
                case R.id.air_modelsetting_Layout_auto://zidong
                    popModel.dismiss();
                    windowSelect.setModel(AirModel.AUTO);
                    break;
                case R.id.air_modelsetting_Layout_cood://制冷
                    popModel.dismiss();
                    windowSelect.setModel(AirModel.COOD);
                    break;
                case R.id.air_modelsetting_Layout_hot://制热
                    popModel.dismiss();
                    windowSelect.setModel(AirModel.HOT);
                    break;
                case R.id.air_modelsetting_Layout_wet://除湿
                    popModel.dismiss();
                    windowSelect.setModel(AirModel.WET);
                    break;
                case R.id.air_modelsetting_Layout_wind://送风
                    popModel.dismiss();
                    windowSelect.setModel(AirModel.WIND);
                    break;

//-------------------------------风速--------------------------------------------------
                case R.id.air_setting_windspeed_cancle://风速取消
                    popWindSpeed.dismiss();
                    break;

//----------------------------风向-----------------------------------------------------
                case R.id.air_setting_winddir_cancle://风向选择取消
                    popWindDirection.dismiss();
                    break;
                case R.id.ari_winddirect_auto://自动风向
                    windowSelect.setWindDirection(AirWindDirection.AUTO);
                    popWindDirection.dismiss();
                    break;
                case R.id.ari_winddirect_topAndBottom://上下风向风向
                    windowSelect.setWindDirection(AirWindDirection.TOP_BOTTOM);
                    popWindDirection.dismiss();
                    break;
                case R.id.ari_winddirect_leftAndRight://左右风向风向
                    windowSelect.setWindDirection(AirWindDirection.LEFT_RIGHT);
                    popWindDirection.dismiss();
                    break;
 //---------------------------定时-----------------------------------------------------
                case R.id.air_setting_windtime_cancle:
                    popupTime.dismiss();
                    break;
                case R.id.air_timesetting_submit:
                    popupTime.dismiss();
                    windowSelect.setTime(listHour.get(SelectHourPos),listMini.get(SelectMiniPos));
                    break;
            }
        }
    }

    //风速选择监听事件
    @Override
    public void seek(float mark, int max, int state, int windState) {
        switch (windState){
            case 0://低速
               windowSelect.setWindSpeed(AirWinSpeed.LOW);
                break;
            case 1://中速
                windowSelect.setWindSpeed(AirWinSpeed.MIDDLE);
                break;
            case 2://快速
                windowSelect.setWindSpeed(AirWinSpeed.LARGE);
                break;
        }
        handler.sendEmptyMessageAtTime(0,2000);
    }


    public interface WindowSelect{
        void setModel(AirModel model );//模式设置
        void setWindSpeed(AirWinSpeed speed);//风速设置
        void setWindDirection(AirWindDirection direction);//风向调节
        void setTime(String hour,String minute);//定时调节,hour：小时，mimute分钟
    }

}
