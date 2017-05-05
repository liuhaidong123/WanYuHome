package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordAda extends BaseAdapter {
    private LayoutInflater mInfalter;
    private Context mContext;

    public RecordAda(Context mContext) {
        this.mContext = mContext;
        this.mInfalter = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            holder.textView = (TextView) convertView.findViewById(R.id.repair_content);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }

        return convertView;
    }

    class RecordHolder {
        TextView textView;
    }
}
