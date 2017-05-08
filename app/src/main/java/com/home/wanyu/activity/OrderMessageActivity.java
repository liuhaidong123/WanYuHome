package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.AlertAreaAda;
import com.home.wanyu.apater.AlertCityAda;
import com.home.wanyu.apater.OrderMsgAda;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class OrderMessageActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    private RelativeLayout mOrder_address_rl;
    private LinearLayout mYear_ll,mMonth_ll;
    private TextView mYear_tv,mMonth_tv;
    private ImageView mYear_img,mMonth_img;
    private MyListView mListview;
    private OrderMsgAda mAdapter;

    private AlertDialog.Builder mYearBuilder;
    private AlertDialog mYearAlert;
    private View mYearView;
    private ListView mYearListview;
    private AlertCityAda mYearAda;
    private List<String> mYearList = new ArrayList<>();


    private AlertDialog.Builder mMonthBuilder;
    private AlertDialog mMonthAlert;
    private View mMonthView;
    private AlertAreaAda mMonthAda;
    private List<String> mMonthList = new ArrayList<>();
    private ListView mMonthListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        initView();
    }

    private void initView() {
        //返回
        mBack= (ImageView) findViewById(R.id.order_msg_back);
        mBack.setOnClickListener(this);
        //跳转地址
        mOrder_address_rl= (RelativeLayout) findViewById(R.id.order_msg_address_rl);
        mOrder_address_rl.setOnClickListener(this);
        //年份
        mYear_ll= (LinearLayout) findViewById(R.id.order_year_ll);
        mYear_ll.setOnClickListener(this);
        mYear_tv= (TextView) findViewById(R.id.order_year_tv);
        mYear_img= (ImageView) findViewById(R.id.order_year_img);
        //年份弹框
        mYearBuilder = new AlertDialog.Builder(this);
        mYearAlert = mYearBuilder.create();
        mYearView = LayoutInflater.from(this).inflate(R.layout.city_alert, null);
        mYearListview = (ListView) mYearView.findViewById(R.id.city_alert_listview);
        //选择城市
        mYearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mYear_tv.setText(mYearAda.getItem(position).toString());
                mYearAlert.dismiss();
            }
        });
        mYearList.add("2015年");
        mYearList.add("2016年");
        mYearList.add("2017年");
        mYearList.add("2018年");
        mYearAda = new AlertCityAda(this, mYearList);
        mYearListview.setAdapter(mYearAda);
        mYearAlert.setView(mYearView);



        //月份
        mMonth_ll= (LinearLayout) findViewById(R.id.order_month_ll);
        mMonth_ll.setOnClickListener(this);
        mMonth_tv= (TextView) findViewById(R.id.order_month_tv);
        mMonth_img= (ImageView) findViewById(R.id.order_month_img);
        //月份弹框
        //小区弹框
        mMonthBuilder = new AlertDialog.Builder(this);
        mMonthAlert = mMonthBuilder.create();
        mMonthView = LayoutInflater.from(this).inflate(R.layout.area_alert, null);
        mMonthListview = (ListView) mMonthView.findViewById(R.id.area_alert_listview);
        mMonthList.add("全部");
        mMonthList.add("1月");
        mMonthList.add("2月");
        mMonthList.add("3月");
        mMonthList.add("4月");
        mMonthList.add("5月");
        mMonthList.add("6月");
        mMonthList.add("7月");
        mMonthList.add("8月");
        mMonthList.add("9月");
        mMonthList.add("10月");
        mMonthList.add("11月");
        mMonthList.add("12月");

        mMonthAda = new AlertAreaAda(this, mMonthList);
        mMonthListview.setAdapter(mMonthAda);
        mMonthAlert.setView(mMonthView);
        //选择小区
        mMonthListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMonth_tv.setText(mMonthAda.getItem(position).toString());
                mMonthAlert.dismiss();
            }
        });


        //账单
        mListview= (MyListView) findViewById(R.id.order_msg_listview);
        mAdapter=new OrderMsgAda(this);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }else if (id==mYear_ll.getId()){//选择年份
            mYear_ll.setBackgroundResource(R.color.bg_rect);
            mYear_tv.setTextColor(ContextCompat.getColor(this,R.color.white));
            mYear_img.setImageResource(R.mipmap.bottom_back_white);

            mMonth_ll.setBackgroundResource(R.color.white);
            mMonth_tv.setTextColor(ContextCompat.getColor(this,R.color.title_color));
            mMonth_img.setImageResource(R.mipmap.bottom_back);

            mYearAlert.show();
            setAlertWidth(mYearAlert,2);
        }else if (id==mMonth_ll.getId()){//选择月份
            mYear_ll.setBackgroundResource(R.color.white);
            mYear_tv.setTextColor(ContextCompat.getColor(this,R.color.title_color));
            mYear_img.setImageResource(R.mipmap.bottom_back);

            mMonth_ll.setBackgroundResource(R.color.bg_rect);
            mMonth_tv.setTextColor(ContextCompat.getColor(this,R.color.white));
            mMonth_img.setImageResource(R.mipmap.bottom_back_white);

            mMonthAlert.show();
            setAlertWidth(mMonthAlert,2);
        }else if (id==mOrder_address_rl.getId()){
            Intent intent=new Intent(this,OrderAddressActivity.class);
            startActivityForResult(intent,123);

        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, int a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = dm.widthPixels / a;
        alert.getWindow().setAttributes(p);//设置生效
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
