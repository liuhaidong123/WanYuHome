package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/5.
 */

public class OrderMsgAda extends BaseAdapter {

    private Context mContext;
    //private List<String> list=new ArrayList<>();
    private LayoutInflater mInflater;

    public OrderMsgAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 4;
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
        OrdermsgHolder holder=null;
        if (convertView==null){
            holder=new OrdermsgHolder();
            convertView=mInflater.inflate(R.layout.order_msg_listview_item,null);
            holder.tv_date= (TextView) convertView.findViewById(R.id.order_msg_date_tv);
            holder.tv_allMoney= (TextView) convertView.findViewById(R.id.all_money);
            holder.tv_water= (TextView) convertView.findViewById(R.id.order_msg_water_tv);
            holder.tv_water_money= (TextView) convertView.findViewById(R.id.water_money);
            holder.tv_ele= (TextView) convertView.findViewById(R.id.order_msg_ele_tv);
            holder.tv_ele_money= (TextView) convertView.findViewById(R.id.ele_money);
            holder.tv_shao= (TextView) convertView.findViewById(R.id.order_msg_shao_tv);
            holder.tv_shao_money= (TextView) convertView.findViewById(R.id.shao_money);
            convertView.setTag(holder);
        }else {
            holder= (OrdermsgHolder) convertView.getTag();
        }


        return convertView;
    }

    class OrdermsgHolder{
        TextView tv_date;
        TextView tv_allMoney;
        TextView tv_water;
        TextView tv_water_money;
        TextView tv_ele;
        TextView tv_ele_money;
        TextView tv_shao;
        TextView tv_shao_money;
    }
}
