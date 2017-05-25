package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.home.wanyu.activity.CircleCardMessageActivity;
import com.home.wanyu.bean.getCircleCardList.Result;
import com.home.wanyu.bean.getCircleCardList.StateList;
import com.home.wanyu.bean.getCircleLike.Root;
import com.home.wanyu.myUtils.TimeUtils;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/9.
 */

public class CircleFriendListviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> list = new ArrayList<>();
    private FriendHolder holder;
    private int mPosition;
    private long userID;
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 113) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                        list.get(mPosition).setIslike(true);
                        list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() + 1);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "撤销点赞", Toast.LENGTH_SHORT).show();
                        list.get(mPosition).setIslike(false);
                        list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() - 1);
                        notifyDataSetChanged();
                    }
                }
            }
        }
    };

    public CircleFriendListviewAda(Context mContext, List<Result> list) {
        this.list = list;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
        httpTools = HttpTools.getHttpToolsInstance();
    }

    public void setList(List<Result> list,long userID) {
        this.list = list;
        this.userID=userID;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new FriendHolder();
            convertView = mInflater.inflate(R.layout.circle_friend_listview_item, null);
            holder.headimg = (RoundImageView) convertView.findViewById(R.id.circle_head_img);
            holder.name = (TextView) convertView.findViewById(R.id.circle_name_tv);
            holder.area = (TextView) convertView.findViewById(R.id.circle_area_tv);
            holder.type = (TextView) convertView.findViewById(R.id.circle_type_tv);
            holder.msg = (TextView) convertView.findViewById(R.id.circle_commend_msg);
            holder.gridview = (MyGridView) convertView.findViewById(R.id.circle_gridview_friend);
            holder.time = (TextView) convertView.findViewById(R.id.circle_time_tv);
            holder.likenum = (TextView) convertView.findViewById(R.id.circle_like_num);
            holder.commendnum = (TextView) convertView.findViewById(R.id.circle_commend_num);
            holder.likeimg = (ImageView) convertView.findViewById(R.id.circle_like_img);
            convertView.setTag(holder);

        } else {
            holder = (FriendHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE + list.get(position).getAvatar()).error(R.mipmap.error_small).into(holder.headimg);
        holder.name.setText(list.get(position).getUserName());
        holder.area.setText(list.get(position).getRname());
        holder.type.setText(list.get(position).getCname());
        holder.msg.setText(list.get(position).getContent());
        holder.time.setText(TimeUtils.getTime(list.get(position).getCreateTimeString()));
        holder.likenum.setText(list.get(position).getLikeNum() + "");
        holder.commendnum.setText(list.get(position).getCommentNum() + "");

        if (list.get(position).islike()) {
            Picasso.with(mContext).load(R.mipmap.circle_like).into(holder.likeimg);
        } else {
            Picasso.with(mContext).load(R.mipmap.circle_like_no).into(holder.likeimg);
        }

        if (!list.get(position).getPicture().equals("")) {
            String[] imgstr = list.get(position).getPicture().split(";");
            holder.gridview.setAdapter(new RecordListviewGridviewAda(mContext, imgstr));
        } else {
            String[] imgstr = new String[0];
            holder.gridview.setAdapter(new RecordListviewGridviewAda(mContext, imgstr));
        }


        final View finalConvertView = convertView;
        //点赞
        holder.likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = position;
                holder.likeimg.setFocusable(true);
                finalConvertView.setFocusable(false);
                httpTools.getCIrcleLikeResult(handler, UserInfo.userToken, list.get(position).getId());
            }
        });


        //跳转到评论页面
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = position;
                holder.likeimg.setFocusable(false);
                finalConvertView.setFocusable(true);
                Intent intent = new Intent(mContext, CircleCardMessageActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("userid",userID);
                intent.putExtra("bean",list.get(mPosition));
                mContext.startActivity(intent);
                Log.e("跳userid=",userID+"");
            }
        });

        return convertView;
    }

    class FriendHolder {
        RoundImageView headimg;
        TextView name;
        TextView area;
        TextView type;
        TextView msg;
        MyGridView gridview;
        TextView time;
        TextView likenum;
        TextView commendnum;
        ImageView likeimg;

    }
}
