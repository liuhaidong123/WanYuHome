package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.getAreaActivityLike.Root;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myUtils.NetWorkMyUtils;

import net.tsz.afinal.http.AjaxParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommunityPostActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private PopupWindow mPopupwindow;
    private View mView;
    private TextView tv;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private WheelView mDateWheelView, mHourWheelView, mMinuteWheelView;
    private MyWheelAdapter50 mDateWheelAda, mHourWheelAda, mMinuiteWheelAda;
    private int datePosition = 0, hourPosition = 0, minutePosition = 0;
    private int dateSureStartPosition = 0, hourSureStartPosition = 0, minuteSureStartPosition = 0;
    private int dateSureEndPosition = 0, hourSureEndPosition = 0, minuteSureEndPosition = 0;
    private int year;
    private List<String> mDateList = new ArrayList<>();
    private List<String> mHourList = new ArrayList<>();
    private List<String> mMinuteList = new ArrayList<>();
    private List<String> mCheckDateList = new ArrayList<>();
    private Calendar mCalendar;
    private TextView mSure_time, mCancle_time;

    private LinearLayout mShow_Start_time, mShow_End_time;
    private TextView mStart_time, mEnd_time;
    private int mFlag = -1;

    //    private int mStartYear;
//    private int mEndYear;
    private TextView mPost_btn;
    private EditText mTitle, mAddress, mPerson, mPhone, mContent;
    private ImageView mOther_area_btn;
    private boolean mImgSelect = false;

    private HttpTools mhttptools;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 124) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityPostActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CommunityPostActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_community);
        mhttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.community_post_back);
        mBack.setOnClickListener(this);


        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int maxDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.e("当前小时-", hour + "");
        Log.e("当前分钟-", minute + "");
        //选择日期view
        mView = LayoutInflater.from(this).inflate(R.layout.community_post_time, null);
        tv = (TextView) mView.findViewById(R.id.tv);
        //日期滚动
        mDateWheelView = (WheelView) mView.findViewById(R.id.date_wheel_c);
        mDateWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                datePosition = newValue;
            }
        });
        //小时滚动
        mHourWheelView = (WheelView) mView.findViewById(R.id.hour_wheel_c);
        mHourWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                hourPosition = newValue;
            }
        });
        //分钟滚动
        mMinuteWheelView = (WheelView) mView.findViewById(R.id.minute_wheel_c);
        mMinuteWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                minutePosition = newValue;
            }
        });
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
                str = "0" + month;
                if (i < 10) {
                    str = str + "-0" + i;
                } else {
                    str = str + "-" + i;
                }
            } else {
                str = "" + month;
                if (i < 10) {
                    str = str + "-0" + i;
                } else {
                    str = str + "-" + i;
                }
            }
            mCheckDateList.add(str);
            Log.e("判断日期：", year + "-" + str);

        }
        //显示小时
        for (int i = 0; i < 24; i++) {
            String str = new String();
            if (i < 10) {
                str = "0" + i;
            } else {
                str = i + "";
            }
            mHourList.add(str);
            Log.e("小时:", str);
        }

        //显示分钟
        for (int i = 0; i < 60; i++) {
            String str = new String();
            if (i < 10) {
                str = "0" + i;
            } else {
                str = i + "";
            }
            mMinuteList.add(str);
            Log.e("分钟:", str);
        }

        mDateWheelAda = new MyWheelAdapter50(this, mDateList, "");
        mHourWheelAda = new MyWheelAdapter50(this, mHourList, "");
        mMinuiteWheelAda = new MyWheelAdapter50(this, mMinuteList, "");
        mDateWheelView.setViewAdapter(mDateWheelAda);
        mHourWheelView.setViewAdapter(mHourWheelAda);
        mMinuteWheelView.setViewAdapter(mMinuiteWheelAda);

