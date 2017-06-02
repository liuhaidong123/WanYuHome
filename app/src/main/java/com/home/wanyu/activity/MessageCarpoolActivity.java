package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.MessageCarpoolAdapter;
import com.home.wanyu.lzhUtils.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

//拼车消息详情
public class MessageCarpoolActivity extends MyActivity {
    @BindView(R.id.message_carpool_listview)ListView message_carpool_listview;
    MessageCarpoolAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_message_carpool);
        ShowChildView(DEFAULTRESID);
        setTitle("拼车消息");
        unbinder= ButterKnife.bind(this,ChildView);
        adapter=new MessageCarpoolAdapter(this);
        message_carpool_listview.setAdapter(adapter);
    }
     @Override
    public void getSerVerData() {

    }
}
