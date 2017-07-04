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
import com.home.wanyu.bean.CircleBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/2.
 */

public class CircleAdapter extends BaseAdapter {
    private List<CircleBean> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public CircleAdapter(List<CircleBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
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
        CircleHolder circleHolder = null;
        if (convertView == null) {
            circleHolder = new CircleHolder();
            convertView = mInflater.inflate(R.layout.circle_listview_item, null);
            circleHolder.bg_rl = (RelativeLayout) convertView.findViewById(R.id.circle_bg_rl);
            circleHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(circleHolder);
        } else {
            circleHolder = (CircleHolder) convertView.getTag();
        }
        circleHolder.bg_rl.setBackgroundResource(mList.get(position).getImg());
        circleHolder.textView.setText(mList.get(position).getName());

        return convertView;
    }

    class CircleHolder {
        RelativeLayout bg_rl;
        TextView textView;
    }
}
