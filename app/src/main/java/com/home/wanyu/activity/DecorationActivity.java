package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.apater.DecorationAda;
import com.home.wanyu.myview.MyListView;

//装修
public class DecorationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private MyListView myListView;
    private DecorationAda mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.decoration_msg_back);
        mback.setOnClickListener(this);

        myListView = (MyListView) findViewById(R.id.decotion_listview);
        mAdapter = new DecorationAda(this);
        myListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        }
    }
}
