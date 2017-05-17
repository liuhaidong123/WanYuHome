package com.home.wanyu.lzhUtils;

import android.app.Activity;

import android.content.Context;
import android.content.IntentFilter;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.R;

import butterknife.Unbinder;


/**
 * Created by wanyu on 2017/4/6.
 */

public  abstract  class MyActivity extends Activity {
    protected String TAG=getClass().getSimpleName();
    protected okhttpTools mTools;
    protected Context con=this;
    public Unbinder unbinder;
    protected TextView titleTextView;
    protected LinearLayout myactivity_lineralayout;//父容器，MyActivity唯一的布局
    protected View ChildView;
    private View errorView;//网络错误显示的view
    private View emptyView;//空白数据显示的view
    private View loadingView;//进入页面时请求数据加载的view
    private View titleView;//页面的标题
    public boolean isLoading=false;//当前是否正在请求数据


    public final int DEFAULTRESID=0;

    private final int emptyId= R.layout.emptyview;
    private final int errorId=R.layout.errorview;
    private final int loadingId=R.layout.loadingview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);
        initTitleView(R.layout.titleview);
        myactivity_lineralayout= (LinearLayout) findViewById(R.id.myactivity_lineralayout);
        initTitleView(R.layout.titleview);//初始化并添加titleview
    }



    //添加，移除标题
    public void addTitieView(){
        if (titleView!=null&&myactivity_lineralayout!=null){
                if (myactivity_lineralayout.getChildCount()!=0){
                    for (int i=0;i<myactivity_lineralayout.getChildCount();i++){
                        myactivity_lineralayout.removeViewAt(i);
                    }
                }
                myactivity_lineralayout.addView(titleView,0);
        }
    }
    public void removeTitleView(){
        if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>1){
            myactivity_lineralayout.removeViewAt(0);
        }
    }


    //添加，移除childview
    public void addChildView(){
            if (ChildView==null){
                Log.e("addChildView失败--","childview不能为空，请先调用initChildView完成初始化");
                return;
                }
            int con=myactivity_lineralayout.getChildCount();
            if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>0){
                if (myactivity_lineralayout.getChildCount()>1){
                    myactivity_lineralayout.removeViewAt(1);
                }
                myactivity_lineralayout.addView(ChildView,1);
                }
        else {
            Log.e("addChildView失败","--"+getClass().getSimpleName());
            }
    }
    public void removeChildView(){
        //0为标题所在的位置
        if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>1){
                Log.i("removeChild--",myactivity_lineralayout.getChildCount()+"---");
                myactivity_lineralayout.removeViewAt(1);
        }
        else {
            Log.e("removeChildView失败---","---"+getClass().getSimpleName());
        }
    }

    //添加，移除网络出错显示的view
    public void addErrorView(){
        if (errorView==null){
                Log.e("addErrorView失败---","errorView没有初始化，请先调用initErrorView初始化---");
                return;
        }
        int con=myactivity_lineralayout.getChildCount();
        if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>0){
            if (myactivity_lineralayout.getChildCount()>1){
                myactivity_lineralayout.removeViewAt(1);
            }
            myactivity_lineralayout.addView(errorView,1);
        }
        else {
            Log.e("addErrorView失败---","--"+getClass().getSimpleName());
            }
    }
    public void removeErrorView(){
        //0为标题所在的位置
        if (myactivity_lineralayout.getChildCount()>1){
                myactivity_lineralayout.removeViewAt(1);
        }
    }

    //添加，移除网络请求时显示的view
    public void addLoadingView(){
        if (loadingView==null){
            Log.e("addLoadingView失败---","loadingView没有初始化，请先调用initLoading初始化---");
            return;
        }
        if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>0){
            if (myactivity_lineralayout.getChildCount()>1){
                myactivity_lineralayout.removeViewAt(1);
            }
            myactivity_lineralayout.addView(loadingView,1);
        }
        else {
            Log.e("addLoadingView失败---","--"+getClass().getSimpleName());
        }
    }
    public void removeLoadingView(){
        //0为标题所在的位置
        if (myactivity_lineralayout.getChildCount()>1){
            myactivity_lineralayout.removeViewAt(1);
        }
    }


    //添加，移除网络请求时显示的view
    public void addEmptyView(){
        if (emptyView==null){
            Log.e("addEmptyView失败---","emptyView没有初始化，请先调用initEmptyView初始化---");
            return;
        }
        if (myactivity_lineralayout!=null&&myactivity_lineralayout.getChildCount()>0){
            if (myactivity_lineralayout.getChildCount()>1){
                myactivity_lineralayout.removeViewAt(1);
            }
            myactivity_lineralayout.addView(emptyView,1);
        }
        else {
            Log.e("addEmptyView失败---","--"+getClass().getSimpleName());
        }
    }
    public void removeEmptyView(){
        //0为标题所在的位置
        if (myactivity_lineralayout.getChildCount()>1){
            myactivity_lineralayout.removeViewAt(1);
        }
    }

    //---------------------------初始化--------------标题，正常状态，网络异常状态，数据为空状态，正在加载状态显示的view以及网络监听的Receiver-----------------------------------------
    //初始化标题(会调用addTitleView添加一个title)
    public void initTitleView(int resId){
        titleView=LayoutInflater.from(this).inflate(resId,null);
        titleTextView= (TextView) titleView.findViewById(R.id.myActivity_title);
        addTitieView();
    }

    //初始化有网状态下的view
    public void initChildView(int resId){
        ChildView=LayoutInflater.from(this).inflate(resId,null);
        ViewGroup.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ChildView.setLayoutParams(params);
    }

    //查询不到数据时显示到view
    public void initEmptyView(int resId){
        emptyView=LayoutInflater.from(this).inflate(resId,null);
        ViewGroup.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(params);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading){
                    return;
                }
                else {
                    isLoading=true;//当前正在请求数据,再次点击时不再继续向服务器发送请求数据的申请
                    getSerVerData();
                }
            }
        });
    }

    //初始化错误的view
    public void initErrorView(int resId){
        errorView=LayoutInflater.from(this).inflate(resId,null);
        ViewGroup.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        errorView.setLayoutParams(params);
        errorView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (isLoading){
                   return;
               }
               else {
                   isLoading=true;//当前正在请求数据,再次点击时不再继续向服务器发送请求数据的申请
                   getSerVerData();
               }
           }
       });
    }

    //初始化进入页面正在请求网络数据时的页面
    public void initLoadingView(int resId){
        loadingView=LayoutInflater.from(this).inflate(resId,null);
        ViewGroup.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingView.setLayoutParams(params);
    }

    //-----------------初始化-------------标题，正常状态，网络异常状态，数据为空状态，正在加载状态显示的view以及网络监听的Receiver------------------------------------------

    //页面初始化网络请求方法（进入页面要调用的方法）
    public abstract void getSerVerData();

    //设置页面标题
    public  void setTitle(String title){
        if (title!=null&&!"".equals(title)){
            titleTextView.setText(title);
        }
        else {
            titleTextView.setText("标题");
            titleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }
    //显示网络请求时的view
    public void ShowLoadView(int ResId){
        if (loadingView==null){
            if (ResId==DEFAULTRESID){
                ResId=loadingId;
            }
            initLoadingView(ResId);
        }
            addLoadingView();
    }
    //显示正常状态下的view
    public void ShowChildView(int ResId){
        if (ChildView==null){
            if (ResId==DEFAULTRESID){
                Log.e("MyActivity-","showChildView-error--resid==DEFAULTRESID,传入的Redid错误");
                return;
            }
            initChildView(ResId);
            }
            addChildView();
    }
    //显示没有数据的view
    public void ShowEmptyView(int ResId){
        if (emptyView==null){
            if (ResId==DEFAULTRESID){
                ResId=emptyId;
                }
            initEmptyView(ResId);
            }
            addEmptyView();
    }
    //显示错误的view(先初始化，再显示)
    public void ShowErrorView(int ResId){
        if (errorView==null){
            if (ResId==DEFAULTRESID){
                ResId=errorId;
            }
            initErrorView(ResId);
        }
        addErrorView();
    }

    public void TitleReturn(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
