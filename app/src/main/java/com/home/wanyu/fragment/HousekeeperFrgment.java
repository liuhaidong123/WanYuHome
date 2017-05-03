package com.home.wanyu.fragment;

import android.os.Bundle;
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

import com.home.wanyu.R;
import com.home.wanyu.apater.MyExpandableAda;
import com.home.wanyu.apater.PropertyViewPagerAda;
import com.home.wanyu.myview.MyExpandableListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */
//物业管家
public class HousekeeperFrgment extends Fragment implements ViewPager.OnPageChangeListener {
    private static HousekeeperFrgment mFragment;
    private ViewPager mViewpager;
    private LinearLayout mViewgroup;
    private List<Integer> mAdList = new ArrayList<>();
    private PropertyViewPagerAda mAdAdapter;
    private ImageView[] mImgArr;

    private MyExpandableListview mMyListview;
    private MyExpandableAda mMyxpandableAda;
    private List<String> mList=new ArrayList<>();
    private List<String> mTwoList=new ArrayList<>();

    private SwipeRefreshLayout mRefresh;
    public static HousekeeperFrgment getInstance() {
        if (mFragment == null) {
            mFragment = new HousekeeperFrgment();
        }
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_housekeeper, null,false);
        initView(vi);
        initAdSmallIcon();
        return vi;
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
        //刷新
        mRefresh= (SwipeRefreshLayout) view.findViewById(R.id.property_refresh);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        //二级listview
        mList.add("食肉动物");
        mList.add("食草动物");
        mList.add("天上飞的");
        mList.add("地上爬的");
        mTwoList.add("老虎");
        mTwoList.add("狮子");
        mTwoList.add("大象");
        mTwoList.add("长劲鹿");
        mTwoList.add("白天鹅");
        mMyListview= (MyExpandableListview) view.findViewById(R.id.my_expandable);
        mMyxpandableAda=new MyExpandableAda(mList,mTwoList,getActivity());
        mMyListview.setAdapter(mMyxpandableAda);
        //默认展开二级菜单
        for(int i = 0; i < mMyxpandableAda.getGroupCount(); i++){
            mMyListview.expandGroup(i);
        }
        //不能点击收缩
        mMyListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //去掉箭头
        mMyListview.setGroupIndicator(null);
    }

    /**
     * 初始化广告下的小图标
     */
    public void initAdSmallIcon() {
        if (mAdList.size() != 0) {
            Log.e("======",mAdList.size()+"");
            mImgArr=new ImageView[mAdList.size() ];
            for (int i = 0; i < mAdList.size(); i++) {
                ImageView imageView = new ImageView(this.getActivity());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginEnd(8);
                imageView.setLayoutParams(layoutParams);
                mImgArr[i]=imageView;
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
            Log.e("手指开始滑动","===");
            //手指松开后自动滑动
        } else if (state == mViewpager.SCROLL_STATE_SETTLING) {
            mRefresh.setEnabled(true);
            Log.e("手指松开后自动滑动","===");
            //停在某一页
        } else {
            mRefresh.setEnabled(true);
            Log.e("停在某一页","===");
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
    public void onDestroy() {
        super.onDestroy();
    }
}
