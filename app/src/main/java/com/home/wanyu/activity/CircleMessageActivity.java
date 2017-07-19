package com.home.wanyu.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CircleFriendListviewAda;
import com.home.wanyu.apater.CircleGridViewAda;
import com.home.wanyu.apater.CircleSelectAreaAlertAda;
import com.home.wanyu.bean.getCircleArea.Result;
import com.home.wanyu.bean.getCircleArea.Root;
import com.home.wanyu.bean.getCircleCardList.StateList;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.NetWorkMyUtils;
import com.home.wanyu.myview.MyListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CircleMessageActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private ImageView mback;
    private ImageView mMsg_img;
    private ImageView mRed_img;

    private LinearLayout mMyArea_ll, mOtherArea_ll;
    private TextView mMyArea_tv, mOther_tv,mMy_line,mOther_line;

    private SwipeRefreshLayout mSwipe_refresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;

    private GridView mTitleGridview;
    private CircleGridViewAda mTitleGridviewAda;
    private List<com.home.wanyu.bean.getCircleTitleList.Result> mTitleList = new ArrayList<>();

    private MyListView mListview;
    private CircleFriendListviewAda mFriendAda;
    private List<com.home.wanyu.bean.getCircleCardList.Result> mCardList = new ArrayList<>();
    private ImageView mPost_img;
    private RelativeLayout mImgViewPager_rl,m_all_rl;
    private ViewPager mImgViewPager;
    private TextView mImg_Cancle_btn;
    private AlertDialog.Builder mYearBuilder;
    private AlertDialog mYearAlert;
    private View mAreaView;
    private ListView mAreaListview;
    private CircleSelectAreaAlertAda mAreaAdapter;
    private List<Result> mCircleAreaList = new ArrayList<>();

    private HttpTools mHttptools;
    private int myOrOtherID = 1;//1.我的小区 2.其他小区
    private long areaID = -1;//小区id
    private long typeID = 1;//类型id
    private int check = 1;//1,刷新数据，2，加载更多
    private int start = 0;
    private int limmit = 10;
    private RelativeLayout mNO_data_rl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 110) {//获取小区地址
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;

                    if (root.getResult() == null) {
                        //默认第一个小区
                        mMyArea_tv.setText("用户未填写");
                        Toast.makeText(CircleMessageActivity.this, "亲，请完善您的小区信息", Toast.LENGTH_SHORT).show();
                    } else {
                        mCircleAreaList = root.getResult();
                        mAreaAdapter.setList(mCircleAreaList);
                        mAreaAdapter.notifyDataSetChanged();
                        if (mCircleAreaList.size() > 1) {
                            mYearAlert.show();
                            setAlertWidth(mYearAlert, 2f);
                        } else {
                            //默认第一个小区
                            mMyArea_tv.setText(mCircleAreaList.get(0).getRname());
                            areaID = mCircleAreaList.get(0).getId();
                        }


                    }

                    mHttptools.getCircleSmallType(mHandler);//获取小标题列表数据
                }
            } else if (msg.what == 111) {//获取小标题列表
                Object o = msg.obj;
                //默认全部
                if (o != null && o instanceof com.home.wanyu.bean.getCircleTitleList.Root) {
                    com.home.wanyu.bean.getCircleTitleList.Root root = (com.home.wanyu.bean.getCircleTitleList.Root) o;
                    if (root.getResult() != null) {
                        mTitleList = root.getResult();
                        mTitleGridviewAda.setList(mTitleList);
                        mTitleGridviewAda.notifyDataSetChanged();
                        if (mCircleAreaList.size() != 0) {
                            //获取友邻圈帖子列表
                            mHttptools.getCircleCardList(mHandler, UserInfo.userToken, mCircleAreaList.get(0).getId(), myOrOtherID, start, limmit, mTitleList.get(0).getId());
                        } else {
                            mSwipe_refresh.setRefreshing(false);
                            mSwipe_refresh.setEnabled(false);
                            mNO_data_rl.setVisibility(View.VISIBLE);
                        }
                    }
                }


            } else if (msg.what == 202) {//获取小标题列表数据错误
                Toast.makeText(CircleMessageActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 112) {//获取友邻圈帖子列表接口
                Object o = msg.obj;
                //默认第一个小区，全部，的数据
                if (o != null && o instanceof com.home.wanyu.bean.getCircleCardList.Root) {
                    com.home.wanyu.bean.getCircleCardList.Root root = (com.home.wanyu.bean.getCircleCardList.Root) o;

                    if (check == 1) {//刷新
                        mSwipe_refresh.setRefreshing(false);
                        Log.e("RQid=", areaID + "");
                        Log.e("cateID=", typeID + "");
                        Log.e("我的或其他ID=", myOrOtherID + "");

                        if (root.getResult() == null) {
                            mNO_data_rl.setVisibility(View.VISIBLE);
                            mListview.setVisibility(View.GONE);
                            mCardList.clear();
                            mFriendAda.setList(mCardList, -1);
                            mFriendAda.notifyDataSetChanged();

                        } else {
                            mCardList = root.getResult();
                            if (mCardList != null && root.getResult().size() != 0) {
                                mNO_data_rl.setVisibility(View.GONE);
                                mListview.setVisibility(View.VISIBLE);
                                mFriendAda.setList(mCardList, root.getUserid());
                                mFriendAda.notifyDataSetChanged();
                            } else {
                                mListview.setVisibility(View.GONE);
                                mNO_data_rl.setVisibility(View.VISIBLE);
                            }

                            if (root.getResult().size() < 10) {
                                mMore_rl.setVisibility(View.GONE);
                                mBar.setVisibility(View.INVISIBLE);
                            } else {
                                mMore_rl.setVisibility(View.VISIBLE);
                                mBar.setVisibility(View.INVISIBLE);
                            }

                            Log.e("userid=", root.getUserid() + "");
                        }

                    } else if (check == 2) {//加载更多
                        List<com.home.wanyu.bean.getCircleCardList.Result> list = new ArrayList<>();
                        mSwipe_refresh.setRefreshing(false);
                        if (root.getResult() != null) {
                            list = root.getResult();
                            mCardList.addAll(list);
                            if (mCardList != null) {
                                mFriendAda.setList(mCardList, root.getUserid());
                                mFriendAda.notifyDataSetChanged();
                            }

                            if (root.getResult().size() < 10) {
                                mMore_rl.setVisibility(View.GONE);
                                mBar.setVisibility(View.INVISIBLE);
                            } else {
                                mMore_rl.setVisibility(View.VISIBLE);
                                mBar.setVisibility(View.INVISIBLE);
                            }

                        } else {
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }
                    }


                }
            } else if (msg.what == 203) {
                mSwipe_refresh.setRefreshing(false);
                mCardList.clear();
                mFriendAda.setList(mCardList, -1);
                mFriendAda.notifyDataSetChanged();
                mMore_rl.setVisibility(View.GONE);
                mBar.setVisibility(View.INVISIBLE);
            } else if (msg.what == 142) {//没有没新消息，小红点
                Object o = msg.obj;
                //默认全部
                if (o != null && o instanceof com.home.wanyu.bean.redCircleMsg.Root) {
                    com.home.wanyu.bean.redCircleMsg.Root root = (com.home.wanyu.bean.redCircleMsg.Root) o;
                    if (root.getHasMessage()) {
                        mRed_img.setVisibility(View.VISIBLE);
                    } else {
                        mRed_img.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    private ImageView mNetWorkBack;
    private TextView mNetWorkTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetWorkMyUtils.isNetworkConnected(this)) {
            setContentView(R.layout.activity_circle_message);
            mHttptools = HttpTools.getHttpToolsInstance();
            mHttptools.getCircleArea(mHandler, UserInfo.userToken);//获取小区地址
            mHttptools.getRedCircleMsg(mHandler, UserInfo.userToken, 11, 30);//没有没新消息，小红点
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
            mNetWorkTitle.setText(R.string.circle_msg_title);

        }


    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.circle_msg_back);//返回
        mback.setOnClickListener(this);
        //下拉刷新
        mSwipe_refresh = (SwipeRefreshLayout) findViewById(R.id.circle_refresh);
        mSwipe_refresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mSwipe_refresh.setRefreshing(true);
        mSwipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                check = 1;
                start = 0;
                mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
            }
        });
        //加载更多
        mMore_rl = (RelativeLayout) findViewById(R.id.circle_more_rl);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.circle_pbLocate);

        //我的小区
        mMyArea_ll = (LinearLayout) findViewById(R.id.circle_my_ll);
        mMyArea_ll.setOnClickListener(this);
        mMyArea_tv = (TextView) findViewById(R.id.circle_my_tv);
        mMy_line=(TextView) findViewById(R.id.my_line);
        //其他小区
        mOtherArea_ll = (LinearLayout) findViewById(R.id.circle_other_ll);
        mOtherArea_ll.setOnClickListener(this);
        mOther_tv = (TextView) findViewById(R.id.circle_other_tv);
        mOther_line=(TextView) findViewById(R.id.other_line);
        //头部标题列表
        mTitleGridview = (GridView) findViewById(R.id.circle_gridview);
        mTitleGridviewAda = new CircleGridViewAda(this, mTitleList);
        mTitleGridview.setAdapter(mTitleGridviewAda);
        //点击头部标题列表获取数据
        mTitleGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mTitleList != null && mTitleList.size() != 0 && mCircleAreaList != null) {
                    mListview.setVisibility(View.GONE);
                    mMore_rl.setVisibility(View.GONE);
                    check = 1;
                    for (int i = 0; i < mTitleList.size(); i++) {
                        if (mTitleGridviewAda.getItem(position) == mTitleList.get(i)) {
                            mTitleList.get(i).setFlag(true);
                        } else {
                            mTitleList.get(i).setFlag(false);
                        }
                    }
                    mTitleGridviewAda.notifyDataSetChanged();
                    typeID = mTitleList.get(position).getId();
                    if (mCircleAreaList.size() != 0) {
                        //获取指定列表友邻圈帖子列表
                        start = 0;
                        mSwipe_refresh.setRefreshing(true);
                        mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, mTitleList.get(position).getId());
                    }
                }
            }
        });


        //发帖
        mPost_img = (ImageView) findViewById(R.id.edit_post_img);
        mPost_img.setOnClickListener(this);


        //消息
        mMsg_img = (ImageView) findViewById(R.id.circle_small_red_img);
        mMsg_img.setOnClickListener(this);
        mRed_img = (ImageView) findViewById(R.id.circle_news_img);


        //选择我的小区弹框
        mYearBuilder = new AlertDialog.Builder(this);
        mYearAlert = mYearBuilder.create();
        mYearAlert.setCanceledOnTouchOutside(false);
        mAreaView = LayoutInflater.from(this).inflate(R.layout.circle_alert_area_box, null);
        mAreaListview = (ListView) mAreaView.findViewById(R.id.box_alert_listview);
        mAreaAdapter = new CircleSelectAreaAlertAda(this, mCircleAreaList);
        mAreaListview.setAdapter(mAreaAdapter);
        mYearAlert.setView(mAreaView);
        //选择小区
        mAreaListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCircleAreaList != null && mCircleAreaList.size() != 0) {
                    mMyArea_tv.setText(mCircleAreaList.get(position).getRname());
                    mYearAlert.dismiss();
                    check = 1;
                    areaID = mCircleAreaList.get(position).getId();
                    start = 0;
                    mSwipe_refresh.setRefreshing(true);
                    mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
                }

            }
        });

        mNO_data_rl = (RelativeLayout) findViewById(R.id.no_data_rl);

        //点击GridView中显示图片
        m_all_rl= (RelativeLayout) findViewById(R.id.m_all_rl);
        mImgViewPager_rl = (RelativeLayout) findViewById(R.id.img_viewpager_rl);
        mImgViewPager= (ViewPager) findViewById(R.id.img_viewpager);
        mImg_Cancle_btn= (TextView) findViewById(R.id.img_cancle);
        mImg_Cancle_btn.setOnClickListener(this);

        //帖子列表listview
        mListview = (MyListView) findViewById(R.id.circle_listview);
        mFriendAda = new CircleFriendListviewAda(this, mCardList,mImgViewPager,mImgViewPager_rl,m_all_rl);
        mListview.setAdapter(mFriendAda);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //点击我的小区
        if (id == mMyArea_ll.getId()) {
            mMore_rl.setVisibility(View.GONE);
            mListview.setVisibility(View.GONE);
            myOrOtherID = 1;
            check = 1;
            start = 0;
            if (mTitleList != null && mTitleList.size() != 0 && mCircleAreaList != null && mCircleAreaList.size() != 0) {
                mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
            }

            mMyArea_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mMy_line.setBackgroundResource(R.color.eac6);
            mOther_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mOther_line.setBackgroundResource(R.color.circle_bg);
            //点击其他小区
        } else if (id == mOtherArea_ll.getId()) {
            mListview.setVisibility(View.GONE);
            mMore_rl.setVisibility(View.GONE);
            myOrOtherID = 2;
            check = 1;
            //获取指定列表友邻圈帖子列表
            if (mTitleList != null && mTitleList.size() != 0 && mCircleAreaList != null && mCircleAreaList.size() != 0) {
                start = 0;
                mSwipe_refresh.setRefreshing(true);
                mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
            }
            mMyArea_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMy_line.setBackgroundResource(R.color.circle_bg);
            mOther_tv.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            mOther_line.setBackgroundResource(R.color.eac6);
        } else if (id == mPost_img.getId()) {//发帖
            startActivity(new Intent(this, CirclePostActivity.class));
        } else if (id == mMsg_img.getId()) {//跳转消息页面
            Intent intent = new Intent(this, CircleGiveYouCommentActivity.class);
            intent.putExtra("type", 0);
            startActivity(intent);
        } else if (id == mback.getId()) {//返回
            finish();
        } else if (id == mMore_rl.getId()) {//加载更多
            check = 2;
            start += 10;
            mBar.setVisibility(View.VISIBLE);
            mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
        }else if (id==mImg_Cancle_btn.getId()){//隐藏viewpager
            m_all_rl.setVisibility(View.VISIBLE);
            mImgViewPager_rl.setVisibility(View.GONE);
        }

    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, float a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels / a);
        alert.getWindow().setAttributes(p);//设置生效
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (areaID != -1 && typeID != -1) {
            start = 0;
            mHttptools.getCircleCardList(mHandler, UserInfo.userToken, areaID, myOrOtherID, start, limmit, typeID);
            Log.e("onResume", "areaID=" + areaID + "---typeID=" + typeID);
        }
    }

    private int lastX, lastY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = x - lastX;
                int offY = y - lastY;
                //调用layout方法来重新放置它的位置
                mPost_img.layout(mPost_img.getLeft() + offX, mPost_img.getTop() + offY,
                        mPost_img.getRight() + offX, mPost_img.getBottom() + offY);
              break;
            case MotionEvent.ACTION_UP:
                int offX1 = x - lastX;
                int offY1 = y - lastY;
                if (Math.abs(offX1)<50|Math.abs(offY1)<50){
                    return false;
                }else {
                    return true;
                }

        }
        return false;
    }

}



