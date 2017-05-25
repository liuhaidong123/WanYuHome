package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.getCircleArea.Result;
import com.home.wanyu.zxing.view.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class CircleSelectAreaAlertAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInfalter;
    private List<Result> list = new ArrayList();

    public CircleSelectAreaAlertAda(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
        this.mInfalter = LayoutInflater.from(this.mContext);
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
        CircleSelectHolder holder = null;
        if (convertView == null) {
            holder = new CircleSelectHolder();
            convertView = mInfalter.inflate(R.layout.circle_select_area_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.alert_area_tv);
            convertView.setTag(holder);
        } else {
            holder = (CircleSelectHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getRname());
        return convertView;
    }

    class CircleSelectHolder {
        TextView textView;
    }
}
