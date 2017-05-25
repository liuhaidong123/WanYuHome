package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Hository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class CommercialAddressHositoryAda extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Hository> list = new ArrayList<>();


    public CommercialAddressHositoryAda(Context mContext, List<Hository> list) {
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
        HoHolder hoHolder = null;

        if (convertView == null) {
            hoHolder = new HoHolder();
            convertView = mInflater.inflate(R.layout.commercial_search_hository_address, null);
            hoHolder.textView = (TextView) convertView.findViewById(R.id.address_name);
            convertView.setTag(hoHolder);

        } else {
            hoHolder = (HoHolder) convertView.getTag();
        }
        hoHolder.textView.setText(list.get(position).getAddress());

        return convertView;
    }

    class HoHolder {
        TextView textView;
    }
}
