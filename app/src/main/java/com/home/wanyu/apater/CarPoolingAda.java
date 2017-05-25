package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.activity.CarPoolingMsgActivity;
import com.home.wanyu.activity.StartActivity;
import com.home.wanyu.bean.carPoolingList.Result;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/15.
 */

public class CarPoolingAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> mlist = new ArrayList<>();
    private int over;

    public CarPoolingAda(Context mContext, List<Result> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setMlist(List<Result> mlist, int over) {
        this.mlist = mlist;
        this.over = over;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

            holder.dan = (TextView) convertView.findViewById(R.id.car_chen_jie_btn);
            holder.siji_rl = (RelativeLayout) convertView.findViewById(R.id.car_end_rl);
            holder.chenke_rl = (RelativeLayout) convertView.findViewById(R.id.car_end_chen_rl);
            holder.chen_comment = (TextView) convertView.findViewById(R.id.car_jie_comment_num);
            holder.chen_state = (TextView) convertView.findViewById(R.id.car_chen_state_tv);
            convertView.setTag(holder);
        } else {
            holder = (CarHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE + mlist.get(position).getAvatar()).resize(ImgUitls.getWith(mContext) / 4, ImgUitls.getWith(mContext) / 4).error(R.mipmap.error_small).into(holder.head_img);
        holder.name.setText(mlist.get(position).getUser_name());
        holder.time.setText(mlist.get(position).getCreateTimeString());
        holder.start_time.setText(mlist.get(position).getDepartureTimeString());
        holder.start_address.setText(mlist.get(position).getDeparturePlace());
        holder.end_address.setText(mlist.get(position).getEnd());


        if (mlist.get(position).getCtype() == 1) {//乘客
            holder.shen.setText("乘客");
            holder.chenke_rl.setVisibility(View.VISIBLE);
            holder.siji_rl.setVisibility(View.GONE);


            if (over == 1) {
                holder.chen_state.setText("正在进行");
            } else if (over == 2) {
                holder.chen_state.setText("已结束");
            }

                //是否接单
            if (mlist.get(position).isOrders()) {
                holder.dan.setBackgroundResource(R.drawable.house_think_bg);
                holder.dan.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                holder.dan.setBackgroundResource(R.drawable.repair_submit_bg);
                holder.dan.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }

            holder.chen_comment.setText(mlist.get(position).getComment() + "");


        } else {

            holder.shen.setText("司机");
            holder.chenke_rl.setVisibility(View.GONE);
            holder.siji_rl.setVisibility(View.VISIBLE);

            if (over == 1) {
                holder.state.setText("正在进行");
            } else if (over == 2) {
                holder.state.setText("已结束");
            }

            holder.join_num.setText(mlist.get(position).getParticipateNumber() + "人参加");

            if (mlist.get(position).islike()) {
                holder.join_img.setImageResource(R.mipmap.community_add);
            } else {
                holder.join_img.setImageResource(R.mipmap.community_add_no);
            }
            holder.comment_num.setText(mlist.get(position).getComment() + "");
        }


        final View finalConvertView = convertView;
        //跳转详情
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarPoolingMsgActivity.class);
                intent.putExtra("bean", (Result) mlist.get(position));
                intent.putExtra("state",over);
                mContext.startActivity(intent);
            }
        });


        //加入
        holder.join_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.setFocusable(false);

            }
        });

        //接单
        holder.dan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.setFocusable(false);

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
        TextView chen_comment;
        TextView chen_state;
    }
}
