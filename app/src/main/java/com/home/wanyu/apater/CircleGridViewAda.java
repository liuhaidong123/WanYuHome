package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.CircleFriend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/8.
 */

public class CircleGridViewAda extends BaseAdapter {
    private List<CircleFriend> list = new ArrayList<>();
    private Context mContext;
    private LayoutInflater minflater;


    public CircleGridViewAda(Context mContext, List<CircleFriend> list) {
        this.mContext = mContext;
        this.list = list;
        this.minflater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<CircleFriend> list) {
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
        CircleTitleHolder holder = null;
        if (convertView == null) {
            holder = new CircleTitleHolder();
            convertView = minflater.inflate(R.layout.circle_msg_gridview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.circle_small_tv);
            holder.imageView = (ImageView) convertView.findViewById(R.id.circle_small_line);
            convertView.setTag(holder);
        } else {
            holder = (CircleTitleHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getType());
        if (list.get(position).isFlag()) {
            holder.imageView.setImageResource(R.mipmap.circle_bottom_line);
        } else {
            holder.imageView.setImageResource(R.mipmap.circle_bottom_white_line);
        }
        return convertView;
    }

    class CircleTitleHolder {
        TextView textView;
        ImageView imageView;
    }
}
