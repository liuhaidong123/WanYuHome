package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.myview.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeDeviceManagerAdapter extends BaseAdapter{
    Context context;
    List<Bean_AllDevice.EquipmentListBean> li;
    public C_MyHomeDeviceManagerAdapter(Context context,List<Bean_AllDevice.EquipmentListBean>li){
        this.context=context;
        this.li=li;
    }
    @Override
    public int getCount() {
        return li==null?0:li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.c_home_devicemanager_listitem,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.c_home_device_text.setText(li.get(position).getName());
        viewHodler.c_home_device_image.setImageResource(icon.getDeviceIcon(li.get(position).getIconId()));
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.c_home_device_image)RoundImageView c_home_device_image;//图标
        @BindView(R.id.c_home_device_text)TextView c_home_device_text;//名字
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
