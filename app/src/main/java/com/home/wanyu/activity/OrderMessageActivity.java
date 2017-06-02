package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.AlertAreaAda;
import com.home.wanyu.apater.AlertCityAda;
import com.home.wanyu.apater.OrderMonthAda;
import com.home.wanyu.apater.OrderMsgAda;
import com.home.wanyu.apater.OrderYearAda;
import com.home.wanyu.bean.haveAddress.Result;
import com.home.wanyu.bean.haveAddress.Root;
import com.home.wanyu.bean.waterEleRan.ItemsYear;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private Button mSearch_btn;
    private RelativeLayout mOrder_address_rl;
    private LinearLayout mYear_ll, mMonth_ll;
    private TextView mYear_tv, mMonth_tv;
    private TextView mNian_tv, mYue_tv;


    private ImageView mYear_img, mMonth_img;
    private MyListView mListview;
    private OrderMsgAda mAdapter;
    private List<ItemsYear> mList = new ArrayList<>();


    private AlertDialog.Builder mYearBuilder;
    private AlertDialog mYearAlert;
    private View mYearView;
    private ListView mYearListview;
    private OrderYearAda mYearAda;
    private List<String> mYearList = new ArrayList<>();


    private AlertDialog.Builder mMonthBuilder;
    private AlertDialog mMonthAlert;
    private View mMonthView;
    private OrderMonthAda mMonthAda;
    private List<String> mMonthList = new ArrayList<>();
    private GridView mMonthListview;

    private TextView mAddressCity_tv, mAddress_tv;
    private Calendar mCalendar;
    private List<Result> mAddressList = new ArrayList<>();
    private Result result;
    private HttpTools mHttptools;
    private long ID = -1;
    private boolean isFlag = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {//获取地址
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mAddressList = root.getResult();
                        if (mAddressList.size() != 0) {
                            if (ID != -1) {//选择地址以后
                                for (int i = 0; i < mAddressList.size(); i++) {
                                    if (ID == mAddressList.get(i).getId()) {//修改地址以后，并没有选择修改后的地址，而是直接按下返回键（设置修改后的地址）
                                        isFlag = true;
                                        mAddressCity_tv.setText(mAddressList.get(i).getCity());
                                        mAddress_tv.setText(mAddressList.get(i).getDetailAddress());
                                        //根据年份，月份，地址查询业主的缴费情况
                                        mHttptools.getMoneyByYearMonthAddress(mHandler, UserInfo.userToken, mAddress_tv.getText().toString(), mYear_tv.getText().toString(), mMonth_tv.getText().toString());
                                        break;
                                    }
                                }

                                if (!isFlag) {//删除地址以后，没有选择地址，而是直接按下返回键，（给他默认第一个地址）
                                    ID = mAddressList.get(0).getId();
                                    mAddressCity_tv.setText(mAddressList.get(0).getCity());
                                    mAddress_tv.setText(mAddressList.get(0).getDetailAddress());
                                    //根据年份，月份，地址查询业主的缴费情况
                                    mHttptools.getMoneyByYearMonthAddress(mHandler, UserInfo.userToken, mAddress_tv.getText().toString(), mYear_tv.getText().toString(), mMonth_tv.getText().toString());
                                }

                            } else {//第一次刚进来，默认第一个地址
                                ID = mAddressList.get(0).getId();
                                mAddressCity_tv.setText(mAddressList.get(0).getCity());
                                mAddress_tv.setText(mAddressList.get(0).getDetailAddress());
                                //根据年份，月份，地址查询业主的缴费情况
                                mHttptools.getMoneyByYearMonthAddress(mHandler, UserInfo.userToken, mAddress_tv.getText().toString(), mYear_tv.getText().toString(), mMonth_tv.getText().toString());
                            }

                        } else {
                            mAddressCity_tv.setText("未知城市");
                            mAddress_tv.setText("未知小区");
                        }
                    } else {//跳转到添加物业账单地址
                        Intent intent = new Intent(OrderMessageActivity.this, AddAddressActivity.class);
                        intent.putExtra("order", 22);
                        startActivity(intent);
                        finish();
                    }

                }
            } else if (msg.what == 104) {//根据年份，月份，地址查询业主的缴费情况
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.waterEleRan.Root) {
                    MyDialog.stopDia();
                    com.home.wanyu.bean.waterEleRan.Root root = (com.home.wanyu.bean.waterEleRan.Root) o;
                    mList = root.getItemsYear();
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址接口
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.order_msg_back);
        mBack.setOnClickListener(this);
        mSearch_btn = (Button) findViewById(R.id.add_submit);
        mSearch_btn.setOnClickListener(this);

        mAddressCity_tv = (TextView) findViewById(R.id.order_msg_pro);
        mAddress_tv = (TextView) findViewById(R.id.order_address_msg);

        //跳转地址
        mOrder_address_rl = (RelativeLayout) findViewById(R.id.order_msg_address_rl);
        mOrder_address_rl.setOnClickListener(this);
        //年份
        mYear_ll = (LinearLayout) findViewById(R.id.order_year_ll);
        mYear_ll.setOnClickListener(this);
        mYear_tv = (TextView) findViewById(R.id.order_year_tv);
        mNian_tv = (TextView) findViewById(R.id.order_nian_tv);


        mYear_img = (ImageView) findViewById(R.id.order_year_img);
        //年份弹框
        mYearBuilder = new AlertDialog.Builder(this);
        mYearAlert = mYearBuilder.create();
        mYearView = LayoutInflater.from(this).inflate(R.layout.order_year_item, null);
        mYearListview = (ListView) mYearView.findViewById(R.id.city_alert_listview);
        //选择城市
        mYearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mYear_tv.setText(mYearAda.getItem(position).toString());
                mYearAlert.dismiss();
            }
        });

        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        for (int i = 0; i < 4; i++) {
            mYearList.add(year - i + "");
        }
        mYearAda = new OrderYearAda(this, mYearList);
        mYearListview.setAdapter(mYearAda);
        mYearAlert.setView(mYearView);


        //月份
        mMonth_ll = (LinearLayout) findViewById(R.id.order_month_ll);
        mMonth_ll.setOnClickListener(this);
        mMonth_tv = (TextView) findViewById(R.id.order_month_tv);
        mYue_tv = (TextView) findViewById(R.id.order_yue_tv);
        mMonth_img = (ImageView) findViewById(R.id.order_month_img);
        //月份弹框
        //小区弹框
        mMonthBuilder = new AlertDialog.Builder(this);
        mMonthAlert = mMonthBuilder.create();
        mMonthView = LayoutInflater.from(this).inflate(R.layout.order_month_item, null);
        mMonthListview = (GridView) mMonthView.findViewById(R.id.area_alert_listview);

        for (int i = 0; i < 13; i++) {
            if (i == 0) {
                mMonthList.add("全部");
            } else {
                mMonthList.add(i + "");
            }
        }

        mMonthAda = new OrderMonthAda(this, mMonthList);
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

        //物业账单默认选择当前年份和当前月的上一个月
        if (mYearList.size() != 0) {
            mYear_tv.setText(year + "");
        } else {
            mYear_tv.setText("未知");
        }
        if (mMonthList.size() != 0) {
            mMonth_tv.setText(month + "");
        } else {
            mMonth_tv.setText("未知");
        }

        //账单
        mListview = (MyListView) findViewById(R.id.order_msg_listview);
        mAdapter = new OrderMsgAda(this, mList);
        mListview.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mYear_ll.getId()) {//选择年份
            mYear_ll.setBackgroundResource(R.color.bg_rect);
            mYear_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mNian_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mYear_img.setImageResource(R.mipmap.bottom_back_white);

            mMonth_ll.setBackgroundResource(R.color.white);
            mMonth_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mYue_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mMonth_img.setImageResource(R.mipmap.bottom_back);

            mYearAlert.show();
            setAlertWidth(mYearAlert, 2);
        } else if (id == mMonth_ll.getId()) {//选择月份
            mYear_ll.setBackgroundResource(R.color.white);
            mYear_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mNian_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mYear_img.setImageResource(R.mipmap.bottom_back);

            mMonth_ll.setBackgroundResource(R.color.bg_rect);
            mMonth_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mYue_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMonth_img.setImageResource(R.mipmap.bottom_back_white);

            mMonthAlert.show();
            setAlertWidth(mMonthAlert, 1.5f);
        } else if (id == mOrder_address_rl.getId()) {//跳转到地址列表页面
            if (mAddressList.size() != 0) {
                Intent intent = new Intent(this, OrderAddressActivity.class);
                // intent.putExtra("ID",ID);
                startActivityForResult(intent, 123);
            } else {
                Toast.makeText(this, "抱歉，无法获取地址", Toast.LENGTH_SHORT).show();
            }


        } else if (id == mSearch_btn.getId()) {//查询账单
            MyDialog.showDialog(this);
            mList.clear();
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
            if (mMonth_tv.getText().toString().equals("全部")) {
                mHttptools.getMoneyByYearMonthAddress(mHandler, UserInfo.userToken, mAddress_tv.getText().toString(), mYear_tv.getText().toString(), "13");
            } else {
                mHttptools.getMoneyByYearMonthAddress(mHandler, UserInfo.userToken, mAddress_tv.getText().toString(), mYear_tv.getText().toString(), mMonth_tv.getText().toString());

            }

        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, float a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels / a);
        alert.getWindow().setAttributes(p);//设置生效
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            result = (Result) data.getSerializableExtra("result");
            if (result != null) {
                mAddressCity_tv.setText(result.getCity());
                mAddress_tv.setText(result.getDetailAddress());
                ID = result.getId();
            } else {//执行返回键或者返回按钮
                isFlag=false;
                mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址接口
            }
            Log.e("回调onActivityResult", "wuye");

        }
    }

}
