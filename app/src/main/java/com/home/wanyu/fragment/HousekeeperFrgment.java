package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.AddAddressActivity;
import com.home.wanyu.activity.CommercialActivity;
import com.home.wanyu.activity.DecorationActivity;
import com.home.wanyu.activity.ExpressActivity;
import com.home.wanyu.activity.HomeServiceActivity;
import com.home.wanyu.activity.HouseMsgActivity;
import com.home.wanyu.activity.LifeMoneyActivity;
import com.home.wanyu.activity.LifeMoneyActivity2;
import com.home.wanyu.activity.OrderActivity;
import com.home.wanyu.activity.OrderMessageActivity;
import com.home.wanyu.activity.RepairActivity;
import com.home.wanyu.apater.MyExpandableAda;
import com.home.wanyu.apater.PropertyViewPagerAda;
import com.home.wanyu.bean.haveAddress.Root;
import com.home.wanyu.myview.MyExpandableListview;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//物业管家
public class HousekeeperFrgment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewpager;
    private LinearLayout mViewgroup;
    private List<Integer> mAdList = new ArrayList<>();
    private PropertyViewPagerAda mAdAdapter;
    private ImageView[] mImgArr;

//    private MyExpandableListview mMyListview;
//    private MyExpandableAda mMyxpandableAda;
//    private List<String> mList = new ArrayList<>();
//    private List<String> mTwoList = new ArrayList<>();

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mScrollRelative;

    private TextView mExpress_msg;
    private TextView mExpress_btn;

    private LinearLayout mRepair_ll;
    private LinearLayout mOrder_ll;
    private LinearLayout mLife_money_ll;
    private LinearLayout mExpress_ll;
    private LinearLayout mCommercial_ll;
    private LinearLayout mDecoration_ll;
    private LinearLayout mHomeService_ll;
    private LinearLayout mHouseMsg_ll;


    private HttpTools mHttptools;
    private Root mHaveRoot;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100) {//判断有没有地址
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mHaveRoot = (Root) o;
                }
            }else if (msg.what==136){//获取未领取的快递
                Object o = msg.obj;
                if (o!=null&&o instanceof com.home.wanyu.bean.Express.Root){
                    com.home.wanyu.bean.Express.Root root = (com.home.wanyu.bean.Express.Root) o;
                   if (root!=null&&root.getResult()!=null){
                       if (root.getResult().size()==0){
                           mExpress_msg.setText("您还没有未领取的快递");
                       }else {
                           mExpress_msg.setText("您还有"+root.getResult().size()+"个快递未领取");
                       }
                   }

                }
            }



        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_housekeeper, null, false);
        initHttp();
        initView(vi);
        initAdSmallIcon();
        return vi;
    }

    private void initHttp() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.expressNOGet(mHandler,UserInfo.userToken);//获取未领取的快递
    }

    public void initView(View view) {
        //广告轮播
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager_property);
        mViewgroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        mAdList.add(R.mipmap.main_background4);
        mAdList.add(R.mipmap.main_background5);
        mAdList.add(R.mipmap.main_background6);
        mAdAdapter = new PropertyViewPagerAda(getActivity(), mAdList);
        mViewpager.setAdapter(mAdAdapter);
        mViewpager.setCurrentItem(0);

        //将scrollview定位到顶部
        mScrollRelative = (RelativeLayout) view.findViewById(R.id.scroll_rl);
        mScrollRelative.setFocusable(true);
        mScrollRelative.setFocusableInTouchMode(true);
        mScrollRelative.requestFocus();
        //刷新
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.property_refresh);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
//        //二级listview
//        mList.add("食肉动物");
//        mList.add("食草动物");
//        mList.add("天上飞的");
//        mList.add("地上爬的");
//        mTwoList.add("老虎");
//        mTwoList.add("狮子");
//        mTwoList.add("大象");
//        mTwoList.add("长劲鹿");
//        mTwoList.add("白天鹅");
//        mMyListview = (MyExpandableListview) view.findViewById(R.id.my_expandable);
//        mMyxpandableAda = new MyExpandableAda(mList, mTwoList, getActivity());
//        mMyListview.setAdapter(mMyxpandableAda);
//        //默认展开二级菜单
//        for (int i = 0; i < mMyxpandableAda.getGroupCount(); i++) {
//            mMyListview.expandGroup(i);
//        }
//        //不能点击收缩
//        mMyListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                return true;
//            }
//        });
//        //去掉箭头
//        mMyListview.setGroupIndicator(null);

        //报事报修
        mRepair_ll = (LinearLayout) view.findViewById(R.id.property_repair);
        mRepair_ll.setOnClickListener(this);
        //物业账单
        mOrder_ll = (LinearLayout) view.findViewById(R.id.property_order);
        mOrder_ll.setOnClickListener(this);
        //生活缴费
        mLife_money_ll = (LinearLayout) view.findViewById(R.id.property_life_money);
        mLife_money_ll.setOnClickListener(this);
        //快递收发
        mExpress_ll = (LinearLayout) view.findViewById(R.id.property_express);
        mExpress_ll.setOnClickListener(this);

        //小区商户
        mCommercial_ll = (LinearLayout) view.findViewById(R.id.property_commercial);
        mCommercial_ll.setOnClickListener(this);
