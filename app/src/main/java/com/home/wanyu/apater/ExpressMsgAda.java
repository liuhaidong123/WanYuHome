package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.areaList.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/17.
 */

public class ExpressMsgAda extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;


    public ExpressMsgAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 10;
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
