package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/5.
 */

public class AlertCityAda extends BaseAdapter {
    private Context mContext;
    private List<String> list=new ArrayList<>();
    private LayoutInflater mInflater;

    public AlertCityAda(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
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
        CityHolder holder=null;
        if (convertView==null){
            holder=new CityHolder();
            convertView=mInflater.inflate(R.layout.city_alert_item,null);
            holder.textView= (TextView) convertView.findViewById(R.id.alert_city_tv);
            convertView.setTag(holder);
        }else {
            holder= (CityHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));

        return convertView;
    }

    class CityHolder{
        TextView textView;
    }
}
