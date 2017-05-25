package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.getCircleCommentMsg.LikeNum;
import com.home.wanyu.lzhView.MyView;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class CircleCommendLikeAda extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<LikeNum> list = new ArrayList<>();

    public CircleCommendLikeAda(Context mContext, List<LikeNum> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<LikeNum> list) {
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LikeHolder holder = null;
        if (convertView == null) {
            holder = new LikeHolder();
            convertView = mInflater.inflate(R.layout.circle_commend_like_item, null);
            holder.imageView = (RoundImageView) convertView.findViewById(R.id.like_img);
            convertView.setTag(holder);
        } else {
            holder = (LikeHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE+list.get(position).getAvatar()).resize(ImgUitls.getWith(mContext)/3,ImgUitls.getWith(mContext)/3).error(R.mipmap.error_small).into(holder.imageView );
        return convertView;
    }

    class LikeHolder {
        RoundImageView imageView;
    }
}
