package com.home.wanyu.apater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.haveAddress.Result;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liuhaidong on 2017/5/5.
 */

public class OrderAddressAda extends BaseAdapter {
    private Context mContext;
    private List<Result> list=new ArrayList<>();
    private LayoutInflater mInflater;

    public OrderAddressAda(Context mContext, List<Result> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setList(List<Result> list) {
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
        AddressHolder holder=null;
        if (convertView==null){
            holder=new AddressHolder();
            convertView=mInflater.inflate(R.layout.order_address_msg_listview_item,null);
            holder.textView= (TextView) convertView.findViewById(R.id.order_address_ms);
            holder.mAddress_bg= (RelativeLayout) convertView.findViewById(R.id.order_address_bg);
            holder.mDelete_ll= (LinearLayout) convertView.findViewById(R.id.order_delete_ll);
            holder.mEdit_ll= (LinearLayout) convertView.findViewById(R.id.order_edit_ll);
            convertView.setTag(holder);
        }else {
            holder= (AddressHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getDetailAddress());
        if (position==0){
            holder.mAddress_bg.setBackgroundResource(R.drawable.order_select_btn);
        }else {
            holder.mAddress_bg.setBackgroundResource(R.drawable.order_select_btn_no);
        }

        return convertView;
    }

    class AddressHolder{
        TextView textView;
        RelativeLayout mAddress_bg;
        LinearLayout mDelete_ll,mEdit_ll;
    }
}
