package com.home.wanyu.apater;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.home.wanyu.R;
import com.home.wanyu.myUtils.ImgUitls;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/3.
 */

public class RepairAddImgAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList = new ArrayList<>();

    public RepairAddImgAda(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 1 : mList.size() + 1;
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
        ImgHolder holder = null;
        if (convertView == null) {
            holder = new ImgHolder();
            convertView = mInflater.inflate(R.layout.repair_img_gridview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.add_img);
            convertView.setTag(holder);
        } else {
            holder = (ImgHolder) convertView.getTag();
        }

        if (mList.size() == 0) {
            Picasso.with(mContext).load(R.mipmap.add_img).into(holder.imageView);
        } else {

                if (position == mList.size()) {
                    Picasso.with(mContext).load(R.mipmap.add_img).into(holder.imageView);
                } else {

                  //  Log.e("12345","12344");
               it.sephiroth.android.library.picasso.Picasso.with(mContext).load(mList.get(position)).centerCrop().resize(ImgUitls.getWith(mContext) / 7,
                            ImgUitls.getWith(mContext) / 7).error(R.mipmap.error_small).into(holder.imageView);

                }

        }
        return convertView;
    }

    class ImgHolder {
        ImageView imageView;
    }

}
