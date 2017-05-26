package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_Message;
import com.home.wanyu.bean.CircleFriend;
import com.home.wanyu.lzhUtils.MyCirleView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class CircleYouMsgAda extends BaseAdapter {
    private Context mContext;
    private List<Bean_Message.RowsBean> listMessage;
    public CircleYouMsgAda(Context mContext,List<Bean_Message.RowsBean> listMessage) {
        this.mContext = mContext;
       this.listMessage=listMessage;
    }

//    public void setList(List<CircleFriend> list) {
//        this.list = list;
//    }

    @Override
    public int getCount() {
        return listMessage==null?0:listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessage.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.circle_you_msg_item, null);
            holder.imageView = (RoundImageView) convertView.findViewById(R.id.you_msg_head);
            holder.name = (TextView) convertView.findViewById(R.id.you_msg_name);
            holder.otherName = (TextView) convertView.findViewById(R.id.you_msg_content);
            holder.mssage= (MyCirleView) convertView.findViewById(R.id.mssage);
            holder.time = (TextView) convertView.findViewById(R.id.you_msg_time);
            convertView.setTag(holder);
        } else {
            holder = (YouHolder) convertView.getTag();
        }
        if (listMessage.get(position).isIsRead()){
           holder.mssage.setVisibility(View.GONE);
        }
        else {
            holder.mssage.setVisibility(View.VISIBLE);
            }
        Picasso.with(mContext).load(Ip.imagePath+listMessage.get(position).getAvatar()).placeholder(R.mipmap.loadinge).error(R.mipmap.loadinge).into(holder.imageView);
        holder.name.setText(listMessage.get(position).getTitle());
        holder.otherName.setText(listMessage.get(position).getContent());

        return convertView;
    }

    class YouHolder {
        MyCirleView mssage;
        TextView name;
        RoundImageView imageView;
        TextView otherName;
        TextView time;

    }
}
