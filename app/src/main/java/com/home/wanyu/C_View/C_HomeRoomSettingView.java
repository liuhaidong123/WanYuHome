package com.home.wanyu.C_View;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.apater.C_HomeRoomGridAdapter;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.myview.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/7/5.
 */
//房间设置
public class C_HomeRoomSettingView implements AdapterView.OnItemClickListener{

    int defaultResId=R.mipmap.homebackground;//默认的房间背景图片的id
    ImageView c_home_device_image;//房间背景layout
    EditText home_device_rela_eidtext_c;//房间名称

    MyGridView home_device_gridView;//设备所在的gridview
    private List<Bean_getSceneData.EquipmentListBean> list;//gridView数据源
    C_HomeRoomGridAdapter adapter;

    private CMyActivity activity;
    File outputImage;//房间图片
    //删除设备
    PopupWindow popX;
    int deleteId;
    //删除设备
    public C_HomeRoomSettingView(CMyActivity activity){
        this.activity=activity;
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        adapter=new C_HomeRoomGridAdapter(list,activity);
        home_device_gridView.setAdapter(adapter);
        home_device_gridView.setOnItemClickListener(this);
    }

    private void initView() {
        home_device_gridView= (MyGridView) activity.findViewById(R.id.home_device_gridView);
        c_home_device_image= (ImageView) activity.findViewById(R.id.c_home_device_image);
        home_device_rela_eidtext_c= (EditText) activity.findViewById(R.id.home_device_rela_eidtext_c);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.home_device_gridView:
                if (position!=adapter.getCount()-1){
                    showWindow(position);
                }
                else {
                    addDevice();//添加设备
                    mToast.Toast(activity,""+position);
                }
                break;
        }
    }

    //添加设备
    private void addDevice() {

    }

    //删除设备
    private void showWindow(int pos) {
        deleteId=pos;
        popX = new PopupWindow();
        View v = LayoutInflater.from(activity).inflate(R.layout.pop_device_delete, null);
        TextView pop_delete= (TextView) v.findViewById(R.id.pop_delete);
        TextView pop_cancle= (TextView) v.findViewById(R.id.pop_cancle);
        pop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(deleteId);
                adapter.notifyDataSetChanged();
                popX.dismiss();
            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popX.dismiss();
            }
        });
        LinearLayout parent = (LinearLayout)  activity.findViewById(R.id.c_homescene_settinglayout);
        PopupSettings.getInstance().windowActivityCenter(popX,parent,activity,v);
    }

    //更换背景图片
    public void setImageBack(Bitmap bitmap){
        if (bitmap!=null&&bitmap.getHeight()*bitmap.getRowBytes()>0){

            c_home_device_image.setImageBitmap(bitmap);
        }
    }
}
