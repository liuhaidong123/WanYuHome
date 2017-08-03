package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_AllDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeDeviceManagerDeleteAdapter extends BaseAdapter{
    Select isAllSelect;
    List<Bean_AllDevice.EquipmentListBean>list;
    Context context;
    public C_MyHomeDeviceManagerDeleteAdapter(Context context,List<Bean_AllDevice.EquipmentListBean>list,Select isAllSelect){
        this.context=context;
        this.list=list;
        this.isAllSelect=isAllSelect;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.c_home_devicemanager_delete_listitem,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        if (list.get(position).isFlag()==false){
            viewHodler.c_home_device_image_select.setSelected(false);
        }
        else {
            viewHodler.c_home_device_image_select.setSelected(true);
            }

        viewHodler.c_home_device_image_select.setTag(position);
        viewHodler.c_home_device_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if (v.isSelected()){
                    list.get(pos).setFlag(false);
                    v.setSelected(false);
                }
                else {
                    list.get(pos).setFlag(true);
                    v.setSelected(true);
                }
                    isAllSelect.isAllSelect(isAllSelect());
            }
        });
        viewHodler.c_home_device_image_delete.setImageResource(icon.getDeviceIcon(list.get(position).getIconId()));
        viewHodler.c_home_device_delete_deviceName.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.c_home_device_image_select)ImageView c_home_device_image_select;//选择按钮
        @BindView(R.id.c_home_device_delete_devicename)TextView c_home_device_delete_deviceName;
        @BindView(R.id.c_home_device_image_delete)ImageView c_home_device_image_delete;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }

    public interface Select{
        void isAllSelect(Boolean select);
    }
    public boolean isAllSelect(){
        boolean flag=true;
        for (int i=0;i<list.size();i++){
            if (list.get(i).isFlag()==false){
                flag=false;
            }
        }
        return flag;
    }
}
