package com.home.wanyu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.HouseMsgListAda;
import com.home.wanyu.bean.HouseFirstList.Root;
import com.home.wanyu.bean.HouseFirstList.Rows;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myUtils.SearchAreaDB;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class HouseSearchAreaListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;

    private MyListView myListView;
    private HouseMsgListAda msgListAda;
    private List<Rows> mList = new ArrayList<>();

    private ImageView mPost_card;

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl, mSearch_rl;
    private ProgressBar mBar;
    private int start = 0;
    private int limit = 10;
    private int flag = 1;//1刷新，2加载
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 146) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mRefresh.setRefreshing(false);
                    MyDialog.stopDia();
                    if (root != null && root.getRows() != null) {
                        if (flag == 1) {
                            mList = root.getRows();
                            msgListAda.setmList(mList);
                            msgListAda.notifyDataSetChanged();
                        } else {
                            List<Rows> list = new ArrayList<>();
                            list = root.getRows();
                            mList.addAll(list);
                            msgListAda.setmList(mList);
                            msgListAda.notifyDataSetChanged();
                        }

                        if (root.getRows().size() >= 10) {
                            mMore_rl.setVisibility(View.VISIBLE);
                            mBar.setVisibility(View.INVISIBLE);
                        } else {
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        Toast.makeText(HouseSearchAreaListActivity.this, "房屋信息错误", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_search_area_list);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_area_back);
        mBack.setOnClickListener(this);
        //搜索
        mSearch_rl = (RelativeLayout) findViewById(R.id.input_area_name);
        mSearch_rl.setOnClickListener(this);

        //小区房屋列表
        myListView = (MyListView) findViewById(R.id.house_area_refresh_listview);
        msgListAda = new HouseMsgListAda(this, mList);
        myListView.setAdapter(msgListAda);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HouseSearchAreaListActivity.this, HouseLookMessageActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                startActivity(intent);
            }
        });

        //发帖
        mPost_card = (ImageView) findViewById(R.id.area_post_card);
        mPost_card.setOnClickListener(this);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.house_area_refresh);
        mRefresh.setRefreshing(true);
        mRefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 1;
                start = 0;
                mHttptools.getHouseByAreaAndCity(handler, UserInfo.userToken, getIntent().getStringExtra("city"), start, limit, getIntent().getStringExtra("searchArea"));
            }
        });
        mMore_rl = (RelativeLayout) findViewById(R.id.house_area_more_rl);
        mMore_rl.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.circle_pbLocate);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mPost_card.getId()) {
            startActivity(new Intent(this, HousePostCardActivity.class));
        } else if (id == mSearch_rl.getId()) {//跳转到搜索
            Intent intent = new Intent(this, HouseSearchAddessAndAreaActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("city", getIntent().getStringExtra("city"));
            startActivity(intent);
            finish();
        } else if (id == mMore_rl.getId()) {//加载更多
            flag = 2;
            start += 10;
            mBar.setVisibility(View.VISIBLE);
            //获取定位城市下的所有小区
            mHttptools.getHouseByAreaAndCity(handler, UserInfo.userToken, getIntent().getStringExtra("city"), start, limit, getIntent().getStringExtra("searchArea"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHttptools.getHouseByAreaAndCity(handler, UserInfo.userToken, getIntent().getStringExtra("city"), start, limit, getIntent().getStringExtra("searchArea"));
    }
}
