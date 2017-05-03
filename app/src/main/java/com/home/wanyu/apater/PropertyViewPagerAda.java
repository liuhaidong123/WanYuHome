package com.home.wanyu.apater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/2.
 */

public class PropertyViewPagerAda extends PagerAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mListImgAd = new ArrayList<>();//广告图片集合

    public PropertyViewPagerAda(Context mContext, List<Integer> list) {
        this.mContext = mContext;
        this.mListImgAd = list;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmListImgAd(List<Integer> mListImgAd) {
        this.mListImgAd = mListImgAd;
    }

    @Override
    public int getCount() {
        return mListImgAd.size() == 1 ? 1 : mListImgAd.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.property_ad_item, null);
        ImageView img = ((ImageView) view.findViewById(R.id.ad_img));
        //Picasso.with(mContext).load(UrlTools.BASE + mListImgAd.get(FirstPageFragment.mSelectPosition).getPicture()).error(R.mipmap.error_big).into(img);
        Picasso.with(mContext).load(mListImgAd.get(position)).into(img);
        //点击轮播图片跳转
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListImgAd != null && mListImgAd.size() > 0) {
//                    Intent intent = new Intent(mContext, InformationDetailsActivity.class);
//                    intent.putExtra("type", "ad");
//                    Log.e("mSelectPosition", FirstPageFragment.mSelectPosition + "");
//                    intent.putExtra("id", mListImgAd.get(FirstPageFragment.mSelectPosition).getId());
//                    mContext.startActivity(intent);
//                }
//            }
//        });
        ((ViewPager) container).addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);

    }
}
