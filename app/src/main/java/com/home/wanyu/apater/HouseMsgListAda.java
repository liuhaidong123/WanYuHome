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
import com.home.wanyu.bean.HouseFirstList.Rows;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseMsgListAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Rows> mList = new ArrayList<>();


    public HouseMsgListAda(Context mContext,List<Rows> mList) {
        this.mContext = mContext;
        this.mList=mList;
        this.mInflater = LayoutInflater.from(mContext);
    }


    public void setmList(List<Rows> mList) {
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
        HouseListHolder holder = null;
        if (convertView == null) {
            holder = new HouseListHolder();
            convertView = mInflater.inflate(R.layout.house_location_area_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.house_location_img);
            holder.msg = (TextView) convertView.findViewById(R.id.house_msg_tv);
            holder.type = (TextView) convertView.findViewById(R.id.house_type_tv);
            holder.price = (TextView) convertView.findViewById(R.id.house_money);
            holder.time = (TextView) convertView.findViewById(R.id.house_time);
            convertView.setTag(holder);
        } else {
            holder = (HouseListHolder) convertView.getTag();
        }

     if (!mList.get(position).getPicture().equals("")){
         String [] str=mList.get(position).getPicture().split(";");
         Picasso.with(mContext).load(UrlTools.BASE+str[0]).resize(ImgUitls.getWith(mContext)/4,ImgUitls.getWith(mContext)/4).error(R.mipmap.error_small).into(holder.imageView);
     }else {
         Picasso.with(mContext).load(R.mipmap.error_small).into(holder.imageView);
     }
        holder.msg.setText(mList.get(position).getCyty()+mList.get(position).getResidentialQuarters()+" "+mList.get(position).getApartmentLayout()+" "+mList.get(position).getHousingArea()+"平米 "+mList.get(position).getPaymentMethod()+" "+mList.get(position).getDirection());
        if (mList.get(position).getRentalTyoe()==1){
            holder.type.setText("整租");
        }else {
            holder.type.setText("合租");
        }
        holder.price.setText(mList.get(position).getRent()+"元/月");
        holder.time.setText(TimeUtils.getTime(mList.get(position).getCreateTimeString()));
        return convertView;
    }

    class HouseListHolder {
        ImageView imageView;
        TextView msg;
        TextView type;
        TextView price;
        TextView time;
    }
}
