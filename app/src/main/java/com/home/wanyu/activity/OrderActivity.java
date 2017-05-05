package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    private TextView mAddAddress_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }

    public void initView(){
        mBack= (ImageView) findViewById(R.id.order_back);
        mBack.setOnClickListener(this);

        mAddAddress_btn= (TextView) findViewById(R.id.add_address_btn);
        mAddAddress_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }else if (id==mAddAddress_btn.getId()){
            startActivity(new Intent(this,AddAddressActivity.class));
        }
    }
}
