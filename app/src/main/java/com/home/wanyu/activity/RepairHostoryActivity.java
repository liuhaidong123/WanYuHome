package com.home.wanyu.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.apater.RecordListviewAda;
import com.home.wanyu.bean.HouseLookConfigure;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

import static com.home.wanyu.R.id.repair_ll;

public class RepairHostoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mAll_type, mAll_status;
    private LinearLayout mAll_type_ll, mAll_status_ll;
    private PopupWindow mPopupWindow_type, mPopupWindow_status;
    private View mView_type, mView_status;
    private GridView mGridview_type, mGridview_status;
    private List<String> mType_list = new ArrayList<>();
    private List<String> mStatus_list = new ArrayList<>();

    private MyListView myListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_hostory);
        initUI();
    }

    private void initUI() {
        mBack = (ImageView) findViewById(R.id.repair_back);
        mBack.setOnClickListener(this);

        mAll_type = (TextView) findViewById(R.id.repair_type_tv);
        mAll_status = (TextView) findViewById(R.id.repair_status_tv);

        mAll_type_ll = (LinearLayout) findViewById(R.id.repair_ll);//全部类型
        mAll_status_ll = (LinearLayout) findViewById(R.id.record_ll);//全部状态
        mAll_type_ll.setOnClickListener(this);
        mAll_status_ll.setOnClickListener(this);

        //全部类型
        mView_type = LayoutInflater.from(this).inflate(R.layout.repair_hostory_all_type, null);
        mGridview_type = (GridView) mView_type.findViewById(R.id.repair_all_type_gridview);
        mType_list.add("全部类型");
        mType_list.add("水电燃气");
        mType_list.add("房屋报修");
        mType_list.add("公共设施");
        mGridview_type.setAdapter(new RepairTypeAda());
        mGridview_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow_type.dismiss();
                mAll_type.setText(mType_list.get(position));
            }
        });

        //全部状态
        mView_status = LayoutInflater.from(this).inflate(R.layout.repair_hostory_all_status, null);
        mGridview_status = (GridView) mView_status.findViewById(R.id.repair_all_status_gridview);
        mStatus_list.add("全部状态");
        mStatus_list.add("待维修");
        mStatus_list.add("处理中");
        mStatus_list.add("已处理");
        mGridview_status.setAdapter(new RepairStatusAda());
        mGridview_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow_status.dismiss();
                mAll_status.setText(mStatus_list.get(position));
            }
        });
        //报修记录
        myListview = (MyListView) findViewById(R.id.record_listview);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mAll_type_ll.getId()) {//全部类型
            mAll_type.setTextColor(ContextCompat.getColor(this, R.color.eac6));
            showTypePopuWindow();
        } else if (id == mAll_status_ll.getId()) {//全部状态
            showStatusPopuWindow();
            mAll_status.setTextColor(ContextCompat.getColor(this, R.color.eac6));
        }
    }


    /**
     * 全部类型
     */
    private void showTypePopuWindow() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        LinearLayout ll = (LinearLayout) findViewById(R.id.two_ll);
        mPopupWindow_type = new PopupWindow(mView_type);
        //设置弹框的款，高
        mPopupWindow_type.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow_type.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow_type.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupWindow_type.setOutsideTouchable(true);//设置内容外可以点击
        // mPopupWindow_type.setAnimationStyle(R.style.popup3_anim);
        //相对某个控件
        mPopupWindow_type.showAsDropDown(ll, 0, dip2px(this, 162.0f));
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPopupWindow_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mAll_type.setTextColor(ContextCompat.getColor(RepairHostoryActivity.this, R.color.titlecolor3));
            }
        });
    }

    /**
     * 全部状态
     */

    private void showStatusPopuWindow() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        LinearLayout ll = (LinearLayout) findViewById(R.id.two_ll);
        mPopupWindow_status = new PopupWindow(mView_status);
        //设置弹框的款，高
        mPopupWindow_status.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow_status.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow_status.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupWindow_status.setOutsideTouchable(true);//设置内容外可以点击
        // mPopupWindow_type.setAnimationStyle(R.style.popup3_anim);
        //相对某个控件
        mPopupWindow_status.showAsDropDown(ll, 0, dip2px(this, 162.0f));
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPopupWindow_status.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mAll_status.setTextColor(ContextCompat.getColor(RepairHostoryActivity.this, R.color.titlecolor3));
            }
        });
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

    /**
     * 全部类型adpater
     */
    class RepairTypeAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mType_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TypeHolder holder = null;
            if (convertView == null) {
                holder = new TypeHolder();
                convertView = LayoutInflater.from(RepairHostoryActivity.this).inflate(R.layout.repair_hostory_all_type_item, null);
                holder.textview = (TextView) convertView.findViewById(R.id.repair_type_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (TypeHolder) convertView.getTag();
            }
            holder.textview.setText(mType_list.get(position));

            return convertView;
        }

        class TypeHolder {
            TextView textview;
        }
    }

    /**
     * 全部状态adpater
     */
    class RepairStatusAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mStatus_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StatusHolder holder = null;
            if (convertView == null) {
                holder = new StatusHolder();
                convertView = LayoutInflater.from(RepairHostoryActivity.this).inflate(R.layout.repair_hostory_all_type_item, null);
                holder.textview = (TextView) convertView.findViewById(R.id.repair_type_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (StatusHolder) convertView.getTag();
            }
            holder.textview.setText(mStatus_list.get(position));
            return convertView;
        }

        class StatusHolder {
            TextView textview;
        }
    }
}
