
package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.MyCircleContactAdapter_a;
import com.home.wanyu.adapter.MyCircleContactAdapter_c;
import com.home.wanyu.bean.Bean_AC;
import com.home.wanyu.bean.Bean_QZ;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的圈子活动页面
public class MyCircleContactActivity extends MyActivity implements View.OnClickListener{
    private LinearLayout circle_myActivity_layout;//切换我的圈子，我的活动的layout
    private TextView circle_my_user_info_submmit;//编辑／完成的view
    private TextView circle_myActivity_title;//显示我的圈子，我的活动的view
    private String[]title={"我的圈子","我的活动"};
    private int SelectPos=0;//当前选择的项目（0我的圈子，1我的活动）
    public static int state=0;//0未编辑状态，1编辑状态
    private String[]Submit={"编辑","完成"};
    private Boolean isDeleteAll;
    @BindView(R.id.activity_my_circle_contact_listview)ListView activity_my_circle_contact_listview;//listview
    @BindView(R.id.activity_my_circle_contact_layout)RelativeLayout activity_my_circle_contact_layout;//删除的layout
    @BindView(R.id.activity_my_circle_contact_bottom_selectAll)ImageView activity_my_circle_contact_bottom_selectAll;//全选／取消全选的阿牛
    private List<Map<String,String>> listCircle;//圈子的数据源
    private List<Map<String,String>> listActi;//活动数据源
    @BindView(R.id.my_circle_progresslayout)RelativeLayout my_circle_progresslayout;//分业的layout
    @BindView(R.id.myText)TextView myText;//分野加载的textview
    @BindView(R.id.myProgress)ProgressBar myProgress;
    private boolean isLoading=false;//是否正在请求数据

    private int start=0;//圈子,活动
    private int limit=10;
    private String resStr;

    private List<Bean_QZ.RowsBean>listQZ;//圈子数据源
    private MyCircleContactAdapter_c adapter_QZ;//我的圈子的适配QI

    private List<Bean_AC.RowsBean>listAC;
    private MyCircleContactAdapter_a adpter_AC;//活动的适配器

    private boolean isShowLoading=true;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isDeleteAll=false;
                    ShowErrorView(DEFAULTRESID);
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1://查询我的圈子
                    int st=1;
                    try{
                        Bean_QZ qz= mGson.gson.fromJson(resStr,Bean_QZ.class);
                        List<Bean_QZ.RowsBean>li=qz.getRows();
                        if (li!=null&&li.size()>0){
                            if (li.size()==limit){
                                isShowLoading=true;
                                setLoadingState(0);
                                my_circle_progresslayout.setVisibility(View.VISIBLE);//显示加载更多
                            }
                            else {
                                isShowLoading=false;
                                isLoading=false;
                                my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                                }
                            listQZ.addAll(li);
                            adapter_QZ.notifyDataSetChanged();
                            start+=li.size();
                        }
                        else {
                            isShowLoading=false;
                            isLoading=false;
                            my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        }
                    }
                    catch (Exception e){
                        isShowLoading=false;
                        isLoading=false;
                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                        e.printStackTrace();
                    }
                    if (listQZ!=null&&listQZ.size()>0){
                        ShowChildView(DEFAULTRESID);
                      return;
                     }
                    ShowEmptyView(DEFAULTRESID);
                    break;
                case 2://删除圈子
                    Log.i("删除圈子成功--",resStr);
//                    adapter_QZ.notifyDataSetChanged();
                    List<Bean_QZ.RowsBean>lt=new ArrayList<>();
                    for (int i=0;i<listQZ.size();i++){
                        if (listQZ.get(i).isSele()){
                            lt.add(listQZ.get(i));
                        }
                    }
                    if (lt!=null&&lt.size()>0){
                        listQZ.removeAll(lt);
                        if (isDeleteAll){
                            state=0;//编辑状态
                            circle_my_user_info_submmit.setText(Submit[state]);
                            activity_my_circle_contact_layout.setVisibility(View.VISIBLE);
                        }
                        adapter_QZ.notifyDataSetChanged();
                    }
                    if (listQZ!=null&& listQZ.size()>0){
                        ShowChildView(DEFAULTRESID);
                        return;
                    }
                    ShowEmptyView(DEFAULTRESID);
                    break;
                case 3://获取我的活动
                    try{
                        Bean_AC ac= mGson.gson.fromJson(resStr,Bean_AC.class);
                        List<Bean_AC.RowsBean>li=ac.getRows();
                        if (li!=null&&li.size()>0){
                            if (li.size()==limit){
                                isShowLoading=true;
                                setLoadingState(0);
                                my_circle_progresslayout.setVisibility(View.VISIBLE);//显示加载更多
                            }
                            else {
                                isShowLoading=false;
                                isLoading=false;
                                my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                            }
                            listAC.addAll(li);
                            adpter_AC.notifyDataSetChanged();
                            start+=li.size();
                        }
                        else {
                            isShowLoading=false;
                            isLoading=false;
                            my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        }

                    }
                    catch (Exception e){
                        isShowLoading=false;
                        isLoading=false;
                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                        e.printStackTrace();
                    }
                    if (listAC!=null&&listAC.size()>0){
                        ShowChildView(DEFAULTRESID);
                        return;
                    }
                    ShowEmptyView(DEFAULTRESID);
                    break;
                case 4://删除活动
                    Log.i("删除活动成功--",resStr);
                    List<Bean_AC.RowsBean>lit=new ArrayList<>();
                    for (int i=0;i<listAC.size();i++){
                        if (listAC.get(i).isSele()){
                            lit.add(listAC.get(i));
                        }
                    }
                    if (lit!=null&&lit.size()>0){
                        listAC.removeAll(lit);
                        if (isDeleteAll){
                            state=0;//编辑状态
                            circle_my_user_info_submmit.setText(Submit[state]);
                            activity_my_circle_contact_layout.setVisibility(View.VISIBLE);
                        }
                        adpter_AC.notifyDataSetChanged();
                        }
                    if (listAC!=null&& listAC.size()>0){
                        ShowChildView(DEFAULTRESID);
                        return;
                    }
                    ShowEmptyView(DEFAULTRESID);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.circlecontact_title);
        initTitle();
        initChildView(R.layout.activity_my_circle_contact);
        initEmptyViewSetting("您还没有发布任何状态哦！",R.mipmap.nostatus);
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        activity_my_circle_contact_layout.setVisibility(View.GONE);//隐藏删除的layout
        initData();//加数据
        getQZdata(start,limit);
    }
