package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.home.wanyu.R;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordListviewGridviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public RecordListviewGridviewAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 6;
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

        return convertView;
    }

    class LGHolder{
        ImageView imageView;
    }
}
