package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.areaList.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class OrderMonthAda extends BaseAdapter {
    private Context mContext;
    private List<String> list=new ArrayList<>();
    private LayoutInflater mInflater;

    public OrderMonthAda(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setList(List<String> list) {
        this.list = list;
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
        MonthHolder holder=null;
        if (convertView==null){
            holder=new MonthHolder();
            convertView=mInflater.inflate(R.layout.city_alert_item,null);
            holder.textView= (TextView) convertView.findViewById(R.id.alert_city_tv);
            convertView.setTag(holder);
        }else {
            holder= (MonthHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));

        return convertView;
    }

    class MonthHolder{
        TextView textView;
    }
}
