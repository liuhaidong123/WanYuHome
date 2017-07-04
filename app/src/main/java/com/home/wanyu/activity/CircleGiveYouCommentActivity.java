package com.home.wanyu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CircleYouMsgAda;
import com.home.wanyu.bean.Bean_M;
import com.home.wanyu.bean.Bean_Message;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//消息列表
public class CircleGiveYouCommentActivity extends MyActivity {
    private ImageView mBack;
    private MyListView mListview;
    private CircleYouMsgAda mAdpater;
    private int type=0;//-1全部类型的消息,0友邻圈，1社区活动，2社区拼车

    //加载更多的view
    RelativeLayout many_relative;
    TextView footer_tv;
    ProgressBar pbLocate;

    RelativeLayout listempty;
    private int start=0;
    private int limit=15;
    private PopupWindow pop;
    private int deletePos;
    private boolean isDelete=false;
    private List<Bean_Message.RowsBean> listMessage;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.Toast(con,"网络异常");
                    setLoadingState(0);
                    break;
                case 1:
                    setLoadingState(0);
                    try{
                        Bean_Message message= mGson.gson.fromJson(resStr,Bean_Message.class);
                        if (message!=null){
                            List<Bean_Message.RowsBean>li=message.getRows();
                            if (li!=null&&li.size()>0){
                                start+=li.size();
                                listMessage.addAll(li);
                                mAdpater.notifyDataSetChanged();
                                if (li.size()!=limit){
                                    many_relative.setVisibility(View.GONE);
                                }
                                else {
                                    many_relative.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
                                many_relative.setVisibility(View.GONE);
                            }

                        }
                        else {
                            many_relative.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        many_relative.setVisibility(View.GONE);
                    }
                    mListview.setEmptyView(listempty);
                    break;
                case 2://删除消息
                    try{
                        Bean_M b=mGson.gson.fromJson(resStr,Bean_M.class);
                        if (b!=null){
                            if ("0".equals(b.getCode())){
                                listMessage.remove(deletePos);
                                mAdpater.notifyDataSetChanged();
                                if (mAdpater!=null&&mAdpater.getCount()==0){
                                    many_relative.setVisibility(View.GONE);
                                }

                            }
                            else {
                                mToast.Toast(con,b.getMessage());
                            }

                        }
                        else {
                            mToast.ToastFaild(con, ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 3://消息已读设置
                    try{
                        Bean_M b=mGson.gson.fromJson(resStr,Bean_M.class);
                        if (b!=null){
                            if ("0".equals(b.getCode())){
                              Log.i("消息已读设置成功----","success");
                            }
                            else {
                                mToast.Toast(con,b.getMessage());
                                }
                        }
                        else {
                            mToast.ToastFaild(con, ToastType.GSONEMPTY);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_give_you_comment);
        initView();
        type=getIntent().getIntExtra("type",-1);
        getSerVerData();
    }
    @Override
    public void getSerVerData() {
        getAllMessage(start,limit);
    }

    private void initView() {
        listempty= (RelativeLayout) findViewById(R.id.listempty);
        listempty.setVisibility(View.GONE);
        listMessage=new ArrayList<>();
        many_relative= (RelativeLayout) findViewById(R.id.many_relative);
        many_relative.setVisibility(View.GONE);
        footer_tv= (TextView) findViewById(R.id.footer_tv);
        pbLocate= (ProgressBar) findViewById(R.id.pbLocate);
        pbLocate.setVisibility(View.GONE);
        mBack= (ImageView) findViewById(R.id.circle_you_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListview= (MyListView) findViewById(R.id.you_msg_listview);
        mAdpater=new CircleYouMsgAda(this,listMessage);
        mListview.setAdapter(mAdpater);
        many_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadingState(1);
                getSerVerData();
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listMessage!=null&&listMessage.get(position).isIsRead()==false){
                        listMessage.get(position).setIsRead(true);
                        mAdpater.notifyDataSetChanged();
                        setReadMessage(position);//设置已读
                    }
//                |消息类型；0=公告，10=分享钥匙，11-30友邻圈消息，11=状态点赞，12=状态评论，31-50为社区活动消息，
//                31=活动照片，32=活动评论，33=活动感兴趣，34=参加活动，51-70为拼车消息，51=拼车评论，
//                52=司机接单，53=乘客加入，不传或传空就是查所有的
                    int messageType=listMessage.get(position).getMsgType();
                    Intent intent=new Intent();
                    if (messageType>=11&&messageType<=30){//友邻圈消息
                        intent.setClass(con,CircleCardMessageActivity.class);
                        intent.putExtra("stateid",Long.parseLong(listMessage.get(position).getReferId()+""));
                        startActivity(intent);
                    }
                else if (messageType>=31&&messageType<=50){//社区活动
                        intent.setClass(con,CommunityCommentActivity.class);
                        intent.putExtra("activityId",Long.parseLong(listMessage.get(position).getReferId()+""));
                        startActivity(intent);
                    }
                else if(messageType>=51&&messageType<=70){//拼车消息
                        if (messageType==51){//拼车评论
                            intent.setClass(con,CarPoolingMsgActivity.class);
                            intent.putExtra("carpoolingId",Long.parseLong(listMessage.get(position).getReferId()+""));
                            startActivity(intent);
                        }
                        else {
                            intent.setClass(con,MessageCarpoolActivity.class);
                            startActivity(intent);
                        }
                    }
                else if (messageType==0){//公告
                        mToast.Toast(con,"公告");
                    }
                else if (messageType==10){//分享钥匙
                        mToast.Toast(con,"分享钥匙");
                    }
                else {
                        mToast.Toast(con,"消息类型未知");
                    }
//                MessageCarpoolActivity
            }
        });
        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    deletePos =position;
                    showWindow(deletePos);
                    return true;
            }
        });
    }
    //设置已读http://192.168.1.55:8080/smarthome/mobileapi/messageLog/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    |token          |String   |Y    |令牌
