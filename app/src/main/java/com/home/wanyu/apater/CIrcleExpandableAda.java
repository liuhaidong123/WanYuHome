package com.home.wanyu.apater;

import android.content.Context;
import android.content.pm.PackageManager;
import android.mtp.MtpObjectInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/10.
 */

public class CIrcleExpandableAda extends BaseExpandableListAdapter {
    private List<String> mList = new ArrayList<>();
    private List<String> mTwoList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public CIrcleExpandableAda(List<String> mList, List<String> mTwoList, Context mContext) {
        this.mList = mList;
        this.mTwoList = mTwoList;
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 4;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       OutHolder holder=null;
        if (convertView==null){
            holder=new OutHolder();
            convertView=mInflater.inflate(R.layout.circle_commend_out_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.out_name_tv);
            holder.msg= (TextView) convertView.findViewById(R.id.out_name_commend_msg);
            convertView.setTag(holder);
        }else {
            holder= (OutHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Inholder holder=null;
        if (convertView==null){
            holder=new Inholder();
            convertView=mInflater.inflate(R.layout.circle_commend_in_item,null);
            holder.name1= (TextView) convertView.findViewById(R.id.in_name_tv_one);
            holder.name2= (TextView) convertView.findViewById(R.id.in_name_tv_two);
            holder.msg= (TextView) convertView.findViewById(R.id.in_name_commend_msg);
            convertView.setTag(holder);
        }else {
            holder= (Inholder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class OutHolder{
        TextView name;
        TextView msg;
    }

    class Inholder{
        TextView name1;
        TextView name2;
        TextView msg;
    }
}
