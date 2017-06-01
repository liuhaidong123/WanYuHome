package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.HouseSearchArea.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/27.
 */

public class HouseSearchAreaAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> list = new ArrayList<>();

    public HouseSearchAreaAda(Context mContext, List<Result> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<Result> list) {
        this.list = list;
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
        AreaHolder hoHolder = null;

        if (convertView == null) {
            hoHolder = new AreaHolder();
            convertView = mInflater.inflate(R.layout.commercial_search_hository_address, null);
            hoHolder.textView = (TextView) convertView.findViewById(R.id.address_name);
            convertView.setTag(hoHolder);

        } else {
            hoHolder = (AreaHolder) convertView.getTag();
        }
        hoHolder.textView.setText(list.get(position).getRname());
        return convertView;
    }
    class AreaHolder{
        TextView textView;
    }
}
