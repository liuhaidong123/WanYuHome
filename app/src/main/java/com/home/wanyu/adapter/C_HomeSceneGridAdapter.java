package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/6/30.
 */

public class C_HomeSceneGridAdapter extends BaseAdapter{
    int[]testIconId={R.mipmap.getupq,R.mipmap.restq,R.mipmap.leaveq,R.mipmap.gohomeq,R.mipmap.playgameq
    ,R.mipmap.getupq,R.mipmap.restq,R.mipmap.leaveq,R.mipmap.gohomeq,R.mipmap.playgameq};//测试用(起床，休息，离家，回家，打游戏)
    String[]testTitle={"起床","休息","离家","回家","打游戏"
    ,"起床","休息","离家","回家","打游戏"};
    Context context;
    public C_HomeSceneGridAdapter( Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return testIconId.length;
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
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.c_homegrid_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.c_homescene_gridViewItem_image.setImageResource(testIconId[position]);
        hodler.c_homescene_gridViewItem_textView.setText(testTitle[position]);
        hodler.c_homescene_gridViewItem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()){
                    v.setSelected(false);
                }
                else {
                    v.setSelected(true);
                }
            }
        });
        return convertView;
    }

    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.c_homescene_gridViewItem_image)RoundImageView c_homescene_gridViewItem_image;
        @BindView(R.id.c_homescene_gridViewItem_textView)TextView c_homescene_gridViewItem_textView;
        @BindView(R.id.c_homescene_gridViewItem_layout)RelativeLayout c_homescene_gridViewItem_layout;
    }
}
