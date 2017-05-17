package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Record.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordAda extends BaseAdapter {
    private LayoutInflater mInfalter;
    private Context mContext;
    private List<RecordBean> list = new ArrayList<>();

    public RecordAda(Context mContext, List<RecordBean> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInfalter = LayoutInflater.from(this.mContext);
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
        RecordHolder holder = null;
        if (convertView == null) {
            holder = new RecordHolder();
            convertView = mInfalter.inflate(R.layout.repair_gridview_item, null);
            holder.t = (TextView) convertView.findViewById(R.id.repair_content);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }

        holder.t.setText(list.get(position).getType());
        return convertView;
    }

    class RecordHolder {
        TextView t;
    }
}
