package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.getAreaActivityLike.Root;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myUtils.NetWorkMyUtils;

import net.tsz.afinal.http.AjaxParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarPoolingPostActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private TextView mPost_btn;

    private ImageView mSiji_img, mCheng_img;
    private TextView mSelect_time;
    private int mFlag = -1;
    private PopupWindow mPopupwindow;
    private View mView;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar;
    private TextView mSure_time, mCancle_time;
    private TextView mShowTime_btn;
    private EditText mStart_tv, mEnd_tv, mPhone,mPerson_edit;
    private ImageView mOther_img;
    private boolean isImg = false;

    private int mStartYear;
    private HttpTools mHttptools;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==127){
                Object o=msg.obj;
                if (o!=null&&o instanceof Root){
                    Root root= (Root) o;
                    MyDialog.stopDia();
                    if (root.getCode().equals("0")){
                        Toast.makeText(CarPoolingPostActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(CarPoolingPostActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling_post);
        mHttptools=HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.car_post_back);
        mback.setOnClickListener(this);
        //发帖
        mPost_btn = (TextView) findViewById(R.id.car_post_btn);
        mPost_btn.setOnClickListener(this);

        //司机乘客
        mSiji_img = (ImageView) findViewById(R.id.post_siji_img);
        mCheng_img = (ImageView) findViewById(R.id.post_chen_img);
        mSiji_img.setOnClickListener(this);
        mCheng_img.setOnClickListener(this);

        //选择时间
        mShowTime_btn = (TextView) findViewById(R.id.car_post_time_msg);
        mShowTime_btn.setOnClickListener(this);
        mView = LayoutInflater.from(this).inflate(R.layout.community_select_time_item, null);
        mCalendar = Calendar.getInstance();

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

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
        //显示时间
        mSelect_time = (TextView) findViewById(R.id.car_post_time_msg);

        //出发地，目的地
        mStart_tv = (EditText) findViewById(R.id.car_post_start_address_msg);
        mEnd_tv = (EditText) findViewById(R.id.car_post_end_address_msg);
        mPhone = (EditText) findViewById(R.id.car_post_phone_msg);

        //其他小区可看
        mOther_img = (ImageView) findViewById(R.id.car_post_select_img_btn);
        mOther_img.setOnClickListener(this);

        mPerson_edit=(EditText) findViewById(R.id.car_post_person_msg);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mPost_btn.getId()) {//发布
            if (checkStartEndTime(mStartYear+"-"+mSelect_time.getText().toString()+":"+"00")){
                if (!(getShen().equals("")||getStartAddresss().equals("")||getEndAddresss().equals("")||getPhone().equals(""))){
                   // Toast.makeText(this,"内容正确",Toast.LENGTH_SHORT).show();
                    if (NetWorkMyUtils.isNetworkConnected(this)){
                        mPost_btn.setFocusable(false);
                        MyDialog.showDialog(this);
                        AjaxParams ajaxParams=new AjaxParams();
                        ajaxParams.put("ctype",mFlag+"");
                        ajaxParams.put("startTime",mSelect_time.getText().toString());
                        ajaxParams.put("departurePlace",getStartAddresss());
                        ajaxParams.put("end",getEndAddresss());
                        ajaxParams.put("cnumber",getPersonNum());
                        ajaxParams.put("telephone",getPhone());
                        mHttptools.carPooloingPost(handler,ajaxParams);
                    }else {
                        Toast.makeText(this,"请检查网络",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(this,"请您补全内容",Toast.LENGTH_SHORT).show();
                }

            }else {

            }


        } else if (id == mSiji_img.getId()) {//司机
            mFlag = 2;
            mSiji_img.setImageResource(R.mipmap.car_post_chen);
            mCheng_img.setImageResource(R.mipmap.car_post_chen_no);
        } else if (id == mCheng_img.getId()) {//乘客
            mSiji_img.setImageResource(R.mipmap.car_post_chen_no);
            mCheng_img.setImageResource(R.mipmap.car_post_chen);
            mFlag = 1;
        } else if (id == mShowTime_btn.getId()) {//弹出选择时间
            showPopuWindow();
        } else if (id == mOther_img.getId()) {//其他小区可见
            if (!isImg) {
                mOther_img.setImageResource(R.mipmap.circle_button_on);
                isImg = true;
            } else {
                mOther_img.setImageResource(R.mipmap.circle_button_off);
                isImg = false;
            }
        } else if (id == mSure_time.getId()) {//确认时间
            mPopupwindow.dismiss();
            String month="";
            String day="";
            String hour="";
            String minute="";
            mStartYear=mDatePicker.getYear();
            if (mDatePicker.getMonth() + 1<10){
                month="0"+(mDatePicker.getMonth() + 1);
            }else {
                month=mDatePicker.getMonth() + 1+"";
            }

            if ( mDatePicker.getDayOfMonth()<10){
                day="0"+mDatePicker.getDayOfMonth();
            }else {
                day=mDatePicker.getDayOfMonth()+"";
            }

            if (mTimePicker.getCurrentHour()<10){
                hour="0"+mTimePicker.getCurrentHour();
            }else {
                hour=""+mTimePicker.getCurrentHour();
            }

            if (mTimePicker.getCurrentMinute()<10){
                minute="0"+mTimePicker.getCurrentMinute();
            }else {
                minute=""+mTimePicker.getCurrentMinute();
            }

            mSelect_time.setText(month + "-" + day + " " + hour + ":" + minute);
        } else if (id == mCancle_time.getId()) {//取消时间
            mPopupwindow.dismiss();
        }
    }


    private void showPopuWindow() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_car_pooling_post);
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

    public String getShen() {
        if (mFlag == 2) {
            return "司机";
        } else if (mFlag == 1) {
            return "乘客";
        } else {
            return "";
        }
    }

    public String getStartAddresss() {
        if (mStart_tv.getText().toString().trim().equals("")) {
            return "";
        }
        return mStart_tv.getText().toString().trim();

    }

    public String getEndAddresss() {
        if (mEnd_tv.getText().toString().trim().equals("")) {
            return "";
        }
        return mEnd_tv.getText().toString().trim();

    }

    public String getPhone() {
        if (!checkPhone(mPhone.getText().toString().trim())) {
            return "";
        }
        return mPhone.getText().toString().trim();

    }

    public String getPersonNum(){
        if (mPerson_edit.getText().toString().trim().equals("")){
            return "";
        }
        return mPerson_edit.getText().toString().trim();
    }


    public boolean checkStartEndTime(String endtime) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            Date endDate = dateformat.parse(endtime);

            if (endDate.getTime() - System.currentTimeMillis() > 0) {
               // Toast.makeText(this, "时间正确", Toast.LENGTH_SHORT).show();
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
