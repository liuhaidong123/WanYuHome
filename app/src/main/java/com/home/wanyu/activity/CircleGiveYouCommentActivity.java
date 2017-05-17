package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CircleYouMsgAda;
import com.home.wanyu.myview.MyListView;

public class CircleGiveYouCommentActivity extends AppCompatActivity {
    private ImageView mBack;
        private MyListView mListview;
    private CircleYouMsgAda mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_give_you_comment);

        initView();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.circle_you_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListview= (MyListView) findViewById(R.id.you_msg_listview);
        mAdpater=new CircleYouMsgAda(this);
        mListview.setAdapter(mAdpater);
    }
}
