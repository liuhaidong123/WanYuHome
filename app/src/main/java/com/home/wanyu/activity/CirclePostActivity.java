package com.home.wanyu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.CirclePopListviewApa;
import com.home.wanyu.apater.CirclePostImgGridViewAda;

import java.util.ArrayList;
import java.util.List;

public class CirclePostActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    private CirclePostImgGridViewAda mImgAdapter;
    private GridView mGridview;
    private List<String> mImgList = new ArrayList<>();

    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;

    private PopupWindow mPop;
    private RelativeLayout mType;
    private TextView mType_tv;
    private View mViewPop;
    private ListView mPopListView;
    private CirclePopListviewApa mPopAdapter;
    private List<String> mPopList=new ArrayList<>();

    private ImageView mLookBtn;
    private boolean mLookBtn_flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_post);
        initView();
    }

    private void initView() {
        //返回
        mBack= (ImageView) findViewById(R.id.circle_post_back);
        mBack.setOnClickListener(this);


        //删除图片是的弹框
        builder = new AlertDialog.Builder(this);
        mAlert = builder.create();
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_box, null);
        mAlert.setView(mAlertView);
        mSure = (TextView) mAlertView.findViewById(R.id.sure);
        mCancle = (TextView) mAlertView.findViewById(R.id.cancle);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        //图片apapter
        mImgAdapter=new CirclePostImgGridViewAda(this,mImgList);
        mGridview= (GridView) findViewById(R.id.circle_post_gridview);
        mGridview.setAdapter(mImgAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImgList.size() == 0) {
                    //跳转相册或拍照
                    init();
                } else {
                    if (mImgList.size() == 4) {
                        if (position != mImgList.size()) {
                            mAlert.show();
                            mPosition = position;
                        } else {
                            Toast.makeText(CirclePostActivity.this, "亲，最多选择4张图片哦", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (position == mImgList.size()) {
                            //跳转相册或拍照
                            init();
                        } else {
                            mAlert.show();
                            mPosition = position;
                        }
                    }
                }
            }
        });

        //选择分类
        mType= (RelativeLayout) findViewById(R.id.circle_type_rl);
        mType_tv= (TextView) findViewById(R.id.circle_type_tv);
        mType.setOnClickListener(this);
        mViewPop=LayoutInflater.from(this).inflate(R.layout.circle_pop_item,null);
        mPopListView= (ListView) mViewPop.findViewById(R.id.pop_listview);
        mPopList.add("健康");
        mPopList.add("母婴");
        mPopList.add("居家");
        mPopList.add("旅游");
        mPopList.add("美食");
        mPopList.add("宠物");
        mPopAdapter=new CirclePopListviewApa(this,mPopList);
        mPopListView.setAdapter(mPopAdapter);
        mPopListView.setSelector(R.color.pop_list_bg);
        //选择类型
        mPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPop.dismiss();
                mType_tv.setText(mPopAdapter.getItem(position).toString());
            }
        });

        mLookBtn= (ImageView) findViewById(R.id.circle_look_btn);
        mLookBtn.setOnClickListener(this);
    }





    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;

    //权限判断
    public void init() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            //如果没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                ActivityCompat.requestPermissions(this,
                        //在这个数组中可以添加很多权限
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                startSelectImgActivity();
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity();
        }
    }

    //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startSelectImgActivity();
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //跳转到选择图片的页面
    public void startSelectImgActivity() {
        Intent intent = new Intent(this, SelectImgActivity.class);
        intent.putExtra("num",4);
        startActivityForResult(intent, 100);
    }

    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");
            if (mImgList.size() != 4) {
                for (int i = 0; i < list.size(); i++) {
                    mImgList.add(list.get(i));
                    if (mImgList.size() == 4) {
                        break;
                    }
                }
                mImgAdapter=new  CirclePostImgGridViewAda(this,mImgList);
                mGridview.setAdapter(mImgAdapter);
            }
        }
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }else if (id == mSure.getId()) {//确定删除图片
            mImgList.remove(mPosition);
            mImgAdapter.setmList(mImgList);
            mImgAdapter.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消
            mAlert.dismiss();
        }else if (id==mType.getId()){//选择分类
            showPopuWindow();
        }else if (id==mLookBtn.getId()){//其他小区可看

            if (!mLookBtn_flag){
                mLookBtn_flag=true;
                mLookBtn.setImageResource(R.mipmap.circle_button_on);
            }else {
                mLookBtn_flag=false;
                mLookBtn.setImageResource(R.mipmap.circle_button_off);
            }

        }
    }


     //分类中的popuwindow显示

    private void showPopuWindow(){
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);
        //popupwindow相对的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.circle_type_rl);
        mPop=new PopupWindow(mViewPop);
        //设置弹框的款，高
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setWidth(dip2px(130));
        mPop.setFocusable(true);//如果有交互需要设置焦点为true
        mPop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mPop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mPop.showAsDropDown(container, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     *
     * @param dpValue
     * @return
     *
     */
    public  int dip2px( float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
