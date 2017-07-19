package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CommunityCommentActivity;
import com.home.wanyu.activity.CommunityMessageActivity;
import com.home.wanyu.bean.getAreaActivityList.Result;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/12.
 */

public class CommunityListViewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> mList = new ArrayList<>();
    private CommunityHolder holder;
    private int over;
    private long userID;
    private int mPostion;
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 120) {//点赞
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                        mList.get(mPostion).setIslike(true);
                        mList.get(mPostion).setLikeNum(mList.get(mPostion).getLikeNum() + 1);

                    } else {
                        Toast.makeText(mContext, "取消点赞", Toast.LENGTH_SHORT).show();
                        mList.get(mPostion).setIslike(false);
                        mList.get(mPostion).setLikeNum(mList.get(mPostion).getLikeNum() - 1);
                    }
                    notifyDataSetChanged();
                }

            } else if (msg.what == 123) {//加入
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "参加活动成功", Toast.LENGTH_SHORT).show();
                        mList.get(mPostion).setJoined(true);
                        mList.get(mPostion).setParticipateNumber(mList.get(mPostion).getParticipateNumber() + 1);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "您已经参加过该活动", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        }
    };

    public CommunityListViewAda(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(mContext);
        httpTools = HttpTools.getHttpToolsInstance();

    }

    public void setmList(List<Result> mList, int over, long userID) {
        this.over = over;
        this.mList = mList;
        this.userID = userID;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
        Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getAvatar()).resize(ImgUitls.getWith(mContext) / 3, ImgUitls.getWith(mContext) / 3).error(R.mipmap.error_small).into(holder.imgHead);
        holder.name_tv.setText(mList.get(position).getUser_name());
        holder.time_tv.setText(mList.get(position).getCreateTimeString());
        holder.title_tv.setText(mList.get(position).getActivityTheme());
        holder.time_msg_tv.setText(mList.get(position).getStarttimeString() + "-" + mList.get(position).getEndtimeString());
        holder.address_tv.setText(mList.get(position).getActivityAddress());
        holder.person_tv.setText(mList.get(position).getActivityNumber() + "人");
        if (over == 1) {
            holder.state_tv.setText("正在进行");
        } else if (over == 2) {
            holder.state_tv.setText("活动结束");
        }
        holder.like_num_tv.setText(mList.get(position).getLikeNum() + "人感兴趣");
        holder.join_num_tv.setText(mList.get(position).getParticipateNumber() + "人参加");

        if (mList.get(position).islike()) {
            Picasso.with(mContext).load(R.mipmap.circle_like).into(holder.like_img);
        } else {
            Picasso.with(mContext).load(R.mipmap.circle_like_no).into(holder.like_img);
        }

        if (mList.get(position).isJoined()) {
            Picasso.with(mContext).load(R.mipmap.community_add).into(holder.add_img);
        } else {
            Picasso.with(mContext).load(R.mipmap.community_add_no).into(holder.add_img);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.like_img.setFocusable(false);
                holder.like_img.setFocusableInTouchMode(false);
                holder.add_img.setFocusable(false);
                holder.add_img.setFocusableInTouchMode(false);
                Intent intent = new Intent(mContext, CommunityCommentActivity.class);
                intent.putExtra("activityId",mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        final View finalConvertView = convertView;
        //点赞
        holder.like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostion = position;
                finalConvertView.setFocusable(false);
                finalConvertView.setFocusableInTouchMode(false);
                holder.add_img.setFocusable(false);
                holder.add_img.setFocusableInTouchMode(false);
                httpTools.getAreaActivityLike(handler, UserInfo.userToken, mList.get(position).getId());

            }
        });
//添加
        holder.add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostion = position;
                finalConvertView.setFocusable(false);
                finalConvertView.setFocusableInTouchMode(false);
                holder.like_img.setFocusable(false);
                holder.like_img.setFocusableInTouchMode(false);
                if (over == 2) {
                    Toast.makeText(mContext, "此活动已结束", Toast.LENGTH_SHORT).show();
                } else {
                    if (mList.get(position).getParticipateNumber()>=mList.get(position).getActivityNumber()){
                        Toast.makeText(mContext, "抱歉,活动人数已满", Toast.LENGTH_SHORT).show();
                    }else {
                        httpTools.areaActivityJoin(handler, UserInfo.userToken, mList.get(position).getId());
                    }

                }
            }
        });

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
