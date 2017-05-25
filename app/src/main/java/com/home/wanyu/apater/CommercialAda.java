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

public class CommercialAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CommercialAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 5;
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
        GoodsHolder holder = null;
        if (convertView == null) {
            holder = new GoodsHolder();
            convertView = mInflater.inflate(R.layout.commercial_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.goods_img);
            holder.name = (TextView) convertView.findViewById(R.id.goods_name);
            holder.price = (TextView) convertView.findViewById(R.id.goods_price);

            convertView.setTag(holder);
        } else {
            holder = (GoodsHolder) convertView.getTag();
        }

        return convertView;
    }

    class GoodsHolder {
        ImageView imageView;

        TextView name;
        TextView price;
        TextView km;
    }
}
