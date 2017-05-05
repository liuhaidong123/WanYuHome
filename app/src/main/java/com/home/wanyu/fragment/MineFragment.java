package com.home.wanyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//个人
public class MineFragment extends Fragment{
    private Unbinder unbinder;
    @BindView(R.id.fragment_mine_image_settings)ImageView fragment_mine_image_settings;//设置的image
    @BindView(R.id.fragment_mine_image_msg)ImageView fragment_mine_image_msg;//消息的iamge

    @BindView(R.id.fragment_mine_image_userphoto)RoundImageView fragment_mine_image_userphoto;//用户头像的image
    @BindView(R.id.fragment_mine_image_username)TextView fragment_mine_image_username;//用户名字
    @BindView(R.id.fragment_mine_image_usersex)ImageView fragment_mine_image_usersex;//用户性别的imageview（select：false女，true男）
    @BindView(R.id.fragment_mine_image_userEdit) ImageView fragment_mine_image_userEdit;//用户信息修改的imageview

    @BindView(R.id.fragment_mine_layout_myhome) RelativeLayout fragment_mine_layout_myhome;//我的家的layout
    @BindView(R.id.fragment_mine_layout_mycircle) RelativeLayout fragment_mine_layout_mycircle;//我的圈子
    @BindView(R.id.fragment_mine_layout_myorder) RelativeLayout fragment_mine_layout_myorder;//我的订单
    @BindView(R.id.fragment_mine_layout_mystore) RelativeLayout fragment_mine_layout_mystore;//我的收藏

    @BindView(R.id.fragment_mine_layout_myaddress)RelativeLayout fragment_mine_layout_myaddress;//收货地址的view
    @BindView(R.id.fragment_mine_layout_about)RelativeLayout fragment_mine_layout_about;//关于宇家的view
    @BindView(R.id.fragment_mine_layout_contacts)RelativeLayout fragment_mine_layout_contacts;//意见反馈的view
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_mine,null);
        unbinder= ButterKnife.bind(this,vi);
        return vi;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
