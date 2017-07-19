package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.NetWorkMyUtils;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class HouseMsgActivity2 extends AppCompatActivity implements View.OnClickListener, AMapLocationListener, DistrictSearch.OnDistrictSearchListener {
    private HttpTools mHttptools;
    private ImageView mBack;
    private String city="";
    private String code="";
    private MyListView myListView;
    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;
    private RelativeLayout mLocation_search_address_rl;
    private TextView location_tv;
    private RelativeLayout mLocation_search_area_rl;
    private ImageView mPost_Card;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;
    private ImageView mNetWorkBack;//没网的返回键
    private TextView mNetWorkTitle;//没网标题
    private LinearLayout mArea_ll, mMoney_ll, mType_ll, mOther_ll, mAll_select_ll;
    private TextView mArea_tv, mMoney_tv, mType_tv, mOther_tv;
    private ImageView mArea_img, mMoney_img, mType_img, mOther_img;
    private PopupWindow mTypePop, mAreaPop, mMoneyPop, mOtherPop;
    private View mType_view, mArea_view, mMoney_view, mOther_view;
    private boolean mTypeFlag = false, mMoneyFlag = false;
    private List<String> typeList = new ArrayList<>();
    private List<String> moneyList = new ArrayList<>();
    private List<String> houseTypeList = new ArrayList<>();
    private List<String> directionList = new ArrayList<>();
    private List<Boolean> houseTypeClickList = new ArrayList<>();
    private List<Boolean> directionClickList = new ArrayList<>();
    private ListView mTypeListView, mMoneyListView;
    private int houseTypeClickPosition = -1, directionClickPosition = -1, houseTypeSurePosition = -1, directionSurePosition = -1;
    private GridView mHouseTypeGridView, mHouseDirectionGridView;
    private MoreDirectionAda moreDirectionAda;
    private MoreHouseTypeAda moreHouseTypeAda;
    private TextView mClear_condition_btn, mSure_condition_btn;

    private TextView mNearby_btn, mArea_btn;
    private ListView mAreaListView1, mAreaListView2, mKmListView;
    private int areaFlag = 1;
    private LinearLayout mListView1_ll, mListView2_ll, mKmListView_ll;
    private List<String> areaCodeList = new ArrayList<>();
    private List<String> areaNameList = new ArrayList<>();
    private List<String> smallAreaNameList = new ArrayList<>();
    private DistrictSearch districtSearch;
    private DistrictSearchQuery districtSearchQuery;
    private List<String> kmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetWorkMyUtils.isNetworkConnected(this)) {
            setContentView(R.layout.activity_house_msg2);
            mHttptools = HttpTools.getHttpToolsInstance();
            //高德地图搜索城市例如：中国>山东省>济南市>历下区>舜华路街道（国>省>市>区>街道）。
            districtSearch = new DistrictSearch(this);
            districtSearchQuery = new DistrictSearchQuery();
            districtSearchQuery.setShowBoundary(true);
            districtSearch.setQuery(districtSearchQuery);
            districtSearch.setOnDistrictSearchListener(this);//查询到结果后的监听
            //districtSearchQuery.setKeywords("130600");//查询定位下区域编码所对应的所有街道或者城镇
            // districtSearch.searchDistrictAnsy();//开始搜索
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
            mNetWorkTitle.setText("租房信息");
        }
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_mag_back);
        mBack.setOnClickListener(this);
        myListView = (MyListView) findViewById(R.id.house_location_area_refresh_listview);

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
        //选择房屋的选项
        mAll_select_ll = (LinearLayout) findViewById(R.id.select_item_ll);
        mArea_ll = (LinearLayout) findViewById(R.id.area_ll);
        mMoney_ll = (LinearLayout) findViewById(R.id.money_ll);
        mType_ll = (LinearLayout) findViewById(R.id.type_ll);
        mOther_ll = (LinearLayout) findViewById(R.id.other_ll);
        mArea_ll.setOnClickListener(this);
        mMoney_ll.setOnClickListener(this);
        mType_ll.setOnClickListener(this);
        mOther_ll.setOnClickListener(this);

        mArea_tv = (TextView) findViewById(R.id.area_tv_msg);
        mMoney_tv = (TextView) findViewById(R.id.money_tv_msg);
        mType_tv = (TextView) findViewById(R.id.type_tv_msg);
        mOther_tv = (TextView) findViewById(R.id.other_tv_msg);

        mArea_img = (ImageView) findViewById(R.id.area_img_star);
        mMoney_img = (ImageView) findViewById(R.id.money_img_star);
        mType_img = (ImageView) findViewById(R.id.type_img_star);
        mOther_img = (ImageView) findViewById(R.id.other_img_star);
        //出租方式弹框
        typeList.add("不限");
        typeList.add("整租");
        typeList.add("合租");
        mType_view = LayoutInflater.from(this).inflate(R.layout.house_type_pop, null);
        mTypeListView = (ListView) mType_view.findViewById(R.id.house_type_listview);
        mTypeListView.setAdapter(new TypeAda());
        //选择出租方式
        mTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTypeFlag = true;
                mType_tv.setText(typeList.get(position));
                mTypePop.dismiss();
            }
        });
        //租金弹框
        moneyList.add("不限");
        moneyList.add("1500元以下");
        moneyList.add("1500-2500元");
        moneyList.add("2500-4000元");
        moneyList.add("4000-5500元");
        moneyList.add("5500-7000元");
        moneyList.add("7000元以上");
        mMoney_view = LayoutInflater.from(this).inflate(R.layout.house_money_pop, null);
        mMoneyListView = (ListView) mMoney_view.findViewById(R.id.house_money_listview);
        mMoneyListView.setAdapter(new MoneyAda());
        //选择租金
        mMoneyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMoneyFlag = true;
                mMoney_tv.setText(moneyList.get(position));
                mMoneyPop.dismiss();
            }
        });

        //更多
        houseTypeList.add("1");
        houseTypeList.add("2");
        houseTypeList.add("3");
        houseTypeList.add("4");
        houseTypeClickList.add(false);
        houseTypeClickList.add(false);
        houseTypeClickList.add(false);
        houseTypeClickList.add(false);
        directionList.add("东");
        directionList.add("南");
        directionList.add("西");
        directionList.add("北");
        directionList.add("南北");
        directionClickList.add(false);
        directionClickList.add(false);
        directionClickList.add(false);
        directionClickList.add(false);
        directionClickList.add(false);
        mOther_view = LayoutInflater.from(this).inflate(R.layout.house_more_pop, null);
        //户型
        mHouseTypeGridView = (GridView) mOther_view.findViewById(R.id.house_type_GridView);
        moreHouseTypeAda = new MoreHouseTypeAda();
        mHouseTypeGridView.setAdapter(moreHouseTypeAda);
        //选择更多户型
        mHouseTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < houseTypeClickList.size(); i++) {
                    if (i == position) {
                        houseTypeClickList.set(i, true);
                    } else {
                        houseTypeClickList.set(i, false);
                    }
                }
                houseTypeClickPosition = position;
                moreHouseTypeAda.notifyDataSetChanged();

            }
        });

        // 朝向
        mHouseDirectionGridView = (GridView) mOther_view.findViewById(R.id.house_direction_GridView);
        moreDirectionAda = new MoreDirectionAda();
        mHouseDirectionGridView.setAdapter(moreDirectionAda);
        //选择更多朝向
        mHouseDirectionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < directionClickList.size(); i++) {
                    if (i == position) {
                        directionClickList.set(i, true);
                    } else {
                        directionClickList.set(i, false);
                    }
                }
                directionClickPosition = position;
                moreDirectionAda.notifyDataSetChanged();

            }
        });
        //清空条件
        mClear_condition_btn = (TextView) mOther_view.findViewById(R.id.clear_condition_btn);
        mClear_condition_btn.setOnClickListener(this);
        //确定
        mSure_condition_btn = (TextView) mOther_view.findViewById(R.id.sure_condition_btn);
        mSure_condition_btn.setOnClickListener(this);

        //区域弹框
        mArea_view = LayoutInflater.from(this).inflate(R.layout.house_area_pop, null);
        mNearby_btn = (TextView) mArea_view.findViewById(R.id.area_nearby_btn);
        mArea_btn = (TextView) mArea_view.findViewById(R.id.area_btn);
        mNearby_btn.setOnClickListener(this);
        mArea_btn.setOnClickListener(this);
        mAreaListView1 = (ListView) mArea_view.findViewById(R.id.area_listview1);
        mAreaListView2 = (ListView) mArea_view.findViewById(R.id.area_listview2);
        //点击查询最小区域
        mAreaListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {//全城市
                    mAreaPop.dismiss();
                    mArea_tv.setText(areaNameList.get(position));
                } else {
                    mListView2_ll.setVisibility(View.GONE);
                    smallAreaNameList.clear();
                    //由定位到的区域搜索该区域下的街道
                    areaFlag = 2;
                    districtSearchQuery.setKeywords(areaCodeList.get(position));//查询定位下区域编码所对应的所有街道或者城镇
                    Log.e("区域编码==", areaCodeList.get(position));
                    districtSearch.searchDistrictAsyn();//开始搜索
                }
            }
        });
        //点击最小区域
        mAreaListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAreaPop.dismiss();
                mArea_tv.setText(smallAreaNameList.get(position));
            }
        });
        //附近
        kmList.add("不限");
        kmList.add("1000米内");
        kmList.add("1500米内");
        kmList.add("2000米内");
        mKmListView = (ListView) mArea_view.findViewById(R.id.km_listview);
        mKmListView.setAdapter(new ListViewKmAda());
        mKmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAreaPop.dismiss();
                mArea_tv.setText(kmList.get(position));
            }
        });
        mListView1_ll = (LinearLayout) mArea_view.findViewById(R.id.area1_ll);
        mListView2_ll = (LinearLayout) mArea_view.findViewById(R.id.area2_ll);
        mKmListView_ll = (LinearLayout) mArea_view.findViewById(R.id.km_ll);
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
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

    /**
     * 授权定位
     */
    private void init() {
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


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mLocation_search_address_rl.getId()) {//跳转到搜索定位地址
            if (!city.equals("")){
                Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity2.class);
                intent.putExtra("city",city);
                startActivityForResult(intent, 123);//搜索城市会jiang城市传回
            }else {
                Toast.makeText(this," 无法获取定位信息",Toast.LENGTH_SHORT).show();
            }

        } else if (id == mLocation_search_area_rl.getId()) {//跳转到搜索小区
            if (!city.equals("")&&!code.equals("")){
                Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("code",code);
                intent.putExtra("type",2);
                startActivity(intent);
            }else {
                Toast.makeText(this," 无法获取定位信息",Toast.LENGTH_SHORT).show();
            }

        } else if (id == mPost_Card.getId()) {//发帖
            startActivity(new Intent(this, HousePostCardActivity.class));
        } else if (id == mType_ll.getId()) {//显示出租类型
            mType_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            mType_img.setImageResource(R.mipmap.commercial_address_blue);
            showTypePop();
        } else if (id == mMoney_ll.getId()) {//显示租金
            mMoney_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            mMoney_img.setImageResource(R.mipmap.commercial_address_blue);
            showMoneyPop();
        } else if (id == mOther_ll.getId()) {//显示更多
            //显示的时候需要将户型和朝向定位到上次的选择
            //已经选择过户型
            if (houseTypeSurePosition != -1) {
                for (int i = 0; i < houseTypeClickList.size(); i++) {
                    if (i == houseTypeSurePosition) {
                        houseTypeClickList.set(i, true);
                    } else {
                        houseTypeClickList.set(i, false);
                    }
                }
                moreHouseTypeAda.notifyDataSetChanged();
            }
            //已经选择过朝向
            if (directionSurePosition != -1) {
                for (int i = 0; i < directionClickList.size(); i++) {
                    if (i == directionSurePosition) {
                        directionClickList.set(i, true);
                    } else {
                        directionClickList.set(i, false);
                    }
                }
                moreDirectionAda.notifyDataSetChanged();
            }
            Log.e("弹框朝向下表", directionSurePosition + "");
            Log.e("弹框户型下表", houseTypeSurePosition + "");
            mOther_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            mOther_img.setImageResource(R.mipmap.commercial_address_blue);
            showMorePop();
        } else if (id == mClear_condition_btn.getId()) {//清空更多
            mOtherPop.dismiss();
            //将选择过得户型，朝向小标回复默认值
            houseTypeSurePosition = -1;
            directionSurePosition = -1;
            houseTypeClickPosition = -1;
            directionClickPosition = -1;
            //回复默认值false
            for (int i = 0; i < houseTypeClickList.size(); i++) {
                houseTypeClickList.set(i, false);
            }
            for (int i = 0; i < directionClickList.size(); i++) {
                directionClickList.set(i, false);
            }
            moreDirectionAda.notifyDataSetChanged();
            moreHouseTypeAda.notifyDataSetChanged();
            mOther_tv.setText("更多");
        } else if (id == mSure_condition_btn.getId()) {//更多确定
            mOtherPop.dismiss();
            mOther_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            mOther_img.setImageResource(R.mipmap.commercial_address);

            houseTypeSurePosition = houseTypeClickPosition;
            directionSurePosition = directionClickPosition;
            //户型，朝向都选择过
            if (houseTypeSurePosition != -1 && directionSurePosition != -1) {
                mOther_tv.setText(houseTypeList.get(houseTypeSurePosition) + "居" + directionList.get(directionSurePosition));
                //选择过户型，但是没有选择过朝向
            } else if (houseTypeSurePosition != -1 && directionSurePosition == -1) {
                mOther_tv.setText(houseTypeList.get(houseTypeSurePosition) + "居");
            } else if (houseTypeSurePosition == -1 && directionSurePosition != -1) {
                mOther_tv.setText(directionList.get(directionSurePosition));
            } else {
                mOther_tv.setText("更多");
            }

            Log.e("确定朝向下表", directionSurePosition + "");
            Log.e("确定户型下表", houseTypeSurePosition + "");
        } else if (id == mArea_ll.getId()) {//区域弹框
            showAreaPop();
            mArea_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            mArea_img.setImageResource(R.mipmap.commercial_address_blue);
        } else if (id == mNearby_btn.getId()) {//点击附近
            mListView1_ll.setVisibility(View.GONE);
            mListView2_ll.setVisibility(View.GONE);
            mKmListView_ll.setVisibility(View.VISIBLE);
            mNearby_btn.setBackgroundResource(R.color.f8f9fa);
            mArea_btn.setBackgroundResource(R.color.white);
        } else if (id == mArea_btn.getId()) {//点击区域
            mNearby_btn.setBackgroundResource(R.color.white);
            mArea_btn.setBackgroundResource(R.color.f8f9fa);
            mListView1_ll.setVisibility(View.VISIBLE);
            mListView2_ll.setVisibility(View.VISIBLE);
            mKmListView_ll.setVisibility(View.GONE);
        }
    }

    /**
     * 定位回调接口
     *
     * @param aMapLocation
     */
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
                location_tv.setText(aMapLocation.getCity());
                Log.e("Location.getAdCode", aMapLocation.getAdCode() + "");
                Log.e("Location.getCityCode", aMapLocation.getCityCode() + "");
                //由定位到的区域搜索该区域下的街道
                districtSearchQuery.setKeywords(aMapLocation.getCity());//查询定位下区域编码所对应的所有街道或者城镇
                districtSearch.searchDistrictAsyn();//开始搜索
                Log.e("城市===", aMapLocation.getCity());
                city= aMapLocation.getCity();
                //获取定位城市下的所有小区接口
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                location_tv.setText("未定位");
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 搜索到城市回调接口
     *
     * @param districtResult
     */
    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        //在回调函数中解析districtResult获取行政区划信息
        //调用districtResult.getDistrict()方法
        //获取查询行政区的结果，详细信息可以参考DistrictItem类。
        List<DistrictItem> arealist = null;
        //List<String> listSmallArea = null;
        if (districtResult.getAMapException().getErrorCode() == 1000) {
            ArrayList<DistrictItem> list = districtResult.getDistrict();
            Log.e("大小", list.size() + "");
            for (int i = 0; i < list.size(); i++) {
                code=list.get(i).getAdcode();
                Log.e("行政区名称", list.get(i).getName());
                Log.e("区域编码", list.get(i).getAdcode());
                Log.e("城市编码", list.get(i).getCitycode());
                Log.e("行政区划级别", list.get(i).getLevel());
                Log.e("城市中心经纬度", list.get(i).getCenter() + "");

                arealist = list.get(i).getSubDistrict();//获取当前区域编码下的城镇，区集合
                if (areaFlag == 2) {
                    smallAreaNameList.add("全" + list.get(i).getName());
                    for (int j = 0; j < arealist.size(); j++) {
                        smallAreaNameList.add(arealist.get(j).getName());
                        Log.e("2区域名称--", arealist.get(j).getName() + "--区域编码" + arealist.get(j).getAdcode());
                    }
                } else {
                    areaCodeList.add(list.get(i).getAdcode());
                    areaNameList.add("全" + list.get(i).getName());
                    for (int k = 0; k < arealist.size(); k++) {
                        areaCodeList.add(arealist.get(k).getAdcode());//区域编码
                        areaNameList.add(arealist.get(k).getName());
                        Log.e("1区域名称--", arealist.get(k).getName() + "--区域编码" + arealist.get(k).getAdcode());
                    }
                }

            }
            if (areaFlag == 2) {
                mAreaListView2.setAdapter(new ListView2Ada(smallAreaNameList));
                mListView2_ll.setVisibility(View.VISIBLE);
            } else {
                mAreaListView1.setAdapter(new ListView1Ada());
            }


        } else {
            Log.e("错误码", districtResult.getAMapException().getErrorCode() + "");
            Log.e("错误信息", districtResult.getAMapException().getErrorMessage());
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
                    Toast.makeText(HouseMsgActivity2.this, "定位授权成功", Toast.LENGTH_SHORT).show();
                    //启动定位
                    mLocationClient.startLocation();
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied
                    mRefresh.setRefreshing(false);
                    location_tv.setText("未定位");
                    Toast.makeText(HouseMsgActivity2.this, "定位授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123&&resultCode==RESULT_OK){
            areaFlag=1;
            areaNameList.clear();
            areaCodeList.clear();
            smallAreaNameList.clear();
            mAreaListView2.setAdapter(new ListView2Ada(smallAreaNameList));
            location_tv.setText(data.getStringExtra("city"));
            mArea_tv.setText(data.getStringExtra("city"));
            //由定位到的区域搜索该区域下的街道
            districtSearchQuery.setKeywords(data.getStringExtra("code"));//查询定位下区域编码所对应的所有街道或者城镇
            districtSearch.searchDistrictAsyn();//开始搜索
        }
    }

    /**
     * 出租方式弹框
     */
    public void showTypePop() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        // ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
        mTypePop = new PopupWindow(mType_view);
        //设置弹框的款，高
        mTypePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTypePop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTypePop.setFocusable(true);//如果有交互需要设置焦点为true
        mTypePop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        //mTypePop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mTypePop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mTypePop.showAsDropDown(mAll_select_ll, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                if (mTypeFlag) {
                    mType_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
                } else {
                    mType_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.titlecolor3));
                }
                mType_img.setImageResource(R.mipmap.commercial_address);
            }
        });
    }

    /**
     * 出租方式适配器
     */
    class TypeAda extends BaseAdapter {

        @Override
        public int getCount() {
            return typeList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TypeHolder holder = null;
            if (convertView == null) {
                holder = new TypeHolder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_type_pop_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.house_type_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (TypeHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.t.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            }
            holder.t.setText(typeList.get(position));
            return convertView;
        }

        class TypeHolder {
            TextView t;
        }
    }


    /**
     * 租金弹框
     */
    public void showMoneyPop() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        // ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
        mMoneyPop = new PopupWindow(mMoney_view);
        //设置弹框的款，高
        mMoneyPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mMoneyPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mMoneyPop.setFocusable(true);//如果有交互需要设置焦点为true
        mMoneyPop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        //mTypePop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mMoneyPop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mMoneyPop.showAsDropDown(mAll_select_ll, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mMoneyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                if (mMoneyFlag) {
                    mMoney_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
                } else {
                    mMoney_tv.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.titlecolor3));
                }
                mMoney_img.setImageResource(R.mipmap.commercial_address);
            }
        });
    }

    /**
     * 租金适配器
     */
    class MoneyAda extends BaseAdapter {

        @Override
        public int getCount() {
            return moneyList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MoneyHolder holder = null;
            if (convertView == null) {
                holder = new MoneyHolder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_money_pop_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.house_money_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (MoneyHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.t.setTextColor(ContextCompat.getColor(HouseMsgActivity2.this, R.color.eac6));
            }
            holder.t.setText(moneyList.get(position));
            return convertView;
        }

        class MoneyHolder {
            TextView t;
        }
    }

    /**
     * 更多弹框
     */

    public void showMorePop() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        // ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
        mOtherPop = new PopupWindow(mOther_view);
        //设置弹框的款，高
        mOtherPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mOtherPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mOtherPop.setTouchable(true);
        mOtherPop.setFocusable(true);//如果有交互需要设置焦点为true
        // 设置背景，否则点击内容外，关闭弹窗失效
//        mOtherPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mOtherPop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        mOtherPop.setOutsideTouchable(true);//设置内容外可以点击

        mOtherPop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mOtherPop.showAsDropDown(mAll_select_ll, 0, 0);
        //  mOtherPop.setOutsideTouchable(true);//点击弹框外面，弹框不会消失
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mOtherPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mOther_img.setImageResource(R.mipmap.commercial_address);
            }
        });
    }

    /**
     * 更多户型适配器
     */
    class MoreHouseTypeAda extends BaseAdapter {

        @Override
        public int getCount() {
            return houseTypeList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HouseTypeHolder holder = null;
            if (convertView == null) {
                holder = new HouseTypeHolder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_more_type_diction_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.house_more_type_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (HouseTypeHolder) convertView.getTag();
            }
            //设置背景
            if (houseTypeClickList.get(position)) {
                holder.t.setBackgroundResource(R.drawable.sure_condition_bg);
            } else {
                holder.t.setBackgroundResource(R.drawable.location_bg);
            }
            //设置数据
            if (position == houseTypeList.size() - 1) {
                holder.t.setText(houseTypeList.get(position) + "居以上");
            } else {
                holder.t.setText(houseTypeList.get(position) + "居");
            }

            return convertView;
        }

        class HouseTypeHolder {
            TextView t;
        }
    }

    /**
     * 更多朝向适配器
     */
    class MoreDirectionAda extends BaseAdapter {

        @Override
        public int getCount() {
            return directionList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DirectionHolder holder = null;
            if (convertView == null) {
                holder = new DirectionHolder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_more_type_diction_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.house_more_type_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (DirectionHolder) convertView.getTag();
            }
            //设置背景
            if (directionClickList.get(position)) {
                holder.t.setBackgroundResource(R.drawable.sure_condition_bg);
            } else {
                holder.t.setBackgroundResource(R.drawable.location_bg);
            }
            holder.t.setText(directionList.get(position));
            return convertView;
        }

        class DirectionHolder {
            TextView t;
        }
    }

    /**
     * 区域弹框
     */
    public void showAreaPop() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        // ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
        mAreaPop = new PopupWindow(mArea_view);
        //设置弹框的款，高
        mAreaPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mAreaPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAreaPop.setTouchable(true);
        mAreaPop.setFocusable(true);//如果有交互需要设置焦点为true
        // 设置背景，否则点击内容外，关闭弹窗失效
