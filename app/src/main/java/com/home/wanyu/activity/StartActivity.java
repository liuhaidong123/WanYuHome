package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StartActivity extends AppCompatActivity {
    private Unbinder unbinder;
    private int time=3;
    @BindView(R.id.start_textV) TextView start_textV;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    start_textV.setText(Html.fromHtml("<font color=\'#858585\'>剩余 </font><font color=\'#f02387\'>"+time+"</font><font color='#858585'>s</font>"));
                    break;
                case 1:
                    start_textV.setText(Html.fromHtml("<font color=\'#858585\'>剩余 </font><font color=\'#f02387\'>"+time+"</font><font color='#858585'>s</font>"));
                    break;
                case 0:
                    if (UserInfo.isLogin(StartActivity.this)){//已经登录
                        startActivity(new Intent(StartActivity.this,MainActivity.class));
                    }
                    else {
                        startActivity(new Intent(StartActivity.this,LoginAndRegisterActivity.class));
                    }

                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        unbinder=ButterKnife.bind(StartActivity.this);
        start_textV.setText(Html.fromHtml("<font color=\'#858585\'>剩余 </font><font color=\'#f02387\'>3</font><font color='#858585'>s</font>"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (time>-1){
                        Thread.sleep(1000);
                        time--;
                        handler.sendEmptyMessage(time);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @OnClick(R.id.start_textV)
    public void Click(View view){
            if (time==0){
                return;
            }
            time=-1;
        if (UserInfo.isLogin(StartActivity.this)){//已经登录
            startActivity(new Intent(StartActivity.this,MainActivity.class));
        }
        else {
            startActivity(new Intent(StartActivity.this,LoginAndRegisterActivity.class));
        }

        finish();
        }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
