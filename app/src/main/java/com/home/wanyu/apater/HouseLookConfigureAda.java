package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.HouseLookConfigure;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseLookConfigureAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HouseLookConfigure> list = new ArrayList<>();

    public HouseLookConfigureAda(Context mContext, List<HouseLookConfigure> list) {
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
        ConfigureHolder holder = null;
        if (convertView == null) {
            holder = new ConfigureHolder();
            convertView = mInflater.inflate(R.layout.house_look_configure_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.look_img);
            holder.name = (TextView) convertView.findViewById(R.id.look_name);
            convertView.setTag(holder);
        } else {
            holder = (ConfigureHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(list.get(position).getImg()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        return convertView;
    }

    class ConfigureHolder {
        ImageView imageView;
        TextView name;
    }
}
