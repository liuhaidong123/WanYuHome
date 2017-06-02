package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.HouseMsgListAda;
import com.home.wanyu.bean.HouseFirstList.Result;
import com.home.wanyu.bean.HouseFirstList.Root;
import com.home.wanyu.bean.HouseFirstList.Rows;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class HouseMsgActivity extends AppCompatActivity implements View.OnClickListener, AMapLocationListener {
    private ImageView mBack;
    private HouseMsgListAda msgListAda;
    private MyListView myListView;
    private List<Result> mList = new ArrayList<>();


    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;
    private RelativeLayout mLocation_search_address_rl;
    private TextView location_tv;
    private RelativeLayout mLocation_search_area_rl;
    private ImageView mPost_Card;

    private int start = 0;
    private int limit = 10;
    private int flag = 1;//1刷新，2加载
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 143) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mRefresh.setRefreshing(false);
                    MyDialog.stopDia();
                    if (root != null && root.getResult() != null) {
                        if (flag == 1) {
                            mList = root.getResult();
                            msgListAda.setmList(mList);
                            msgListAda.notifyDataSetChanged();
                        } else {
                            List<Result> list = new ArrayList<>();
                            list = root.getResult();
                            mList.addAll(list);
                            msgListAda.setmList(mList);
                            msgListAda.notifyDataSetChanged();
                        }

                        if (root.getResult().size() >= 10) {
                            mMore_rl.setVisibility(View.VISIBLE);
                            mBar.setVisibility(View.INVISIBLE);
                        } else {
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        Toast.makeText(HouseMsgActivity.this, "还没有数据哦", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    };

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_msg);
        mHttptools = HttpTools.getHttpToolsInstance();
        //初始化定位
        initLocation();
        init();//授权定位
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_mag_back);
        mBack.setOnClickListener(this);
        myListView = (MyListView) findViewById(R.id.house_location_area_refresh_listview);
        msgListAda = new HouseMsgListAda(this, mList);
        myListView.setAdapter(msgListAda);
//        跳转详情
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HouseMsgActivity.this, HouseLookMessageActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                startActivity(intent);
            }
        });

        //定位地址
        mLocation_search_address_rl = (RelativeLayout) findViewById(R.id.house_location_address);
        mLocation_search_address_rl.setOnClickListener(this);
        location_tv = (TextView) findViewById(R.id.location_msg);


        //搜索小区名称
        mLocation_search_area_rl = (RelativeLayout) findViewById(R.id.input_area_name);
        mLocation_search_area_rl.setOnClickListener(this);

        //发帖
        mPost_Card = (ImageView) findViewById(R.id.house_post_card);
        mPost_Card.setOnClickListener(this);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.house_location_area_refresh);
        mRefresh.setRefreshing(true);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 1;
                start = 0;
                //获取定位城市下的所有小区
                Log.e("citynames刷新", location_tv.getText().toString());
                mHttptools.getHouseFirstList(handler, UserInfo.userToken, start, limit, location_tv.getText().toString());
            }
        });
        mMore_rl = (RelativeLayout) findViewById(R.id.house_location_area_more_rl);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.circle_pbLocate);
    }

    //初始化定位
    public void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mLocation_search_address_rl.getId()) {//跳转到搜索定位地址
            Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
            intent.putExtra("type", 1);
            startActivityForResult(intent, 123);//搜索城市会jiang城市传回
        } else if (id == mLocation_search_area_rl.getId()) {//跳转到搜索小区
            Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("city", location_tv.getText().toString());
            startActivity(intent);
        } else if (id == mPost_Card.getId()) {//发帖
            startActivity(new Intent(this, HousePostCardActivity.class));
        } else if (id == mMore_rl.getId()) {//加载更多
            flag = 2;
            start += 10;
            mBar.setVisibility(View.VISIBLE);
            //获取定位城市下的所有小区
            mHttptools.getHouseFirstList(handler, UserInfo.userToken, start, limit, location_tv.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {
            String cityname = data.getStringExtra("cityname");
            flag = 1;
            start = 0;
            mMore_rl.setVisibility(View.GONE);
            //获取定位城市下的所有小区
            mList.clear();
            msgListAda.setmList(mList);
            msgListAda.notifyDataSetChanged();
            location_tv.setText(cityname);
            MyDialog.showDialog(this);
            Log.e("cityname", cityname);
            mHttptools.getHouseFirstList(handler, UserInfo.userToken, start, limit, cityname);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!location_tv.getText().toString().equals("未定位")){
//            start = 0;
//            flag = 1;
//            //获取定位城市下的所有小区
//            mHttptools.getHouseFirstList(handler, UserInfo.userToken, start, limit, location_tv.getText().toString());
//        }

    }

    //定位监听回调
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                location_tv.setText(aMapLocation.getDistrict());
                Log.e("城市===", aMapLocation.getDistrict());
                //获取定位城市下的所有小区接口
                mHttptools.getHouseFirstList(handler, UserInfo.userToken, start, limit, aMapLocation.getDistrict());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                location_tv.setText("未定位");
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;

    public void init() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            //如果读取电话权限没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                ActivityCompat.requestPermissions(this,
                        //在这个数组中可以添加很多权限
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                //启动定位
                mLocationClient.startLocation();
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            //启动定位
            mLocationClient.startLocation();
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
                    Toast.makeText(HouseMsgActivity.this, "定位授权成功", Toast.LENGTH_SHORT).show();
                    //启动定位
                    mLocationClient.startLocation();
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied
                    mRefresh.setRefreshing(false);
                    location_tv.setText("未定位");
                    Toast.makeText(HouseMsgActivity.this, "定位授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
