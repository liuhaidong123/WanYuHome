package com.home.wanyu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.home.wanyu.apater.ExpressFaAda;
import com.home.wanyu.apater.ExpressMsgAda;
import com.home.wanyu.bean.expressList.Result;
import com.home.wanyu.bean.expressList.Root;
import com.home.wanyu.bean.expressList.Rows;
import com.home.wanyu.myUtils.NetWorkMyUtils;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ExpressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private LinearLayout mShou_ll, mFa_ll;
    private TextView mShou_tv, mFa_tv, mPrompt;
    private MyListView mExpressMsgListview;
    private ExpressMsgAda mMsgAdapter;
    private List<com.home.wanyu.bean.expressList.Result> mExpressList = new ArrayList<>();

    private SwipeRefreshLayout mrefresh;
    private RelativeLayout mRefresh_rl;
    private RelativeLayout mMore_rl;
    private ProgressBar mbar;
    private ListView mFaListview;
    private ExpressFaAda mFaAdapter;
    private List<com.home.wanyu.bean.expressCompany.Result> mFaList = new ArrayList<>();

    private int start = 0;
    private int limit = 10;
    private int flag = 1;//1刷新2加载
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 137) {//获取所有快递数据
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mrefresh.setRefreshing(false);
                    Root root = (Root) o;
                    if (root.getResult() != null) {

                        if (flag == 1) {
                            if (root.getResult().size()==0){
                                Toast.makeText(ExpressActivity.this,"抱歉,您还没有快递哦!",Toast.LENGTH_SHORT).show();
                            }else {
                              //  mNo_data_rl.setVisibility(View.GONE);
                                mExpressList = root.getResult();
                                mMsgAdapter.setmExpressList(mExpressList);
                                mMsgAdapter.notifyDataSetChanged();
                            }

                        } else {
                            List<Result> list = new ArrayList<>();
                            list = root.getResult();
                            mExpressList.addAll(list);
                            mMsgAdapter.setmExpressList(mExpressList);
                            mMsgAdapter.notifyDataSetChanged();

                        }

                        if (root.getResult().size() == 10) {
                            mMore_rl.setVisibility(View.VISIBLE);
                            mbar.setVisibility(View.INVISIBLE);
                        } else {
                            mMore_rl.setVisibility(View.GONE);
                            mbar.setVisibility(View.INVISIBLE);
                        }

                    }
                }
            } else if (msg.what == 138) {//获取快递公司
                mrefresh.setRefreshing(false);
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.expressCompany.Root) {
                    com.home.wanyu.bean.expressCompany.Root root = (com.home.wanyu.bean.expressCompany.Root) o;
                    if (root.getResult() != null) {
                        mFaList = root.getResult();
                        mFaAdapter = new ExpressFaAda(ExpressActivity.this, mFaList);
                        mFaListview.setAdapter(mFaAdapter);
                    }
                }
            }
        }
    };

    private RelativeLayout mNo_data_rl;
    private ImageView mNetWorkBack;
    private TextView mNetWorkTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetWorkMyUtils.isNetworkConnected(this)) {
            setContentView(R.layout.activity_express);
            httpTools = HttpTools.getHttpToolsInstance();
            httpTools.expressAllList(handler, UserInfo.userToken, start, limit);//获取所有快递数据
            httpTools.expressCompanyMsg(handler, UserInfo.userToken);//获取快递公司
            initView();
        }else {
            setContentView(R.layout.no_network);
            mNetWorkBack = (ImageView) findViewById(R.id.network_back);
            mNetWorkBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mNetWorkTitle = (TextView) findViewById(R.id.network_title_msg);
            mNetWorkTitle.setText(R.string.property_express);
        }

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.express_msg_back);
        mBack.setOnClickListener(this);

        mShou_ll = (LinearLayout) findViewById(R.id.express_shou_ll);
        mFa_ll = (LinearLayout) findViewById(R.id.express_fa_ll);
        mShou_ll.setOnClickListener(this);
        mFa_ll.setOnClickListener(this);
        mShou_tv = (TextView) findViewById(R.id.express_shou_tv);
        mFa_tv = (TextView) findViewById(R.id.express_fa_tv);
        mPrompt = (TextView) findViewById(R.id.express_prompt);

        //所有快递数据
        mExpressMsgListview = (MyListView) findViewById(R.id.express_msg_listview);
        mMsgAdapter = new ExpressMsgAda(this, mExpressList);
        mExpressMsgListview.setAdapter(mMsgAdapter);
        //快递公司
        mFaListview = (ListView) findViewById(R.id.fa_listview);

        mrefresh = (SwipeRefreshLayout) findViewById(R.id.mrefresh_express);
        mrefresh.setRefreshing(true);
        mrefresh.setColorSchemeResources(R.color.bg_rect, R.color.colorAccent, R.color.result_points);
        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 1;
                start = 0;
                httpTools.expressAllList(handler, UserInfo.userToken, start, limit);//获取所有快递数据
            }
        });


        mRefresh_rl = (RelativeLayout) findViewById(R.id.refresh_rl);
        mMore_rl = (RelativeLayout) findViewById(R.id.express_many_relative);
        mMore_rl.setOnClickListener(this);
        mbar = (ProgressBar) findViewById(R.id.express_pbLocate);

        //没有数据
      //  mNo_data_rl= (RelativeLayout) findViewById(R.id.no_data_express);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBack.getId()) {
            finish();
        } else if (id == mShou_ll.getId()) {//显示所有快递
            mShou_ll.setBackgroundResource(R.color.bg_rect);
            mShou_tv.setTextColor(ContextCompat.getColor(this, R.color.white));

            mFa_ll.setBackgroundResource(R.color.white);
            mFa_tv.setTextColor(ContextCompat.getColor(this, R.color.titlecolor3));
            mRefresh_rl.setVisibility(View.VISIBLE);
            mFaListview.setVisibility(View.GONE);
            mrefresh.setVisibility(View.VISIBLE);
            mPrompt.setVisibility(View.VISIBLE);
          //  mNo_data_rl.setVisibility(View.GONE);

        } else if (id == mFa_ll.getId()) {//显示快递公司
            mShou_ll.setBackgroundResource(R.color.white);
            mShou_tv.setTextColor(ContextCompat.getColor(this, R.color.titlecolor3));

            mFa_ll.setBackgroundResource(R.color.bg_rect);
            mFa_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mFaListview.setVisibility(View.VISIBLE);
            mRefresh_rl.setVisibility(View.GONE);
            mrefresh.setRefreshing(false);
            mrefresh.setVisibility(View.GONE);
            mPrompt.setVisibility(View.GONE);
         //   mNo_data_rl.setVisibility(View.GONE);
        } else if (id == mMore_rl.getId()) {
            flag = 2;
            start += 10;
            mbar.setVisibility(View.VISIBLE);
            httpTools.expressAllList(handler, UserInfo.userToken, start, limit);//获取所有快递数据
        }
    }
}
