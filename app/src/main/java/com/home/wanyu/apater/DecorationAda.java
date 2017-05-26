package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.homeService.Menulist;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class DecorationAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Menulist> mList = new ArrayList<>();

    public DecorationAda(Context mContext,List<Menulist> mList) {
        this.mContext = mContext;
        this.mList=mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public List<Menulist> getmList() {
        return mList;
    }

    public void setmList(List<Menulist> mList) {
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
        DecorationHolder holder = null;
        if (convertView == null) {
            holder = new DecorationHolder();
            convertView = mInflater.inflate(R.layout.decotion_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.decoretion_img);
            holder.name = (TextView) convertView.findViewById(R.id.decoretion_name);
            holder.price = (TextView) convertView.findViewById(R.id.decoretion_price);
            convertView.setTag(holder);
        } else {
            holder = (DecorationHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getPicture()).resize(ImgUitls.getWith(mContext)/3,ImgUitls.getWith(mContext)/3).error(R.mipmap.error_small).into(
                holder.imageView
        );
        holder.name.setText(mList.get(position).getProductName());
        holder.price.setText("¥"+mList.get(position).getPrice()+"元/㎡");


        return convertView;
    }

    class DecorationHolder {
        ImageView imageView;
        TextView name;
        TextView price;

    }
}
