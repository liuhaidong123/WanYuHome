package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CircleYouMsgAda;
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
    private int limit=1;

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
        //获取消息
        switch (type){
            case -1://获取全部消息
                getAllMessage(start,limit);
                break;
        }
    }

    private void initView() {
        listempty= (RelativeLayout) findViewById(R.id.listempty);
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
        mListview.setEmptyView(listempty);
        many_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadingState(1);
                getSerVerData();
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

    //获取消息http://192.168.1.55:8080/smarthome/mobileapi/message/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    分页查询消息列表接口
//    http://192.168.1.55:8080/smarthome/mobileapi/message/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&msgType=
//    Method:GET
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |start        |Integer  |N    |分页开始加载记录索引，从0开始
//    |limit        |Integer  |N    |每页多少条数据
//    |msgType      |Integer  |N    |消息类型；0=公告，10=分享钥匙，11-30友邻圈消息，11=状态点赞，12=状态评论，31-50为社区活动消息，
// 31=活动照片，32=活动评论，33=活动感兴趣，34=参加活动，51-70为拼车消息，51=拼车评论，52=司机接单，53=乘客加入，不传或传空就是查所有的
//    |msgTypeBegin |Integer  |N    |消息类型开始数字（查询大于等于此参数的记录）
//            |msgTypeEnd   |Integer  |N    |消息类型结束数字（查询小于等于此参数的记录），开始和结束两个参数配合使用查询一个范围区间
    public void getAllMessage(int st,int limi) {
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
}
