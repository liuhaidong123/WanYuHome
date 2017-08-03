package com.home.wanyu.C_View;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapterPadding;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanyu on 2017/7/21.
 */

public class DeviceSettingC_LockSharePresenter implements OnWheelChangedListener,View.OnClickListener{
    Tim tim;
    Activity activity;
    TimeSelect timeSelect;
    PopupWindow pop;
    List<String> listMonth;//日期
    List<String>listHour;
    List<String>listMinute;
    WheelView pop_lockshare_wheelViewMonth;
    WheelView pop_lockshare_wheelViewHour;
    WheelView pop_lockshare_wheelViewMinute;
    String currentMonth,currentHour,currentMinute;
    public DeviceSettingC_LockSharePresenter(Activity activity,TimeSelect timeSelect){
        this.activity=activity;
        this.timeSelect=timeSelect;
        initList();
    }

    private void initList() {
        Calendar now = Calendar.getInstance();
        Log.e("---tttt--",now.get(Calendar.MONTH)+"---"+now.get(Calendar.DAY_OF_MONTH));

        listMonth=new ArrayList<>();
        for (int i=0;i<20;i++){
            if (i==0){
                now.add(Calendar.DAY_OF_MONTH,0);
            }
            else {
                now.add(Calendar.DAY_OF_MONTH,1);
            }
            String month=now.get(Calendar.MONTH)+"";
            if (now.get(Calendar.MONTH)<9){//getMonth返回值从0到11（11代表12月份，0代表1月）
                month="0"+(now.get(Calendar.MONTH)+1);
            }
            String data=now.get(Calendar.DAY_OF_MONTH)+"";
            if (now.get(Calendar.DAY_OF_MONTH)<10){
                data="0"+now.get(Calendar.DAY_OF_MONTH);
            }
            listMonth.add(month+"/"+data);
            Log.e("month---"+month,"data----"+data);
        }
        listMinute=new ArrayList<>();
        for (int i=0;i<60;i++){
            String minute="";
            if (i<10){
                minute+=("0"+i);
            }
            else {
                minute+=""+i;
            }
            listMinute.add(minute);
        }
        listHour=new ArrayList<>();
        for (int i=0;i<24;i++){
            String hour="";
            if (i<10){
                hour+="0"+i;
            }
            else {
                hour+=""+i;
            }
            listHour.add(hour);
        }
    }


    //弹窗选择时间
    public void ShowWindowSelect(Tim state){
        tim=state;
        if (pop==null){
            pop=new PopupWindow();
        }
        currentMonth=listMonth.get(0);
        currentHour=listHour.get(0);
        currentMinute=listMinute.get(0);

        View vi= LayoutInflater.from(activity).inflate(R.layout.pop_lockshare,null);
        TextView lockShare_title= (TextView) vi.findViewById(R.id.lockShare_title);
        if (tim==Tim.START){
            lockShare_title.setText("选择钥匙开启时间");
        }
        else {
            lockShare_title.setText("选择钥匙关闭时间");
        }

        TextView lockShare_submits= (TextView) vi.findViewById(R.id.lockShare_submits);
        lockShare_submits.setOnClickListener(this);
        //日期按当前月份加20天
        pop_lockshare_wheelViewMonth= (WheelView) vi.findViewById(R.id.pop_lockshare_wheelViewMonth);//日期
        MyWheelAdapterPadding adapter1=new MyWheelAdapterPadding(activity,listMonth,activity.getResources().getDimension(R.dimen.wheelLeftPadding),0);
        pop_lockshare_wheelViewMonth.setViewAdapter(adapter1);
        pop_lockshare_wheelViewMonth.addChangingListener(this);

        pop_lockshare_wheelViewHour= (WheelView) vi.findViewById(R.id.pop_lockshare_wheelViewHour);//小时
        MyWheelAdapterPadding adapter2=new MyWheelAdapterPadding(activity,listHour,activity.getResources().getDimension(R.dimen.left),0);
        pop_lockshare_wheelViewHour.setViewAdapter(adapter2);
        pop_lockshare_wheelViewHour.addChangingListener(this);

        pop_lockshare_wheelViewMinute= (WheelView) vi.findViewById(R.id.pop_lockshare_wheelViewMinute);//分钟
        MyWheelAdapterPadding adapter3=new MyWheelAdapterPadding(activity,listMinute,0,activity.getResources().getDimension(R.dimen.wheelRightPadding));
        pop_lockshare_wheelViewMinute.setViewAdapter(adapter3);
        pop_lockshare_wheelViewMinute.addChangingListener(this);

        View parent=activity.findViewById(R.id.activity_device_setting_c__lock_share);
        PopupSettings.getInstance().windowBottomUpSet(pop,parent,activity,vi);
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (wheel.equals(pop_lockshare_wheelViewMonth)){
                currentMonth=listMonth.get(newValue);
            }
            else if (wheel.equals(pop_lockshare_wheelViewHour)){
                currentHour=listHour.get(newValue);
            }
            else if (wheel.equals(pop_lockshare_wheelViewMinute)){
                currentMinute=listMinute.get(newValue);
            }
    }

    @Override
    public void onClick(View v) {
        timeSelect.setTime(currentMonth+" "+currentHour+":"+currentMinute,tim);
        if (pop!=null){
            pop.dismiss();
        }
    }

    public interface TimeSelect{
       void setTime(String time,Tim ti);
    }

    public enum Tim{
        START,END;
    }
}
