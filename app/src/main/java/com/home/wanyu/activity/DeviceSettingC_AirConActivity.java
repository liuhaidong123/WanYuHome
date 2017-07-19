package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.C_View.DeviceSettingC_AirConView;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.mEmeu.AirModel;
import com.home.wanyu.mEmeu.AirWinSpeed;
import com.home.wanyu.mEmeu.AirWindDirection;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//空调页面
public class DeviceSettingC_AirConActivity extends CMyActivity implements DeviceSettingC_AirConView.WindowSelect {
    DeviceSettingC_AirConView airConView;

    @BindView(R.id.c_deviceSetting_air_wendu) TextView c_deviceSetting_air_wendu;//温度显示的view

    @BindView(R.id.c_airsetting_model_image) ImageView c_airsetting_model_image;//显示模式的view
    @BindView(R.id.c_airsetting_model_text) TextView c_airsetting_model_text;//显示模式的名字

    @BindView(R.id.c_airsetting_wind_image) ImageView c_airsetting_wind_image;//显示风速的image
    @BindView(R.id.c_airsetting_wind_text) TextView c_airsetting_wind_text;//显示风速的名字

    @BindView(R.id.c_airsetting_windDirect_image) ImageView c_airsetting_windDirect_image;//显示风向
    @BindView(R.id.c_airsetting_windDirect_text) TextView c_airsetting_windDirect_text;//显示风向名称

    @BindView(R.id.c_airsetting_time_image)  ImageView c_airsetting_time_image;//显示定时时间的view
    @BindView(R.id.c_airsetting_time_text) TextView c_airsetting_time_text;//显示定时的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__air_con);
        ButterKnife.bind(this);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        titleButton= (TextView) findViewById(R.id.titleButton);
        initTitle("空调","确定",true);
        airConView=new DeviceSettingC_AirConView(this,this);
    }
    @OnClick({R.id.c_deviceSetting_air_wendu_plus,R.id.c_deviceSetting_air_wendu_add,R.id.c_airsetting_layout_model
    ,R.id.c_airsetting_layout_windspeed,R.id.c_airsetting_layout_winddirection,R.id.c_airsetting_layout_timing})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_deviceSetting_air_wendu_plus://空调温度减
                minusTemp();
                break;
            case R.id.c_deviceSetting_air_wendu_add://空调温度加
               addTemp();
                break;
            case R.id.c_airsetting_layout_model://模式选择
                airConView.showModelSelect();
                break;
            case R.id.c_airsetting_layout_windspeed://风速选择
                airConView.showWindSpeedSelect();
                break;
            case R.id.c_airsetting_layout_winddirection://风向选择
                airConView.showWindDirectionSelect();
                break;
            case R.id.c_airsetting_layout_timing://定时操作
                airConView.showWindTimeSelect();
                break;
        }
    }
    //增加温度最高30度
    public void addTemp(){
        int tem=Integer.parseInt(c_deviceSetting_air_wendu.getText().toString());
        if (tem<=29){
            tem+=1;
            c_deviceSetting_air_wendu.setText(tem+"");
        }
        else {
            mToast.Toast(this,"已达最高温度");
        }
    }
    //降低温度最低16度
    public void minusTemp(){
        int tem=Integer.parseInt(c_deviceSetting_air_wendu.getText().toString());
        if (tem>=17){
            tem-=1;
            c_deviceSetting_air_wendu.setText(tem+"");
        }
        else {
            mToast.Toast(this,"已达最低温度");
        }
    }

    @Override
    public void getServerData() {

    }
//-------------------------------------弹窗操作-------------------------------------------------------
    //模式设置
    @Override
    public void setModel(AirModel model) {
        c_airsetting_model_text.setText(model.getModeName(model));
        c_airsetting_model_image.setImageResource(model.getModelRes(model));
    }
    //风速设置
    @Override
    public void setWindSpeed(AirWinSpeed speed) {
        c_airsetting_wind_text.setText(speed.getSpeedName(speed));
    }
    //风向设置
    @Override
    public void setWindDirection(AirWindDirection direction) {
        c_airsetting_windDirect_text.setText(direction.getWindDirectionName(direction));
    }
    //定时设置
    @Override
    public void setTime(String hour, String minute) {
        c_airsetting_time_text.setText(hour+":"+minute);
    }
    //------------------------------------弹窗操作--------------------------------------------------------
}
