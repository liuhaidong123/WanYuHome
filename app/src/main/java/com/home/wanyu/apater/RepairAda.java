package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.repairType.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by liuhaidong on 2017/5/3.
 */

public class RepairAda extends BaseAdapter {
    private LayoutInflater mInfalter;
    private Context mContext;
    private List<Result> list = new ArrayList<>();


    public RepairAda(Context mContext, List<Result> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInfalter = LayoutInflater.from(this.mContext);
    }

    public void setList(List<Result> list) {
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
        RepairHolder holder = null;
        if (convertView == null) {
            holder = new RepairHolder();
            convertView = mInfalter.inflate(R.layout.repair_gridview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.repair_content);
            convertView.setTag(holder);
        } else {
            holder = (RepairHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getTypeName());
        return convertView;
    }

    class RepairHolder {
        TextView textView;
    }
}
