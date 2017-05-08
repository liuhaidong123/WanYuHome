package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.myUtils.ImgUitls;

public class MoneyNumActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private EditText mEdit;
    private Button mSubmit_btn;
    private TextView mUnit_tv;
    private TextView mCity_tv;
    private TextView mAddress_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_num);
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.money2_back);
        mBack.setOnClickListener(this);

        //城市
        mCity_tv = (TextView) findViewById(R.id.money2_msg_city);
        //地址
        mAddress_tv = (TextView) findViewById(R.id.money2_address_msg);
        //缴费单位
        mUnit_tv = (TextView) findViewById(R.id.money2_unit_msg);
        //用户编号
        mEdit = (EditText) findViewById(R.id.money2_num_edit);

        //下一步或者保存
        mSubmit_btn = (Button) findViewById(R.id.money2_submit);
        mSubmit_btn.setOnClickListener(this);
        if (getIntent().getIntExtra("update", -1) == 2) {//没有修改编号,下一步
            mSubmit_btn.setText("下一步");
        } else if (getIntent().getIntExtra("update", -1) == 1) {//修改编号，保存
            mSubmit_btn.setText("保存");
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }else if (id==mSubmit_btn.getId()){
            if (getIntent().getIntExtra("update", -1) == 2) {//没有修改编号,下一步
               if (getEditNum().equals("")){
                   Toast.makeText(this,"请填写您的缴费编号",Toast.LENGTH_SHORT).show();
               }else {
                   Intent intent=new Intent(this,MoneyInputSureActivity.class);
                   startActivity(intent);
               }
            } else if (getIntent().getIntExtra("update", -1) == 1) {//修改编号，保存
               finish();
            }

        }
    }

    public String getEditNum(){
        if (mEdit.getText().toString().equals("")){
            return "";
        }
        return mEdit.getText().toString();
    }
}
