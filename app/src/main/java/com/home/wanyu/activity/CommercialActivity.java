package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CommercialAda;
import com.home.wanyu.bean.shoppingList.Result;
import com.home.wanyu.bean.shoppingList.Root;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

//附近商户
public class CommercialActivity extends AppCompatActivity implements View.OnClickListener {

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
                            mMore_rl.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial);
        mHttptools = HttpTools.getHttpToolsInstance();

        initView();
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
                mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, mAddress_msg.getText().toString(), 115.984108
                        , 39.484636);
                Log.e("mRefresh", mAddress_msg.getText().toString());
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

        mHttptools.shoppingList(mhandler, UserInfo.userToken, start, limit, mAddress_msg.getText().toString(), 115.984108
                , 39.484636);

        Log.e("小区名称", mAddress_msg.getText().toString());
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


}
