package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.getAreaActivityMsg.ActivityLoglist;
import com.home.wanyu.bean.getAreaActivityMsg.Activitypicturelist;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/24.
 */

public class AreaActivityImgAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int listSize;
    private List<String> mList=new ArrayList<>();

    public AreaActivityImgAda(Context mContext, List<String> mList,int listSize) {
        this.mContext = mContext;
        this.mList = mList;
        this.listSize=listSize;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 1 : mList.size() + 1;
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
        ImgHolder holder=null;
        if (convertView==null){

            holder=new ImgHolder();
            convertView=mInflater.inflate(R.layout.community_camera_img,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.house_post_img);
            convertView.setTag(holder);
        }else {
            holder= (ImgHolder) convertView.getTag();
        }

        if (mList.size()==0){
            holder.imageView.setImageResource(R.mipmap.house_add_photo);
            //Picasso.with(mContext).load(R.mipmap.house_add_photo).into(holder.imageView);
        }else {
            if (position == mList.size()) {
                holder.imageView.setImageResource(R.mipmap.house_add_photo);
               // Picasso.with(mContext).load(R.mipmap.house_add_photo).into(holder.imageView);
            }else {
//                if (position>=listSize){
//                    it.sephiroth.android.library.picasso.Picasso.with(mContext).load(mList.get(position)).centerCrop().resize(ImgUitls.getWith(mContext) / 6,
//                       ImgUitls.getWith(mContext) / 6).error(R.mipmap.error_small).into(holder.imageView);
//                }else {
                   Picasso.with(mContext).load(UrlTools.BASE+mList.get(position)).resize(ImgUitls.getWith(mContext)/5,ImgUitls.getWith(mContext)/5).error(R.mipmap.error_small).into(holder.imageView);
              //  }

            }
        }


        return convertView;
    }

    class ImgHolder{
        ImageView imageView;
    }
}
