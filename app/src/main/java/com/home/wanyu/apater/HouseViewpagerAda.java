package com.home.wanyu.apater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/22.
 */

public class HouseViewpagerAda extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> list = new ArrayList<>();
    private View view;
    private ImageView imageView;

    public HouseViewpagerAda(List<Integer> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = mInflater.inflate(R.layout.house_viewpager_item, null);
        imageView = (ImageView) view.findViewById(R.id.viewpager_img);
        Picasso.with(mContext).load(list.get(position)).resize(ImgUitls.getWith(mContext)/3,ImgUitls.getWith(mContext)/3).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
