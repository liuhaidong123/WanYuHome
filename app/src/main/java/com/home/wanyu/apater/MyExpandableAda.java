package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/2.
 */

public class MyExpandableAda extends BaseExpandableListAdapter {

    private List<String> mList = new ArrayList<>();
    private List<String> mTwoList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public MyExpandableAda(List<String> mList, List<String> mTwoList, Context context) {
        this.mList = mList;
        this.mTwoList = mTwoList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    //获取外层集合数量
    @Override
    public int getGroupCount() {
        return mList.size();
    }

    //获取每一个外层中的第二层数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return mTwoList.size();
    }

    //获取每一个外层的实体
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    //获取每一个外层中内层的实体
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    //获取外层下标
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取内层下标
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        OneHolder oneHolder = null;
        if (convertView == null) {
            oneHolder = new OneHolder();
            convertView = mInflater.inflate(R.layout.property_listview_first, null);
            oneHolder.textView = (TextView) convertView.findViewById(R.id.title_every);
            convertView.setTag(oneHolder);
        } else {
            oneHolder = (OneHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TwoHolder twoHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.property_listview_second, null);
            twoHolder = new TwoHolder();
            twoHolder.message = (TextView) convertView.findViewById(R.id.message);
            twoHolder.tv_btn = (TextView) convertView.findViewById(R.id.tv_btn);
            convertView.setTag(twoHolder);
        } else {
            twoHolder = (TwoHolder) convertView.getTag();
        }

        return convertView;
    }
    //内层item是否可点击

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class OneHolder {
        TextView textView;
    }

    class TwoHolder {
        TextView message;
        TextView tv_btn;
    }
}
