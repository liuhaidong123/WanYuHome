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
import com.home.wanyu.apater.CommercialAda;
import com.home.wanyu.bean.shoppingList.Result;
import com.home.wanyu.bean.shoppingList.Root;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myUtils.NetWorkMyUtils;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

//附近商户
public class CommercialActivity extends AppCompatActivity implements View.OnClickListener, AMapLocationListener {

    private ImageView mBack;
    private MyListView myListView;
    private CommercialAda mAdapter;
    private List<Result> mList = new ArrayList<>();


    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;

    private RelativeLayout mAddress_rl;
    private TextView mAddress_msg;

    private int start = 0;
    private int limit = 10;
    private int flag = 1;//1代表刷新，2代表加载
    private HttpTools mHttptools;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 132) {//小区商户列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mRefresh.setRefreshing(false);
                    MyDialog.stopDia();
                    if (root != null && root.getResult() != null) {
                        if (flag == 1) {
                            mList = root.getResult();
                            mAdapter.setmList(mList);
                            mAdapter.notifyDataSetChanged();
                        } else if (flag == 2) {
                            List<Result> list = new ArrayList<>();
                            list = root.getResult();
                            mList.addAll(list);
                            mAdapter.setmList(mList);
                            mAdapter.notifyDataSetChanged();
                        }

                        if (root.getResult().size() == 10) {
                            mMore_rl.setVisibility(View.VISIBLE);
                            mBar.setVisibility(View.INVISIBLE);
                        } else {
                            if (root.getResult().size()==0){
                                Toast.makeText(CommercialActivity.this,"抱歉，该小区没有商户",Toast.LENGTH_SHORT).show();
                            }
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
    };

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static double lat;
    private static double lng;

    private ImageView mNetWorkBack;
    private TextView mNetWorkTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetWorkMyUtils.isNetworkConnected(this)) {
            setContentView(R.layout.activity_commercial);
            mHttptools = HttpTools.getHttpToolsInstance();
            //初始化定位
            initLocation();
            init();//授权定位
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
            mNetWorkTitle.setText(R.string.property_commercial);
        }


    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.commercial_back);
        mBack.setOnClickListener(this);

        //adapter
        myListView = (MyListView) findViewById(R.id.commercial_listview);
        mAdapter = new CommercialAda(this, mList);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommercialActivity.this, CommercialMessageActivity.class);
                intent.putExtra("businessId", mList.get(position).getId());
                startActivity(intent);
            }
        });
        //下拉刷新
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.commercial_refresh);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                flag = 1;
                Log.e("lat纬度==", lat + "");
                Log.e("lng经度==", lng + "");
                mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, mAddress_msg.getText().toString(), lat
                        , lng);

            }
        });

        //加载更多
        mMore_rl = (RelativeLayout) findViewById(R.id.commercial_more_rl);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.circle_pbLocate);

        //选择地址
        mAddress_rl = (RelativeLayout) findViewById(R.id.commercial_address_rl);
        mAddress_rl.setOnClickListener(this);
        mAddress_msg = (TextView) findViewById(R.id.address_msg);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBack.getId()) {
            finish();
        } else if (id == mMore_rl.getId()) {//加载更多
            start += 10;
            flag = 2;
            mBar.setVisibility(View.VISIBLE);
            mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, mAddress_msg.getText().toString(), 115.984108
                    , 39.484636);

        } else if (id == mAddress_rl.getId()) {//选择地址
            Intent intent = new Intent(this, CommercialSearchAddressActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            mAddress_msg.setText(name);
            MyDialog.showDialog(this);
            flag = 1;
            mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, name, 115.984108
                    , 39.484636);
        }
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
                mAddress_msg.setText(aMapLocation.getAoiName());
                mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, aMapLocation.getAoiName(), aMapLocation.getLatitude()
                        , aMapLocation.getLongitude());

                Log.e("城市===", aMapLocation.getDistrict());
                lat = aMapLocation.getLatitude();//获取纬度
                lng = aMapLocation.getLongitude();//获取经度
                Log.e("lat纬度==", aMapLocation.getLatitude() + "");
                Log.e("lng经度==", aMapLocation.getLongitude() + "");
                Log.e("小区名称==", aMapLocation.getAoiName());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                mAddress_msg.setText("未知小区");
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
                    Toast.makeText(CommercialActivity.this, "定位授权成功", Toast.LENGTH_SHORT).show();
                    //启动定位
                    mLocationClient.startLocation();
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied
                    mRefresh.setRefreshing(false);
                    mAddress_msg.setText("未知小区");
                    Toast.makeText(CommercialActivity.this, "定位授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
