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
 * Created by liuhaidong on 2017/5/10.
 */

public class CirclePopListviewApa extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public CirclePopListviewApa(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
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
        PopHolder holder = null;
        if (convertView == null) {
            holder = new PopHolder();
            convertView = mInflater.inflate(R.layout.circle_pop_item2, null);
            holder.textView = (TextView) convertView.findViewById(R.id.pop_tv);
            convertView.setTag(holder);
        } else {
            holder = (PopHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class PopHolder {
        TextView textView;
    }
}
