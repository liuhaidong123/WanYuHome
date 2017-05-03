package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */
public class HomeScenePagerItemListApdater extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;
    public HomeScenePagerItemListApdater(List<Map<String,String>> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.homescenepageritemlist_item,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.fragment_home_scene_viewpager_listitem_imageSettings.setImageResource(Integer.parseInt(list.get(position).get("url")));
        viewHodler.fragment_home_scene_viewpager_listitem_textVsetting.setText(list.get(position).get("title"));
        return convertView;
    }

    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.fragment_home_scene_viewpager_listitem_imageSettings)ImageView fragment_home_scene_viewpager_listitem_imageSettings;
        @BindView(R.id.fragment_home_scene_viewpager_listitem_textVsetting)TextView fragment_home_scene_viewpager_listitem_textVsetting;
        @BindView(R.id.fragment_home_scene_viewpager_listitem_switch)Switch fragment_home_scene_viewpager_listitem_switch;
    }
}