//        mDatePicker = (DatePicker) mView.findViewById(R.id.community_date_picker);
//        mDatePicker.setMinDate(System.currentTimeMillis());
//        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//            }
//        });
//        mTimePicker = (TimePicker) mView.findViewById(R.id.community_time_picker);
//        mTimePicker.setCurrentHour(hour);
//        mTimePicker.setCurrentMinute(minute);
//        mTimePicker.setIs24HourView(true);
//        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//
//            }
//        });
        //确定时间，取消时间
        mSure_time = (TextView) mView.findViewById(R.id.community_time_sure);
        mSure_time.setOnClickListener(this);
        mCancle_time = (TextView) mView.findViewById(R.id.community_time_cancle);
        mCancle_time.setOnClickListener(this);


        //显示开始活动时间，结束时间
        mShow_Start_time = (LinearLayout) findViewById(R.id.community_start_ll);
        mShow_Start_time.setOnClickListener(this);
        mShow_End_time = (LinearLayout) findViewById(R.id.community_end_ll);
        mShow_End_time.setOnClickListener(this);
        mStart_time = (TextView) findViewById(R.id.start_time_tv);
        mEnd_time = (TextView) findViewById(R.id.end_time_tv);

        //活动标题，地址，人数，电话，内容
        mPost_btn = (TextView) findViewById(R.id.community_post_tv);
        mPost_btn.setOnClickListener(this);
        mTitle = (EditText) findViewById(R.id.community_title_edit);
        mAddress = (EditText) findViewById(R.id.community_address_edit);
        mPerson = (EditText) findViewById(R.id.community_person_edit);
        mPhone = (EditText) findViewById(R.id.community_phone_edit);
        mContent = (EditText) findViewById(R.id.community_content_edit);
        //其他小区可见性
        mOther_area_btn = (ImageView) findViewById(R.id.community_on_off_btn);
        mOther_area_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mShow_Start_time.getId()) {
            mFlag = 1;//开始时间
            tv.setText("开始时间");
            showPopuWindow();
        } else if (id == mShow_End_time.getId()) {
            mFlag = 2;//结束时间
            tv.setText("结束时间");
            showPopuWindow();
        } else if (id == mSure_time.getId()) {
            mPopupwindow.dismiss();

            if (mFlag == 1) {//设置开始时间
                dateSureStartPosition = datePosition;
                hourSureStartPosition = hourPosition;
                minuteSureStartPosition = minutePosition;
                mStart_time.setTextColor(ContextCompat.getColor(this, R.color.title_color));
                mStart_time.setText(mDateList.get(dateSureStartPosition) + " " + mHourList.get(hourSureStartPosition) + ":" + mMinuteList.get(minuteSureStartPosition));
//                String month = "";
//                String day = "";
//                String hour = "";
//                String minute = "";
//                mStartYear = mDatePicker.getYear();
//                if (mDatePicker.getMonth() + 1 < 10) {
//                    month = "0" + (mDatePicker.getMonth() + 1);
//                } else {
//                    month = mDatePicker.getMonth() + 1 + "";
//                }
//
//                if (mDatePicker.getDayOfMonth() < 10) {
//                    day = "0" + mDatePicker.getDayOfMonth();
//                } else {
//                    day = mDatePicker.getDayOfMonth() + "";
//                }
//
//                if (mTimePicker.getCurrentHour() < 10) {
//                    hour = "0" + mTimePicker.getCurrentHour();
//                } else {
//                    hour = "" + mTimePicker.getCurrentHour();
//                }
//
//                if (mTimePicker.getCurrentMinute() < 10) {
//                    minute = "0" + mTimePicker.getCurrentMinute();
//                } else {
//                    minute = "" + mTimePicker.getCurrentMinute();
//                }
//
//                mStart_time.setText(month + "-" + day + " " + hour + ":" + minute);
            } else if (mFlag == 2) {//设置结束时间Start
                dateSureEndPosition = datePosition;
                hourSureEndPosition = hourPosition;
                minuteSureEndPosition = minutePosition;
                mEnd_time.setTextColor(ContextCompat.getColor(this, R.color.title_color));
                mEnd_time.setText(mDateList.get(dateSureEndPosition) + " " + mHourList.get(hourSureEndPosition) + ":" + mMinuteList.get(minuteSureEndPosition));
//                String month = "";
//                String day = "";
//                String hour = "";
//                String minute = "";
//                mEndYear = mDatePicker.getYear();
//                if (mDatePicker.getMonth() + 1 < 10) {
//                    month = "0" + (mDatePicker.getMonth() + 1);
//                } else {
//                    month = mDatePicker.getMonth() + 1 + "";
//                }
//
//                if (mDatePicker.getDayOfMonth() < 10) {
//                    day = "0" + mDatePicker.getDayOfMonth();
//                } else {
//                    day = mDatePicker.getDayOfMonth() + "";
//                }
//
//                if (mTimePicker.getCurrentHour() < 10) {
//                    hour = "0" + mTimePicker.getCurrentHour();
//                } else {
//                    hour = "" + mTimePicker.getCurrentHour();
//                }
//
//                if (mTimePicker.getCurrentMinute() < 10) {
//                    minute = "0" + mTimePicker.getCurrentMinute();
//                } else {
//                    minute = "" + mTimePicker.getCurrentMinute();
//                }
//
//                mEnd_time.setText(month + "-" + day + " " + hour + ":" + minute);
            }
        } else if (id == mCancle_time.getId()) {//取消选择时间
            mPopupwindow.dismiss();
        } else if (id == mPost_btn.getId()) {//发布yyyy-MM-dd HH:mm:ss
            //判断开始时间是否大于当前时间
            if (checkStartEndTime(year + "-" + mCheckDateList.get(dateSureStartPosition) + " " + mHourList.get(hourSureStartPosition) + ":" + mMinuteList.get(minuteSureStartPosition) + ":" + "00")) {
                //判断结束时间是否大于开始时间
                if (checkStartEndTime(year + "-" + mCheckDateList.get(dateSureStartPosition) + " " + mHourList.get(hourSureStartPosition) + ":" + mMinuteList.get(minuteSureStartPosition) + ":" + "00", year + "-" + mCheckDateList.get(dateSureEndPosition) + " " + mHourList.get(hourSureEndPosition) + ":" + mMinuteList.get(minuteSureEndPosition) + ":" + "00")) {
                    Log.e("开始时间", year + "-" + mCheckDateList.get(dateSureStartPosition) + " " + mHourList.get(hourSureStartPosition) + ":" + mMinuteList.get(minuteSureStartPosition) + ":" + "00");
                    Log.e("结束时间", year + "-" + mCheckDateList.get(dateSureEndPosition) + " " + mHourList.get(hourSureEndPosition) + ":" + mMinuteList.get(minuteSureEndPosition) + ":" + "00");
                    if (!(getActTitle().equals("") || getActAddress().equals("") || getActPersonNum().equals("") || getActPhone().equals("") || getActContent().equals(""))) {
                        if (NetWorkMyUtils.isNetworkConnected(this)) {
                            mPost_btn.setClickable(false);
                            AjaxParams ajaxParams = new AjaxParams();
                            ajaxParams.put("activityTheme", getActTitle());
                            ajaxParams.put("activityContent", getActContent());
                            ajaxParams.put("activityAddress", getActAddress());
                            ajaxParams.put("activityNumber", getActPersonNum());
                            ajaxParams.put("activityTelephone", getActPhone());
                            ajaxParams.put("starttimeString", mCheckDateList.get(dateSureStartPosition) + " " + mHourList.get(hourSureStartPosition) + ":" + mMinuteList.get(minuteSureStartPosition));
                            ajaxParams.put("endtimeString", mCheckDateList.get(dateSureEndPosition) + " " + mHourList.get(hourSureEndPosition) + ":" + mMinuteList.get(minuteSureEndPosition));
                            mhttptools.areaActivityPost(mhandler, ajaxParams);
                        } else {
                            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "请填写正确的活动信息", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "请选择正确活动时间", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (id == mOther_area_btn.getId()) {
            if (!mImgSelect) {
                mOther_area_btn.setImageResource(R.mipmap.circle_button_on);
                mImgSelect = true;
            } else {
                mOther_area_btn.setImageResource(R.mipmap.circle_button_off);
                mImgSelect = false;
            }
        }


    }
    //项目中的popuwindow显示

    private void showPopuWindow() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_post_community);
        mPopupwindow = new PopupWindow(mView);
        //设置弹框的款，高
        mPopupwindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupwindow.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupwindow.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mPopupwindow.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
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


    public String getActTitle() {
        if (mTitle.getText().toString().equals("")) {
            return "";
        }
        return mTitle.getText().toString();
    }

    public String getActAddress() {
        if (mAddress.getText().toString().trim().equals("")) {
            return "";
        }
        return mAddress.getText().toString().trim();
    }

    public String getActPersonNum() {
        if (mPerson.getText().toString().equals("")) {
            return "";
        }
        return mPerson.getText().toString();
    }

    public String getActPhone() {
        if (!checkPhone(mPhone.getText().toString())) {
            return "";
        }
        return mPhone.getText().toString();
    }

    public String getActContent() {
        if (mContent.getText().toString().trim().equals("")) {
            return "";
        }
        return mContent.getText().toString().trim();
    }

    /**
     * 判断结束时间是否大于开始时间
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public boolean checkStartEndTime(String starttime, String endtime) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date starDate = dateformat.parse(starttime);
            Date endDate = dateformat.parse(endtime);

            if (endDate.getTime() - starDate.getTime() >= 0) {
                // Toast.makeText(this, "时间正确", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(this, "时间错误", Toast.LENGTH_SHORT).show();
            return false;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "创建活动时间解析错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断开始时间是否大于当前时间
     *
     * @param endtime
     * @return
     */
    public boolean checkStartEndTime(String endtime) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            Date endDate = dateformat.parse(endtime);

            if (endDate.getTime() - System.currentTimeMillis() > 0) {
                //Toast.makeText(this, "时间正确", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(this, "请选择正确的活动开始时间", Toast.LENGTH_SHORT).show();
            return false;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "请选择正确的活动开始时间", Toast.LENGTH_SHORT).show();
        return false;
    }
}
