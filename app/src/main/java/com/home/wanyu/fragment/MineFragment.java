package com.home.wanyu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CircleGiveYouCommentActivity;
import com.home.wanyu.activity.MyAboutUsActivity;
import com.home.wanyu.activity.MyCircleContactActivity;
import com.home.wanyu.activity.MyContactUs;
import com.home.wanyu.activity.MyHouseActivity;
import com.home.wanyu.activity.MySettingActivity;
import com.home.wanyu.activity.MyUserMsgEditorActivity;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.bean.Bean_UnReadMsg;
import com.home.wanyu.bean.Bean_UserInfo;
import com.home.wanyu.lzhUtils.MyCirleView;
import com.home.wanyu.lzhUtils.MyImageView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//个人
public class MineFragment extends Fragment{
    private Unbinder unbinder;
    @BindView(R.id.fragment_mine_image_settings)ImageView fragment_mine_image_settings;//设置的image
    @BindView(R.id.fragment_mine_image_msg)MyImageView fragment_mine_image_msg;//消息的iamge
    @BindView(R.id.fragment_mine_image_userEditor)RelativeLayout fragment_mine_image_userEditor;//用户信息的layout
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

    private okhttpTools tools;
    private String resStr;
    Bean_UserInfo.PersonalBean personal;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(getActivity(), ToastType.FAILD);
                    break;
                case 1:
                    try {
                        Bean_UserInfo userInfo = mGson.gson.fromJson(tools.mResponStr, Bean_UserInfo.class);
                        if (userInfo != null) {
                            if ("0".equals(userInfo.getCode())) {
                                personal = userInfo.getPersonal();
                                fragment_mine_image_username.setText(personal.getUserName() == null ? "未填写" : personal.getUserName());
                                int gender = personal.getGender();
                                if (gender == 1) {//男
                                    fragment_mine_image_usersex.setSelected(true);
                                } else if (gender == 2) {//女
                                    fragment_mine_image_usersex.setSelected(false);
                                }
                                Picasso.with(getActivity()).load(Ip.imagePath+personal.getAvatar()).error(R.mipmap.loadinge).placeholder(R.mipmap.loadinge).into(fragment_mine_image_userphoto);
                            }
                            else {
                                mToast.Toast(getActivity(), userInfo.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(getActivity(),ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(getActivity(), ToastType.GSONFAILD);
                    }
                    break;
                case 2://获取有无未读消息
                    try{
                        Bean_UnReadMsg m=mGson.gson.fromJson(resStr,Bean_UnReadMsg.class);
                        if (m!=null){
                            if ("0".equals(m.getCode())){
                                if (m.isHasMessage()){
                                    fragment_mine_image_msg.setRead(true);
                                }
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_mine,null);
        unbinder= ButterKnife.bind(this,vi);
        tools=new okhttpTools();
        getUserMsg();
        return vi;
    }
    @OnClick({R.id.fragment_mine_image_userEditor,R.id.fragment_mine_layout_myhome,R.id.fragment_mine_layout_mycircle
    ,R.id.fragment_mine_image_settings,R.id.fragment_mine_layout_about,R.id.fragment_mine_layout_contacts,R.id.fragment_mine_image_msg})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.fragment_mine_image_userEditor://用户信息编辑
                Intent intent=new Intent();
                intent.setClass(getActivity(),MyUserMsgEditorActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",personal);
                intent.putExtra("data",bundle);
                startActivityForResult(intent,110);
                break;
            case R.id.fragment_mine_layout_myhome://我的家
                startActivity(new Intent(getActivity(), MyHouseActivity.class));
                break;
            case R.id.fragment_mine_layout_mycircle://我的圈子
                startActivity(new Intent(getActivity(), MyCircleContactActivity.class));
                break;
            case R.id.fragment_mine_image_settings://设置
                startActivity(new Intent(getActivity(), MySettingActivity.class));
                break;
            case R.id.fragment_mine_layout_about://关于
                startActivity(new Intent(getActivity(), MyAboutUsActivity.class));
                break;
            case R.id.fragment_mine_layout_contacts://意见反馈
                startActivity(new Intent(getActivity(), MyContactUs.class));
                break;
            case R.id.fragment_mine_image_msg://消息
                startActivity(new Intent(getActivity(), CircleGiveYouCommentActivity.class));
                break;
        }
    }
    //http://192.168.1.55:8080/smarthome/mobileapi/personal/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取用户个人信息
    public void getUserMsg(){
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        tools.getServerData(handler,1,Ip.serverPath+Ip.interface_getUserInfo,mp, "获取个人信息---");
    }

    @Override
    public void onStart() {
        super.onStart();
        fragment_mine_image_msg.setRead(false);
        getUnReadMsg();//获取有无未读消息
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==110){
            if (resultCode==200){
//                Intent intent=new Intent();
//                intent.putExtra("fresh","0");
                if (data!=null){
                    if ("0".equals(data.getStringExtra("fresh"))){
                        getUserMsg();
                    }
                }
            }
        }
    }

    //http://192.168.1.55:8080/smarthome/mobileapi/message/hasmessage.do?msgType=&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
    public void getUnReadMsg() {
      Map<String,String> mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        okhttp.getCall(Ip.serverPath+Ip.interface_MessageisRead,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取有无未读消息--",resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }
}
