package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeRoomManagerAdapter;
import com.home.wanyu.adapter.C_MyHomeSceneManagerAdapter;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.home.wanyu.R.id.activity_c__my_home_room_manager_listview;

//情景管理
public class C_MyHomeSceneManagerActivity extends CMyActivity {
    C_MyHomeSceneManagerAdapter adapter;
    @BindView(R.id.activity_c__my_home_scene_manager_listview)ListView activity_c__my_home_scene_manager_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_scene_manager);
        unbinder = ButterKnife.bind(this);
        titleButton = (TextView) findViewById(R.id.titleButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        initTitle("情景管理", "--", true);
        initAdapter();
    }

    private void initAdapter() {
        adapter=new C_MyHomeSceneManagerAdapter(this);
        activity_c__my_home_scene_manager_listview.setAdapter(adapter);
    }
    @OnClick({R.id.c_home_scene_delete,R.id.c_home_scene_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_scene_delete://删除情景
                startActivity(new Intent(this,C_MyHomeSceneDeleteActivity.class));
                break;
            case R.id.c_home_scene_add://添加情景
                startActivity(new Intent(this,C_HomeSceneAddActivity.class));
                break;
        }
    }
}
