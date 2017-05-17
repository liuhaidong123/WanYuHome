package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.waterEleRan.Items;
import com.home.wanyu.bean.waterEleRan.ItemsYear;
import com.home.wanyu.bean.waterEleRan.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/5.
 */

public class OrderMsgAda extends BaseAdapter {

    private Context mContext;
    private List<ItemsYear> list = new ArrayList<>();
    private LayoutInflater mInflater;

    public OrderMsgAda(Context mContext, List<ItemsYear> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ItemsYear> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        OrdermsgHolder holder = null;
        if (convertView == null) {
            holder = new OrdermsgHolder();
            convertView = mInflater.inflate(R.layout.order_msg_listview_item, null);
            holder.tv_date = (TextView) convertView.findViewById(R.id.order_msg_date_tv);
            holder.tv_allMoney = (TextView) convertView.findViewById(R.id.all_money);
            holder.tv_water = (TextView) convertView.findViewById(R.id.order_msg_water_tv);
            holder.tv_water_money = (TextView) convertView.findViewById(R.id.water_money);
            holder.tv_ele = (TextView) convertView.findViewById(R.id.order_msg_ele_tv);
            holder.tv_ele_money = (TextView) convertView.findViewById(R.id.ele_money);
            holder.tv_shao = (TextView) convertView.findViewById(R.id.order_msg_shao_tv);
            holder.tv_shao_money = (TextView) convertView.findViewById(R.id.shao_money);
            convertView.setTag(holder);
        } else {
            holder = (OrdermsgHolder) convertView.getTag();
        }

        holder.tv_date.setText(list.get(position).getTime());
        holder.tv_allMoney.setText("合计缴费:" + list.get(position).getMoneySum() + "元");
        //如果每个月费用的总数为0
        if (list.get(position).getMoneySum() == 0) {
            holder.tv_water.setText("水费7");
            holder.tv_water_money.setText("0元");
            holder.tv_ele.setText("电费");
            holder.tv_ele_money.setText("0元");
            holder.tv_shao.setText("燃气费9");
            holder.tv_shao_money.setText("0元");
        } else {
            List<Items> list1 = list.get(position).getItems();
            for (int i = 0; i < list1.size(); i++) {
                if (list1.get(i).getPaymentItemId() == 1) {
                    holder.tv_water.setText("水费7");
                    holder.tv_water_money.setText(list1.get(i).getMoney() + "元");
                } else if (list1.get(i).getPaymentItemId() == 2) {
                    holder.tv_ele.setText("电费");
                    holder.tv_ele_money.setText(list1.get(i).getMoney() + "元");
                } else {
                    holder.tv_shao.setText("燃气费9");
                    holder.tv_shao_money.setText(list1.get(i).getMoney() + "元");
                }
            }
        }


        return convertView;
    }

    class OrdermsgHolder {
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
