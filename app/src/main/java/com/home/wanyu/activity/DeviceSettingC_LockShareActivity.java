package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.home.wanyu.C_View.DeviceSettingC_LockSharePresenter;
import com.home.wanyu.R;
import com.home.wanyu.apater.DeviceSettingC_LockShareAdapter;
import com.home.wanyu.bean.Bean_ShareMumber;
import com.home.wanyu.lzhView.MyList;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.myview.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//分享钥匙
public class DeviceSettingC_LockShareActivity extends CMyActivity implements AdapterView.OnItemClickListener,DeviceSettingC_LockSharePresenter.TimeSelect {
    DeviceSettingC_LockSharePresenter presenter;
    @BindView(R.id.lockShare_listview)MyListView listview;
    @BindView(R.id.lockShare_textv_startTime)TextView lockShare_textv_startTime;//钥匙开始时间
    @BindView(R.id.lockShare_textv_endTime)TextView lockShare_textv_endTime;//钥匙结束时间
    DeviceSettingC_LockShareAdapter adapter;
    ArrayList<Bean_ShareMumber> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting_c__lock_share);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("分享钥匙","--",true);
        unbinder= ButterKnife.bind(this);

        presenter=new DeviceSettingC_LockSharePresenter(this,this);
        initAdapter();//测试用
    }

    private void initAdapter() {
        list=new ArrayList<>();
        for (int i=0;i<15;i++){
            Bean_ShareMumber mumber=new Bean_ShareMumber();
            mumber.setImageRes(R.mipmap.it1);
            mumber.setName("LIM"+i);
            mumber.setSelect(false);
            list.add(mumber);
        }
        adapter=new DeviceSettingC_LockShareAdapter(this,list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @OnClick({R.id.lockShare_textv_startTime,R.id.lockShare_textv_endTime,R.id.lockShare_submit})
    public void click(View view){
        switch (view.getId()){
            case R.id.lockShare_textv_startTime://选择开始时间
                presenter.ShowWindowSelect(DeviceSettingC_LockSharePresenter.Tim.START);
                break;
            case R.id.lockShare_textv_endTime://选择结束时间
                presenter.ShowWindowSelect(DeviceSettingC_LockSharePresenter.Tim.END);
                break;
            case R.id.lockShare_submit://提交按钮

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            initList(position);
    }

    public void initList(int pos){
        for (int i=0;i<list.size();i++){
            list.get(i).setSelect(false);
        }
        list.get(pos).setSelect(true);
        adapter.notifyDataSetChanged();
    }

    //设置开始时间，结束时间参数time：时间字符串 参数ti：标识位（开始／结束）
    @Override
    public void setTime(String time, DeviceSettingC_LockSharePresenter.Tim ti) {
        switch (ti){
            case START:
                lockShare_textv_startTime.setText(time);
                break;
            case END:
                lockShare_textv_endTime.setText(time);
                break;
        }
    }
}
