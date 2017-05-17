package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/5/12.
 */

public class CommunityListViewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CommunityListViewAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 10;
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

        CommunityHolder holder = null;
        if (convertView == null) {
            holder = new CommunityHolder();
            convertView = mInflater.inflate(R.layout.community_listview_item, null);
            holder.imgHead = (RoundImageView) convertView.findViewById(R.id.community_head_img);
            holder.name_tv = (TextView) convertView.findViewById(R.id.community_name);
            holder.time_tv = (TextView) convertView.findViewById(R.id.community_time);
            holder.title_tv = (TextView) convertView.findViewById(R.id.community_ming);
            holder.time_msg_tv = (TextView) convertView.findViewById(R.id.community_activity_time);
            holder.address_tv = (TextView) convertView.findViewById(R.id.community_address);
            holder.person_tv = (TextView) convertView.findViewById(R.id.community_person_num);
            holder.state_tv = (TextView) convertView.findViewById(R.id.community_jin);
            holder.like_num_tv = (TextView) convertView.findViewById(R.id.community_like_num);
            holder.join_num_tv = (TextView) convertView.findViewById(R.id.community_join_num);

            holder.like_img = (ImageView) convertView.findViewById(R.id.community_like_img);
            holder.add_img = (ImageView) convertView.findViewById(R.id.community_add_img);
            convertView.setTag(holder);
        } else {
            holder = (CommunityHolder) convertView.getTag();
        }
        return convertView;
    }

    class CommunityHolder {
        RoundImageView imgHead;
        TextView name_tv;
        TextView time_tv;
        TextView title_tv;
        TextView time_msg_tv;
        TextView address_tv;
        TextView person_tv;
        TextView state_tv;
        TextView like_num_tv;
        TextView join_num_tv;

        ImageView like_img;
        ImageView add_img;


    }
}
