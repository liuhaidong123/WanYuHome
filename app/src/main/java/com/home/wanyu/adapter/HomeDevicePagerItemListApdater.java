package com.home.wanyu.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.lzhView.MyFloatingView;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/5/4.
 */

public class HomeDevicePagerItemListApdater extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;
    private MyFloatingView floatingView;
    public HomeDevicePagerItemListApdater(List<Map<String,String>> list,Context context,MyFloatingView floatingView) {
        this.list = list;
        this.context = context;
        this.floatingView=floatingView;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.homedevicepageritemlist_item,null);
            viewHodler=new ViewHodler();
            viewHodler.fragment_home_device_viewpager_listitem_switch= (SwitchCompat) convertView.findViewById(R.id.fragment_home_device_viewpager_listitem_switch);
            viewHodler.fragment_home_device_viewpager_listitem_imageSettings= (ImageView) convertView.findViewById(R.id.fragment_home_device_viewpager_listitem_imageSettings);
            viewHodler.fragment_home_device_viewpager_listitem_textVsetting= (TextView) convertView.findViewById(R.id.fragment_home_device_viewpager_listitem_textVsetting);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.fragment_home_device_viewpager_listitem_imageSettings.setImageResource(Integer.parseInt(list.get(position).get("url")));
        viewHodler.fragment_home_device_viewpager_listitem_textVsetting.setText(list.get(position).get("title"));
        final String state=list.get(position).get("state");
        if ("0".equals(state)){
            viewHodler.fragment_home_device_viewpager_listitem_switch.setChecked(false);
        }
        else if ("1".equals(state)){
            viewHodler.fragment_home_device_viewpager_listitem_switch.setChecked(true);
        }
        viewHodler.fragment_home_device_viewpager_listitem_switch.setTag(position);

        viewHodler.fragment_home_device_viewpager_listitem_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos= (int) buttonView.getTag();
                if (isChecked){
                    list.get(pos).put("state","1");
                }
                else {
                    list.get(pos).put("state","0");
                }
                if (getState()){//全选状态
                    floatingView.setOnAndOff(true);
                }
                else {//非全选状态
                    floatingView.setOnAndOff(false);
                }
            }
        });
        floatingView.setOnAndOff(getState());
        return convertView;
    }

    class ViewHodler{
        ImageView fragment_home_device_viewpager_listitem_imageSettings;
        TextView fragment_home_device_viewpager_listitem_textVsetting;
        SwitchCompat fragment_home_device_viewpager_listitem_switch;
    }


    public boolean getState(){
        boolean flag=true;
        for (int i=0;i<list.size();i++){
            if ("0".equals(list.get(i).get("state"))){
                flag=false;
            }
        }
        return flag;
    }
}
