package com.home.wanyu.activity;

import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.ExpressFaAda;
import com.home.wanyu.apater.ExpressMsgAda;
import com.home.wanyu.bean.Express.ExpressBean;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ExpressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private LinearLayout mShou_ll, mFa_ll;
    private TextView mShou_tv, mFa_tv, mPrompt;
    private MyListView mExpressMsgListview;
    private ExpressMsgAda mMsgAdapter;
    private SwipeRefreshLayout mrefresh;
    private ListView mFaListview;
    private ExpressFaAda mFaAdapter;
    private List<ExpressBean> mFaList = new ArrayList<>();
private RelativeLayout mRefresh_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        initView();
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

        mExpressMsgListview = (MyListView) findViewById(R.id.express_msg_listview);
        mMsgAdapter = new ExpressMsgAda(this);
        mExpressMsgListview.setAdapter(mMsgAdapter);

        mrefresh = (SwipeRefreshLayout) findViewById(R.id.mrefresh_express);

        ExpressBean e1 = new ExpressBean("顺丰快递", R.mipmap.sf, R.mipmap.express_phone, 18335277251l);
        ExpressBean e2 = new ExpressBean("韵达快递", R.mipmap.yunda, R.mipmap.express_phone, 18335277251l);
        ExpressBean e3 = new ExpressBean("圆通快递", R.mipmap.yt, R.mipmap.express_phone, 18335277251l);
        ExpressBean e4 = new ExpressBean("申通快递", R.mipmap.sto, R.mipmap.express_phone, 18335277251l);
        mFaList.add(e1);
        mFaList.add(e2);
        mFaList.add(e3);
        mFaList.add(e4);
        mFaListview = (ListView) findViewById(R.id.fa_listview);
        mFaAdapter = new ExpressFaAda(this, mFaList);
        mFaListview.setAdapter(mFaAdapter);

        mRefresh_rl= (RelativeLayout) findViewById(R.id.refresh_rl);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBack.getId()) {
            finish();
        } else if (id == mShou_ll.getId()) {
            mShou_ll.setBackgroundResource(R.color.bg_rect);
            mShou_tv.setTextColor(ContextCompat.getColor(this, R.color.white));

            mFa_ll.setBackgroundResource(R.color.white);
            mFa_tv.setTextColor(ContextCompat.getColor(this, R.color.titlecolor3));
            mRefresh_rl.setVisibility(View.VISIBLE);
            mFaListview.setVisibility(View.GONE);
            mrefresh.setVisibility(View.VISIBLE);
            mPrompt.setVisibility(View.VISIBLE);

        } else if (id == mFa_ll.getId()) {
            mShou_ll.setBackgroundResource(R.color.white);
            mShou_tv.setTextColor(ContextCompat.getColor(this, R.color.titlecolor3));

            mFa_ll.setBackgroundResource(R.color.bg_rect);
            mFa_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            mFaListview.setVisibility(View.VISIBLE);
            mRefresh_rl.setVisibility(View.GONE);
            mrefresh.setRefreshing(false);
            mrefresh.setVisibility(View.GONE);
            mPrompt.setVisibility(View.GONE);
        }
    }
}
