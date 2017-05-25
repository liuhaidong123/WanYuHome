package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CommercialAda;
import com.home.wanyu.myview.MyListView;

//附近商户
public class CommercialActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack;
    private MyListView myListView;
    private CommercialAda mAdapter;

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mMore_rl;
    private ProgressBar mBar;

    private RelativeLayout mAddress_rl;
    private TextView mAddress_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial);

        initView();
    }

    private void initView() {
       mBack= (ImageView) findViewById(R.id.commercial_back);
        mBack.setOnClickListener(this);

        //adapter
        myListView= (MyListView) findViewById(R.id.commercial_listview);
        mAdapter=new CommercialAda(this);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CommercialActivity.this,CommercialMessageActivity.class));
            }
        });
        //下拉刷新
        mRefresh= (SwipeRefreshLayout) findViewById(R.id.commercial_refresh);
        mRefresh.setColorSchemeResources(R.color.bg_rect,R.color.colorAccent,R.color.result_points);

        //加载更多
        mMore_rl= (RelativeLayout) findViewById(R.id.commercial_more_rl);
        mMore_rl.setOnClickListener(this);
        mBar= (ProgressBar) findViewById(R.id.circle_pbLocate);

        //选择地址
        mAddress_rl= (RelativeLayout) findViewById(R.id.commercial_address_rl);
        mAddress_rl.setOnClickListener(this);
        mAddress_msg= (TextView) findViewById(R.id.address_msg);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if (id==mBack.getId()){
            finish();
        }else if (id==mMore_rl.getId()){//加载更多

        }else if (id==mAddress_rl.getId()){//选择地址
            Intent intent=new Intent(this,CommercialSearchAddressActivity.class);
            startActivityForResult(intent,100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
