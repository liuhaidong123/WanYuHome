package com.home.wanyu.apater;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.activity.CirclePostActivity;
import com.home.wanyu.bean.getCircleTitleList.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/10.
 */

public class CirclePopListviewApa extends BaseAdapter {
    private List<Result> list = new ArrayList<>();
    private List<Boolean> booleanList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public CirclePopListviewApa(Context mContext, List<Result> list, List<Boolean> booleanList) {
        this.mContext = mContext;
        this.list = list;
        this.booleanList = booleanList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
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
            convertView = mInflater.inflate(R.layout.circle_post_tag_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tag_item_tv);
            convertView.setTag(holder);
        } else {
            holder = (PopHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getCname());

        if (booleanList.get(position) == true) {
            holder.textView.setBackgroundResource(R.color.circle_bg);
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.textView.setBackgroundResource(R.drawable.circle_post_tag_bg);
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.titlecolor3));
        }
        return convertView;
    }

    class PopHolder {
        TextView textView;
    }
}
