package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CarPoolingCommentActivity;
import com.home.wanyu.bean.carPoolingMsg.Commentlist;
import com.home.wanyu.bean.getCircleCommentMsg.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/25.
 */

public class CarPoolingCommentAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Commentlist> mCommentList = new ArrayList<>();
    private CarHolder holder;
    private long coverPersonalId = 0;
    private long carpoolingId;
    public CarPoolingCommentAda(Context mContext, List<Commentlist> mCommentList) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }


    public void setmCommentList(List<Commentlist> mCommentList, long carpoolingId) {
        this.mCommentList = mCommentList;
        this.carpoolingId= carpoolingId;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            holder = new CarHolder();
            convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
            holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
            holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
            holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
            holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
            convertView.setTag(holder);
        } else {
            holder = (CarHolder) convertView.getTag();
        }

        //只是某个人评论的
        if (mCommentList.get(position).getCoverPersonalId() == 0) {
            holder.name1.setText(mCommentList.get(position).getUserName());//张三评论：
            holder.name2.setVisibility(View.GONE);
            holder.huifu.setVisibility(View.GONE);
            holder.msg.setText(":" + mCommentList.get(position).getContent());//你是个小猫咪


            //某个人回复某个人
        } else {
            holder.name1.setText(mCommentList.get(position).getUserName());//张三
            holder.name2.setVisibility(View.VISIBLE);
            holder.huifu.setVisibility(View.VISIBLE);//回复
            holder.name2.setText(mCommentList.get(position).getCoverPersonalName());//李四
            holder.msg.setText(":" + mCommentList.get(position).getContent());//我很好

        }
        holder.name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCommentList.get(position).getPersonalId() == UserInfo.personalId) {
                    coverPersonalId = 0;
                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
                    intent.putExtra("flag","carPooling");
                    intent.putExtra("coverPersonalId",coverPersonalId);
                    intent.putExtra("Id",carpoolingId);
                    intent.putExtra("hint","说点什么");
                    mContext.startActivity(intent);
                } else {
                    coverPersonalId = mCommentList.get(position).getPersonalId();
                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
                    intent.putExtra("flag","carPooling");
                    intent.putExtra("coverPersonalId",coverPersonalId);
                    intent.putExtra("Id",carpoolingId);
                    intent.putExtra("hint","回复" +mCommentList.get(position).getUserName());
                    mContext.startActivity(intent);
                }
            }
        });

        holder.name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommentList.get(position).getCoverPersonalId() == UserInfo.personalId) {
                    coverPersonalId = 0;
                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
                    intent.putExtra("flag","carPooling");
                    intent.putExtra("coverPersonalId",coverPersonalId);
                    intent.putExtra("Id",carpoolingId);
                    intent.putExtra("hint","说点什么");
                    mContext.startActivity(intent);
                } else {
                    coverPersonalId = mCommentList.get(position).getCoverPersonalId();
                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
                    intent.putExtra("flag","carPooling");
                    intent.putExtra("coverPersonalId",coverPersonalId);
                    intent.putExtra("Id",carpoolingId);
                    intent.putExtra("hint","回复" +mCommentList.get(position).getCoverPersonalName());

                    mContext.startActivity(intent);
                }
            }
        });




        return convertView;
    }

    class CarHolder {
        TextView name1;
        TextView name2;
        TextView huifu;
        TextView msg;
    }
}
