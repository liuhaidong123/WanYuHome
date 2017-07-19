package com.home.wanyu.myUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;

import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/7/5.
 */
//带有btn的view(不带有的需要执行)
/**
 *
 * <include
 android:layout_width="match_parent"
 android:layout_height="44dp"
 layout="@layout/activity_titleinclude_btn">
 </include>

 titleButton= (TextView) findViewById(R.id.titleButton);
 titleTextView= (TextView) findViewById(R.id.titleTextView);
 initTitle("房间管理","--",true);
 *
 */

public  class CMyActivity extends Activity {
    public Unbinder unbinder;
    public  void getServerData(){};
    public TextView titleTextView,titleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //button的点击事件
    public void Submit(View vi){

    }

    //确保TextView已经初始化
    public void initTitle(String title,String btnName,boolean isHideBtn){
        if (titleTextView==null|titleButton==null){
            return;
        }
        titleTextView.setText(title);
        titleButton.setText(btnName);
        if (isHideBtn){
            titleButton.setVisibility(View.GONE);
        }
    }

    //推出按钮都必须实现goBack的onClick页面返回按钮
    public void goBack(View vi){
        finish();
    }


    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
