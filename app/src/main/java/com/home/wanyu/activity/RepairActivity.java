package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.MyExpandableAda;
import com.home.wanyu.apater.RecordAda;
import com.home.wanyu.apater.RecordListviewAda;
import com.home.wanyu.apater.RepairAda;
import com.home.wanyu.apater.RepairAddImgAda;
import com.home.wanyu.bean.Record.RecordBean;
import com.home.wanyu.bean.repairType.Result;
import com.home.wanyu.bean.repairType.Root;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myUtils.NetWorkMyUtils;
import com.home.wanyu.myview.MyListView;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepairActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private GridView mGridview;
    private List<Result> mRepairList = new ArrayList<>();
    private RepairAda mAdapter;
    private TextView mRepair_Type_tv;
    private TextView mRepair_hostory_btn;

    private LinearLayout mRepair_ll, mRecord_ll;//我要报修，报修记录
    private TextView mRepair_tv, mRecord_tv;
    private ImageView mRepair_img, mRecord_img;

    private LinearLayout mRepair_category_rl;
    private RelativeLayout mRepair_message_rl;//我要报修的全部类型，我要报修填写的信息

    private GridView mImg_gridview;
    private RepairAddImgAda mImgAdapter;
    private ArrayList<String> mImgList = new ArrayList<>();

    private RelativeLayout mImgViewPager_rl, m_all_rl;
    private ViewPager mImgViewPager;
    private TextView mImg_Cancle_btn;

    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;

    private GridView mRecordGridView;
    private RecordAda mRecordAda;
    private List<RecordBean> mRecordList = new ArrayList<>();
    private RelativeLayout mRecord_category_rl, mRecord_message_rl;//报修记录中的全部类型，报修记录中的详细信息

    private SwipeRefreshLayout mRecord_refresh;
    private MyListView mRecord_Listview;
    private List<com.home.wanyu.bean.RecordMsg.Result> mRecordMsgList = new ArrayList<>();
    private RecordListviewAda mRecordListviewAda;


    private TextView mSure_time, mCancle_time;
    private RelativeLayout mTime_rl;
    private TextView mTime_tv;
    private int year;
    private List<String> mDateList = new ArrayList<>();
    private List<String> mCheckDateList = new ArrayList<>();
    private List<String> mTimeList = new ArrayList<>();
    private List<String> mCheckTimeList = new ArrayList<>();
    private WheelView mDateWheelView, mTimeWheelView;
    private MyWheelAdapter50 mDateWheelAda, mTimeWheelAda;
    private int datePosition = 0, timePosition = 0;
    private int sureDatePostion = 0, sureTimePosition = 0;
    private Calendar mCalendar;
    private PopupWindow mPopupwindow;
    private LinearLayout mWater_ll,mHouse_ll,mTree_ll;
    private ImageView mWater_img,mHouse_img,mTree_img;
    private View mView;
    private EditText mName, mPhone, mAddress, mDetails;
    private Button mSubmit_btn;
    private int mID = -1;
    private int mRecord_id = -1;
    private AjaxParams ajaxParams;
    private int start = 0;
    private int limit = 10;
    private int moreFlag = -1;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;
    private HttpTools mhttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 105) {//获取报修类型
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mRepairList = root.getResult();
                        mAdapter.setList(mRepairList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else if (msg.what == 107) {//获取报修记录详情列表
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.RecordMsg.Root) {
                    com.home.wanyu.bean.RecordMsg.Root root = (com.home.wanyu.bean.RecordMsg.Root) o;
                    if (moreFlag == 2) {//加载跟多
                        List<com.home.wanyu.bean.RecordMsg.Result> list = new ArrayList<>();
                        if (root.getResult() != null) {
                            list = root.getResult();
                            mRecordMsgList.addAll(list);
                            mRecordListviewAda.setList(mRecordMsgList);
                            mRecordListviewAda.notifyDataSetChanged();
                        }
                    } else if (moreFlag == 1) {//下拉刷新
                        mRecord_refresh.setRefreshing(false);
                        if (root.getResult() != null) {
                            mRecordMsgList = root.getResult();
                            if (mRecordMsgList.size() == 0) {
                                mRecord_Listview.setVisibility(View.GONE);
                                //    mNoData_rl.setVisibility(View.VISIBLE);
                                Toast.makeText(RepairActivity.this, "抱歉,没有数据哦", Toast.LENGTH_SHORT).show();
                            } else {
                                //    mNoData_rl.setVisibility(View.GONE);
                                mRecord_Listview.setVisibility(View.VISIBLE);
                                mRecordListviewAda.setList(mRecordMsgList);
                                mRecordListviewAda.notifyDataSetChanged();
                            }

                        }
                    } else if (moreFlag == 3) {//点击报修记录类型
                        mRecord_refresh.setRefreshing(false);
                        if (root.getResult() != null) {
                            mRecordMsgList = root.getResult();
                            if (mRecordMsgList.size() == 0) {
                                mRecord_Listview.setVisibility(View.GONE);
                                Toast.makeText(RepairActivity.this, "抱歉,没有数据哦", Toast.LENGTH_SHORT).show();
                                //  mNoData_rl.setVisibility(View.VISIBLE);
                            } else {
                                //  mNoData_rl.setVisibility(View.GONE);
                                mRecord_Listview.setVisibility(View.VISIBLE);
                                mRecordListviewAda.setList(mRecordMsgList);
                                mRecordListviewAda.notifyDataSetChanged();
                            }
                        }
                    }


                    if (root.getResult().size() < 10) {
                        mMore_rl.setVisibility(View.GONE);
                        mBar.setVisibility(View.INVISIBLE);
                    } else {
                        mMore_rl.setVisibility(View.VISIBLE);
                        mBar.setVisibility(View.INVISIBLE);
                    }


                }
            } else if (msg.what == 201) {
                mRecordMsgList.clear();
                mRecordListviewAda.setList(mRecordMsgList);
                mRecordListviewAda.notifyDataSetChanged();
                mRecord_refresh.setRefreshing(false);
                Toast.makeText(RepairActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 106) {//提交报修信息
                Object o = msg.obj;

                if (o != null && o instanceof com.home.wanyu.bean.repairSubmitType.Root) {
                    com.home.wanyu.bean.repairSubmitType.Root root = (com.home.wanyu.bean.repairSubmitType.Root) o;
                    if (((com.home.wanyu.bean.repairSubmitType.Root) o).getCode().equals("0")) {
                        mSubmit_btn.setClickable(true);
                        MyDialog.stopDia();
                        Toast.makeText(RepairActivity.this, "提交信息成功", Toast.LENGTH_SHORT).show();
                        mName.setText("");
                        mPhone.setText("");
                        mAddress.setText("");
                        mDetails.setText("");
                        mImgList.clear();
                        mImgAdapter.setmList(mImgList);
                        mImgAdapter.notifyDataSetChanged();
                        mTime_tv.setText("请选择期望处理时间");
                    } else {
                        mSubmit_btn.setClickable(true);
                        MyDialog.stopDia();
                        Toast.makeText(RepairActivity.this, "提交信息失败:" + root.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private ImageView mNetWorkBack;
    private TextView mNetWorkTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetWorkMyUtils.isNetworkConnected(this)) {
            setContentView(R.layout.activity_repair);
            mhttptools = HttpTools.getHttpToolsInstance();
            mhttptools.getRepairType(mHandler);//获取报修类型
            ajaxParams = new AjaxParams();
            initView();
        } else {
            setContentView(R.layout.no_network);
            mNetWorkBack = (ImageView) findViewById(R.id.network_back);
            mNetWorkBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mNetWorkTitle = (TextView) findViewById(R.id.network_title_msg);
            mNetWorkTitle.setText(R.string.property_repair);
        }

    }

    public void initView() {

        mback = (ImageView) findViewById(R.id.repair_back);
        mback.setOnClickListener(this);
        //报修记录
        mRepair_hostory_btn = (TextView) findViewById(R.id.repair_hostory_btn);
        mRepair_hostory_btn.setOnClickListener(this);
        //报修类型LinearLayout
        mWater_ll= (LinearLayout) findViewById(R.id.water_ll);
        mHouse_ll= (LinearLayout) findViewById(R.id.house_ll);
        mTree_ll= (LinearLayout) findViewById(R.id.tree_ll);
        mWater_ll.setOnClickListener(this);
        mHouse_ll.setOnClickListener(this);
        mTree_ll.setOnClickListener(this);
        //报修类型图片
        mWater_img= (ImageView) findViewById(R.id.repair_water_img);
        mHouse_img= (ImageView) findViewById(R.id.repair_house_img);
        mTree_img= (ImageView) findViewById(R.id.repair_tree_img);
        //报修类型的gridview
        mGridview = (GridView) findViewById(R.id.repair_gridview);
        mAdapter = new RepairAda(this, mRepairList);
        mGridview.setAdapter(mAdapter);
        mRepair_Type_tv = (TextView) findViewById(R.id.tv_message);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRepair_category_rl.setVisibility(View.GONE);
                mRepair_message_rl.setVisibility(View.VISIBLE);
                mRepair_Type_tv.setText(mRepairList.get(position).getTypeName());
                mID = mRepairList.get(position).getId();
                Log.e("mID==", mID + "");

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

        mRepair_category_rl = (LinearLayout) findViewById(R.id.all_category);//显示的我要报修的全部类型
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
        mRecordList.add(new RecordBean("待处理", 1));
        mRecordList.add(new RecordBean("处理中", 2));
        mRecordList.add(new RecordBean("已完成", 3));
        mRecordList.add(new RecordBean("已取消", 4));
        mRecordGridView = (GridView) findViewById(R.id.record_gridview);
        mRecordAda = new RecordAda(this, mRecordList);
        mRecordGridView.setAdapter(mRecordAda);
        //点击显示报修记录详情
        mRecordGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moreFlag = 3;
                mRecord_refresh.setRefreshing(true);
                mRecord_category_rl.setVisibility(View.GONE);
                mRecord_refresh.setVisibility(View.VISIBLE);
                mRecord_id = mRecordList.get(position).getId();
                //获取报修记录详情
                mhttptools.getRecordMsg(mHandler, UserInfo.userToken, mRecordList.get(position).getId(), start, limit);
            }
        });
        //报修记录中的全部类型
        mRecord_category_rl = (RelativeLayout) findViewById(R.id.all_record_category);
        mRecord_refresh = (SwipeRefreshLayout) findViewById(R.id.record_swipe_refresh);
        mRecord_refresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        //刷新记录列表
        mRecord_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moreFlag = 1;
                start = 0;
                mhttptools.getRecordMsg(mHandler, UserInfo.userToken, mRecord_id, start, limit);

            }
        });


        //报修记录加载更多
        mMore_rl = (RelativeLayout) findViewById(R.id.more_relative);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.pbLocate);

        //选择时间
        mName = (EditText) findViewById(R.id.repair_name);
        mPhone = (EditText) findViewById(R.id.repair_phone);
        mAddress = (EditText) findViewById(R.id.repair_address);
        mDetails = (EditText) findViewById(R.id.repair_message_edit);

        mSubmit_btn = (Button) findViewById(R.id.repair_submit);
        mSubmit_btn.setOnClickListener(this);//提交报修详情
        mTime_rl = (RelativeLayout) findViewById(R.id.time_rl);
        mTime_rl.setOnClickListener(this);
        mTime_tv = (TextView) findViewById(R.id.repair_time_tv);


        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH)+1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int maxDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //显示时候日期
        for (int i = day; i <= maxDay; i++) {
            String str = new String();
            if (month < 10) {
                str = "0" + month + "月";
                if (i < 10) {
                    str = str + "0" + i + "日";
                } else {
                    str = str + i + "日";
                }
            } else {
                str = month + "月";
                if (i < 10) {
                    str = str + "0" + i + "日";
                } else {
                    str = str + i + "日";
                }
            }
            mDateList.add(str);
            Log.e("日期：", str);
        }
        //提交时候判断日期
        for (int i = day; i <= maxDay; i++) {
            String str = new String();
            if (month < 10) {
                str = "-0" + month;
                if (i < 10) {
                    str = str + "-0" + i;
                } else {
                    str = str + "-" + i;
                }
            } else {
                str = "-" + month;
                if (i < 10) {
                    str = str + "-0" + i;
                } else {
                    str = str + "-" + i;
                }
            }
            mCheckDateList.add(year + str);
            Log.e("判断日期：", year + str);

        }

        //显示时候的时间
        mTimeList.add("09:00-13:00");
        mTimeList.add("13:00-18:00");
        mTimeList.add("18:00-22:00");

        mCheckTimeList.add("13:00:00");
        mCheckTimeList.add("18:00:00");
        mCheckTimeList.add("22:00:00");
        mView = LayoutInflater.from(this).inflate(R.layout.repair_popupwindow_select_time, null);
        mDateWheelView = (WheelView) mView.findViewById(R.id.date_wheel);
        mTimeWheelView = (WheelView) mView.findViewById(R.id.time_wheel);
        mDateWheelAda = new MyWheelAdapter50(this, mDateList, "");
        mTimeWheelAda = new MyWheelAdapter50(this, mTimeList, "");
        mDateWheelView.setViewAdapter(mDateWheelAda);
        mTimeWheelView.setViewAdapter(mTimeWheelAda);
        //日期滚动
        mDateWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                datePosition = newValue;
            }
        });
        //时间滚动
        mTimeWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                timePosition = newValue;
            }
        });

        //确定时间，取消时间
        mSure_time = (TextView) mView.findViewById(R.id.community_time_sure);
        mSure_time.setOnClickListener(this);
        mCancle_time = (TextView) mView.findViewById(R.id.community_time_cancle);
        mCancle_time.setOnClickListener(this);


        //报修记录没有数据时现实的布局
        //  mNoData_rl= (RelativeLayout) findViewById(R.id.no_data_repair);

        //点击GridView中显示图片
        m_all_rl = (RelativeLayout) findViewById(R.id.m_all_rl);
        mImgViewPager_rl = (RelativeLayout) findViewById(R.id.img_viewpager_rl);
        mImgViewPager = (ViewPager) findViewById(R.id.img_viewpager);
        mImg_Cancle_btn = (TextView) findViewById(R.id.img_cancle);
        mImg_Cancle_btn.setOnClickListener(this);

        //报修记录详情中的listview
        mRecord_Listview = (MyListView) findViewById(R.id.record_listview);
        mRecordListviewAda = new RecordListviewAda(this, mRecordMsgList, mImgViewPager, mImgViewPager_rl, m_all_rl, mMore_rl);
        mRecord_Listview.setAdapter(mRecordListviewAda);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mRepair_hostory_btn.getId()) {//报修记录
            startActivity(new Intent(RepairActivity.this, RepairHostoryActivity.class));
        } else if (id==mWater_ll.getId()){//水电燃气
            mWater_img.setImageResource(R.mipmap.repair_water);
            mHouse_img.setImageResource(R.mipmap.repair_house_no);
            mTree_img.setImageResource(R.mipmap.repair_tree_no);
        }else if (id==mHouse_ll.getId()){//房屋维修
            mWater_img.setImageResource(R.mipmap.repair_water_no);
            mHouse_img.setImageResource(R.mipmap.repair_house);
            mTree_img.setImageResource(R.mipmap.repair_tree_no);
        }else if (id==mTree_ll.getId()){//公共设施
            mWater_img.setImageResource(R.mipmap.repair_water_no);
            mHouse_img.setImageResource(R.mipmap.repair_house_no);
            mTree_img.setImageResource(R.mipmap.repair_tree);
        }



        else if (id == mRepair_ll.getId()) {//我要报修
            mRepair_ll.setBackgroundResource(R.color.repair_color);
            mRepair_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mRepair_img.setImageResource(R.mipmap.bottom_back_white);

            mRecord_ll.setBackgroundResource(R.color.white);
            mRecord_tv.setTextColor(ContextCompat.getColor(this, R.color.homeFragChangeColor));
            mRecord_img.setImageResource(R.mipmap.bottom_back);

            mRepair_category_rl.setVisibility(View.VISIBLE);
            mRepair_message_rl.setVisibility(View.GONE);
            mRecord_category_rl.setVisibility(View.GONE);
            mRecord_refresh.setVisibility(View.GONE);
            //mNoData_rl.setVisibility(View.GONE);
        } else if (id == mRecord_ll.getId()) {//报修记录
            mRepair_ll.setBackgroundResource(R.color.white);
            mRepair_tv.setTextColor(ContextCompat.getColor(this, R.color.homeFragChangeColor));
            mRepair_img.setImageResource(R.mipmap.bottom_back);

            mRecord_ll.setBackgroundResource(R.color.repair_color);
            mRecord_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mRecord_img.setImageResource(R.mipmap.bottom_back_white);

            mRepair_category_rl.setVisibility(View.GONE);
            mRepair_message_rl.setVisibility(View.GONE);
            mRecord_refresh.setVisibility(View.GONE);
            mRecord_category_rl.setVisibility(View.VISIBLE);
            // mNoData_rl.setVisibility(View.GONE);

        } else if (id == mSure.getId()) {//确定删除图片
            mImgList.remove(mPosition);
            mImgAdapter.setmList(mImgList);
            mImgAdapter.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消删除图片
            mAlert.dismiss();
        } else if (id == mTime_rl.getId()) {//选择日期
            mDateWheelView.setCurrentItem(sureDatePostion);//将日期设置到当初选择的日期
            mTimeWheelView.setCurrentItem(sureTimePosition);//将时间设置到当初选择的时间
            showPopuWindow();
        } else if (id == mSure_time.getId()) {//确定选择日期
            // mTime_tv.setText(mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" + mDatePicker.getDayOfMonth() + " " + mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute() + ":" + "00");
            mTime_tv.setText(year + "年" + mDateList.get(datePosition) + " " + mTimeList.get(timePosition));
            sureDatePostion = datePosition;//点击确定时的日期下标
            sureTimePosition = timePosition;//点击确定时的时间下标
            mPopupwindow.dismiss();
        } else if (id == mCancle_time.getId()) {//取消选择日期
            mPopupwindow.dismiss();
        } else if (id == mSubmit_btn.getId()) {//提交报修详情
            if (checkStartEndTime(mCheckDateList.get(sureDatePostion)+" "+mCheckTimeList.get(sureTimePosition))) {
                if (!getName().equals("") && !getPhone().equals("") && !getAddresss().equals("") && !getDetails().equals("")) {
                    mSubmit_btn.setClickable(false);
                    MyDialog.showDialog(this);
                    ajaxParams.put("cname", getName());
                    ajaxParams.put("telephone", getPhone());
                    ajaxParams.put("address", getAddresss());
                    ajaxParams.put("type", String.valueOf(mID));
                    ajaxParams.put("processingTime", mTime_tv.getText().toString());
                    ajaxParams.put("details", getDetails());
                    for (int i = 0; i < mImgList.size(); i++) {
                        try {
                            ajaxParams.put("图片" + i, transImage(mImgList.get(i), ImgUitls.getWith(this), ImgUitls.getHeight(this), 90, "图片" + i));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    //提交数据
                    mhttptools.submitRepairTypr(mHandler, ajaxParams);
                } else {
                    Toast.makeText(this, "请补全报修信息", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(this, "请选择正确的时间", Toast.LENGTH_SHORT).show();
            }
        } else if (id == mMore_rl.getId()) {//报修记录详情中加载更多
            start += 10;
            moreFlag = 2;//代表加载更多
            mBar.setVisibility(View.VISIBLE);
            mhttptools.getRecordMsg(mHandler, UserInfo.userToken, mRecord_id, start, limit);
        } else if (id == mImg_Cancle_btn.getId()) {//隐藏viewpager
            //m_all_rl.setVisibility(View.VISIBLE);
            mImgViewPager_rl.setVisibility(View.GONE);
        }
    }

    /**
     * @param pathName 图片路径
     * @param width    屏幕宽度
     * @param height   屏幕 高度
     * @param quality  图片质量
     * @param fileName 图片名称
     * @return
     */
    public File transImage(String pathName, int width, int height, int quality, String fileName) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth - 0.1f;
            float scaleHeight = (float) height / bitmapHeight - 0.1f;
            Log.e("bitmapWidth", bitmapWidth + "");
            Log.e("bitmapHeight", bitmapHeight + "");
            Log.e("scaleWidth", scaleWidth + "");
            Log.e("scaleHeight", scaleHeight + "");
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            File file = null;
            //存储媒体已经挂载，并且挂载点可读/写
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//可写
                //保存
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：", Environment.DIRECTORY_PICTURES);

            } else {
                file = new File(getFilesDir(), fileName + ".jpg");
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
                bos.flush();
                bos.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }
            Log.i("--file-大小----", file.length() / 1024 + "");
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
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
        intent.putExtra("num", 8);
        startActivityForResult(intent, 100);
    }

    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");
            Log.e("大小", list.size() + "");
            if (mImgList.size() != 8) {
                for (int i = 0; i < list.size(); i++) {
                    mImgList.add(list.get(i));
                    if (mImgList.size() == 8) {
                        break;
                    }
                }
                mImgAdapter = new RepairAddImgAda(this, mImgList);
                mImg_gridview.setAdapter(mImgAdapter);

            }
        }
    }


    private void showPopuWindow() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_repair);
        mPopupwindow = new PopupWindow(mView);
        //设置弹框的款，高
        mPopupwindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupwindow.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupwindow.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        // mPopupwindow.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mPopupwindow.setAnimationStyle(R.style.popup3_anim);
        //相对于父控件的位置
        mPopupwindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    public String getName() {
        if (mName.getText().toString().equals("")) {
            return "";
        }
        return mName.getText().toString();
    }

    public String getAddresss() {
        if (mAddress.getText().toString().equals("")) {
            return "";
        }
        return mAddress.getText().toString();

    }

    public String getPhone() {
        if (!checkPhone(mPhone.getText().toString())) {
            return "";
        }
        return mPhone.getText().toString();

    }

    public String getDetails() {
        if (mDetails.getText().toString().equals("")) {
            return "";
        }
        return mDetails.getText().toString();

    }

    public boolean checkStartEndTime(String endtime) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            Date endDate = dateformat.parse(endtime);

            if (endDate.getTime() - System.currentTimeMillis() > 0) {
                //Toast.makeText(this, "时间正确", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(this, "时间错误", Toast.LENGTH_SHORT).show();
            return false;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 检查手机号
     *
     * @param phonenum
     * @return true 代表手机号正确
     */
    public boolean checkPhone(String phonenum) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phonenum);
        b = m.matches();
        return b;
    }
}
