package com.home.wanyu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.AreaActivityImgAda;
import com.home.wanyu.apater.AreaActivityJoinAda;
import com.home.wanyu.apater.AreaActivityLikeAda;
import com.home.wanyu.apater.ImgViewPager;
import com.home.wanyu.bean.getAreaActivityMsg.ActivityLoglist;
import com.home.wanyu.bean.getAreaActivityMsg.Activitypicturelist;
import com.home.wanyu.bean.getAreaActivityMsg.Commentlist;
import com.home.wanyu.bean.getAreaActivityMsg.Root;
import com.home.wanyu.bean.getAreaActivityMsg.UpVptelist;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommunityCommentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private LinearLayout mComment_ll, mLike_ll, mJoin_ll;
    private ImageView mLike_img, mJoin_img;
    private int over;
    private long activityId;
    private long coverPersonalId = 0;
    private int allPersonNum;
    private int joinNum;
    private RoundImageView mHead_img;
    private TextView mName, mtime, mState, mTitle, mTime_msg, mAddress, mPerson_num, mPhone, mContent;
    private TextView mDelete_btn;//删除按钮

    private MyGridView mLikeGridView, mJoinGridView, mImgGridView;
    private MyListView mCommentListView;
    private AreaActivityLikeAda mLikeAda;
    private AreaActivityJoinAda mJoinAda;
    private AreaActivityImgAda mImgAda;
    private List<UpVptelist> mLikeList = new ArrayList<>();
    private List<ActivityLoglist> mJoinList = new ArrayList<>();
    private List<Activitypicturelist> mImgList = new ArrayList<>();
    private List<String> mImgStrList = new ArrayList<>();

    private List<Commentlist> mCommentList = new ArrayList<>();
    private AreaActivityCommentAda mCommentAda;
    private int imgNum = 2;//判断用户上传过几张图片
    private int mSize;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 119) {//活动详情
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null && root.getResult() != null && root.getResult().getActivityEntity() != null && root.getResult().getActivityLoglist() != null && root.getResult().getActivitypicturelist() != null && root.getResult().getCommentlist() != null && root.getResult().getUpVptelist() != null) {
                        mNoData.setVisibility(View.GONE);
                        mAll_rl.setVisibility(View.VISIBLE);
                        if (root.getIslike()) {
                            mLike_img.setImageResource(R.mipmap.circle_like);
                        } else {
                            mLike_img.setImageResource(R.mipmap.circle_like_no);
                        }

                        if (root.getJoined()) {
                            mJoin_img.setImageResource(R.mipmap.community_add);
                        } else {
                            mJoin_img.setImageResource(R.mipmap.community_add_no);
                        }
                        mLikeList = root.getResult().getUpVptelist();
                        mJoinList = root.getResult().getActivityLoglist();
                        mImgList = root.getResult().getActivitypicturelist();
                        mCommentList = root.getResult().getCommentlist();
                        mLikeAda = new AreaActivityLikeAda(CommunityCommentActivity.this, mLikeList);
                        mLikeGridView.setAdapter(mLikeAda);
                        mJoinAda = new AreaActivityJoinAda(CommunityCommentActivity.this, mJoinList);
                        mJoinGridView.setAdapter(mJoinAda);

                        mCommentAda = new AreaActivityCommentAda(mCommentList, CommunityCommentActivity.this);
                        mCommentListView.setAdapter(mCommentAda);

                        mImgStrList.clear();
                        //取出图片
                        for (int i = 0; i < mImgList.size(); i++) {
                            //判断自己曾经上传过几张图片
                            if (mImgList.get(i).getPersonalId() == UserInfo.personalId) {
                                if (mImgList.get(i).getPicture().equals("")) {
                                    imgNum = 2;//可以传2张
                                } else {
                                    if (mImgList.get(i).getPicture().split(";").length == 1) {
                                        imgNum = 1; //可以传1张
                                    } else if (mImgList.get(i).getPicture().split(";").length == 2) {
                                        imgNum = 0; //不可以传图片
                                    }
                                }
                            }
                            //获取所有用户上传过的图片
                            if (!mImgList.get(i).getPicture().equals("")) {

                                String[] str = mImgList.get(i).getPicture().split(";");
                                for (int j = 0; j < str.length; j++) {
                                    mImgStrList.add(str[j]);
                                }
                            }

                            mSize = mImgStrList.size();
                        }

                        imgstr = new String[mImgStrList.size()];
                        for (int k = 0; k < mImgStrList.size(); k++) {
                            imgstr[k] = mImgStrList.get(k);
                        }

                        mImgAda = new AreaActivityImgAda(CommunityCommentActivity.this, mImgStrList, mSize);
                        mImgGridView.setAdapter(mImgAda);


                        if (root.getResult().getActivityEntity() != null) {
                            Picasso.with(CommunityCommentActivity.this).load(UrlTools.BASE + root.getResult().getActivityEntity().getAvatar()).resize(ImgUitls.getWith(CommunityCommentActivity.this) / 3, ImgUitls.getWith(CommunityCommentActivity.this) / 3).error(R.mipmap.error_small).into(mHead_img);
                            mName.setText(root.getResult().getActivityEntity().getUser_name());
                            mtime.setText(root.getResult().getActivityEntity().getCreateTimeString());
                            if (root.getResult().getActivityEntity().getOver() == 1) {
                                over = 1;
                                mState.setText("正在进行");
                            } else if (root.getResult().getActivityEntity().getOver() == 2) {
                                over = 2;
                                mState.setText("活动结束");
                            }
                            mTitle.setText(root.getResult().getActivityEntity().getActivityTheme());
                            mTime_msg.setText(root.getResult().getActivityEntity().getStarttimeString() + "至" + root.getResult().getActivityEntity().getEndtimeString());
                            mAddress.setText(root.getResult().getActivityEntity().getActivityAddress());
                            mPerson_num.setText(root.getResult().getActivityEntity().getActivityNumber() + "");
                            mPhone.setText(root.getResult().getActivityEntity().getActivityTelephone() + "");
                            mContent.setText(root.getResult().getActivityEntity().getActivityContent());
                            allPersonNum = root.getResult().getActivityEntity().getActivityNumber();//总人数
                            joinNum = root.getResult().getActivityEntity().getParticipateNumber();//实时参与人数

                            if (root.getResult().getActivityEntity().getPersonalId() == UserInfo.personalId) {
                                mDelete_btn.setVisibility(View.VISIBLE);
                            } else {
                                mDelete_btn.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        mAll_rl.setVisibility(View.GONE);
                        Toast.makeText(CommunityCommentActivity.this, "作者已删除", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 121) {//发布图片
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaPostImg.Root) {
                    com.home.wanyu.bean.getAreaPostImg.Root root = (com.home.wanyu.bean.getAreaPostImg.Root) o;

                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "上传活动图片成功", Toast.LENGTH_SHORT).show();
                        if (activityId != -1) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
                        }
                    } else {
                        Toast.makeText(CommunityCommentActivity.this, "上传活动图片失败", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 120) {//点赞
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        if (activityId != -1) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
                        }
                        mLike_img.setImageResource(R.mipmap.circle_like);
                    } else {
                        if (activityId != -1) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
                        }
                        mLike_img.setImageResource(R.mipmap.circle_like_no);
                    }
                }

            } else if (msg.what == 123) {//加入
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "参加活动成功", Toast.LENGTH_SHORT).show();
                        if (activityId != -1) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
                        }
                        mJoin_img.setImageResource(R.mipmap.community_add);
                    } else {
                        Toast.makeText(CommunityCommentActivity.this, "您已参加活动", Toast.LENGTH_SHORT).show();

                    }
                }

            } else if (msg.what == 125) {//删除
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CommunityCommentActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 122) {//社区活动评论
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        mComment_all_ll.setVisibility(View.VISIBLE);
                        mEdit_ll.setVisibility(View.GONE);
                        if (activityId != -1) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
                        }
                    } else {
                        Toast.makeText(CommunityCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private RelativeLayout mImgViewPager_rl, m_all_rl;
    private ViewPager mImgViewPager;
    private TextView mImg_Cancle_btn;
    private ImgViewPager mAdapter;
    private String[] imgstr;

    private RelativeLayout mNoData, mAll_rl;

    private LinearLayout mComment_all_ll, mEdit_ll;
    private EditText mEdit_content;
    private TextView mComment_Send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_comment);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        //软键盘发送按钮
        mEdit_content = (EditText) findViewById(R.id.edit);
        //发送按钮
        mEdit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getContent().equals("")) {
                        Toast.makeText(CommunityCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    } else {
                        AjaxParams ajax = new AjaxParams();
                        ajax.put("token", UserInfo.userToken);
                        ajax.put("ActivityId", activityId + "");
                        ajax.put("coverPersonalId", coverPersonalId + "");
                        ajax.put("content", getContent());

                        Log.e("coverPersonalId", coverPersonalId + "");
                        Log.e("ActivityId", activityId + "");
                        mHttptools.AreaActivityComment(mHandler, ajax);
                        mEdit_content.setText("");
                        mEdit_content.setHint("评论...");
                        coverPersonalId = 0;
                        //隐藏软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }
                return false;
            }

        });
        //新增的发送按钮
        mComment_Send_btn = (TextView) findViewById(R.id.comment_send_btn);
        mComment_Send_btn.setOnClickListener(this);
        //底部评论按钮全部布局（包括评论按钮、兴趣按钮、参加按钮）
        mComment_all_ll = (LinearLayout) findViewById(R.id.community_all_bottom_ll);
        //输入评论内容布局(输入框和发送按钮)
        mEdit_ll = (LinearLayout) findViewById(R.id.card_comment_box);

        mNoData = (RelativeLayout) findViewById(R.id.no_data_rl);
        mAll_rl = (RelativeLayout) findViewById(R.id.all_rl);

        activityId = getIntent().getLongExtra("activityId", -1);
        if (activityId != -1) {
            //获取活动详情
            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
        }
        mDelete_btn = (TextView) findViewById(R.id.u_delete_btn);
        mDelete_btn.setOnClickListener(this);
        mHead_img = (RoundImageView) findViewById(R.id.u_head_img);
        mName = (TextView) findViewById(R.id.u_name);
        mtime = (TextView) findViewById(R.id.u_post_time);
        mState = (TextView) findViewById(R.id.u_state);
        mTitle = (TextView) findViewById(R.id.u_title_tv);
        mTime_msg = (TextView) findViewById(R.id.time_msg_v);
        mAddress = (TextView) findViewById(R.id.address_msg_v);
        mPerson_num = (TextView) findViewById(R.id.person_msg_v);
        mPhone = (TextView) findViewById(R.id.phone_msg_v);
        mContent = (TextView) findViewById(R.id.content_msg_tv);

        //点赞GridView
        mLikeGridView = (MyGridView) findViewById(R.id.community_like_gridview);
        mLikeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommunityCommentActivity.this, OtherPersonInfoActivity.class);
                intent.putExtra("id", mLikeList.get(position).getPersonalId() + "");
                startActivity(intent);
            }
        });
        //加入GridView
        mJoinGridView = (MyGridView) findViewById(R.id.community_join_gridview);
        //添加图片GridView
        mImgGridView = (MyGridView) findViewById(R.id.activity_img_gridview);
        //评论listview
        mCommentListView = (MyListView) findViewById(R.id.community_commend_listview);
        //选择上传图片
        mImgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImgStrList.size() == 0) {
                    //跳转相册或拍照
                    init(imgNum);
                } else {
                    if (mImgStrList.size() == position) {
                        if (mImgStrList.size() == 20) {
                            Toast.makeText(CommunityCommentActivity.this, "亲，此活动图片已满", Toast.LENGTH_SHORT).show();
                        } else {
                            if (imgNum == 0) {
                                Toast.makeText(CommunityCommentActivity.this, "亲，您已经添加过2张图片了，不能再次添加了哦", Toast.LENGTH_SHORT).show();
                            } else {
                                if (mImgStrList.size() == 19) {
                                    if (imgNum == 1) {
                                        init(1);
                                    } else if (imgNum == 2) {
                                        init(1);
                                    }
                                } else {
                                    if (imgNum == 1) {
                                        init(1);
                                    } else if (imgNum == 2) {
                                        init(2);
                                    }
                                }

                            }

                        }
                    } else {
                        m_all_rl.setVisibility(View.GONE);
                        mImgViewPager_rl.setVisibility(View.VISIBLE);
                        mAdapter = new ImgViewPager(CommunityCommentActivity.this, imgstr);
                        mImgViewPager.setAdapter(mAdapter);
                        mImgViewPager.setCurrentItem(position);
                    }


                }
            }
        });

        mBack = (ImageView) findViewById(R.id.community_msg_back);
        mBack.setOnClickListener(this);

        mComment_ll = (LinearLayout) findViewById(R.id.comment_ll);
        mComment_ll.setOnClickListener(this);
        mLike_ll = (LinearLayout) findViewById(R.id.like_ll);
        mLike_ll.setOnClickListener(this);
        mJoin_ll = (LinearLayout) findViewById(R.id.join_ll);
        mJoin_ll.setOnClickListener(this);
        mLike_img = (ImageView) findViewById(R.id.like_img);
        mJoin_img = (ImageView) findViewById(R.id.join_img);

        //点击GridView中显示图片
        m_all_rl = (RelativeLayout) findViewById(R.id.m_all_rl);
        mImgViewPager_rl = (RelativeLayout) findViewById(R.id.img_viewpager_rl);
        mImgViewPager = (ViewPager) findViewById(R.id.img_viewpager);
        mImg_Cancle_btn = (TextView) findViewById(R.id.img_cancle);
        mImg_Cancle_btn.setOnClickListener(this);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mComment_all_ll.setVisibility(View.VISIBLE);
                mEdit_ll.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    private final int REQUEST_CODE_ASK_READ_PHONE = 123;

    //权限判断
    public void init(int num) {
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
                startSelectImgActivity(num);
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity(num);
        }
    }

    //跳转到选择图片的页面
    public void startSelectImgActivity(int i) {
        Intent intent = new Intent(this, SelectImgActivity.class);
        intent.putExtra("num", i);
        startActivityForResult(intent, 100);
    }

    //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startSelectImgActivity(imgNum);
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");

            if (imgNum - list.size() == 0) {
                imgNum = 0;
            } else if (imgNum - list.size() == 1) {
                imgNum = 1;
            } else if (imgNum - list.size() == 2) {
                imgNum = 2;
            }

            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("token", UserInfo.userToken);
            ajaxParams.put("activity_id", activityId + "");
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    try {
                        ajaxParams.put("图片" + i, transImage(list.get(i), ImgUitls.getWith(this), ImgUitls.getHeight(this), 90, "图片" + i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                ajaxParams.put("number", list.size() + "");
                mHttptools.AreaActivityPosImg(mHandler, ajaxParams);
            }
        }

//        else if (requestCode == 101 && resultCode == RESULT_OK) {//评论结束后
//            if (activityId != -1) {
//                //获取活动详情
//                mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, activityId);
//            }
//        }

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
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：", Environment.DIRECTORY_PICTURES);

            } else {
                file = new File(getFilesDir(), fileName + ".jpg");
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


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mComment_ll.getId()) {//评论
            mComment_all_ll.setVisibility(View.GONE);
            mEdit_ll.setVisibility(View.VISIBLE);

        } else if (id == mComment_Send_btn.getId()) {
            if (getContent().equals("")) {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            } else {
                AjaxParams ajax = new AjaxParams();
                ajax.put("token", UserInfo.userToken);
                ajax.put("ActivityId", activityId + "");
                ajax.put("coverPersonalId", coverPersonalId + "");
                ajax.put("content", getContent());

                Log.e("coverPersonalId", coverPersonalId + "");
                Log.e("ActivityId", activityId + "");
                mHttptools.AreaActivityComment(mHandler, ajax);
                mEdit_content.setText("");
                mEdit_content.setHint("评论...");
                coverPersonalId = 0;
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        } else if (id == mLike_ll.getId()) {//点赞
            if (activityId != -1) {
                mHttptools.getAreaActivityLike(mHandler, UserInfo.userToken, activityId);
            }

        } else if (id == mJoin_ll.getId()) {//参加
            if (activityId != -1) {
                if (over == 2) {
                    Toast.makeText(this, "此活动已经结束了", Toast.LENGTH_SHORT).show();
                } else if (over == 1) {
                    if (joinNum >= allPersonNum) {
                        Toast.makeText(this, "此活动活动人数已满", Toast.LENGTH_SHORT).show();
                    } else {
                        mHttptools.areaActivityJoin(mHandler, UserInfo.userToken, activityId);
                    }

                }

            }
        } else if (id == mDelete_btn.getId()) {//删除
            if (activityId != -1) {
                mHttptools.areaActivityDelete(mHandler, UserInfo.userToken, activityId, UserInfo.personalId);
            }

        } else if (id == mImg_Cancle_btn.getId()) {//隐藏viewpager
            m_all_rl.setVisibility(View.VISIBLE);
            mImgViewPager_rl.setVisibility(View.GONE);
        }
    }

    /**
     * 输入评论内容
     *
     * @return
     */
    public String getContent() {
        if (mEdit_content.getText().toString().trim().equals("")) {
            return "";
        }
        return mEdit_content.getText().toString().trim();
    }

    /**
     * 评论适配器
     */
    class AreaActivityCommentAda extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<Commentlist> list = new ArrayList<>();
        private MyAreaHolder holder;

        public AreaActivityCommentAda(List<Commentlist> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        public void setList(List<Commentlist> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new MyAreaHolder();
                convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
                holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
                holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
                holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
                holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
                convertView.setTag(holder);
            } else {
                holder = (MyAreaHolder) convertView.getTag();
            }
            //只是当前用户评论的
            if (list.get(position).getCoverPersonalId() == 0) {
                holder.name1.setText(list.get(position).getUserName());//张三评论：
                holder.name2.setVisibility(View.GONE);
                holder.huifu.setVisibility(View.GONE);
                holder.msg.setText(":" + list.get(position).getContent());//你是个小猫咪


                //当前用户回复回复某个人
            } else {
                holder.name1.setText(list.get(position).getUserName());//张三
                holder.name2.setVisibility(View.VISIBLE);
                holder.huifu.setVisibility(View.VISIBLE);//回复
                holder.name2.setText(list.get(position).getCoverPersonalName());//李四
                holder.msg.setText(":" + list.get(position).getContent());//我很好

            }
            holder.name1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mComment_all_ll.setVisibility(View.GONE);
                    mEdit_ll.setVisibility(View.VISIBLE);
                    if (list.get(position).getPersonalId() == UserInfo.personalId) {
                        coverPersonalId = 0;
                        mEdit_content.setHint("说点什么");
                    } else {
                        coverPersonalId = list.get(position).getPersonalId();
                        mEdit_content.setHint("回复" + list.get(position).getUserName());
                    }
                }
            });

            holder.name2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mComment_all_ll.setVisibility(View.GONE);
                    mEdit_ll.setVisibility(View.VISIBLE);
                    if (list.get(position).getCoverPersonalId() == UserInfo.personalId) {
                        coverPersonalId = 0;
                        mEdit_content.setHint("说点什么");
                    } else {
                        coverPersonalId = list.get(position).getCoverPersonalId();
                        mEdit_content.setHint("回复" + list.get(position).getCoverPersonalName());
                    }
                }
            });

            return convertView;
        }


        class MyAreaHolder {
            TextView name1;
            TextView name2;
            TextView huifu;
            TextView msg;
        }
    }
}
