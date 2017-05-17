
package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.MyCircleContactAdapter_c;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的圈子活动页面
public class MyCircleContactActivity extends MyActivity implements View.OnClickListener{
    private LinearLayout circle_myActivity_layout;//切换我的圈子，我的活动的layout
    private TextView circle_my_user_info_submmit;//编辑／完成的view
    private TextView circle_myActivity_title;//显示我的圈子，我的活动的view
    private String[]title={"我的圈子","我的活动"};
    private int SelectPos=0;//当前选择的项目（0我的圈子，1我的活动）
    public static int state=0;//0未编辑状态，1编辑状态
    private String[]Submit={"编辑","完成"};

    @BindView(R.id.activity_my_circle_contact_listview)ListView activity_my_circle_contact_listview;//listview
    @BindView(R.id.activity_my_circle_contact_layout)RelativeLayout activity_my_circle_contact_layout;//删除的layout
    @BindView(R.id.activity_my_circle_contact_bottom_selectAll)ImageView activity_my_circle_contact_bottom_selectAll;//全选／取消全选的阿牛
    private List<Map<String,String>> listCircle;//圈子的数据源
    private List<Map<String,String>> listActi;//活动数据源
    private String[]data={"2017-3-21 12:12:40","2017-5-10 14:52:33","2016-5-16 17:00:00","2017-5-16 16:00:00"};//发布时间
    private int[] resId={R.mipmap.ph1,R.mipmap.ph2,R.mipmap.ph3,R.mipmap.ph4,R.mipmap.ph5};
    private String[]name={"刘明","刘文","王文","小明","阿三","HE"};
    private MyCircleContactAdapter_c adapter_c;//我的圈子的适配QI
    private MyCircleContactAdapter_c adapter_a;//我的活动的view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.circlecontact_title);
        initTitle();
        initChildView(R.layout.activity_my_circle_contact);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        activity_my_circle_contact_layout.setVisibility(View.GONE);//隐藏删除的layout
        initData();//加数据
    }
@OnClick({R.id.activity_my_circle_contact_bottom_selectAll})
public void cli(View vi){
    switch (vi.getId()){
        case R.id.activity_my_circle_contact_bottom_selectAll://全选／取消全选按钮
            if (activity_my_circle_contact_bottom_selectAll.isSelected()){//当前全选
                if (SelectPos==0){//我的圈子
                    for (int i=0;i<listCircle.size();i++){
                        listCircle.get(i).put("select","0");
                    }
                   adapter_c.notifyDataSetChanged();
                }
                else if (SelectPos==1){//我的活动
                    for (int i=0;i<listActi.size();i++){
                        listActi.get(i).put("select","0");
                    }
                    adapter_a.notifyDataSetChanged();
                }
            }
            else {//当前是非全选
                if (SelectPos==0){//我的圈子
                    for (int i=0;i<listCircle.size();i++){
                        listCircle.get(i).put("select","1");
                    }
                    adapter_c.notifyDataSetChanged();
                }
                else if (SelectPos==1){//我的活动
                    for (int i=0;i<listActi.size();i++){
                        listActi.get(i).put("select","1");
                    }
                    adapter_a.notifyDataSetChanged();
                }
            }
            activity_my_circle_contact_bottom_selectAll.setSelected(!activity_my_circle_contact_bottom_selectAll.isSelected());
            break;
    }
}
    private void initData() {
        listCircle=new ArrayList<>();
        listActi=new ArrayList<>();
        for (int i=0;i<15;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("select","0");//是否被选择
            mp.put("islike","0");
            mp.put("image",resId[i%resId.length]+"");//头像
            mp.put("title","圈子标题"+i);
            mp.put("data",data[i%data.length]);
            mp.put("address","名流一品");
            mp.put("name",name[i%name.length]);
            mp.put("likenum",i+"");
            mp.put("pl",(15-i)+"");
            mp.put("imageNum",i+"");
            mp.put("contact","今天天气很好"+i);
            listCircle.add(mp);
            mp.put("title","活动标题"+i);
            listActi.add(mp);
        }
        adapter_c=new MyCircleContactAdapter_c(listCircle,con);
        activity_my_circle_contact_listview.setAdapter(adapter_c);
        adapter_a=new MyCircleContactAdapter_c(listActi,con);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long millionSeconds;
//        try{
//            millionSeconds = sdf.parse(list.get(position).getCreateTimeString()).getTime();//毫秒
//        }
    }

    private void initTitle() {
        circle_myActivity_title= (TextView) findViewById(R.id.circle_myActivity_title);
        circle_myActivity_layout= (LinearLayout) findViewById(R.id.circle_myActivity_layout);
        circle_my_user_info_submmit= (TextView) findViewById(R.id.circle_my_user_info_submmit);
        circle_my_user_info_submmit.setText(Submit[state]);
        circle_myActivity_title.setText(title[SelectPos]);
        circle_myActivity_layout.setOnClickListener(this);
        circle_my_user_info_submmit.setOnClickListener(this);
    }

    @Override
    public void getSerVerData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_myActivity_layout://切换的view
                if (SelectPos==0){//我的圈子
                    SelectPos=1;//我的活动
                    activity_my_circle_contact_listview.setAdapter(adapter_a);
                }
                else {
                    SelectPos=0;//我的圈子
                    activity_my_circle_contact_listview.setAdapter(adapter_c);
                }
                circle_myActivity_title.setText(title[SelectPos]);
                break;
            case R.id.circle_my_user_info_submmit://编辑／完成bianji
                if (state==0){//编辑
                    state=1;//完成
                    activity_my_circle_contact_layout.setVisibility(View.VISIBLE);
                }
                else {
                    state=0;
                    activity_my_circle_contact_layout.setVisibility(View.GONE);
                }
                circle_my_user_info_submmit.setText(Submit[state]);
                break;
        }
    }
}
