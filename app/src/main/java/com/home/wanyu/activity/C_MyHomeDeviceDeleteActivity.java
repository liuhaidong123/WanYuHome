package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerDeleteAdapter;
import com.home.wanyu.myUtils.CMyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class C_MyHomeDeviceDeleteActivity extends CMyActivity implements C_MyHomeDeviceManagerDeleteAdapter.Select{
    C_MyHomeDeviceManagerDeleteAdapter adapter;
    ArrayList<Integer> list;//测试用数据源
    @BindView(R.id.c_home_device_delete_listview)ListView c_home_device_delete_listview;
    @BindView(R.id.c_device_Image_select)TextView c_device_Image_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__my_home_device_delete);
        unbinder= ButterKnife.bind(this);
        initAdapter();
    }
    //测试用
    private void initAdapter() {
        list=new ArrayList<>();
        for (int i=0;i<15;i++){
            list.add(0);
        }
        adapter=new C_MyHomeDeviceManagerDeleteAdapter(this,list,this);
        c_home_device_delete_listview.setAdapter(adapter);
    }

    @OnClick({R.id.c_home_device_delete_btn,R.id.c_device_Image_select})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.c_home_device_delete_btn://删除按钮
                List<Integer> li=new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    if (list.get(i)==1){
                        li.add(list.get(i));
                    }
                }
                list.removeAll(li);
                adapter.notifyDataSetChanged();
                break;
            case R.id.c_device_Image_select://全选按钮
                    for (int i=0;i<list.size();i++){
                        if (c_device_Image_select.isSelected()){
                            list.set(i,0);
                        }
                        else {
                            list.set(i,1);
                        }
                    }
                adapter.notifyDataSetChanged();
                c_device_Image_select.setSelected(!c_device_Image_select.isSelected());
                break;
        }
    }

    @Override
    public void isAllSelect(Boolean select) {
        if (select){
            c_device_Image_select.setSelected(true);
        }
        else {
            c_device_Image_select.setSelected(false);
        }
    }
}
