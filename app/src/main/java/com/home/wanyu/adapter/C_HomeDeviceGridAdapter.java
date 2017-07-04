package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.lzhView.SwitchButton;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/3.
 */

public class C_HomeDeviceGridAdapter extends BaseAdapter{

    int[]testIconId={R.mipmap.tvaq,R.mipmap.airconditiona,R.mipmap.lighta,R.mipmap.curtaina,R.mipmap.sounda,R.mipmap.socketa
    ,R.mipmap.tvaq,R.mipmap.airconditiona,R.mipmap.sounda,R.mipmap.socketa};//测试用(起床，休息，离家，回家，打游戏)
    String[]testTitle={"电视","空调","灯","窗帘","音响","插座"
    ,"电视","空调","音响","插座"};
    Context context;
    public C_HomeDeviceGridAdapter( Context context){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.c_homedevice_grid_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.c_homescene_gridViewItem_image.setImageResource(testIconId[position]);
        hodler.c_homescene_gridViewItem_textView.setText(testTitle[position]);
        hodler.c_homescene_gridViewItem_switch.setTag(hodler.c_homescene_gridViewItem_layout);
        //控制设备的开启／关闭
        hodler.c_homescene_gridViewItem_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
            @Override
            public void change(Boolean sta, String text, SwitchButton vi) {
                RelativeLayout rela= (RelativeLayout) vi.getTag();
                if (sta){
                    rela.setSelected(true);
                }
                else {
                    rela.setSelected(false);
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
        @BindView(R.id.c_homescene_gridViewItem_switch)SwitchButton c_homescene_gridViewItem_switch;//设备开关按钮
    }
}
