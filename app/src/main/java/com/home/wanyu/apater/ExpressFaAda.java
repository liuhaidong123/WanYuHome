package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Express.ExpressBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class ExpressFaAda extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpressBean> list = new ArrayList<>();

    public ExpressFaAda(Context context, List<ExpressBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
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
        ShouHolder holder = null;
        if (convertView == null) {
            holder = new ShouHolder();
            convertView = inflater.inflate(R.layout.express_shou_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.express_img);
            holder.textView = (TextView) convertView.findViewById(R.id.express_tv);
            holder.phone = (ImageView) convertView.findViewById(R.id.express_phone);
            convertView.setTag(holder);
        } else {
            holder = (ShouHolder) convertView.getTag();
        }

        Picasso.with(context).load(list.get(position).getImg()).error(R.mipmap.error_small).into(holder.imageView);
        holder.textView.setText(list.get(position).getName());
        Picasso.with(context).load(list.get(position).getPhoneImg()).error(R.mipmap.error_small).into(holder.phone);


        return convertView;
    }

    class ShouHolder {
        ImageView imageView;
        TextView textView;
        ImageView phone;
    }
}
