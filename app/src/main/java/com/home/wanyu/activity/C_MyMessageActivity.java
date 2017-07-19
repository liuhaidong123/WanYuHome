package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.home.wanyu.C_View.C_MyMessagePresenter;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//消息页面
public class C_MyMessageActivity extends CMyActivity {
    C_MyMessagePresenter presenter;
    @BindView(R.id.c_message_car_messageTime)TextView c_message_car_messageTime;//拼车消息时间
    @BindView(R.id.c_message_car_message)TextView c_message_car_message;//拼车消息内容

    @BindView(R.id.c_message_SQ_messageTime)TextView c_message_SQ_messageTime;//社区活动消息时间
    @BindView(R.id.c_message_SQ_message)TextView c_message_SQ_message;//社区活动消息内容

    @BindView(R.id.c_message_circle_messageTime)TextView c_message_circle_messageTime;//友邻圈消息时间
    @BindView(R.id.c_message_circle_message)TextView c_message_circle_message;//友邻圈消息内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_message);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("消息",",,",true);
        unbinder= ButterKnife.bind(this);
    }
    @OnClick({R.id.c_myMessage_Layout_carmessage,R.id.c_myMessage_Layout_activity})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_myMessage_Layout_carmessage://拼车消息
                startActivity(new Intent(this,MessageCarpoolActivity.class));
                break;
            case R.id.c_myMessage_Layout_activity://社区活动消息

                break;
            case R.id.c_myMessage_Layout_circle://友邻圈消息
                break;
        }
    }
    @Override
    public void getServerData() {

    }
}
