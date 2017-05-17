package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/5/9.
 */

public class CircleFriendListviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CircleFriendListviewAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(this.mContext);
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
        FriendHolder holder=null;
        if (convertView==null){
            holder=new FriendHolder();
            convertView=mInflater.inflate(R.layout.circle_friend_listview_item,null);
            holder.headimg= (RoundImageView) convertView.findViewById(R.id.circle_head_img);
            holder.name= (TextView) convertView.findViewById(R.id.circle_name_tv);
            holder.area=(TextView) convertView.findViewById(R.id.circle_area_tv);
            holder.type=(TextView) convertView.findViewById(R.id.circle_type_tv);
            holder.msg=(TextView) convertView.findViewById(R.id.circle_commend_msg);
            holder.gridview= (MyGridView) convertView.findViewById(R.id.circle_gridview_friend);
            holder.time=(TextView) convertView.findViewById(R.id.circle_time_tv);
            holder.likenum=(TextView) convertView.findViewById(R.id.circle_like_num);
            holder.commendnum=(TextView) convertView.findViewById(R.id.circle_commend_num);
            holder.likeimg= (ImageView) convertView.findViewById(R.id.circle_like_img);
            convertView.setTag(holder);

        }else {
            holder= (FriendHolder) convertView.getTag();
        }

        return convertView;
    }

    class FriendHolder{
        RoundImageView headimg;
        TextView name;
        TextView area;
        TextView type;
        TextView msg;;
        MyGridView gridview;
        TextView time;
        TextView likenum;
        TextView commendnum;
        ImageView likeimg;

    }
}
