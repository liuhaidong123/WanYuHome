package com.home.wanyu.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.C_View.DeviceSettingC_TVPresenter;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//电视设置
public class DeviceSettingC_TVActivity extends CMyActivity implements DeviceSettingC_TVPresenter.TvSetting{
    DeviceSettingC_TVPresenter tvPresenter;
    int state=1;//电视的状态 0关闭状态，1开启状态
    //最大电视台数目，当前电视台
    int MAX_TV=10;
    int MIN_TV=1;
    int currentTV=1;
    //最大音量与当前音量
    int MAX_VOICE=15;
    int MIN_VOICE=0;
    int currentVoice=0;

    @BindView(R.id.textv_voice)TextView textv_voice;//静音
    @BindView(R.id.textv_close)TextView textv_close;//关闭
    @BindView(R.id.textv_timing)TextView textv_timing;//定时
    @BindView(R.id.image_Voice)ImageView image_Voice;//静音的图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__tv);
        unbinder= ButterKnife.bind(this);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        titleButton= (TextView) findViewById(R.id.titleButton);
        initTitle("电视","确定",true);
        tvPresenter=new DeviceSettingC_TVPresenter(this,this);
    }
    @OnClick({R.id.tv_setting_left,R.id.tv_setting_right,R.id.tv_setting_add,R.id.tv_setting_minus,R.id.image_Voice,R.id.image_Close,R.id.image_timing})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.tv_setting_left://tv_setting_left;//向前按钮
                tvPresenter.setTV(MAX_TV,MIN_TV,currentTV, DeviceSettingC_TVPresenter.TvType.MINUS);
                break;
            case R.id.tv_setting_right://向后按钮
                tvPresenter.setTV(MAX_TV,MIN_TV,currentTV, DeviceSettingC_TVPresenter.TvType.ADD);
                break;
            case R.id.tv_setting_add://音量加
                tvPresenter.setVoice(MAX_VOICE,MIN_VOICE,currentVoice, DeviceSettingC_TVPresenter.VoiceType.ADD);
                break;
            case R.id.tv_setting_minus://音量减
                tvPresenter.setVoice(MAX_VOICE,MIN_VOICE,currentVoice, DeviceSettingC_TVPresenter.VoiceType.MINUS);
                break;
            case R.id.image_Voice://静音按钮,静音按钮不会操作当前的音量，不会将当前音量重置
                image_Voice.setSelected(!image_Voice.isSelected());
                break;
            case R.id.image_Close://关闭按钮
                if (state==0){//当前处于关闭状态
                    state=1;
                    textv_close.setText("关闭");
                }
                else {
                    state=0;
                    textv_close.setText("开启");
                }
                break;
            case R.id.image_timing://定时按钮
               tvPresenter.showWindTimeSelect(state);
                break;
        }
    }
    @Override
    public void getServerData() {

    }

    //搜台控制
    @Override
    public void setTV(int current) {
        currentTV=current;
        mToast.Toast(this,"当前台号："+current);
    }
    //声音控制
    @Override
    public void setVoice(int current) {
        currentVoice=current;
        mToast.Toast(this,"当前音量："+current);
        if (current==MIN_VOICE){//达到最小音量时，进行静音操作
            image_Voice.setSelected(true);
        }
        else {
            image_Voice.setSelected(false);
        }
    }
    //定时控制
    @Override
    public void setTiming(int hours, int minute) {
        String hou,minu;
        hou=hours+"";
        minu=minute+"";
        if (hours<10){
            hou="0"+hours;
        }
        if (minute<10){
            minu="0"+minute;
        }
        textv_timing.setText(hou+":"+minu);
    }


}
