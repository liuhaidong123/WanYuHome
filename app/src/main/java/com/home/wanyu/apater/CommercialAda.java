package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.bean.shoppingList.Result;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class CommercialAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> mList = new ArrayList<>();

    public CommercialAda(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<Result> mList) {
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
        GoodsHolder holder = null;
        if (convertView == null) {
            holder = new GoodsHolder();
            convertView = mInflater.inflate(R.layout.commercial_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.goods_img);
            holder.name = (TextView) convertView.findViewById(R.id.goods_name);
            holder.price = (TextView) convertView.findViewById(R.id.goods_price);
            holder.km = (TextView) convertView.findViewById(R.id.km_shop);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            convertView.setTag(holder);
        } else {
            holder = (GoodsHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getPicture()).resize(ImgUitls.getWith(mContext) / 4, ImgUitls.getWith(mContext) / 4).error(R.mipmap.error_small).into(holder.imageView);
        holder.name.setText(mList.get(position).getBusinessName());
        holder.price.setText("人均：¥" + mList.get(position).getAverage() + "元/人");
        holder.km.setText(mList.get(position).getDistance() + "m");
        holder.ratingBar.setRating(mList.get(position).getStar());
        return convertView;
    }

    class GoodsHolder {
        ImageView imageView;

        TextView name;
        TextView price;
        TextView km;
        RatingBar ratingBar;
    }
}
