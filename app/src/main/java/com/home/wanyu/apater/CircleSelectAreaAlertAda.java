package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

class CircleSelectAreaAlertAda extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInfalter;
    private List<String> list=new ArrayList();

    public CircleSelectAreaAlertAda(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
        this.mInfalter=LayoutInflater.from(this.mContext);
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


        return convertView;
    }

    class CircleSelectHolder{
        TextView textView;
    }
}
