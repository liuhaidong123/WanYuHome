package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.areaList.Result;
import com.home.wanyu.bean.expressList.Rows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class ExpressMsgAda extends BaseAdapter {

    private List<Rows> mExpressList=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public ExpressMsgAda(Context mContext,List<Rows> mExpressList) {
        this.mContext = mContext;
        this.mExpressList=mExpressList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setmExpressList(List<Rows> mExpressList) {
        this.mExpressList = mExpressList;
    }

    @Override
    public int getCount() {
        return mExpressList.size();
    }

    @Override
    public Object getItem(int position) {
        return mExpressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpressMsgHolder holder = null;
        if (convertView == null) {
            holder = new ExpressMsgHolder();
            convertView=mInflater.inflate(R.layout.express_msg_item,null);
            holder.state = (TextView) convertView.findViewById(R.id.express_state_tv);
            holder.time = (TextView) convertView.findViewById(R.id.express_time_tv);
            holder.zitigui = (TextView) convertView.findViewById(R.id.express_zi_tv);
            holder.ma = (TextView) convertView.findViewById(R.id.express_ma_tv);
            holder.from = (TextView) convertView.findViewById(R.id.express_from_tv);
            holder.num = (TextView) convertView.findViewById(R.id.express_num_tv);
            holder.shou = (TextView) convertView.findViewById(R.id.end_tv);

            convertView.setTag(holder);
        } else {
            holder = (ExpressMsgHolder) convertView.getTag();
        }

        if (mExpressList.get(position).getPropertyType()==1){
            holder.state.setText("服务站已签收");
        }else {
            holder.state.setText("服务站未签收");
        }
        holder.time.setText(mExpressList.get(position).getSignTimeString());
        holder.zitigui.setText(mExpressList.get(position).getCabinet());
        holder.ma.setText(mExpressList.get(position).getDeliveryCode());
        holder.from.setText(mExpressList.get(position).getEname());
        holder.num.setText(mExpressList.get(position).getExpressNumber());

        if (mExpressList.get(position).getPersonalType()==1){
            holder.shou.setText("未收取");
        }else if (mExpressList.get(position).getPersonalType()==2){
            holder.shou.setText("已收取");
        }else {
            holder.shou.setText("已失效");
        }


        return convertView;
    }

    class ExpressMsgHolder {
        TextView state;
        TextView time;
        TextView zitigui;
        TextView ma;
        TextView from;
        TextView num;
        TextView shou;

    }
}