@OnClick({R.id.activity_my_circle_contact_bottom_selectAll,R.id.activity_my_circle_contact_bottom_delete,R.id.my_circle_progresslayout})
public void cli(View vi){
    switch (vi.getId()){
        case R.id.activity_my_circle_contact_bottom_selectAll://全选／取消全选按钮
            switch (SelectPos){
                case 0://我的圈子
                    boolean sel=activity_my_circle_contact_bottom_selectAll.isSelected();
                    if (listQZ!=null&&listQZ.size()>0){
                        for (int i=0;i<listQZ.size();i++){
                                listQZ.get(i).setSele(!sel);
                        }
                        adapter_QZ.notifyDataSetChanged();
                    }
                    activity_my_circle_contact_bottom_selectAll.setSelected(!sel);
                    break;
                case 1://wode 活动
                    boolean sele=activity_my_circle_contact_bottom_selectAll.isSelected();
                    if (listAC!=null&&listAC.size()>0){
                        for (int i=0;i<listAC.size();i++){
                            listAC.get(i).setSele(!sele);
                        }
                        adpter_AC.notifyDataSetChanged();
                    }
                    activity_my_circle_contact_bottom_selectAll.setSelected(!sele);
                    break;
                }
            break;
        case R.id.activity_my_circle_contact_bottom_delete://删除按钮
            switch (SelectPos){
                case 0://我的圈子
                    deleteQZdata();//删除圈子
                    break;
                case 1://我的活动
                    deleteACdata();
                    break;
            }
            break;
        case R.id.my_circle_progresslayout:
            if (isLoading){
                return;
                    }
            switch (SelectPos){
                case 0://圈子
                    setLoadingState(1);//正在加载
                    getQZdata(start,limit);
                    break;
                case 1://活动
                    setLoadingState(1);//正在加载
                    getACdata(start,limit);
                    break;
            }
            break;
    }
}
    private void initData() {
        circle_my_user_info_submmit.setText(Submit[state]);
        listQZ=new ArrayList<>();
        adapter_QZ=new MyCircleContactAdapter_c(listQZ,con);
        start=0;
        activity_my_circle_contact_listview.setAdapter(adapter_QZ);
    }

    private void initTitle() {
        circle_myActivity_title= (TextView) findViewById(R.id.circle_myActivity_title);
        circle_myActivity_layout= (LinearLayout) findViewById(R.id.circle_myActivity_layout);
        circle_my_user_info_submmit= (TextView) findViewById(R.id.circle_my_user_info_submmit);
        circle_myActivity_title.setText(title[SelectPos]);
        circle_myActivity_layout.setOnClickListener(this);
        circle_my_user_info_submmit.setOnClickListener(this);
    }

    @Override
    public void getSerVerData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_myActivity_layout://切换的view
                switch (SelectPos){
                    case 0://0我的圈子
                        start=0;//重置分业start
                        SelectPos=1;//当前显示我的活动
                        circle_myActivity_title.setText(title[SelectPos]);


                        //设置适配器
                        listAC=new ArrayList<>();
                        adpter_AC=new MyCircleContactAdapter_a(listAC,con);
                        start=0;
                        activity_my_circle_contact_listview.setAdapter(adpter_AC);
                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        setLoadingState(0);//显示加载更多
                        getACdata(start,limit);//获取前10条数据

                        break;
                    case 1://1我的活动
                        start=0;//重置start
                        SelectPos=0;//当前显示我的圈子
                        circle_myActivity_title.setText(title[SelectPos]);
//                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
//                        setLoadingState(0);//显示加载更多
                        //设置适配器
                        listQZ=new ArrayList<>();
                        adapter_QZ=new MyCircleContactAdapter_c(listQZ,con);
                        start=0;
                        activity_my_circle_contact_listview.setAdapter(adapter_QZ);
                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        setLoadingState(0);//显示加载更多
                        getQZdata(start,limit);//获取前10条数据
                        break;
                }
                break;
            case R.id.circle_my_user_info_submmit://编辑／完成bianji
                switch (state){
                    case 0://0当前显示的是编辑
                        state=1;//编辑状态
                        circle_my_user_info_submmit.setText(Submit[state]);
                        activity_my_circle_contact_layout.setVisibility(View.VISIBLE);
                        switch (SelectPos){
                            case 0://我的圈子
                                adapter_QZ.notifyDataSetChanged();
                                break;
                            case 1://wode 活动
                                adpter_AC.notifyDataSetChanged();
                                break;
                        }
                        my_circle_progresslayout.setVisibility(View.GONE);//隐藏加载更多
                        break;
                    case 1://当前显示的是完成
                        activity_my_circle_contact_layout.setVisibility(View.GONE);//隐藏加载更多
                        state=0;
                        circle_my_user_info_submmit.setText(Submit[state]);
                        if (isShowLoading){
                            my_circle_progresslayout.setVisibility(View.VISIBLE);
                        }
                        switch (SelectPos){
                            case 0://我的圈子
                                adapter_QZ.notifyDataSetChanged();
                                break;
                            case 1://wode 活动
                                adpter_AC.notifyDataSetChanged();
                                break;
                        }
                        break;
                }
                break;
        }
    }
    //设置状态
    public void setLoadingState(int sta){
        switch (sta){
            case 0://显示加载更多
                isLoading=false;
                myProgress.setVisibility(View.GONE);
                myText.setText("加载更多");
                break;
            case 1:
                isLoading=true;
                myProgress.setVisibility(View.VISIBLE);
                myText.setText("正在加载。。。");
                break;
        }
    }
