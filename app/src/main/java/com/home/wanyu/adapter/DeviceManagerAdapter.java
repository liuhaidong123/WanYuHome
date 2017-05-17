package com.home.wanyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.R;
import com.home.wanyu.activity.MyHouseFamilyInfoActivity;
import com.home.wanyu.bean.Bean_AllDevice;
import com.home.wanyu.myview.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/15.
 */

public class DeviceManagerAdapter extends BaseAdapter{
    private Context context;
    List<Bean_AllDevice.EquipmentListBean> list;
    public DeviceManagerAdapter(Context context,List<Bean_AllDevice.EquipmentListBean> list){
        this.context=context;
        this.list=list;
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
      ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.devicemanager_list_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.devicemanager_image.setImageResource(list.get(position).getIconId()>icon.mIconRes.length-1?R.mipmap.errorphoto:icon.mIconRes[list.get(position).getIconId()]);
        hodler.lockshare_list_item_text.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.devicemanager_text)TextView lockshare_list_item_text;
        @BindView(R.id.devicemanager_image)ImageView devicemanager_image;
    }
}
