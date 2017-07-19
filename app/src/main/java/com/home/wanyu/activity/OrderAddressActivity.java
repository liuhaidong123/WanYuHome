package com.home.wanyu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.OrderAddressAda;
import com.home.wanyu.bean.haveAddress.Result;
import com.home.wanyu.bean.haveAddress.Root;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private TextView mAddAddress_btn;
    private ListView mListView;
    private AddressAda mAdapter;
    private List<Result> mList = new ArrayList<>();

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlert;
    private View mView;
    private TextView mDelete;
    private TextView mUpdate;
    private long addressId = -1;//删除地址需要的id
    private long addressID;//上个Activity传过来的地址ID
    private int mPosition;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mList = root.getResult();
                        //mAdapter.setList(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else if (msg.what == 141) {//删除地址
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(OrderAddressActivity.this, "删除地址成功", Toast.LENGTH_SHORT).show();
                        mList.remove(mPosition);
                        //mAdapter.setList(mList);
                        mAdapter.notifyDataSetChanged();


                    } else {
                        Toast.makeText(OrderAddressActivity.this, "删除地址失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        addressID=getIntent().getLongExtra("addressId",-1);
        mback = (ImageView) findViewById(R.id.order_msg_back);
        mback.setOnClickListener(this);
        //添加地址
        mAddAddress_btn = (TextView) findViewById(R.id.order_add_address);
        mAddAddress_btn.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.order_address_listview);
        mAdapter = new AddressAda();
        mListView.setAdapter(mAdapter);

        mBuilder = new AlertDialog.Builder(this);
        mAlert = mBuilder.create();
        mAlert.setCanceledOnTouchOutside(false);
        mView = LayoutInflater.from(this).inflate(R.layout.address_delete_orupdate_alert, null);
        mDelete = (TextView) mView.findViewById(R.id.delete);//确定删除
        mUpdate = (TextView) mView.findViewById(R.id.update);//取消删除
        mUpdate.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mAlert.setView(mView);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();

        } else if (id == mAddAddress_btn.getId()) {
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
        } else if (id == mDelete.getId()) {//删除
            mHttptools.addressDelete(mHandler, UserInfo.userToken, addressId);
            mAlert.dismiss();
        } else if (id == mUpdate.getId()) {//取消
            mAlert.dismiss();

        }
    }

    //设置alert宽度
    public void setAlertWidth(AlertDialog alert) {

        android.view.WindowManager.LayoutParams p = alert.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = dip2px(this, 270.5f);
        alert.getWindow().setAttributes(p);//设置生效
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHttptools.haveUserAddress(mHandler, UserInfo.userToken);//获取地址列表
    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    /**
     * 账单地址适配器
     */
    class AddressAda extends BaseAdapter {
        private LayoutInflater mInflater;

        public AddressAda() {
            this.mInflater = LayoutInflater.from(OrderAddressActivity.this);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AddressHolder holder = null;
            if (convertView == null) {
                holder = new AddressHolder();
                convertView = mInflater.inflate(R.layout.order_address_msg_listview_item, null);
                holder.textView = (TextView) convertView.findViewById(R.id.order_address_ms);
                holder.mAddress_bg = (RelativeLayout) convertView.findViewById(R.id.order_address_bg);
                holder.mDelete_ll = (LinearLayout) convertView.findViewById(R.id.order_delete_ll);
                holder.mEdit_ll = (LinearLayout) convertView.findViewById(R.id.order_edit_ll);
                convertView.setTag(holder);
            } else {
                holder = (AddressHolder) convertView.getTag();
            }

            holder.textView.setText(mList.get(position).getDetailAddress());
            if (addressID == mList.get(position).getId()) {
                holder.mAddress_bg.setBackgroundResource(R.drawable.order_select_btn);
            } else {
                holder.mAddress_bg.setBackgroundResource(R.drawable.order_select_btn_no);
            }

            //将这个地址的返回给上一个Activity
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.size() != 0) {
                        Intent intent = getIntent();
                        intent.putExtra("result", mList.get(position));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });


            // 删除
            holder.mDelete_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addressId = mList.get(position).getId();
                    mPosition = position;
                    mAlert.show();
                    setAlertWidth(mAlert);
                }
            });

            //编辑
            holder.mEdit_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderAddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("type", 10);
                    intent.putExtra("id", mList.get(position).getId());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    class AddressHolder {
        TextView textView;
        RelativeLayout mAddress_bg;
        LinearLayout mDelete_ll, mEdit_ll;
    }

}
