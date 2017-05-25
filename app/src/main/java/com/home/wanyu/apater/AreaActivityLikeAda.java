package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.getAreaActivityMsg.UpVptelist;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class AreaActivityLikeAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<UpVptelist> mList=new ArrayList<>();

    public AreaActivityLikeAda(Context mContext, List<UpVptelist> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    public void setmList(List<UpVptelist> mList) {
        this.mList = mList;
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

        Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getAvatar()).resize(ImgUitls.getWith(mContext)/5,ImgUitls.getWith(mContext)/5).error(R.mipmap.error_small).into(holder.imageView);
        return convertView;
    }

    class LikeHolder{
        RoundImageView imageView;
    }
}
