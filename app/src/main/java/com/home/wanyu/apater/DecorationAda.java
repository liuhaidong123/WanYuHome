package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class DecorationAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public DecorationAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
    }


    @Override
    public int getCount() {
        return 6;
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
        DecorationHolder holder = null;
        if (convertView == null) {
            holder = new DecorationHolder();
            convertView = mInflater.inflate(R.layout.decotion_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.decoretion_img);
            holder.name = (TextView) convertView.findViewById(R.id.decoretion_name);
            holder.price = (TextView) convertView.findViewById(R.id.decoretion_price);
            convertView.setTag(holder);
        } else {
            holder = (DecorationHolder) convertView.getTag();
        }


        return convertView;
    }

    class DecorationHolder {
        ImageView imageView;
        TextView name;
        TextView price;

    }
}
