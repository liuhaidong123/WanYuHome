package com.home.wanyu.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CarPoolingActivity;
import com.home.wanyu.activity.CircleMessageActivity;
import com.home.wanyu.activity.CommunityMessageActivity;
import com.home.wanyu.activity.MyHouseInfoActivity;
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
    private TextView mStatus_view;
    private ListView mListview;
    private CircleAdapter mAdapter;
    private List<CircleBean> mList = new ArrayList<>();
    private List<Result> mCircleAreaList;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//获取小区地址
            super.handleMessage(msg);
            if (msg.what == 110) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mCircleAreaList = new ArrayList<>();
                        mCircleAreaList = root.getResult();
                    }

                }
            }
        }
    };

    private int statusBarHeights;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeights = getResources().getDimensionPixelSize(resourceId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_communication, null);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView(vi);
        return vi;
    }

    public void initView(View view) {
        mStatus_view = (TextView) view.findViewById(R.id.status_view);
        //设置状态栏高度
        ViewGroup.LayoutParams params = mStatus_view.getLayoutParams();
        params.height = statusBarHeights;
        mStatus_view.setLayoutParams(params);
        Log.e("状态栏高度", statusBarHeights + "");
        mList.add(new CircleBean(R.mipmap.circle1, "友邻圈", 1));
        mList.add(new CircleBean(R.mipmap.circle2, "社区活动", 2));
        mList.add(new CircleBean(R.mipmap.circle3, "社区拼车", 3));
        mAdapter = new CircleAdapter(mList, this.getActivity());
        mListview = (ListView) view.findViewById(R.id.circle_listview);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCircleAreaList == null) {

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

                } else {

                    if (mCircleAreaList.size() == 0) {
                        Toast.makeText(getContext(), "请添加小区地址", Toast.LENGTH_SHORT).show();
                        //跳转到添加小区页面
                        Intent intent = new Intent(getContext(), MyHouseInfoActivity.class);
                        startActivity(intent);
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
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("圈子onResume", "==");
        mHttptools.getCircleArea(mHandler, UserInfo.userToken);//友邻圈获取小区接口
    }


}