//        mOtherPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mAreaPop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        mAreaPop.setOutsideTouchable(true);//设置内容外可以点击

        mAreaPop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mAreaPop.showAsDropDown(mAll_select_ll, 0, 0);
        //  mOtherPop.setOutsideTouchable(true);//点击弹框外面，弹框不会消失
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mAreaPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mArea_img.setImageResource(R.mipmap.commercial_address);
            }
        });
    }


    class ListView1Ada extends BaseAdapter {

        @Override
        public int getCount() {
            return areaNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListView1Holder holder = null;
            if (convertView == null) {
                holder = new ListView1Holder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_area_pop_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.area_name_tv);
                convertView.setTag(holder);
            } else {
                holder = (ListView1Holder) convertView.getTag();
            }
            holder.t.setText(areaNameList.get(position));
            return convertView;
        }

        class ListView1Holder {
            TextView t;
        }
    }

    class ListView2Ada extends BaseAdapter {
        private List<String> list = new ArrayList<>();

        public ListView2Ada(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListView2Holder holder = null;
            if (convertView == null) {
                holder = new ListView2Holder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_area_pop_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.area_name_tv);
                convertView.setTag(holder);
            } else {
                holder = (ListView2Holder) convertView.getTag();
            }

            holder.t.setText(list.get(position));
            return convertView;
        }

        class ListView2Holder {
            TextView t;
        }
    }

    class ListViewKmAda extends BaseAdapter {
        @Override
        public int getCount() {
            return kmList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewKmHolder holder = null;
            if (convertView == null) {
                holder = new ListViewKmHolder();
                convertView = LayoutInflater.from(HouseMsgActivity2.this).inflate(R.layout.house_area_pop_item, null);
                holder.t = (TextView) convertView.findViewById(R.id.area_name_tv);
                convertView.setTag(holder);
            } else {
                holder = (ListViewKmHolder) convertView.getTag();
            }
            holder.t.setText(kmList.get(position));
            return convertView;
        }

        class ListViewKmHolder {
            TextView t;
        }
    }

}