//    |messageId      |Long     |N    |消息表编号
    public void setReadMessage(int pos){
        Map<String,String>mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        mp.put("messageId",listMessage.get(pos).getId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_MessageRead,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                isDelete=false;
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                isDelete=false;
                resStr=response.body().string();
                Log.i("消息已读设置--"+deletePos,resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }
    //删除消息http://192.168.1.55:8080/smarthome/mobileapi/message/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    //Method:POST
    private void deleteMessage(int position) {
        if (isDelete){
            mToast.Toast(con,"正在操作，请稍后");
            return;
        }
        isDelete=true;
        Map<String,String>mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        mp.put("ids",listMessage.get(position).getId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_MessageDelete,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                isDelete=false;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                isDelete=false;
                resStr=response.body().string();
                Log.i("删除消息--"+deletePos,resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }


    public void setLoadingState(int state){
        switch (state){
            case 0:
                many_relative.setClickable(true);
                footer_tv.setText("加载更多");
                pbLocate.setVisibility(View.GONE);
                break;
            case 1:
                many_relative.setClickable(false);
                footer_tv.setText("正在加载");
                pbLocate.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void getAllMessage(int st,int limi) {
        //-1全部类型的消息,0友邻圈，1社区活动，2社区拼车
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("start",st+"");
        mp.put("limit",limi+"");
        switch (type){
            //-1全部类型的消息,0友邻圈，1社区活动，2社区拼车
            case -1:
//                mp.put("msgType","");
                break;
            case 0://友邻圈11-30
                mp.put("msgTypeBegin","11");
                mp.put("msgTypeEnd","30");
                break;
            case 1://社区活动31-50为社区活动消息
                mp.put("msgTypeBegin","31");
                mp.put("msgTypeEnd","50");
                break;
            case 2://51-70为拼车消息
                mp.put("msgTypeBegin","51");
                mp.put("msgTypeEnd","70");
                break;
            default://全部
//                mp.put("msgType","");
                break;
        }
        Log.i("ip---",Ip.serverPath+Ip.interface_get_Message);
        okhttp.getCall(Ip.serverPath+Ip.interface_get_Message,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取消息："+type,resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }
    //删除消息的view
    private void showWindow(int pos) {
        deletePos=pos;
        pop = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_device_delete, null);
        TextView pop_delete= (TextView) v.findViewById(R.id.pop_delete);
        pop_delete.setText("删除消息");
        TextView pop_cancle= (TextView) v.findViewById(R.id.pop_cancle);
        pop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessage(deletePos);
                pop.dismiss();
            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_circle_give_you_comment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(v);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup2_anim);
        pop.showAtLocation(parent, Gravity.CENTER,0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }
}