//获取我的圈子信息http://192.168.1.55:8080/smarthome/mobileapi/state/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |start        |Integer  |N    |分页开始加载记录索引，从0开始
//    |limit        |Integer  |N    |每页多少条数据
    public void getQZdata(int star,int limi){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("start",star+"");
        mp.put("limit",limi+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_getMyCircle,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取我的圈子--",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }
//    http://192.168.1.55:8080/smarthome/mobileapi/state/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |ids          |String   |Y    |发状态编号,可以传一个或多个编号，编号之间用英文半角的逗号“,”分隔。
    public void deleteQZdata(){
        isDeleteAll=true;
        if (listQZ!=null&&listQZ.size()>0){
            Map<String,String>mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
            String ids="";
            for (int i=0;i<listQZ.size();i++){
                    if (listQZ.get(i).isSele()){
                        ids+=(listQZ.get(i).getId()+",");
                    }
                else {
                        isDeleteAll=false;
                    }
            }
            if (!"".equals(ids)){
                Log.i("ids---",ids);
                mp.put("ids",ids);
                okhttp.getCall(Ip.serverPath+Ip.interface_deleteMyCircle,mp,okhttp.OK_POST).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("删除我的圈子--",resStr);
                        handler.sendEmptyMessage(2);handler.sendEmptyMessage(2);
                    }
                });
            }
            else {
                mToast.Toast(con,"您还没有选择要删除的圈子");
            }

        }
     else {
            Log.e("删除圈子--","deleteQZdata()-----圈子数据源为空或者数据源没有数据");
        }
    }
//http://192.168.1.55:8080/smarthome/mobileapi/activity/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//获取我的活动
    public void getACdata(int st,int limi){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", UserInfo.userToken);
        mp.put("start",st+"");
        mp.put("limit",limi+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_getMyActivity,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
                }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取我的活动--",resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }
//删除我的活动http://192.168.1.55:8080/smarthome/mobileapi/activity/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
//    参数列表:
//            |参数名        |类型      |必需  |描述
//    |-----        |----     |---- |----
//            |token        |String   |Y    |令牌
//    |ids          |String   |Y    |社区活动编号,可以传一个或多个编号，编号之间用英文半角的逗号“,”分
    public void deleteACdata(){
        isDeleteAll=true;
        if (listAC!=null&&listAC.size()>0){
            Map<String,String>mp=new HashMap<>();
            mp.put("token", UserInfo.userToken);
            String ids="";

            for (int i=0;i<listAC.size();i++){
                if (listAC.get(i).isSele()){
                    ids+=(listAC.get(i).getId()+",");
                }
                else {
                    isDeleteAll=false;
                }
            }
            if (!"".equals(ids)){
                Log.i("ids---",ids);
                mp.put("ids",ids);
                okhttp.getCall(Ip.serverPath+Ip.interface_deleteMyActivity,mp,okhttp.OK_POST).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("删除我的活动--",resStr);
                        handler.sendEmptyMessage(4);
                    }
                });
            }
            else {
                mToast.Toast(con,"您还没有选择要删除的圈子");
            }

        }
        else {
            Log.e("删除圈子--","deleteQZdata()-----圈子数据源为空或者数据源没有数据");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        state=0;
        circle_my_user_info_submmit.setText("编辑");
    }
}
