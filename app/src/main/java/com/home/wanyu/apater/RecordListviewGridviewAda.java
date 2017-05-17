package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordListviewGridviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private String [] strimg;
    public RecordListviewGridviewAda(Context mContext,String [] strimg) {
        this.mContext = mContext;
        this.strimg=strimg;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setStrimg(String[] strimg) {
        this.strimg = strimg;
    }

    @Override
    public int getCount() {
        return strimg.length;
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
        LGHolder holder=null;
        if (convertView==null){
            holder=new LGHolder();
            convertView=mInflater.inflate(R.layout.record_listview_img_gridview_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.record_listview_gridview_img);
            convertView.setTag(holder);
        }else {
            holder= (LGHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE+strimg[position]).error(R.mipmap.error_small).resize(ImgUitls.getWith(mContext)/3,ImgUitls.getWith(mContext)/3).into(holder.imageView);
        return convertView;
    }

    class LGHolder{
        ImageView imageView;
    }
}
