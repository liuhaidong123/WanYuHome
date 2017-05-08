package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.home.wanyu.R;

public class LifeMoneyActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mMoneyAdd_Address;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_money);
        initView();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.money_back);
        mBack.setOnClickListener(this);
        //添加地址
        mMoneyAdd_Address= (Button) findViewById(R.id.money_add_area_submit);
        mMoneyAdd_Address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mMoneyAdd_Address.getId()){
            Intent intent=new Intent(this,AddAddressActivity.class);
            intent.putExtra("money",11);
            startActivity(intent);
        }else if (mBack.getId()==id){
            finish();
        }
    }
}
