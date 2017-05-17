package com.home.wanyu.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.home.wanyu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommunityPostActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private PopupWindow mPopupwindow;
    private View mView;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar;
    private TextView mSure_time, mCancle_time;

    private LinearLayout mShow_Start_time, mShow_End_time;
    private TextView mStart_time, mEnd_time;
    private int mFlag = -1;

    private TextView mPost_btn;
    private EditText mTitle, mAddress, mPerson, mPhone, mContent;
    private ImageView mOther_area_btn;
    private boolean mImgSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_community);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.community_post_back);
        mBack.setOnClickListener(this);


        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        //选择日期view
        mView = LayoutInflater.from(this).inflate(R.layout.community_select_time_item, null);
        mDatePicker = (DatePicker) mView.findViewById(R.id.community_date_picker);
        mDatePicker.setMinDate(System.currentTimeMillis());
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        mTimePicker = (TimePicker) mView.findViewById(R.id.community_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });
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
            showPopuWindow();
        } else if (id == mShow_End_time.getId()) {
            mFlag = 2;//结束时间
            showPopuWindow();
        } else if (id == mSure_time.getId()) {
            mPopupwindow.dismiss();
            if (mFlag == 1) {//设置开始时间
                mStart_time.setTextColor(ContextCompat.getColor(this, R.color.title_color));
                mStart_time.setText(mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" + mDatePicker.getDayOfMonth() + " " + mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute() + ":" + "00");
            } else if (mFlag == 2) {//设置结束时间
                mEnd_time.setTextColor(ContextCompat.getColor(this, R.color.title_color));
                mEnd_time.setText(mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" + mDatePicker.getDayOfMonth() + " " + mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute() + ":" + "00");
            }
        } else if (id == mCancle_time.getId()) {//取消选择时间
            mPopupwindow.dismiss();
        } else if (id == mPost_btn.getId()) {//发布
            if (checkStartEndTime(mStart_time.getText().toString(), mEnd_time.getText().toString())) {
                if (!(getActTitle().equals("") || getActAddress().equals("") || getActPersonNum().equals("") || getActPhone().equals("") || getActContent().equals(""))) {
                    Toast.makeText(this, "所有信息填写正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请填写正确的活动信息", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "请选择正确活动时间", Toast.LENGTH_SHORT).show();
            }
        } else if (id == mOther_area_btn.getId()) {//其他小区可见
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
        if (mAddress.getText().toString().equals("")) {
            return "";
        }
        return mAddress.getText().toString();
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
        if (mContent.getText().toString().equals("")) {
            return "";
        }
        return mContent.getText().toString();
    }

    public boolean checkStartEndTime(String starttime, String endtime) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date starDate = dateformat.parse(starttime);
            Date endDate = dateformat.parse(endtime);

            if (endDate.getTime() - starDate.getTime() > 0) {
                Toast.makeText(this, "时间正确", Toast.LENGTH_SHORT).show();
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
}
