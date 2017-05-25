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

public class HomeServiceAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public HomeServiceAda(Context mContext) {
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
        HomeServiceHolder holder = null;
        if (convertView == null) {
            holder = new HomeServiceHolder();
            convertView = mInflater.inflate(R.layout.home_service_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.homeservice_img);
            holder.name = (TextView) convertView.findViewById(R.id.homeservice_name);
            holder.price = (TextView) convertView.findViewById(R.id.homeservice_price);
            convertView.setTag(holder);
        } else {
            holder = (HomeServiceHolder) convertView.getTag();
        }


        return convertView;
    }

    class HomeServiceHolder {
        ImageView imageView;
        TextView name;
        TextView price;

    }
}
