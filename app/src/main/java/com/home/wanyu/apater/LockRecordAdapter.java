package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_lockRecord;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/23.
 */

public class LockRecordAdapter extends BaseAdapter{
    private Context context;
    List<Bean_lockRecord.RowsBean> li;
    public LockRecordAdapter(Context context,List<Bean_lockRecord.RowsBean>li){
        this.context=context;
        this.li=li;
    }
    @Override
    public int getCount() {
        return li==null?0:li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.lockrecord_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        Picasso.with(context).load(Ip.imagePath+li.get(position).getAvatar()).error(R.mipmap.errorphoto).into(hodler.record_item_userImage);
        hodler.record_item_userName.setText(li.get(position).getUserName());
        hodler.record_item_userNickName.setText(li.get(position).getUserType()+"");
        hodler.record_item_recoreTime.setText(li.get(position).getCreateTimeString());
        return convertView;
    }


    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.record_item_userImage)RoundImageView record_item_userImage;//头像
        @BindView(R.id.record_item_userName)TextView record_item_userName;//姓名
        @BindView(R.id.record_item_userNickName)TextView record_item_userNickName;//称为
        @BindView(R.id.record_item_recoreTime)TextView record_item_recoreTime;//开锁时间
    }
}
