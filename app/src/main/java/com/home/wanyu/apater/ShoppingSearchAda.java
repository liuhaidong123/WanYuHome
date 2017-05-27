package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Hository;
import com.home.wanyu.bean.shoppingSearchAddress.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/26.
 */

public class ShoppingSearchAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> list = new ArrayList<>();

    public ShoppingSearchAda(Context mContext, List<Result> list) {
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
        SHolder hoHolder = null;

        if (convertView == null) {
            hoHolder = new SHolder();
            convertView = mInflater.inflate(R.layout.commercial_search_hository_address, null);
            hoHolder.textView = (TextView) convertView.findViewById(R.id.address_name);
            convertView.setTag(hoHolder);

        } else {
            hoHolder = (SHolder) convertView.getTag();
        }
        hoHolder.textView.setText(list.get(position).getRname());
        return convertView;
    }


    class SHolder {
        TextView textView;
    }
}
