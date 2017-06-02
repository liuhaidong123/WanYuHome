package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.AlertAreaAda;
import com.home.wanyu.apater.AlertCityAda;
import com.home.wanyu.bean.areaList.Result;
import com.home.wanyu.bean.cityList.BaseAreaList;
import com.home.wanyu.bean.cityList.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle_Address;
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
    private List<BaseAreaList> mCityList = new ArrayList<>();


    private AlertDialog.Builder mAreaBuilder;
    private AlertDialog mAreaAlert;
    private View mAreaView;
    private AlertAreaAda mAreaAda;
    private List<Result> mAreaList = new ArrayList<>();
    private ListView mAreaListview;
    private String areaCode;
    private int mFlag = -1;
    private long mAreaID = -1;
    private HttpTools mHttptools;
    private Map<String, String> mMap = new HashMap<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 102) {//获取城市列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getBaseAreaList() != null) {
                        mCityList = root.getBaseAreaList();
                        mCityAda.setList(mCityList);
                        mCityAda.notifyDataSetChanged();
                        if (mCityList.size() != 0) {
                            mCity_tv.setText(mCityList.get(0).getAreaName());
                            areaCode=mCityList.get(0).getAreaCode();
                            mHttptools.getAreaList(mHandler, mCityList.get(0).getAreaCode());//请求小区接口
                        } else {
                            areaCode="";
                            mCity_tv.setText("");
                        }
                    }

                }
            } else if (msg.what == 103) {//获取小区
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.areaList.Root) {
                    com.home.wanyu.bean.areaList.Root root = (com.home.wanyu.bean.areaList.Root) o;
                    if (root.getResult()!=null){
                        mAreaList = root.getResult();
                        mAreaAda.setList(mAreaList);
                        mAreaAda.notifyDataSetChanged();
                    }
                }
            } else if (msg.what == 101) {//添加地址
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.addAddressNoOwner.Root) {
                    com.home.wanyu.bean.addAddressNoOwner.Root root = (com.home.wanyu.bean.addAddressNoOwner.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(AddAddressActivity.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                        //跳转到生活缴费
                        if (getIntent().getIntExtra("order", -1) == 11) {
                            startActivity(new Intent(AddAddressActivity.this, LifeMoneyActivity2.class));
                            finish();
                            //跳转到物业账单
                        } else if (getIntent().getIntExtra("order", -1) == 22) {
                            startActivity(new Intent(AddAddressActivity.this, OrderMessageActivity.class));
                            finish();
                        } else {
                            finish();
                        }
                    } else if (root.getCode().equals("-1")) {
                        Toast.makeText(AddAddressActivity.this, "请确定您的业主姓名与绑定的手机号是否正确", Toast.LENGTH_SHORT).show();
                    } else if (root.getCode().equals("-2")) {
                        Toast.makeText(AddAddressActivity.this, "抱歉，您已添加过该地址，添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if (msg.what == 139) {//获取小区
                Object o1 = msg.obj;
                if (o1 != null && o1 instanceof com.home.wanyu.bean.addressUpdate.Root) {
                    com.home.wanyu.bean.addressUpdate.Root root1 = (com.home.wanyu.bean.addressUpdate.Root) o1;
                    //姓名，手机，楼号，楼层，单元，房号
                    if (root1.getResult() != null) {
                        mName.setText(root1.getResult().getOwnerName());
                        mPhone.setText(root1.getResult().getRqtelephone() + "");
                        mLou.setText(root1.getResult().getBuildingNumber() + "");
                        mCeng.setText(root1.getResult().getFloor() + "");
                        mUnit.setText(root1.getResult().getUnitNumber() + "");
                        mHourse.setText(root1.getResult().getRoomNumber() + "");

                    }
                }
            }else if (msg.what==140){//提交修改地址
               Object o= msg.obj;
                if (o!=null&& o instanceof  com.home.wanyu.bean.getAreaActivityLike.Root){
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")){
                        Toast.makeText(AddAddressActivity.this, "修改地址成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(AddAddressActivity.this, "修改地址失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private long addressId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getCityList(mHandler);
        initView();
    }

    public void initView() {

        mTitle_Address= (TextView) findViewById(R.id.title_address);

        if (getIntent().getIntExtra("type", -1) == 10) {
            addressId = getIntent().getLongExtra("id", -1);
            mHttptools.addressUpdate(mHandler, UserInfo.userToken, addressId); //根据地址id修改地址
            mTitle_Address.setText("修改地址");
        }

        mBack = (ImageView) findViewById(R.id.add_back);
        mBack.setOnClickListener(this);

        mCity_rl = (RelativeLayout) findViewById(R.id.city_rl);
        mCity_rl.setOnClickListener(this);
        mCity_tv = (TextView) findViewById(R.id.city_tv_msg);


        //城市弹框
        mCityBuilder = new AlertDialog.Builder(this);
        mCityAlert = mCityBuilder.create();
        mCityView = LayoutInflater.from(this).inflate(R.layout.city_alert, null);
        mCityListview = (ListView) mCityView.findViewById(R.id.city_alert_listview);
        //选择城市
        mCityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCity_tv.setText(mCityList.get(position).getAreaName());
                mHttptools.getAreaList(mHandler, mCityList.get(position).getAreaCode());
                areaCode= mCityList.get(position).getAreaCode();
                mCityAlert.dismiss();
            }
        });
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
        mAreaAda = new AlertAreaAda(this, mAreaList);
        mAreaListview.setAdapter(mAreaAda);
        mAreaAlert.setView(mAreaView);
        //选择小区
        mAreaListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArea_tv.setText(mAreaList.get(position).getRname());
                mAreaID = mAreaList.get(position).getId();
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
        mSubmit = (Button) findViewById(R.id.add_submit);
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
            if (mCityList.size()!=0){
                mCityAlert.show();
                setAlertWidth(mCityAlert, 2);
            }else {
                Toast.makeText(this, "抱歉，无法显示城市", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mBack.getId()) {
            finish();
        } else if (id == mArea_rl.getId()) {//选择小区弹框
            if (mAreaList.size()!=0){
                mAreaAlert.show();
                setAlertWidth(mAreaAlert, 2);
            }else {
                Toast.makeText(this, "抱歉，无法显示小区", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mSubmit.getId()) {
            if (getCity().equals("") || getArea().equals("") || getName().equals("") || getPhone().equals("") || getLou().equals("") || getCeng().equals("") || getUnit().equals("") || getHourse().equals("")) {
                Toast.makeText(this, "亲,请填写正确的信息哦", Toast.LENGTH_SHORT).show();

            } else {

                // Toast.makeText(this, "亲,地址正确", Toast.LENGTH_SHORT).show();
                mMap.put("token", UserInfo.userToken);
                mMap.put("city", getCity());
                mMap.put("yard", getArea());
                mMap.put("yardid", String.valueOf(mAreaID));
                mMap.put("ownerName", getName());
                mMap.put("ownertelephone", getPhone());
                mMap.put("buildingNumber", getLou() + "");
                mMap.put("floor", getCeng() + "");
                mMap.put("unitNumber", getUnit() + "");
                mMap.put("roomNumber", getHourse() + "");
                mMap.put("areaCode", areaCode);

                if (getIntent().getIntExtra("type", -1) == 10){//提交修改地址
                    mMap.put("AddressId", addressId+"");
                    mHttptools.addressUpdateSubmit(mHandler,mMap);

                }else {
                    mHttptools.addUserAddress(mHandler, mMap);
                }


            }
        }
    }

    public String getCity() {
        if (mCity_tv.getText().toString().equals("")) {
            return "";
        }
        return mCity_tv.getText().toString();
    }

    public String getArea() {
        if (mArea_tv.getText().toString().equals("")) {
            return "";
        }
        return mArea_tv.getText().toString();
    }

    public String getName() {
        if (mName.getText().toString().equals("")) {
            return "";
        }
        return mName.getText().toString();
    }

    public String getPhone() {
        if (!checkPhone(mPhone.getText().toString())) {
            return "";
        }
        return mPhone.getText().toString();
    }

    public String getLou() {
        if (mLou.getText().toString().equals("")) {
            return "";
        }
        return mLou.getText().toString();
    }

    public String getCeng() {
        if (mCeng.getText().toString().equals("")) {
            return "";
        }
        return mCeng.getText().toString();
    }

    public String getUnit() {
        if (mUnit.getText().toString().equals("")) {
            return "";
        }
        return mUnit.getText().toString();
    }

    public String getHourse() {
        if (mHourse.getText().toString().equals("")) {
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
