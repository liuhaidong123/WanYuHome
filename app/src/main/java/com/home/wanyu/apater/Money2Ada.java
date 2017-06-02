package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.CircleBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/8.
 */

public class Money2Ada extends BaseAdapter {
    private List<Integer> imgList=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public Money2Ada( Context mContext, List<Integer> imgList) {
        this.mContext = mContext;
        this.imgList=imgList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Money2Holder holder = null;
        if (convertView == null) {
            holder = new Money2Holder();
            convertView = mInflater.inflate(R.layout.money2_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.money2_ele_img);
            holder.textView = (TextView) convertView.findViewById(R.id.money2_tv);
            holder.textView_num = (TextView) convertView.findViewById(R.id.money2_num_tv);

            convertView.setTag(holder);
        } else {
            holder = (Money2Holder) convertView.getTag();
        }
        if (position==0){
            holder.textView.setText("电费");
            holder.textView_num.setText("2017001002");
        }else if (position==1){
            holder.textView.setText("水费");
            holder.textView_num.setText("2017001003");
        }else if (position==2){
            holder.textView.setText("燃气费");
            holder.textView_num.setText("2017001004");
        }else if (position==3){
            holder.textView.setText("物业费");
            holder.textView_num.setText("2017001005");
        }

        Picasso.with(mContext).load(imgList.get(position)).into(holder.imageView);

        return convertView;
    }

    class Money2Holder {
        ImageView imageView;
        TextView textView;
        TextView textView_num;
    }
}
