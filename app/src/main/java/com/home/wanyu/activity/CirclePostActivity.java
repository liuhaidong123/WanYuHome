package com.home.wanyu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CirclePopAreaAda;
import com.home.wanyu.apater.CirclePopListviewApa;
import com.home.wanyu.apater.CirclePostImgGridViewAda;
import com.home.wanyu.apater.CircleSelectAreaAlertAda;
import com.home.wanyu.bean.getCircleArea.Result;
import com.home.wanyu.bean.getCircleArea.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myUtils.NetWorkMyUtils;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CirclePostActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private EditText mEdit_content;
    private TextView mSend_card;
    private CirclePostImgGridViewAda mImgAdapter;
    private GridView mGridview;
    private List<String> mImgList = new ArrayList<>();

    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;

    private long areaID = -1;
    private long typeID = -1;
    private PopupWindow mTypePop;
    private PopupWindow mAreaPop;
    private View mViewTypePop;
    private View mViewAreaPop;
    private ListView mTypeListView;
    private ListView mAreaListView;
    private List<Result> mCircleAreaList = new ArrayList<>();
    private List<com.home.wanyu.bean.getCircleTitleList.Result> mTitleList = new ArrayList<>();
    private CirclePopListviewApa mPopTypeAdapter;
    private CirclePopAreaAda mAreaAdapter;

    private ImageView mLookBtn;
    private int mLookBtn_flag = 1;

    private LinearLayout mSelect_type_ll, mSelect_area_ll;
    private TextView mType_tv1, mArea_tv;

    private HttpTools mHttptools;
    private AjaxParams ajaxParams;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 111) {//获取分类列表
                Object o = msg.obj;
                //默认全部
                if (o != null && o instanceof com.home.wanyu.bean.getCircleTitleList.Root) {
                    com.home.wanyu.bean.getCircleTitleList.Root root = (com.home.wanyu.bean.getCircleTitleList.Root) o;
                    mTitleList = root.getResult();
                    mTitleList.remove(0);
                    mPopTypeAdapter = new CirclePopListviewApa(CirclePostActivity.this, mTitleList);
                    mTypeListView.setAdapter(mPopTypeAdapter);
                }
            } else if (msg.what == 110) {//获取小区接口
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mCircleAreaList = root.getResult();
                        mAreaAdapter = new CirclePopAreaAda(CirclePostActivity.this, mCircleAreaList);
                        mAreaListView.setAdapter(mAreaAdapter);
                    }

                }
            } else if (msg.what == 114) {//发帖返回结果
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getCirclePostCardResult.Root) {
                    com.home.wanyu.bean.getCirclePostCardResult.Root root = (com.home.wanyu.bean.getCirclePostCardResult.Root) o;
                    if (root.getCode().equals("0")) {
                        MyDialog.stopDia();
                        Toast.makeText(CirclePostActivity.this, "发帖成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        MyDialog.stopDia();
                        Toast.makeText(CirclePostActivity.this, "发帖失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_post);
        ajaxParams = new AjaxParams();
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getCircleSmallType(mHandler);//获取小标题列表数据
        mHttptools.getCircleArea(mHandler, UserInfo.userToken);//获取小区接口
        initView();
    }

    private void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.circle_post_back);
        mBack.setOnClickListener(this);
        //内容
        mEdit_content = (EditText) findViewById(R.id.circle_post_edit);

        mSend_card = (TextView) findViewById(R.id.send_btn);
        mSend_card.setOnClickListener(this);
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
        mImgAdapter = new CirclePostImgGridViewAda(this, mImgList);
        mGridview = (GridView) findViewById(R.id.circle_post_gridview);
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


        //选择类型和小区
        mSelect_type_ll = (LinearLayout) findViewById(R.id.select_type_ll);
        mSelect_area_ll = (LinearLayout) findViewById(R.id.select_area_ll);
        mSelect_type_ll.setOnClickListener(this);
        mSelect_area_ll.setOnClickListener(this);
        mType_tv1 = (TextView) findViewById(R.id.circle_post_select_type);
        mArea_tv = (TextView) findViewById(R.id.circle_post_select_area);

        //类型view
        mViewTypePop = LayoutInflater.from(this).inflate(R.layout.circle_pop_item, null);
        mTypeListView = (ListView) mViewTypePop.findViewById(R.id.pop_listview);
        //小区view
        mViewAreaPop = LayoutInflater.from(this).inflate(R.layout.pop_area_view, null);
        mAreaListView = (ListView) mViewAreaPop.findViewById(R.id.pop_area_listview);

        //选择类型
        mTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mType_tv1.setText(mTitleList.get(position).getCname());
                mTypePop.dismiss();
                typeID = mTitleList.get(position).getId();
            }
        });
        //选择小区
        mAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArea_tv.setText(mCircleAreaList.get(position).getRname());
                mAreaPop.dismiss();
                areaID = mCircleAreaList.get(position).getId();
            }
        });
        //其他小区可看
        mLookBtn = (ImageView) findViewById(R.id.circle_look_btn);
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
        intent.putExtra("num", 4);
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
                mImgAdapter = new CirclePostImgGridViewAda(this, mImgList);
                mGridview.setAdapter(mImgAdapter);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mSure.getId()) {//确定删除图片
            mImgList.remove(mPosition);
            mImgAdapter.setmList(mImgList);
            mImgAdapter.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消
            mAlert.dismiss();
        } else if (id == mLookBtn.getId()) {//其他小区可看
            if (mLookBtn_flag == 1) {
                mLookBtn_flag = 2;
                mLookBtn.setImageResource(R.mipmap.circle_button_on);
            } else {
                mLookBtn_flag = 1;
                mLookBtn.setImageResource(R.mipmap.circle_button_off);
            }

        } else if (id == mSelect_type_ll.getId()) {//选择分类
            showPopuWindowType();
        } else if (id == mSelect_area_ll.getId()) {//选择分类
            showPopuWindowArea();
        } else if (id == mSend_card.getId()) {//发送

            if (!getContent().equals("") && typeID != -1 && areaID != -1 && NetWorkMyUtils.isNetworkConnected(this)) {
//                if (typeID != -1) {//类型
//                    if (areaID != -1) {//小区
//                        if (NetWorkMyUtils.isNetworkConnected(this)){

                Map<String,String> map=new HashMap<>();
                File[] files=new File[mImgList.size()];
                mSend_card.setClickable(false);
                MyDialog.showDialog(this);
                ajaxParams.put("RQid", String.valueOf(areaID));
                ajaxParams.put("categoryId", String.valueOf(typeID));
                ajaxParams.put("content", getContent());
                ajaxParams.put("visibleRange", String.valueOf(mLookBtn_flag));

                map.put("RQid", String.valueOf(areaID));
                map.put("categoryId", String.valueOf(typeID));
                map.put("content", getContent());
                map.put("visibleRange", String.valueOf(mLookBtn_flag));
                Log.e("RQid=", areaID + "");
                Log.e("categoryId=", typeID + "");
                Log.e("content=", getContent());
                Log.e("visibleRange=", mLookBtn_flag + "");
                for (int i = 0; i < mImgList.size(); i++) {
                    try {

                        files[i]=transImage(mImgList.get(i), ImgUitls.getWith(this), ImgUitls.getHeight(this), 90, "图片" + i);
                        ajaxParams.put("图片" + i, transImage(mImgList.get(i), ImgUitls.getWith(this), ImgUitls.getHeight(this), 90, "图片" + i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                mHttptools.circlePostCard(mHandler, ajaxParams,map,files);
            }
//            else {
//                            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else {
//                        Toast.makeText(this, "请选择小区", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(this, "请选择类型", Toast.LENGTH_SHORT).show();
//                }
//
//            }
            else {
                Toast.makeText(this, "请补全内容,类型,小区", Toast.LENGTH_SHORT).show();
            }

        }
    }


    //分类popuwindow显示

    private void showPopuWindowType() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);
        //popupwindow相对的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.select_type_ll);
        mTypePop = new PopupWindow(mViewTypePop);
        //设置弹框的款，高
        mTypePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTypePop.setWidth(container.getWidth());
        mTypePop.setFocusable(true);//如果有交互需要设置焦点为true
        mTypePop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mTypePop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mTypePop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mTypePop.showAsDropDown(container, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    //小区popuwindow显示
    private void showPopuWindowArea() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);
        //popupwindow相对的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.select_area_ll);
        mAreaPop = new PopupWindow(mViewAreaPop);
        //设置弹框的款，高
        mAreaPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mAreaPop.setWidth(container.getWidth());
        mAreaPop.setFocusable(true);//如果有交互需要设置焦点为true
        mAreaPop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mAreaPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mAreaPop.setAnimationStyle(R.style.popup2_anim);
        //相对于父控件的位置
        mAreaPop.showAsDropDown(container, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mAreaPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    public String getContent() {

        if (mEdit_content.getText().toString().equals("")) {
            return "";
        }
        return mEdit_content.getText().toString();
    }

    /**
     * @param pathName 图片路径
     * @param width    屏幕宽度
     * @param height   屏幕 高度
     * @param quality  图片质量
     * @param fileName 图片名称
     * @return
     */
    public File transImage(String pathName, int width, int height, int quality, String fileName) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth - 0.1f;
            float scaleHeight = (float) height / bitmapHeight - 0.1f;
            Log.e("bitmapWidth", bitmapWidth + "");
            Log.e("bitmapHeight", bitmapHeight + "");
            Log.e("scaleWidth", scaleWidth + "");
            Log.e("scaleHeight", scaleHeight + "");
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            File file = null;
            //存储媒体已经挂载，并且挂载点可读/写
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//可写
                //保存
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
                Log.e("图片名称：", file.getName());
                Log.e("图片文件夹名称：", Environment.DIRECTORY_PICTURES);

            } else {
                file = new File(getFilesDir(), fileName + ".jpg");
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：", getFilesDir().toString());

            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
                bos.flush();
                bos.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }
            Log.e("--file-大小----", file.length() / 1024 + "");
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
