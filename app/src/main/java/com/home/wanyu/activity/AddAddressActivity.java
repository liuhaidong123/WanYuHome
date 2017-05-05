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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.AlertAreaAda;
import com.home.wanyu.apater.AlertCityAda;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private RelativeLayout mCity_rl;
    private TextView mCity_tv;
    private RelativeLayout mArea_rl;
    private TextView mArea_tv;
    private EditText mName;
    private EditText mPhone;
    private EditText mLou;
    private EditText mCeng;
    private EditText mUnit;
    private EditText mHourse;
    private Button mSubmit;

    private AlertDialog.Builder mCityBuilder;
    private AlertDialog mCityAlert;
    private View mCityView;
    private ListView mCityListview;
    private AlertCityAda mCityAda;
    private List<String> mCityList = new ArrayList<>();


    private AlertDialog.Builder mAreaBuilder;
    private AlertDialog mAreaAlert;
    private View mAreaView;
    private AlertAreaAda mAreaAda;
    private List<String> mAreaList = new ArrayList<>();
    private ListView mAreaListview;

    private int mFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    public void initView() {
        mBack = (ImageView) findViewById(R.id.add_back);
        mBack.setOnClickListener(this);

        mCity_rl = (RelativeLayout) findViewById(R.id.city_rl);
        mCity_tv = (TextView) findViewById(R.id.city_tv_msg);
        mCity_rl.setOnClickListener(this);
        //城市弹框
        mCityBuilder = new AlertDialog.Builder(this);
        mCityAlert = mCityBuilder.create();
        mCityView = LayoutInflater.from(this).inflate(R.layout.city_alert, null);
        mCityListview = (ListView) mCityView.findViewById(R.id.city_alert_listview);
        //选择城市
        mCityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCity_tv.setText(mCityAda.getItem(position).toString());
                mCityAlert.dismiss();
            }
        });
        mCityList.add("涿州市");
        mCityList.add("北京市");
        mCityAda = new AlertCityAda(this, mCityList);
        mCityListview.setAdapter(mCityAda);
        mCityAlert.setView(mCityView);

        //小区弹框
        mArea_rl = (RelativeLayout) findViewById(R.id.area_rl);
        mArea_rl.setOnClickListener(this);
        mArea_tv = (TextView) findViewById(R.id.area_tv_msg);
        mAreaBuilder = new AlertDialog.Builder(this);
        mAreaAlert = mAreaBuilder.create();
        mAreaView = LayoutInflater.from(this).inflate(R.layout.area_alert, null);
        mAreaListview = (ListView) mAreaView.findViewById(R.id.area_alert_listview);
        mAreaList.add("名流一品小区");
        mAreaList.add("钻石广场小区");
        mAreaList.add("风景洋房小区");
        mAreaList.add("名流翡翠城");
        mAreaAda = new AlertAreaAda(this, mAreaList);
        mAreaListview.setAdapter(mAreaAda);
        mAreaAlert.setView(mAreaView);
        //选择小区
        mAreaListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArea_tv.setText(mAreaAda.getItem(position).toString());
                mAreaAlert.dismiss();
            }
        });

        //姓名，手机，楼号，楼层，单元，房号
        mName = (EditText) findViewById(R.id.name_tv_msg);
        mPhone = (EditText) findViewById(R.id.phone_tv_msg);
        mLou = (EditText) findViewById(R.id.lou_tv_msg);
        mCeng = (EditText) findViewById(R.id.ceng_tv_msg);
        mUnit = (EditText) findViewById(R.id.unit_tv_msg);
        mHourse = (EditText) findViewById(R.id.hourse_tv_msg);
        mSubmit= (Button) findViewById(R.id.add_submit);
        mSubmit.setOnClickListener(this);

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
    public void onClick(View v) {
        int id = v.getId();
        if (id == mCity_rl.getId()) {//选择城市弹框
            mCityAlert.show();
            setAlertWidth(mCityAlert, 2);
        } else if (id == mBack.getId()) {
            finish();
        } else if (id == mArea_rl.getId()) {//选择小区弹框
            mAreaAlert.show();
            setAlertWidth(mAreaAlert, 2);
        }else if (id==mSubmit.getId()){
            if (getCity().equals("")||getArea().equals("")||getName().equals("")||getPhone().equals("")||getLou().equals("")||getCeng().equals("")||getUnit().equals("")||getHourse().equals("")){
              Toast.makeText(this,"亲,请补全地址信息哦",Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(this,"亲,地址正确",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getCity(){
        if (mCity_tv.getText().toString().equals("")){
            return "";
        }
        return mCity_tv.getText().toString();
    }

    public String getArea(){
        if (mArea_tv.getText().toString().equals("")){
            return "";
        }
        return mArea_tv.getText().toString();
    }

    public String getName(){
        if (mName.getText().toString().equals("")){
            return "";
        }
        return mName.getText().toString();
    }

    public String getPhone(){
        if (!checkPhone(mPhone.getText().toString())){
            return "";
        }
        return mPhone.getText().toString();
    }

    public String getLou(){
        if (mLou.getText().toString().equals("")){
            return "";
        }
        return mLou.getText().toString();
    }

    public String getCeng(){
        if (mCeng.getText().toString().equals("")){
            return "";
        }
        return mCeng.getText().toString();
    }

    public String getUnit(){
        if (mUnit.getText().toString().equals("")){
            return "";
        }
        return mUnit.getText().toString();
    }

    public String getHourse(){
        if (mHourse.getText().toString().equals("")){
            return "";
        }
        return mHourse.getText().toString();
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
