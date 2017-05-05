package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.MyExpandableAda;
import com.home.wanyu.apater.RecordAda;
import com.home.wanyu.apater.RecordListviewAda;
import com.home.wanyu.apater.RepairAda;
import com.home.wanyu.apater.RepairAddImgAda;
import com.home.wanyu.myview.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RepairActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private GridView mGridview;
    private RepairAda mAdapter;

    private LinearLayout mRepair_ll, mRecord_ll;//我要报修，报修记录
    private TextView mRepair_tv, mRecord_tv;
    private ImageView mRepair_img, mRecord_img;

    private RelativeLayout mRepair_category_rl, mRepair_message_rl;//我要报修的全部类型，我要报修填写的信息

    private GridView mImg_gridview;
    private RepairAddImgAda mImgAdapter;
    private ArrayList<String> mImgList = new ArrayList<>();

    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;

    private GridView mRecordGridView;
    private RecordAda mRecordAda;
    private RelativeLayout mRecord_category_rl, mRecord_message_rl;//报修记录中的全部类型，报修记录中的详细信息

    private ScrollView mScroll;
    private MyListView mRecord_Listview;
    private RecordListviewAda mRecordListviewAda;

    private RelativeLayout mTime_rl;
    private View mTimeAlertView;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private TextView mTimeSure, mTimeCancle;
    private AlertDialog.Builder mTimeBuilder;
    private AlertDialog mTimeAlert;
    private Calendar mClaendar;
    private int year, month,twoMonth, day,hour,minute;
    private int mYear,mMonth,mDay,mHour,mMinute;
    private TextView mTime_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        initView();
    }

    public void initView() {

        mback = (ImageView) findViewById(R.id.repair_back);
        mback.setOnClickListener(this);
        //报修类型的gridview
        mGridview = (GridView) findViewById(R.id.repair_gridview);
        mAdapter = new RepairAda(this);
        mGridview.setAdapter(mAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRepair_category_rl.setVisibility(View.GONE);
                mRepair_message_rl.setVisibility(View.VISIBLE);
            }
        });

        mRepair_ll = (LinearLayout) findViewById(R.id.repair_ll);//我要报修
        mRecord_ll = (LinearLayout) findViewById(R.id.record_ll);//报修记录
        mRepair_ll.setOnClickListener(this);
        mRecord_ll.setOnClickListener(this);
        //切换我要报修和报修记录时需要改变的背景和文字颜色
        mRepair_tv = (TextView) findViewById(R.id.repair_tv);
        mRepair_img = (ImageView) findViewById(R.id.repair_img);
        mRecord_tv = (TextView) findViewById(R.id.record_tv);
        mRecord_img = (ImageView) findViewById(R.id.record_img);

        mRepair_category_rl = (RelativeLayout) findViewById(R.id.all_category);//显示的我要报修的全部类型
        mRepair_message_rl = (RelativeLayout) findViewById(R.id.repair_message_rl);//显示的我要报修填写的信息
        //删除图片是的弹框
        builder = new AlertDialog.Builder(this);
        mAlert = builder.create();
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_box, null);
        mAlert.setView(mAlertView);
        mSure = (TextView) mAlertView.findViewById(R.id.sure);
        mCancle = (TextView) mAlertView.findViewById(R.id.cancle);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);

        //添加的图片的gridview
        mImg_gridview = (GridView) findViewById(R.id.add_img_gridview);
        mImgAdapter = new RepairAddImgAda(this, mImgList);
        mImg_gridview.setAdapter(mImgAdapter);
        mImg_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImgList.size() == 0) {
                    //跳转相册或拍照
                    init();
                } else {
                    if (mImgList.size() == 8) {
                        if (position != mImgList.size()) {
                            mAlert.show();
                            mPosition = position;
                        } else {
                            Toast.makeText(RepairActivity.this, "亲，最多选择8张图片哦", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (position == mImgList.size()) {
                            //跳转相册或拍照
                            init();
                        } else {
                            mAlert.show();
                            mPosition = position;
                        }
                    }
                }
            }
        });

        //报修记录中全部类型GridView
        mRecordGridView = (GridView) findViewById(R.id.record_gridview);
        mRecordAda = new RecordAda(this);
        mRecordGridView.setAdapter(mRecordAda);
        //点击显示报修记录详情
        mRecordGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRecord_category_rl.setVisibility(View.GONE);
                mScroll.setVisibility(View.VISIBLE);
            }
        });
        //报修记录中的全部类型
        mRecord_category_rl = (RelativeLayout) findViewById(R.id.all_record_category);
        mScroll = (ScrollView) findViewById(R.id.srcoll_view);
        //报修记录详情中的listview
        mRecord_Listview = (MyListView) findViewById(R.id.record_listview);
        mRecordListviewAda = new RecordListviewAda(this);
        mRecord_Listview.setAdapter(mRecordListviewAda);

        //选择期望处理时间
        mClaendar=Calendar.getInstance();
        //获取年月日时分秒的信息
        year = mClaendar.get(Calendar.YEAR);
        //month从0开始计算(一月month = 0)
        month = mClaendar.get(Calendar.MONTH);
        twoMonth=month+1;
        day = mClaendar.get(Calendar.DAY_OF_MONTH);
        hour=mClaendar.get(Calendar.HOUR_OF_DAY);
        minute=mClaendar.get(Calendar.MINUTE);

        mTime_rl = (RelativeLayout) findViewById(R.id.time_rl);
        mTime_rl.setOnClickListener(this);
        mTime_tv= (TextView) findViewById(R.id.repair_time_tv);
        mTimeBuilder=new AlertDialog.Builder(this);
        mTimeAlert=mTimeBuilder.create();
        mTimeAlertView = LayoutInflater.from(this).inflate(R.layout.repair_time_box, null);
        mTimeAlert.setView(mTimeAlertView);
        mDatePicker= (DatePicker) mTimeAlertView.findViewById(R.id.datepicker);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear=year;
                mMonth=monthOfYear+1;
                mDay=dayOfMonth;
                Toast.makeText(RepairActivity.this,year+"/"+mMonth+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
            }
        });
        mTimePicker= (TimePicker) mTimeAlertView.findViewById(R.id.timepicker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour=hourOfDay;
                mMinute=minute;
                Toast.makeText(RepairActivity.this,hourOfDay+"/"+minute,Toast.LENGTH_SHORT).show();
            }
        });
        mTimeSure= (TextView) mTimeAlertView.findViewById(R.id.time_sure);
        mTimeCancle= (TextView) mTimeAlertView.findViewById(R.id.time_cancle);
        mTimeSure.setOnClickListener(this);
        mTimeCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mRepair_ll.getId()) {//我要报修
            mRepair_ll.setBackgroundResource(R.color.repair_color);
            mRepair_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mRepair_img.setImageResource(R.mipmap.bottom_back_white);

            mRecord_ll.setBackgroundResource(R.color.white);
            mRecord_tv.setTextColor(ContextCompat.getColor(this, R.color.homeFragChangeColor));
            mRecord_img.setImageResource(R.mipmap.bottom_back);

            mRepair_category_rl.setVisibility(View.VISIBLE);
            mRepair_message_rl.setVisibility(View.GONE);
            mRecord_category_rl.setVisibility(View.GONE);
            mScroll.setVisibility(View.GONE);
        } else if (id == mRecord_ll.getId()) {//报修记录
            mRepair_ll.setBackgroundResource(R.color.white);
            mRepair_tv.setTextColor(ContextCompat.getColor(this, R.color.homeFragChangeColor));
            mRepair_img.setImageResource(R.mipmap.bottom_back);

            mRecord_ll.setBackgroundResource(R.color.repair_color);
            mRecord_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mRecord_img.setImageResource(R.mipmap.bottom_back_white);

            mRepair_category_rl.setVisibility(View.GONE);
            mRepair_message_rl.setVisibility(View.GONE);
            mScroll.setVisibility(View.GONE);
            mRecord_category_rl.setVisibility(View.VISIBLE);

        } else if (id == mSure.getId()) {//确定删除图片
            mImgList.remove(mPosition);
            mImgAdapter.setmList(mImgList);
            mImgAdapter.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消
            mAlert.dismiss();
        } else if (id == mTime_rl.getId()) {//选择日期
            mTimeAlert.show();
        }else if (id == mTimeSure.getId()) {//确定选择日期
            if (mYear==0){
                if (mHour==0){
                    mTime_tv.setText(year+"-"+twoMonth+"-"+day+" "+hour+":"+minute+":"+"00");
                }else {
                    mTime_tv.setText(year+"-"+twoMonth+"-"+day+" "+mHour+":"+mMinute+":"+"00");
                }
            }else {
                if (mHour==0){
                    mTime_tv.setText(mYear+"-"+mMonth+"-"+mDay+" "+hour+":"+minute+":"+"00");
                }else {
                    mTime_tv.setText(mYear+"-"+mMonth+"-"+mDay+" "+mHour+":"+mMinute+":"+"00");
                }
            }
            mTimeAlert.dismiss();

        }else if (id == mTimeCancle.getId()) {//取消选择日期
            mTimeAlert.dismiss();
        }
    }

    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;

    //权限判断
    public void init() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            //如果没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                ActivityCompat.requestPermissions(this,
                        //在这个数组中可以添加很多权限
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                startSelectImgActivity();
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity();
        }
    }

    //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startSelectImgActivity();
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //跳转到选择图片的页面
    public void startSelectImgActivity() {
        Intent intent = new Intent(this, SelectImgActivity.class);
        startActivityForResult(intent, 100);
    }

    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");
            if (mImgList.size() != 8) {
                for (int i = 0; i < list.size(); i++) {
                    mImgList.add(list.get(i));
                    if (mImgList.size() == 8) {
                        break;
                    }
                }
                mImgAdapter.setmList(mImgList);
                mImgAdapter.notifyDataSetChanged();
            }
        }
    }
}
