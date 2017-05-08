package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;

public class MoneyInputSureActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack,mMoney_tell_img;
    private EditText mEdit_input;
    private TextView mMoney_unit,mMoney_num,mMoney_name,mMoney_address,mMoney_type,mMoney_many,mMoney_tell_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_input_sure);

        initView();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.money2_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
