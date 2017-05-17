package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.apater.CIrcleExpandableAda;
import com.home.wanyu.apater.CircleCommendLikeAda;
import com.home.wanyu.myview.MyExpandableListview;
import com.home.wanyu.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class CircleCardMessageActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack;
    private MyExpandableListview mListView;
    private CIrcleExpandableAda mAdapter;
    private List<String> mOneList = new ArrayList<>();
    private List<String> mTwoList = new ArrayList<>();

    private MyGridView mGridview;
    private CircleCommendLikeAda mGridviewAda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_card_message);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.circle_card_msg_back);
        mBack.setOnClickListener(this);

        mListView = (MyExpandableListview) findViewById(R.id.circle_commend_listview);
        mAdapter = new CIrcleExpandableAda(mOneList, mTwoList, this);
        mListView.setAdapter(mAdapter);

        //默认展开二级菜单
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
        }
        //不能点击收缩
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //去掉箭头
        mListView.setGroupIndicator(null);
        //去掉下划线
        mListView.setDivider(null);


        mGridview = (MyGridView) findViewById(R.id.circle_card_gridview);
        mGridviewAda = new CircleCommendLikeAda(this);
        mGridview.setAdapter(mGridviewAda);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }
}
