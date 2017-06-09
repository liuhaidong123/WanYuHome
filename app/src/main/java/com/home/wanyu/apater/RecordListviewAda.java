package com.home.wanyu.apater;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.RecordCancleFinish.Root;
import com.home.wanyu.bean.RecordMsg.Result;
import com.home.wanyu.myview.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/5/4.
 */

public class RecordListviewAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    private ImgViewPager mAdapter;
    private RelativeLayout mViewpaget_rl;
    private  RelativeLayout m_All_rl;
    private  RelativeLayout mMore_rl;
    private List<Result> list = new ArrayList<>();
    private HttpTools httpTools;
    private int mState = -1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 108) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
                        httpTools.getRecordMsg(handler, UserInfo.userToken, mState, 0, 10);
                    } else {
                        Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 107) {//操作成功以后，重新获取报修记录列表
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.RecordMsg.Root) {
                    com.home.wanyu.bean.RecordMsg.Root root = (com.home.wanyu.bean.RecordMsg.Root) o;
                    if (root.getResult()!=null){
                        list = root.getResult();
                        notifyDataSetChanged();
                        if (root.getResult().size()<10){
                            mMore_rl.setVisibility(View.GONE);
                        }else {
                            mMore_rl.setVisibility(View.VISIBLE);
                        }
                    }

                }
            } else if (msg.what == 201) {//后台数据有问题
                list.clear();
                notifyDataSetChanged();
                Toast.makeText(mContext, "数据错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public RecordListviewAda(Context mContext, List<Result> list, ViewPager viewPager, RelativeLayout mviewpaget_rl,RelativeLayout m_all_rl,RelativeLayout mMore_rl) {
        this.mContext = mContext;
        this.list = list;
        this.mViewPager=viewPager;
        this.mViewpaget_rl=mviewpaget_rl;
        this.m_All_rl=m_all_rl;
        this.mMore_rl=mMore_rl;
        this.mInflater = LayoutInflater.from(this.mContext);
        httpTools = HttpTools.getHttpToolsInstance();
    }

    public void setList(List<Result> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = null;
        if (convertView == null) {
            holder = new ListViewHolder();
            convertView = mInflater.inflate(R.layout.record_listview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.record_img);
            holder.tv_category = (TextView) convertView.findViewById(R.id.record_categoty);
            holder.tv_time = (TextView) convertView.findViewById(R.id.record_time);
            holder.tv_state = (TextView) convertView.findViewById(R.id.record_state);
            holder.tv_msg = (TextView) convertView.findViewById(R.id.record_message);
            holder.gridView = (MyGridView) convertView.findViewById(R.id.record_gridview);
            holder.foot_rl = (RelativeLayout) convertView.findViewById(R.id.foot_rl);
            holder.tv_cancle = (TextView) convertView.findViewById(R.id.cancle_btn);
            holder.tv_finish = (TextView) convertView.findViewById(R.id.finish_btn);

            convertView.setTag(holder);

        } else {
            holder = (ListViewHolder) convertView.getTag();
        }

        if (list.get(position).getRepairType() == 1) {//水电燃气

            Picasso.with(mContext).load(R.mipmap.repair_water).into(holder.imageView);
            holder.tv_category.setText("水电燃气报修");

        } else if (list.get(position).getRepairType() == 2) {//房屋报修
            Picasso.with(mContext).load(R.mipmap.repair_house).into(holder.imageView);
            holder.tv_category.setText("房屋报修");
        } else if (list.get(position).getRepairType() == 3) {//公共设施
            Picasso.with(mContext).load(R.mipmap.repair_tree).into(holder.imageView);
            holder.tv_category.setText("公共设施报修");
        }

        //状态
        if (list.get(position).getState() == 1) {
            mState = 1;
            holder.tv_state.setText("待处理");
            holder.foot_rl.setVisibility(View.VISIBLE);
            holder.tv_finish.setVisibility(View.VISIBLE);
            holder.tv_cancle.setVisibility(View.VISIBLE);
            holder.tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpTools.recordCancleFinish(handler, UserInfo.userToken, 4, list.get(position).getId());
                }
            });

            holder.tv_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpTools.recordCancleFinish(handler, UserInfo.userToken, 3, list.get(position).getId());
                }
            });

        } else if (list.get(position).getState() == 2) {
            mState = 2;
            holder.tv_state.setText("处理中");
            holder.foot_rl.setVisibility(View.VISIBLE);
            holder.tv_finish.setVisibility(View.VISIBLE);
            holder.tv_cancle.setVisibility(View.GONE);
            holder.tv_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpTools.recordCancleFinish(handler, UserInfo.userToken, 3, list.get(position).getId());
                }
            });
        } else if (list.get(position).getState() == 3) {
            mState = 3;
            holder.tv_state.setText("已完成");
            holder.foot_rl.setVisibility(View.GONE);
        } else if (list.get(position).getState() == 4) {
            mState = 4;
            holder.tv_state.setText("已取消");
            holder.foot_rl.setVisibility(View.GONE);
        }

        holder.tv_time.setText("期望处理时间:" + list.get(position).getProcessingTimeString());
        holder.tv_msg.setText(list.get(position).getDetails());

        if (!list.get(position).getPicture().equals("")) {
            final String[] imgstr = list.get(position).getPicture().split(";");
            Log.e("图片数组长度", imgstr.length + "");
            Log.e("第一个图片", imgstr[0] + "");
            holder.gridView.setAdapter(new RecordListviewGridviewAda(mContext, imgstr));
            //点击显示图片
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // m_All_rl.setVisibility(View.GONE);
                    mViewpaget_rl.setVisibility(View.VISIBLE);

                    mAdapter=new ImgViewPager(mContext,imgstr);
                    mViewPager.setAdapter(mAdapter);
                    mViewPager.setCurrentItem(position);
                }
            });

        } else {
            String[] imgstr = new String[0];
            holder.gridView.setAdapter(new RecordListviewGridviewAda(mContext, imgstr));
        }

        return convertView;
    }

    class ListViewHolder {
        ImageView imageView;
        TextView tv_category;
        TextView tv_time;
        TextView tv_state;
        TextView tv_msg;
        MyGridView gridView;
        RelativeLayout foot_rl;
        TextView tv_cancle;
        TextView tv_finish;
    }

}
