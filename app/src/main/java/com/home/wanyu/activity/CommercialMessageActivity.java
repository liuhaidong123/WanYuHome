package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CommercialOthersGoodsAda;
import com.home.wanyu.myview.MyListView;

public class CommercialMessageActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mback;
    private MyListView myListView;
    private CommercialOthersGoodsAda mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_message);
        initView();
    }

    private void initView() {
        mback= (ImageView) findViewById(R.id.commercial_msg_back);
        mback.setOnClickListener(this);

        myListView= (MyListView) findViewById(R.id.goods_others_listview);
        mAdapter=  new CommercialOthersGoodsAda(this);
        myListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mback.getId()){
            finish();
        }
    }
}
