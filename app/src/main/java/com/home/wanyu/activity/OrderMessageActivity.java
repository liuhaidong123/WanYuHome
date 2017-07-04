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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.OkHttpManager;
import com.home.wanyu.HttpUtils.UrlTools;
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
    private View mYear_view_line, mMonth_view_line;

    private ImageView mYear_img, mMonth_img;
    private MyListView mListview;
    private OrderMsgAda mAdapter;
    private List<ItemsYear> mList = new ArrayList<>();

    private View mYearView;
    private ListView mYearListview;
    private OrderYearAda mYearAda;
    private List<String> mYearList = new ArrayList<>();

    private View mMonthView;
    private OrderMonthAda mMonthAda;
    private List<String> mMonthList = new ArrayList<>();
    private ListView mMonthListview;


    private PopupWindow mYearPopupWindow;
    private PopupWindow mMonthPopupWindow;
    private TextView mAddressCity_tv, mAddress_tv;
    private Calendar mCalendar;
    private List<Result> mAddressList = new ArrayList<>();
    private Result result;
    private HttpTools mHttptools;
    // private OkHttpManager okHttpManager;
    // private Gson mGson;
    private long ID = -1;
    private boolean isFlag = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {//获取地址
                // String str = (String) msg.obj;
                //Object o = mGson.fromJson(str, Root.class);
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
        //mGson = new Gson();
        //okHttpManager = OkHttpManager.getInstance();
        //okHttpManager.getMethod(UrlTools.BASE + UrlTools.HAVE_USER_ADDRESS + "token=" + UserInfo.userToken, "获取物业账单地址", mHandler, 100);
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
        mYear_view_line = findViewById(R.id.year_view_line);


        mYear_img = (ImageView) findViewById(R.id.order_year_img);
        //年份弹框
        mYearView = LayoutInflater.from(this).inflate(R.layout.order_year_item, null);
        mYearListview = (ListView) mYearView.findViewById(R.id.city_alert_listview);
        //选择城市
        mYearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mYear_tv.setText(mYearAda.getItem(position).toString());
                mYearPopupWindow.dismiss();
                // mYearAlert.dismiss();
            }
        });

        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        for (int i = 0; i < 3; i++) {
            mYearList.add(year - i + "");
        }
        mYearAda = new OrderYearAda(this, mYearList);
        mYearListview.setAdapter(mYearAda);



        //月份
        mMonth_ll = (LinearLayout) findViewById(R.id.order_month_ll);
        mMonth_ll.setOnClickListener(this);
        mMonth_tv = (TextView) findViewById(R.id.order_month_tv);
        mYue_tv = (TextView) findViewById(R.id.order_yue_tv);
        mMonth_img = (ImageView) findViewById(R.id.order_month_img);
        mMonth_view_line = findViewById(R.id.month_view_line);
        //月份弹框
        mMonthView = LayoutInflater.from(this).inflate(R.layout.order_month_item, null);
        mMonthListview = (ListView) mMonthView.findViewById(R.id.area_alert_listview);

        for (int i = 0; i < 13; i++) {
            if (i == 0) {
                mMonthList.add("全部");
            } else {
                mMonthList.add(i + "");
            }
        }

        mMonthAda = new OrderMonthAda(this, mMonthList);
        mMonthListview.setAdapter(mMonthAda);
        //选择小区
        mMonthListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMonth_tv.setText(mMonthAda.getItem(position).toString());
                mMonthPopupWindow.dismiss();
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
            mYear_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mNian_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mYear_img.setImageResource(R.mipmap.order_click_down_blue);
            mYear_view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.c2a5));

            mMonth_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mYue_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mMonth_img.setImageResource(R.mipmap.bottom_back);
            mMonth_view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

            showPopuWindowYear();
        } else if (id == mMonth_ll.getId()) {//选择月份
            mYear_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mNian_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            mYear_img.setImageResource(R.mipmap.bottom_back);
            mYear_view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

            mMonth_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mYue_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mMonth_img.setImageResource(R.mipmap.order_click_down_blue);
            mMonth_view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.c2a5));
            showPopuWindowMonth();
        } else if (id == mOrder_address_rl.getId()) {//跳转到地址列表页面
            if (mAddressList.size() != 0) {
                Intent intent = new Intent(this, OrderAddressActivity.class);
                intent.putExtra("addressId",ID);
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
                isFlag = false;
                mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址接口
                //okHttpManager.getMethod(UrlTools.BASE + UrlTools.HAVE_USER_ADDRESS + "token=" + UserInfo.userToken, "获取物业账单地址", mHandler, 100);
            }
            Log.e("回调onActivityResult", "wuye");

        }
    }


    //年份popuwindow显示

    private void showPopuWindowYear() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);

        mYearPopupWindow = new PopupWindow(mYearView);
        //设置弹框的款，高
        mYearPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //宽度为他的相对容器的高度
        mYearPopupWindow.setWidth(mYear_ll.getWidth());
        mYearPopupWindow.setFocusable(true);//如果有交互需要设置焦点为true
        mYearPopupWindow.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mYearPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mYearPopupWindow.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mYearPopupWindow.showAsDropDown(mYear_ll, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mYearPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    //月份popuwindow显示
    private void showPopuWindowMonth() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        mMonthPopupWindow = new PopupWindow(mMonthView);
        //设置弹框的款，高
        mMonthPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //他相对容器的宽度
        mMonthPopupWindow.setWidth(mMonth_ll.getWidth());
        mMonthPopupWindow.setFocusable(true);//如果有交互需要设置焦点为true
        mMonthPopupWindow.setOutsideTouchable(true);//设置内容外可以点击
        // 设置背景，否则点击内容外，关闭弹窗失效
        mMonthPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mMonthPopupWindow.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mMonthPopupWindow.showAsDropDown(mMonth_ll, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mMonthPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

}
