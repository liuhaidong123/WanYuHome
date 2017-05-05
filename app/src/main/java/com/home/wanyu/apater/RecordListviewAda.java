package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.MyGridView;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordListviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public RecordListviewAda(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 2;
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
        ListViewHolder holder = null;
        if (convertView == null) {
            holder = new ListViewHolder();
            convertView = mInflater.inflate(R.layout.record_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.record_img);
            holder.tv_category = (TextView) convertView.findViewById(R.id.record_categoty);
            holder.tv_time = (TextView) convertView.findViewById(R.id.record_time);
            holder.tv_state = (TextView) convertView.findViewById(R.id.record_state);
            holder.tv_msg = (TextView) convertView.findViewById(R.id.record_message);
            holder.gridView = (MyGridView) convertView.findViewById(R.id.record_gridview);
            holder.foot_rl = (RelativeLayout) convertView.findViewById(R.id.foot_rl);
            holder.tv_cancle = (TextView) convertView.findViewById(R.id.cancle_btn);
            holder.tv_finish = (TextView) convertView.findViewById(R.id.finish_btn);

            convertView.setTag(holder);

        } else {
            holder = (ListViewHolder) convertView.getTag();
        }

        holder.gridView.setAdapter(new RecordListviewGridviewAda(mContext));
        return convertView;
    }

    class ListViewHolder {
        ImageView imageView;
        TextView tv_category;
        TextView tv_time;
        TextView tv_state;
        TextView tv_msg;
        MyGridView gridView;
        RelativeLayout foot_rl;
        TextView tv_cancle;
        TextView tv_finish;
    }

}
