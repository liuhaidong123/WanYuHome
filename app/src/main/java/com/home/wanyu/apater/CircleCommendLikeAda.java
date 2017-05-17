package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/5/11.
 */

public class CircleCommendLikeAda extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public CircleCommendLikeAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(this.mContext);
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
        LikeHolder holder=null;
        if (convertView==null){
            holder=new LikeHolder();
            convertView=mInflater.inflate(R.layout.circle_commend_like_item,null);
            holder.imageView= (RoundImageView) convertView.findViewById(R.id.like_img);
            convertView.setTag(holder);
        }else {
            holder= (LikeHolder) convertView.getTag();
        }

        return convertView;
    }

    class LikeHolder{
        RoundImageView imageView;
    }
}
