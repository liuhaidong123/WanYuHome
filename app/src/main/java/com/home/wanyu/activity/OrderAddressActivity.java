package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.OrderAddressAda;
import com.home.wanyu.bean.haveAddress.Result;
import com.home.wanyu.bean.haveAddress.Root;

import java.util.ArrayList;
import java.util.List;

public class OrderAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private Button mAddAddress_btn;
    private ListView mListView;
    private OrderAddressAda mAdapter;
    private List<Result> mList = new ArrayList<>();

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlert;
    private View mView;
    private TextView mDelete;
    private TextView mUpdate;

    private HttpTools mHttptools;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                Object o=msg.obj;
                if (o!=null&&o instanceof Root){
                    Root root= (Root) o;
                    mList=root.getResult();
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);
        mHttptools=HttpTools.getHttpToolsInstance();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址列表
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.order_msg_back);
        mback.setOnClickListener(this);
        //添加地址
        mAddAddress_btn = (Button) findViewById(R.id.add_address_submit);
        mAddAddress_btn.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.order_address_listview);
        mAdapter = new OrderAddressAda(this, mList);
        mListView.setAdapter(mAdapter);

        mBuilder = new AlertDialog.Builder(this);
        mAlert = mBuilder.create();
        mView = LayoutInflater.from(this).inflate(R.layout.address_delete_orupdate_alert, null);
        mDelete= (TextView) mView.findViewById(R.id.delete);
        mUpdate=(TextView) mView.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mAlert.setView(mView);

        //选择地址
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.size()!=0){
                    Intent intent = getIntent();
                    intent.putExtra("result",mList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAlert.show();
                setAlertWidth(mAlert,1.5f);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mAddAddress_btn.getId()) {
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
        } else if (id == mDelete.getId()) {
            mAlert.dismiss();
        }else if (id == mUpdate.getId()) {
            mAlert.dismiss();
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, float a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) ((int)dm.widthPixels / a);
        alert.getWindow().setAttributes(p);//设置生效
    }
}
