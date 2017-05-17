package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.activity.CarPoolingMsgActivity;
import com.home.wanyu.activity.StartActivity;
import com.home.wanyu.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/5/15.
 */

public class CarPoolingAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CarPoolingAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 8;
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
        CarHolder holder = null;
        if (convertView == null) {
            //需要区分乘客还是司机
            holder = new CarHolder();
            convertView = mInflater.inflate(R.layout.carpooling_listview_item, null);
            holder.head_img = (RoundImageView) convertView.findViewById(R.id.car_head);
            holder.name = (TextView) convertView.findViewById(R.id.car_name);
            holder.time = (TextView) convertView.findViewById(R.id.car_time);
            holder.shen = (TextView) convertView.findViewById(R.id.car_shen_msg);
            holder.start_time = (TextView) convertView.findViewById(R.id.car_time_msg);
            holder.start_address = (TextView) convertView.findViewById(R.id.car_start_address_msg);
            holder.end_address = (TextView) convertView.findViewById(R.id.car_end_address_msg);
            holder.state = (TextView) convertView.findViewById(R.id.car_state_tv);
            holder.comment_num = (TextView) convertView.findViewById(R.id.car_comment_num);
            holder.join_num = (TextView) convertView.findViewById(R.id.car_join_num);
            holder.join_img = (ImageView) convertView.findViewById(R.id.car_join_img);
            convertView.setTag(holder);
        } else {
            holder = (CarHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarPoolingMsgActivity.class);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class CarHolder {
        RoundImageView head_img;
        TextView name, time, shen, start_time, start_address, end_address, state, comment_num, join_num;
        ImageView join_img;
        RelativeLayout siji_rl, chenke_rl;
        TextView dan;
    }
}
