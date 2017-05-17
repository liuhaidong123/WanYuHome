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
import com.home.wanyu.myview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class CircleYouMsgAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater minflater;


    public CircleYouMsgAda(Context mContext) {
        this.mContext = mContext;
        this.minflater = LayoutInflater.from(this.mContext);
    }

//    public void setList(List<CircleFriend> list) {
//        this.list = list;
//    }

    @Override
    public int getCount() {
        return 20;
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
        YouHolder holder = null;
        if (convertView == null) {
            holder = new YouHolder();
            convertView = minflater.inflate(R.layout.circle_you_msg_item, null);

            holder.imageView = (RoundImageView) convertView.findViewById(R.id.you_msg_head);
            holder.name = (TextView) convertView.findViewById(R.id.you_msg_name);
            holder.otherName = (TextView) convertView.findViewById(R.id.you_msg_content);
            holder.time = (TextView) convertView.findViewById(R.id.you_msg_time);
            convertView.setTag(holder);
        } else {
            holder = (YouHolder) convertView.getTag();
        }

        return convertView;
    }

    class YouHolder {
        TextView name;
        RoundImageView imageView;
        TextView otherName;
        TextView time;

    }
}
