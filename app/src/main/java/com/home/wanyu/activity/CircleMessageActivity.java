package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CircleFriendListviewAda;
import com.home.wanyu.apater.CircleGridViewAda;
import com.home.wanyu.bean.CircleFriend;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class CircleMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private ImageView mMsg_img;

    private LinearLayout mMyArea_ll, mOtherArea_ll;
    private TextView mMyArea_tv, mOther_tv;

    private GridView mTitleGridview;
    private CircleGridViewAda mTitleGridviewAda;
    private List<CircleFriend> mTitleList = new ArrayList<>();

    private MyListView mListview;
    private CircleFriendListviewAda mFriendAda;
    private ImageView mPost_img;

    private AlertDialog.Builder mYearBuilder;
    private AlertDialog mYearAlert;
    private View mYearView;
    private ListView mYearListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_message);
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.circle_msg_back);//返回
        mback.setOnClickListener(this);
        //我的小区
        mMyArea_ll = (LinearLayout) findViewById(R.id.circle_my_ll);
        //其他小区
        mOtherArea_ll = (LinearLayout) findViewById(R.id.circle_other_ll);
        mMyArea_ll.setOnClickListener(this);
        mOtherArea_ll.setOnClickListener(this);
        mMyArea_tv = (TextView) findViewById(R.id.circle_my_tv);
        mOther_tv = (TextView) findViewById(R.id.circle_other_tv);

        mTitleList.add(new CircleFriend("全部", true));
        mTitleList.add(new CircleFriend("健康", false));
        mTitleList.add(new CircleFriend("居家", false));
        mTitleList.add(new CircleFriend("母婴", false));
        mTitleList.add(new CircleFriend("旅游", false));
        mTitleList.add(new CircleFriend("美食", false));
        mTitleList.add(new CircleFriend("宠物", false));
        //头部标题列表
        mTitleGridview = (GridView) findViewById(R.id.circle_gridview);
        mTitleGridviewAda = new CircleGridViewAda(this, mTitleList);
        mTitleGridview.setAdapter(mTitleGridviewAda);
        mTitleGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.circle_small_line);
                for (int i = 0; i < mTitleList.size(); i++) {
                    if (mTitleGridviewAda.getItem(position) == mTitleList.get(i)) {
                        mTitleList.get(i).setFlag(true);
                    } else {
                        mTitleList.get(i).setFlag(false);
                    }

                }

                mTitleGridviewAda.notifyDataSetChanged();
            }
        });

        //评论区域listview
        mListview= (MyListView) findViewById(R.id.circle_listview);
        mFriendAda=new CircleFriendListviewAda(this);
        mListview.setAdapter(mFriendAda);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CircleMessageActivity.this,CircleCardMessageActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        //发帖
        mPost_img= (ImageView) findViewById(R.id.circle_post_img);
        mPost_img.setOnClickListener(this);

        //消息
        mMsg_img= (ImageView) findViewById(R.id.circle_small_red_img);
        mMsg_img.setOnClickListener(this);


        //年份弹框
        mYearBuilder = new AlertDialog.Builder(this);
        mYearAlert = mYearBuilder.create();
        mYearView = LayoutInflater.from(this).inflate(R.layout.order_year_item, null);
        mYearListview = (ListView) mYearView.findViewById(R.id.city_alert_listview);
        //选择城市
        mYearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // mYear_tv.setText(mYearAda.getItem(position).toString());
                mYearAlert.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //点击我的小区
        if (id == mMyArea_ll.getId()) {
            mMyArea_ll.setBackgroundResource(R.color.bg_rect);
            mMyArea_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mOtherArea_ll.setBackgroundResource(R.color.white);
            mOther_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));
            //点击其他小区
        } else if (id == mOtherArea_ll.getId()) {
            mMyArea_ll.setBackgroundResource(R.color.white);
            mMyArea_tv.setTextColor(ContextCompat.getColor(this, R.color.title_color));

            mOtherArea_ll.setBackgroundResource(R.color.bg_rect);
            mOther_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        }else if (id==mPost_img.getId()){//发帖
            startActivity(new Intent(this,CirclePostActivity.class));
        }else if (id==mMsg_img.getId()){//跳转消息页面
          Intent intent = new Intent(this,CircleGiveYouCommentActivity.class);
            startActivity(intent);
        }else if (id==mback.getId()){//返回
            finish();
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
}
