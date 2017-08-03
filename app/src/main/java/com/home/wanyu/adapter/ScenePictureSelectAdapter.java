package com.home.wanyu.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.home.wanyu.Icons.SceneIcons;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_ScenePicture;
import com.home.wanyu.lzhUtils.WindowUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/25.
 */

public class ScenePictureSelectAdapter extends BaseAdapter{
    List<Bean_ScenePicture> list;
    Context con;
    public ScenePictureSelectAdapter(Context con, List<Bean_ScenePicture> list){
        this.con=con;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
            convertView= LayoutInflater.from(con).inflate(R.layout.scenepicture,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.sceneitem_image.setImageResource(list.get(position).getIcon());
        hodler.sceneitem_Layout.setSelected(false);
        if (list.get(position).isSelect()){
            hodler.sceneitem_Layout.setSelected(true);
        }
        ViewGroup.LayoutParams params=hodler.sceneitem_Layout.getLayoutParams();
        params.height= WindowUtils.getWinowWidth(con)/3;
        hodler.sceneitem_Layout.setLayoutParams(params);
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.sceneitem_Layout)RelativeLayout sceneitem_Layout;
        @BindView(R.id.sceneitem_image)  ImageView sceneitem_image;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
