package com.home.wanyu.apater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/6/7.
 */

public class ImgViewPager extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private View view;
    private ImageView imageView;
    private String[] strImg;

    public ImgViewPager(Context mContext, String[] strImg) {
        this.mContext = mContext;
        this.strImg = strImg;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return this.strImg.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = mInflater.inflate(R.layout.house_viewpager_item, null);
        imageView = (ImageView) view.findViewById(R.id.viewpager_img);
        Picasso.with(mContext).load(UrlTools.BASE+strImg[position]).resize(ImgUitls.getWith(mContext)/3,ImgUitls.getWith(mContext)/3).error(R.mipmap.error_big).into(imageView);
        container.addView(view);
        return view;
    }
}
