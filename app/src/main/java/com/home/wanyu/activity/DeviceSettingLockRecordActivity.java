package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.LockRecordAdapter;
import com.home.wanyu.bean.Bean_lockRecord;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.MyList;
import com.home.wanyu.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//开锁记录
public class DeviceSettingLockRecordActivity extends MyActivity {
    private String DeviceId;
    LockRecordAdapter adapter;//开锁记录的adapter
    @BindView(R.id.lock_record_loadingLayout)RelativeLayout lock_record_loadingLayout;//加载跟多的view
    @BindView(R.id.lock_record_progress)ProgressBar lock_record_progress;
    @BindView(R.id.lock_record_loading_text)TextView lock_record_loading_text;

    @BindView(R.id.lock_record_listview)MyListView lock_record_listview;
    int count=10;
    int start=0;
    int limit=10;
    private String resStr;
    private boolean isLoading=false;
    List<Bean_lockRecord.RowsBean>li;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isLoading=false;
                    mToast.ToastFaild(con, ToastType.FAILD);
                    lock_record_loadingLayout.setVisibility(View.GONE);
                    break;
                case 1:
                    isLoading=false;
                    try{
                        Bean_lockRecord record= mGson.gson.fromJson(resStr,Bean_lockRecord.class);
                        if (record!=null){
                                List<Bean_lockRecord.RowsBean>lit= record.getRows();
                                if (lit!=null&&lit.size()>0){
                                   li.addAll(lit);
                                    adapter.notifyDataSetChanged();
                                    if (lit.size()==limit){
                                        setLoadingState(0);
                                        lock_record_loadingLayout.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        lock_record_loadingLayout.setVisibility(View.GONE);
                                    }
                                    start+=lit.size();
                                }
                                else {
                                    lock_record_loadingLayout.setVisibility(View.GONE);
                                }
                        }
                        else {
                            lock_record_loadingLayout.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                        lock_record_loadingLayout.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("开锁记录");
        initChildView(R.layout.activity_device_setting_lock_record);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        DeviceId=getIntent().getStringExtra("id");
        lock_record_loadingLayout.setVisibility(View.GONE);
        li=new ArrayList<>();
        adapter=new LockRecordAdapter(con,li);
        lock_record_listview.setAdapter(adapter);
        getSerVerData();

    }
    @OnClick(R.id.lock_record_loadingLayout)
    public void click(View vi){
        switch (vi.getId()){
            case R.id.lock_record_loadingLayout://加载更多
                setLoadingState(1);
                getRecord(start,limit);
                break;
        }
    }
    public void setLoadingState(int state){
        switch (state){
            case 0://加载跟多
                lock_record_progress.setVisibility(View.GONE);
                lock_record_loading_text.setText("加载更多");
                break;
            case 1://正在加载
                lock_record_progress.setVisibility(View.VISIBLE);
                lock_record_loading_text.setText("正在加载。。。");
                break;
        }
    }
    //http://192.168.1.55:8080/smarthome/mobileapi/unlockRecords/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&equipmentId=6
    @Override
    public void getSerVerData() {
        if (!"".equals(DeviceId)&&!TextUtils.isEmpty(DeviceId)){
               getRecord(start,limit);
        }
        else {
            Log.e(TAG,"无法获取到设备id，无法查询开锁记录");
        }
    }

    public void getRecord(int st,int limi){
        if ( isLoading){
            return;
        }
        isLoading=true;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("equipmentId",DeviceId);
        Log.i("Deviceid--",DeviceId);
        mp.put("start",st+"");
        mp.put("limit",limi+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_getLockRecord,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取开锁记录---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
