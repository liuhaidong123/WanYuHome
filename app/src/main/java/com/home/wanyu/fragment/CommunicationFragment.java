package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CarPoolingActivity;
import com.home.wanyu.activity.CircleMessageActivity;
import com.home.wanyu.activity.CommunityMessageActivity;
import com.home.wanyu.apater.CircleAdapter;
import com.home.wanyu.bean.CircleBean;
import com.home.wanyu.bean.getCircleArea.Result;
import com.home.wanyu.bean.getCircleArea.Root;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */

//圈子
public class CommunicationFragment extends Fragment {
    private ListView mListview;
    private CircleAdapter mAdapter;
    private List<CircleBean> mList = new ArrayList<>();
    private List<Result> mCircleAreaList;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 110) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mCircleAreaList = root.getResult();

                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_communication, null);
        mHttptools=HttpTools.getHttpToolsInstance();
        mHttptools.getCircleArea(mHandler, UserInfo.userToken);
        initView(vi);
        return vi;
    }

    public void initView(View view) {
        mList.add(new CircleBean(R.mipmap.circle_friend, "友邻圈", 1));
        mList.add(new CircleBean(R.mipmap.circle_area_activity, "社区活动", 2));
        mList.add(new CircleBean(R.mipmap.circle_area_car, "社区拼车", 3));
        mAdapter = new CircleAdapter(mList, this.getActivity());
        mListview = (ListView) view.findViewById(R.id.circle_listview);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCircleAreaList != null && mCircleAreaList.size() == 0) {
                    Toast.makeText(getContext(), "请添加小区地址", Toast.LENGTH_SHORT).show();
                    //跳转到添加小区页面

                } else {
                    //友邻圈
                    if (position == 0) {
                        Intent intent = new Intent(getActivity(), CircleMessageActivity.class);
                        startActivity(intent);
                    } else if (position == 1) {//社区活动
                        Intent intent = new Intent(getActivity(), CommunityMessageActivity.class);
                        startActivity(intent);
                    } else {//社区拼车
                        Intent intent = new Intent(getActivity(), CarPoolingActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
