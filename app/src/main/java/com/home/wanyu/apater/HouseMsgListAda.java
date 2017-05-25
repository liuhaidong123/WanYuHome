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
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseMsgListAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public HouseMsgListAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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
        HouseListHolder holder = null;
        if (convertView == null) {
            holder = new HouseListHolder();
            convertView = mInflater.inflate(R.layout.house_location_area_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.house_location_img);
            holder.msg = (TextView) convertView.findViewById(R.id.house_msg_tv);
            holder.type = (TextView) convertView.findViewById(R.id.house_type_tv);
            holder.price = (TextView) convertView.findViewById(R.id.house_money);
            holder.time = (TextView) convertView.findViewById(R.id.house_time);
            convertView.setTag(holder);
        } else {
            holder = (HouseListHolder) convertView.getTag();
        }
        return convertView;
    }

    class HouseListHolder {
        ImageView imageView;
        TextView msg;
        TextView type;
        TextView price;
        TextView time;
    }
}
