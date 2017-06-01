package com.home.wanyu.apater;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.HouseLookConfigure;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseLookConfigureAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HouseLookConfigure> list = new ArrayList<>();

    public HouseLookConfigureAda(Context mContext, List<HouseLookConfigure> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<HouseLookConfigure> list) {
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
        ConfigureHolder holder = null;
        if (convertView == null) {
            holder = new ConfigureHolder();
            convertView = mInflater.inflate(R.layout.house_look_configure_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.look_img);
            holder.name = (TextView) convertView.findViewById(R.id.look_name);
            convertView.setTag(holder);
        } else {
            holder = (ConfigureHolder) convertView.getTag();
        }

        if (list.get(position).isFlag()) {
            holder.name.setText(list.get(position).getName());
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.titlecolor3));
            if (list.get(position).getName().equals("电视")) {
                Picasso.with(mContext).load(R.mipmap.television).into(holder.imageView);
            }else if (list.get(position).getName().equals("宽带")){
                Picasso.with(mContext).load(R.mipmap.broadband).into(holder.imageView);
            }else if (list.get(position).getName().equals("洗衣机")){
                Picasso.with(mContext).load(R.mipmap.washer).into(holder.imageView);
            }else if (list.get(position).getName().equals("床")){
                Picasso.with(mContext).load(R.mipmap.bed).into(holder.imageView);
            }else if (list.get(position).getName().equals("热水器")){
                Picasso.with(mContext).load(R.mipmap.heater).into(holder.imageView);
            }else if (list.get(position).getName().equals("供暖")){
                Picasso.with(mContext).load(R.mipmap.heating).into(holder.imageView);
            }else if (list.get(position).getName().equals("柜子")){
                Picasso.with(mContext).load(R.mipmap.cabinet).into(holder.imageView);
            }else if (list.get(position).getName().equals("沙发")){
                Picasso.with(mContext).load(R.mipmap.sofa).into(holder.imageView);
            }else if (list.get(position).getName().equals("冰箱")){
                Picasso.with(mContext).load(R.mipmap.fridge).into(holder.imageView);
            }else if (list.get(position).getName().equals("空调")){
                Picasso.with(mContext).load(R.mipmap.air_conditioner).into(holder.imageView);
            }

        } else {
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.color9));
            holder.name.setText(list.get(position).getName());
            if (list.get(position).getName().equals("电视")) {
                Picasso.with(mContext).load(R.mipmap.television_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("宽带")){
                Picasso.with(mContext).load(R.mipmap.broadband_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("洗衣机")){
                Picasso.with(mContext).load(R.mipmap.washer_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("床")){
                Picasso.with(mContext).load(R.mipmap.bed_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("热水器")){
                Picasso.with(mContext).load(R.mipmap.heater_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("供暖")){
                Picasso.with(mContext).load(R.mipmap.heating_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("柜子")){
                Picasso.with(mContext).load(R.mipmap.cabinet_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("沙发")){
                Picasso.with(mContext).load(R.mipmap.sofa_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("冰箱")){
                Picasso.with(mContext).load(R.mipmap.fridge_no).into(holder.imageView);
            }else if (list.get(position).getName().equals("空调")){
                Picasso.with(mContext).load(R.mipmap.air_conditioner_no).into(holder.imageView);
            }
        }

        return convertView;
    }

    class ConfigureHolder {
        ImageView imageView;
        TextView name;
    }
}
