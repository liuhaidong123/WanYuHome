package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.MyGridBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/6/29.
 */

public class MyGridViewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MyGridBean> mList = new ArrayList<>();

    public MyGridViewAda(Context mContext, List<MyGridBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MYHolder holder = null;
        if (convertView == null) {
            holder = new MYHolder();
            convertView = mInflater.inflate(R.layout.my_gridview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_img);
            holder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (MYHolder) convertView.getTag();
        }

        if (mList.size()-1!=position){
            Picasso.with(mContext).load(mList.get(position).getImg()).into(holder.imageView);
            holder.textView.setText(mList.get(position).getName());
        }

        return convertView;
    }

    class MYHolder {
        TextView textView;
        ImageView imageView;
    }
}
