package com.home.wanyu.C_View;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.adapter.LockPopGridViewAdapter;

/**
 * Created by wanyu on 2017/7/21.
 */

public class DeviceSettingC_LockPresenter implements AdapterView.OnItemClickListener{
    PopupWindow pop;
    TextView pop_lock_open_password;//密码显示框
    GridView pop_lock_open_gridView;
    LockPopGridViewAdapter adapter;
    Locking locking;
    Activity activity;
    String psd="";
    public DeviceSettingC_LockPresenter(Activity activity,Locking locking){
        this.activity=activity;
        this.locking=locking;
    }
    //密码解锁弹窗
    public void ShowWindowLocking(){
        psd="";//默认；
        if (pop==null){
            pop=new PopupWindow();
        }
        View vi= LayoutInflater.from(activity).inflate(R.layout.pop_lock_open,null);
        pop_lock_open_password= (TextView) vi.findViewById(R.id.pop_lock_open_password);
        pop_lock_open_gridView= (GridView) vi.findViewById(R.id.pop_lock_open_gridView);
        if (adapter==null){
            adapter=new LockPopGridViewAdapter(activity);
        }
        pop_lock_open_gridView.setAdapter(adapter);
        pop_lock_open_gridView.setOnItemClickListener(this);
        View parent=activity.findViewById(R.id.activity_c__device_setting_lock);
        PopupSettings.getInstance().windowBottomUpSet(pop,parent,activity,vi);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position<9|position==10){//0到8(1-9),10-0
            add(position);
        }
        else if (position==9){//删除
            psd="";
            pop_lock_open_password.setText("");
        }
        else {//position==1:确认开锁
            if (!"".equals(psd)&&!TextUtils.isEmpty(psd)&&psd.length()==6){
                mToast.Toast(activity,psd);
            }
            else {
                mToast.Toast(activity,"密码长度不足");
                }
        }
    }

    private void add(int position) {
        if (!"".equals(psd)){
            if (psd.length()<6){//密码最大6位
               if (position==10){
                   psd+="0";
               }
                else {
                   psd+=(position+1);
               }
            }
        }
        else {//psd="-1";
            if (position==10){
                psd="0";
            }
            else {
                psd=psd+(position+1);
                }
        }
        String str="";
        if (!"".equals(psd)){

            for (int i=0;i<psd.length();i++){
                str+="*";
            }
        }
        pop_lock_open_password.setText(str);
    }

    //开锁
    public interface Locking{
        void Success();//失败
        void Failed();//成功
        void onNetWorkFailed();//网络异常
        void onLocking();//请求开锁操作
    }
}