//装修
        mDecoration_ll = (LinearLayout) view.findViewById(R.id.property_decoration);
        mDecoration_ll.setOnClickListener(this);
//家政服务
        mHomeService_ll = (LinearLayout) view.findViewById(R.id.property_home_srevice);
        mHomeService_ll.setOnClickListener(this);

        //租房信息
        mHouseMsg_ll = (LinearLayout) view.findViewById(R.id.property_home_message);
        mHouseMsg_ll.setOnClickListener(this);

        //快递通知
        mExpress_msg= (TextView) view.findViewById(R.id.express_message);
        mExpress_btn=(TextView) view.findViewById(R.id.express_look_btn);
        mExpress_btn.setOnClickListener(this);
    }

    /**
     * 初始化广告下的小图标
     */
    public void initAdSmallIcon() {
        if (mAdList.size() != 0) {
            Log.e("======", mAdList.size() + "");
            mImgArr = new ImageView[mAdList.size()];
            for (int i = 0; i < mAdList.size(); i++) {
                ImageView imageView = new ImageView(this.getActivity());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginEnd(8);
                imageView.setLayoutParams(layoutParams);
                mImgArr[i] = imageView;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mAdList.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    imageView.setBackgroundResource(R.mipmap.select);
                } else {
                    imageView.setBackgroundResource(R.mipmap.select_no);
                }
                //将每一个小圆点添加到容器中
                mViewgroup.addView(imageView);

            }
            //滑动监听
            if (mAdList.size() > 1) {
                mViewpager.addOnPageChangeListener(this);
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //停在某一页的时候
    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    //滑动状态改变
    @Override
    public void onPageScrollStateChanged(int state) {
        //手指开始滑动
        if (state == mViewpager.SCROLL_STATE_DRAGGING) {
            mRefresh.setEnabled(false);
            Log.e("手指开始滑动", "===");
            //手指松开后自动滑动
        } else if (state == mViewpager.SCROLL_STATE_SETTLING) {
            mRefresh.setEnabled(true);
            Log.e("手指松开后自动滑动", "===");
            //停在某一页
        } else {
            mRefresh.setEnabled(true);
            Log.e("停在某一页", "===");
        }
    }

    /**
     * 停在某一页时，变换小圆点
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < mImgArr.length; i++) {
            if (i == selectItems) {
                mImgArr[i].setBackgroundResource(R.mipmap.select);
            } else {
                mImgArr[i].setBackgroundResource(R.mipmap.select_no);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mRepair_ll.getId()) {//报事报修
            startActivity(new Intent(getActivity(), RepairActivity.class));
        } else if (id == mOrder_ll.getId()) {//物业账单
            if (mHaveRoot != null && mHaveRoot.getCode().equals("-1")) {//没有地址，显示需要添加地址的页面
                startActivity(new Intent(getActivity(), OrderActivity.class));
            } else if (mHaveRoot != null && mHaveRoot.getCode().equals("0")) {//有地址的话，直接显示物业账单信息
                startActivity(new Intent(getActivity(), OrderMessageActivity.class));
            } else {
                Toast.makeText(getContext(), "获取地址错误", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mLife_money_ll.getId()) {//生活缴费

            if (mHaveRoot != null && mHaveRoot.getCode().equals("-1")) {//没有地址，显示需要添加地址的页面
                startActivity(new Intent(getActivity(), OrderActivity.class));
            } else if (mHaveRoot != null && mHaveRoot.getCode().equals("0")) {//有地址的话，直接显示物业账单信息
                startActivity(new Intent(getActivity(), LifeMoneyActivity2.class));
            } else {
                Toast.makeText(getContext(), "获取地址错误", Toast.LENGTH_SHORT).show();
            }

        } else if (id == mExpress_ll.getId()) {//快递收发
            startActivity(new Intent(getActivity(), ExpressActivity.class));
        } else if (id == mCommercial_ll.getId()) {//小区商户
            startActivity(new Intent(getActivity(), CommercialActivity.class));
        } else if (id == mDecoration_ll.getId()) {//装修
            startActivity(new Intent(getActivity(), DecorationActivity.class));
        } else if (id == mHomeService_ll.getId()) {//家政服务
            startActivity(new Intent(getActivity(), HomeServiceActivity.class));
        } else if (id == mHouseMsg_ll.getId()) {//租房信息
            startActivity(new Intent(getActivity(), HouseMsgActivity.class));
        }else if (id==mExpress_btn.getId()){//点击查看跳转到快递
            startActivity(new Intent(getActivity(), ExpressActivity.class));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);
    }
}
