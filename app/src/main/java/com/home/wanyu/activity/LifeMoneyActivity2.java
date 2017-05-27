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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.Money2Ada;
import com.home.wanyu.bean.haveAddress.Result;
import com.home.wanyu.bean.haveAddress.Root;

import java.util.ArrayList;
import java.util.List;

import static com.home.wanyu.R.id.money2_msg_address_rl;

public class LifeMoneyActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private ListView mListview;
    private RelativeLayout mIntentAddress;
    private TextView mMoney2_Record;
    private Money2Ada mAdapter;
    private List<Integer> mImgList = new ArrayList<>();

    private int flag = 1;//1代表默认刚进来的第一个地址，2代表跳转到地址列表后选择地址
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlert;
    private View mView;
    private TextView mCancle;
    private TextView mUpdate;
    private TextView mAddressCity_tv, mAddress_tv;
    private List<Result> mAddressList = new ArrayList<>();
    private Result result;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {//获取地址
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mAddressList = root.getResult();
                        if (mAddressList.size() != 0) {
                            mAddressCity_tv.setText(mAddressList.get(0).getCity());
                            mAddress_tv.setText(mAddressList.get(0).getDetailAddress());
                        } else {
                            mAddressCity_tv.setText("未知城市");
                            mAddress_tv.setText("未知小区");
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_money2);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址接口
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.money2_back);
        mBack.setOnClickListener(this);
        //城市，地址
        mAddressCity_tv = (TextView) findViewById(R.id.money2_msg_pro);
        mAddress_tv = (TextView) findViewById(R.id.money2_address_msg);
        //缴费记录
        mMoney2_Record = (TextView) findViewById(R.id.money2_record);
        mMoney2_Record.setOnClickListener(this);
        //跳转到地址
        mIntentAddress = (RelativeLayout) findViewById(R.id.money2_msg_address_rl);
        mIntentAddress.setOnClickListener(this);
        //适配器
        mImgList.add(R.mipmap.money_ele);
        mImgList.add(R.mipmap.money_water);
        mImgList.add(R.mipmap.money_ran);
        mImgList.add(R.mipmap.money_property);
        mListview = (ListView) findViewById(R.id.money2_listview);
        mAdapter = new Money2Ada(this, mImgList);
        mListview.setAdapter(mAdapter);

        //
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAddressList.size() != 0) {
                    Intent intent = new Intent(LifeMoneyActivity2.this, MoneyNumActivity.class);
                    if (flag == 1) {//默认地址
                        intent.putExtra("bean", mAddressList.get(0));

                    } else if (flag == 2) {
                        if (result != null) {
                            intent.putExtra("bean", result);
                            //还需要交费类型,用户编号
                           // intent.putExtra("type", "");
                            // intent.putExtra("usernum", "");
                        } else {
                            Toast.makeText(LifeMoneyActivity2.this, "请选择小区", Toast.LENGTH_SHORT).show();
                        }
                    }
                    intent.putExtra("update", 2);
                    startActivity(intent);

                } else {
                    Toast.makeText(LifeMoneyActivity2.this, "请选择小区", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //长按修改
        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAlert.show();
                setAlertWidth(mAlert, 1.5f);
                return true;
            }
        });
        //长安弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlert = mBuilder.create();
        mView = LayoutInflater.from(this).inflate(R.layout.money_update_num, null);
        mCancle = (TextView) mView.findViewById(R.id.cancle);
        mUpdate = (TextView) mView.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        mAlert.setView(mView);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mIntentAddress.getId()) {
            startActivityForResult(new Intent(this, OrderAddressActivity.class), 66);

        } else if (id == mCancle.getId()) {
            mAlert.dismiss();
        } else if (id == mUpdate.getId()) {//修改编号
            Intent intent = new Intent(this, MoneyNumActivity.class);
            intent.putExtra("update", 1);
            startActivity(intent);
            mAlert.dismiss();
        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert, float a) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) ((int) dm.widthPixels / a);
        alert.getWindow().setAttributes(p);//设置生效
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66 && resultCode == RESULT_OK) {
            flag = 2;
            result = (Result) data.getSerializableExtra("result");
            mAddressCity_tv.setText(result.getCity());
            mAddress_tv.setText(result.getDetailAddress());
        }
    }
}
