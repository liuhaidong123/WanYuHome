package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.Money2Ada;

import java.util.ArrayList;
import java.util.List;

import static com.home.wanyu.R.id.money2_msg_address_rl;

public class LifeMoneyActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private ListView mListview;
    private RelativeLayout mIntentAddress;
    private TextView mMoney2_Record;
    private Money2Ada mAdapter;
    private List<Integer> mImgList = new ArrayList<>();

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlert;
    private View mView;
    private TextView mCancle;
    private TextView mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_money2);
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.money2_back);
        mBack.setOnClickListener(this);
        //缴费记录
        mMoney2_Record = (TextView) findViewById(R.id.money2_record);
        mMoney2_Record.setOnClickListener(this);
        //跳转到地址
        mIntentAddress = (RelativeLayout) findViewById(R.id.money2_msg_address_rl);
        mIntentAddress.setOnClickListener(this);
        //适配器
        mImgList.add(R.mipmap.money_ele);
        mImgList.add(R.mipmap.money_water);
        mImgList.add(R.mipmap.money_ran);
        mImgList.add(R.mipmap.money_property);
        mListview = (ListView) findViewById(R.id.money2_listview);
        mAdapter = new Money2Ada(this, mImgList);
        mListview.setAdapter(mAdapter);

        //
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LifeMoneyActivity2.this, MoneyNumActivity.class);
                intent.putExtra("update", 2);
                startActivity(intent);

            }
        });

        //长按修改
        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAlert.show();
                setAlertWidth(mAlert, 1.5f);
                return true;
            }
        });
        //长安弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlert = mBuilder.create();
        mView = LayoutInflater.from(this).inflate(R.layout.money_update_num, null);
        mCancle = (TextView) mView.findViewById(R.id.cancle);
        mUpdate = (TextView) mView.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        mAlert.setView(mView);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mIntentAddress.getId()) {
            startActivity(new Intent(this, OrderAddressActivity.class));
            finish();
        } else if (id == mCancle.getId()) {
            mAlert.dismiss();
        } else if (id == mUpdate.getId()) {//修改编号
            Intent intent = new Intent(this, MoneyNumActivity.class);
            intent.putExtra("update", 1);
            startActivity(intent);
            mAlert.dismiss();
        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, float a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) ((int) dm.widthPixels / a);
        alert.getWindow().setAttributes(p);//设置生效
    }
}
