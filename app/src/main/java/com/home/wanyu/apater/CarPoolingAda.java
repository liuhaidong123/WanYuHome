package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CarPoolingMsgActivity;
import com.home.wanyu.activity.StartActivity;
import com.home.wanyu.bean.carPoolingList.Result;
import com.home.wanyu.bean.getAreaActivityComment.Root;
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
    private int mPosition;
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 130) {//加入拼车
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "加入拼车成功", Toast.LENGTH_SHORT).show();
                        mlist.get(mPosition).setIslike(true);
                        mlist.get(mPosition).setParticipateNumber(mlist.get(mPosition).getParticipateNumber() + 1);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "亲，您已经加入此拼车，不能重复加入了哦", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 131) {//接单
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "接单成功", Toast.LENGTH_SHORT).show();
                        mlist.get(mPosition).setOrders(true);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "亲，您已经接过此单子了哦", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    public CarPoolingAda(Context mContext, List<Result> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
        this.mInflater = LayoutInflater.from(this.mContext);
        httpTools = HttpTools.getHttpToolsInstance();
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
            holder.person_num = (TextView) convertView.findViewById(R.id.car_chen_state_tv);
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
        holder.person_num.setText(mlist.get(position).getCnumber()+"");


        if (mlist.get(position).getCtype() == 1) {//乘客
            holder.shen.setText("乘客");
            holder.chenke_rl.setVisibility(View.VISIBLE);
            holder.siji_rl.setVisibility(View.GONE);
            holder.chen_comment.setText(mlist.get(position).getComment() + "");

            if (over == 1) {
                holder.chen_state.setText("正在进行");
                //是否接单
                if (mlist.get(position).isOrders()) {
                    holder.dan.setBackgroundResource(R.drawable.house_think_bg);
                    holder.dan.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    holder.dan.setBackgroundResource(R.drawable.repair_submit_bg);
                    holder.dan.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                }

            } else if (over == 2) {
                holder.chen_state.setText("已结束");
                holder.dan.setBackgroundResource(R.drawable.house_think_bg);
                holder.dan.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }

        } else {

            holder.shen.setText("司机");
            holder.chenke_rl.setVisibility(View.GONE);
            holder.siji_rl.setVisibility(View.VISIBLE);

            if (over == 1) {
                holder.state.setText("正在进行");
                if (mlist.get(position).islike()) {
                    holder.join_img.setImageResource(R.mipmap.community_add);
                } else {
                    holder.join_img.setImageResource(R.mipmap.community_add_no);
                }
            } else if (over == 2) {
                holder.state.setText("已结束");
                holder.join_img.setImageResource(R.mipmap.community_add_no);
            }

            holder.join_num.setText(mlist.get(position).getParticipateNumber() + "人参加");
            holder.comment_num.setText(mlist.get(position).getComment() + "");
        }


        final View finalConvertView = convertView;
        //跳转详情
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarPoolingMsgActivity.class);
                intent.putExtra("bean", (Result) mlist.get(position));
                intent.putExtra("state", over);
                mContext.startActivity(intent);
            }
        });


        //加入
        holder.join_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.setFocusable(false);
                mPosition = position;
                if (over == 2) {
                    Toast.makeText(mContext, "亲，此拼车活动已结束了哦", Toast.LENGTH_SHORT).show();
                } else {
                    if (mlist.get(position).islike()) {
                        Toast.makeText(mContext, "亲，您已经加入此拼车，不能重复加入了哦", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mlist.get(position).getParticipateNumber() < mlist.get(position).getCnumber()) {
                            httpTools.carPoolingJoin(handler, UserInfo.userToken, mlist.get(position).getId());
                        } else {
                            Toast.makeText(mContext, "亲，人数已满，不能继续添加了哦", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

        //接单
        holder.dan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.setFocusable(false);

                mPosition = position;
                if (over == 2) {
                    Toast.makeText(mContext, "亲，此拼车活动已结束了哦", Toast.LENGTH_SHORT).show();
                } else {
                    if (mlist.get(position).isOrders()) {
                        Toast.makeText(mContext, "亲，您已经接过此单子了哦", Toast.LENGTH_SHORT).show();
                    } else {
                        httpTools.carPoolingOrder(handler, UserInfo.userToken, mlist.get(position).getId());
                    }
                }


            }
        });
        return convertView;
    }

    class CarHolder {
        RoundImageView head_img;
        TextView name, time, shen, start_time, start_address, end_address, state, comment_num, join_num, person_num;
        ImageView join_img;
        RelativeLayout siji_rl, chenke_rl;
        TextView dan;
        TextView chen_comment;
        TextView chen_state;
    }
}
