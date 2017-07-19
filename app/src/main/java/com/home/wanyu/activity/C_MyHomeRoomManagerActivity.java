package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerAdapter;
import com.home.wanyu.adapter.C_MyHomeRoomManagerAdapter;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class C_MyHomeRoomManagerActivity extends CMyActivity {
    @BindView(R.id.activity_c__my_home_room_manager_listview) ListView activity_c__my_home_room_manager_listview;
    C_MyHomeRoomManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_room_manager);
        unbinder = ButterKnife.bind(this);
        titleButton = (TextView) findViewById(R.id.titleButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        initTitle("房间管理", "--", true);
        initAdapter();
    }

    private void initAdapter() {
        adapter=new C_MyHomeRoomManagerAdapter(this);
        activity_c__my_home_room_manager_listview.setAdapter(adapter);
    }
    @OnClick({R.id.c_home_room_delete,R.id.c_home_room_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_room_delete://删除房间
                startActivity(new Intent(this,C_MyHomeRoomDeleteActivity.class));
                break;
            case R.id.c_home_room_add://添加房间
                startActivity(new Intent(this,C_HomeRoomSettingActivity.class));
                break;
        }
    }
}
