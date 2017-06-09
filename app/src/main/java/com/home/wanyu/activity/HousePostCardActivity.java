package com.home.wanyu.activity;

import android.Manifest;
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
import android.widget.Button;
import android.widget.EditText;
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
import com.home.wanyu.apater.CirclePostImgGridViewAda;
import com.home.wanyu.apater.HouseLookConfigureAda;
import com.home.wanyu.apater.HousePostConfigureAda;
import com.home.wanyu.apater.HousePostImgAda;
import com.home.wanyu.bean.HouseLookConfigure;
import com.home.wanyu.bean.getAreaActivityLike.Root;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.MyDialog;
import com.home.wanyu.myview.MyGridView;

import net.tsz.afinal.http.AjaxParams;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HousePostCardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private TextView mPostNum;
    private EditText mAreaName, mShi, mTing, mWei, mM_size, mCeng, mAll_ceng, mMoney, mName, mPhone;
    private MyGridView myConfigureGridView;
    private HousePostConfigureAda mConfigureAda;
    private List<HouseLookConfigure> mConfigureList = new ArrayList<>();
    private List<HouseLookConfigure> mSelectConfigureList = new ArrayList<>();

    private MyGridView myImgGridview;
    private HousePostImgAda mImgAdapter;
    private List<String> mImgList = new ArrayList<>();
    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;

    private ImageView mZzu_img, mHzu_img;
    private int mZuFlag = 1;//1代表整租，2代表合租
    private int checkFlag = -1;//1代表卧室弹框，2代表朝向弹框，3代表租金弹框

    private PopupWindow mDirectionPop;
    private View direction;
    private WheelView mDirectionView;
    private MyWheelAdapter50 mDirectionAdapter;
    private List<String> mDirectionList = new ArrayList<>();
    private List<String> mWoList = new ArrayList<>();
    private List<String> mMoneyList = new ArrayList<>();
    private TextView mCancle_direction, mSure_direction;
    private LinearLayout mDirection_ll;
    private TextView mDirection_tv;
    private int mDirectionPosition = 0;

    private RelativeLayout mWo_rl;
    private RelativeLayout mWo_alert_rl;
    private TextView mWo_name;
    private RelativeLayout mHu_rl;
    private LinearLayout mPay_money_type_ll;
    private TextView mPay_money_tv;
    private RelativeLayout mSelect_city_rl;
    private TextView mCity_name;

    private PopupWindow mCityPop;
    private View cityView;
    private WheelView bigCityWheelView;
    private WheelView smallCityWheelView;
    private MyWheelAdapter50 bigCityAda;
    private MyWheelAdapter50 smallCityAda;
    private TextView mCity_sure_btn, mCity_cancle_btn;
    private int mBigCityPosition = 0;
    private int mSmallCityPosition = 0;

    private Button mSubmit_btn;

    private int postNum;
    private HttpTools mhttptools;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==149){
                Object o=msg.obj;
                if (o!=null&&o instanceof  com.home.wanyu.bean.getAreaActivityLike.Root){
                    com.home.wanyu.bean.getAreaActivityLike.Root root= (Root) o;
                    MyDialog.stopDia();
                    if (root.getCode().equals("0")){
                        Toast.makeText(HousePostCardActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(HousePostCardActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if (msg.what==150){//获取总数
                Object o=msg.obj;
                if (o!=null&&o instanceof  com.home.wanyu.bean.HousePostNumber.PostNum){
                    com.home.wanyu.bean.HousePostNumber.PostNum  root= (com.home.wanyu.bean.HousePostNumber.PostNum ) o;
                    if (root.getCode().equals("0")){
                        postNum=root.getNumber();
                        if (postNum==8){
                            mPostNum.setText("抱歉,您本月发布的的房源已经达到上限，请下个月发布");
                        }else {
                            mPostNum.setText("您本月可以发帖"+8+"条,"+"本月还可以免费发帖"+(8-postNum)+"条");
                        }

                    }else {
                        mPostNum.setText("无法获取您本月发布房屋的数量");
                        postNum=-1;
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_house_post_card);
        mhttptools=HttpTools.getHttpToolsInstance();
        mhttptools.housePostNumber(handler, UserInfo.userToken);//获取发布房源的总数
        pullXml();
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.house_post_back);
        mback.setOnClickListener(this);

        mPostNum=(TextView) findViewById(R.id.house_prompt_num);

        mAreaName = (EditText) findViewById(R.id.area_name);
        mShi = (EditText) findViewById(R.id.house_shi_edit);
        mTing = (EditText) findViewById(R.id.house_ting_edit);
        mWei = (EditText) findViewById(R.id.house_wei_edit);
        mM_size = (EditText) findViewById(R.id.house_m_edit);
        mCeng = (EditText) findViewById(R.id.house_ceng_edit);
        mAll_ceng = (EditText) findViewById(R.id.house_all_ceng_edit);
        mMoney = (EditText) findViewById(R.id.house_money_edit);
        mName = (EditText) findViewById(R.id.house_phone_name_edit);
        mPhone = (EditText) findViewById(R.id.house_phone_num_edit);

        //提交
        mSubmit_btn = (Button) findViewById(R.id.house_post_submit);
        mSubmit_btn.setOnClickListener(this);
        //配置
        mConfigureList.add(new HouseLookConfigure("电视", R.mipmap.house_conigure_no, 1));
        mConfigureList.add(new HouseLookConfigure("宽带", R.mipmap.house_conigure_no, 2));
        mConfigureList.add(new HouseLookConfigure("空调", R.mipmap.house_conigure_no, 3));
        mConfigureList.add(new HouseLookConfigure("热水器", R.mipmap.house_conigure_no, 4));
        mConfigureList.add(new HouseLookConfigure("床", R.mipmap.house_conigure_no, 5));
        mConfigureList.add(new HouseLookConfigure("供暖", R.mipmap.house_conigure_no, 6));
        mConfigureList.add(new HouseLookConfigure("洗衣机", R.mipmap.house_conigure_no, 7));
        mConfigureList.add(new HouseLookConfigure("冰箱", R.mipmap.house_conigure_no, 8));
        mConfigureList.add(new HouseLookConfigure("柜子", R.mipmap.house_conigure_no, 9));
        mConfigureList.add(new HouseLookConfigure("沙发", R.mipmap.house_conigure_no, 10));

        mConfigureAda = new HousePostConfigureAda(this, mConfigureList);
        myConfigureGridView = (MyGridView) findViewById(R.id.house_configure_gridview);
        myConfigureGridView.setAdapter(mConfigureAda);
        //点击选择配置
        myConfigureGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.look_img);
                if (mSelectConfigureList.contains(mConfigureAda.getItem(position))) {
                    mSelectConfigureList.remove(mConfigureAda.getItem(position));
                    imageView.setImageResource(R.mipmap.house_conigure_no);
                } else {
                    imageView.setImageResource(R.mipmap.house_conigure);
                    mSelectConfigureList.add((HouseLookConfigure) mConfigureAda.getItem(position));
                }
            }
        });

        //添加图片
        myImgGridview = (MyGridView) findViewById(R.id.house_img_gridview);
        mImgAdapter = new HousePostImgAda(this, mImgList);
        myImgGridview.setAdapter(mImgAdapter);
        myImgGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImgList.size() == 0) {
                    //跳转相册或拍照
                    init();
                } else {
                    if (mImgList.size() == 8) {
                        if (position != mImgList.size()) {
                            mAlert.show();
                            mPosition = position;
                        } else {
                            Toast.makeText(HousePostCardActivity.this, "亲，最多选择8张图片哦", Toast.LENGTH_SHORT).show();
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

        //删除图片是的弹框
        builder = new AlertDialog.Builder(this);
        mAlert = builder.create();
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_box, null);
        mAlert.setView(mAlertView);
        mSure = (TextView) mAlertView.findViewById(R.id.sure);
        mCancle = (TextView) mAlertView.findViewById(R.id.cancle);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);

        //整租，合租
        mZzu_img = (ImageView) findViewById(R.id.house_rent_img_all);
        mHzu_img = (ImageView) findViewById(R.id.house_rent_img_other);
        mZzu_img.setOnClickListener(this);
        mHzu_img.setOnClickListener(this);


        //朝向弹框
        mDirection_ll = (LinearLayout) findViewById(R.id.direction_ll);
        mDirection_ll.setOnClickListener(this);
        mDirection_tv = (TextView) findViewById(R.id.direction_tv_msg);
        //卧室弹框
        mWo_rl = (RelativeLayout) findViewById(R.id.house_wo_rl);
        mHu_rl = (RelativeLayout) findViewById(R.id.house_type_rl);
        mWo_alert_rl = (RelativeLayout) findViewById(R.id.house_wo_relative);
        mWo_alert_rl.setOnClickListener(this);
        mWo_name = (TextView) findViewById(R.id.house_wo_name);

        //租金类型弹框
        mPay_money_type_ll = (LinearLayout) findViewById(R.id.pay_monet_type_ll);
        mPay_money_type_ll.setOnClickListener(this);
        mPay_money_tv = (TextView) findViewById(R.id.pay_money_tv);

        mDirectionList.add("东");
        mDirectionList.add("南");
        mDirectionList.add("西");
        mDirectionList.add("北");
        mDirectionList.add("南北");
        mDirectionList.add("东西");

        mWoList.add("主卧");
        mWoList.add("次卧");
        mWoList.add("隔断");

        mMoneyList.add("押一付一");
        mMoneyList.add("押一付三");
        mMoneyList.add("半年付");
        mMoneyList.add("年付");


        //选择城市弹框
        mSelect_city_rl = (RelativeLayout) findViewById(R.id.select_city_rl);
        mSelect_city_rl.setOnClickListener(this);
        mCity_name = (TextView) findViewById(R.id.house_city_name);


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

    //跳转到选择图片的页面
    public void startSelectImgActivity() {
        Intent intent = new Intent(this, SelectImgActivity.class);
        intent.putExtra("num", 8);
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


    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");
            if (mImgList.size() != 8) {
                for (int i = 0; i < list.size(); i++) {
                    mImgList.add(list.get(i));
                    if (mImgList.size() == 8) {
                        break;
                    }
                }
                mImgAdapter = new HousePostImgAda(this, mImgList);
                myImgGridview.setAdapter(mImgAdapter);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mSure.getId()) {//确定删除图片
            mImgList.remove(mPosition);
            mImgAdapter.setmList(mImgList);
            mImgAdapter.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消删除图片
            mAlert.dismiss();
        } else if (id == mZzu_img.getId()) {//整租
            mZuFlag = 1;
            mHu_rl.setVisibility(View.VISIBLE);
            mWo_rl.setVisibility(View.INVISIBLE);
            mHzu_img.setImageResource(R.mipmap.select_house_rent_no);
            mZzu_img.setImageResource(R.mipmap.select_house_rent);

        } else if (id == mHzu_img.getId()) {//合租
            mZuFlag = 2;
            mHu_rl.setVisibility(View.INVISIBLE);
            mWo_rl.setVisibility(View.VISIBLE);
            mHzu_img.setImageResource(R.mipmap.select_house_rent);
            mZzu_img.setImageResource(R.mipmap.select_house_rent_no);
        } else if (id == mDirection_ll.getId()) {//朝向弹框
            checkFlag = 2;
            showDirectionPop(mDirectionList);
        } else if (id == mWo_alert_rl.getId()) {//卧室弹框
            checkFlag = 1;
            showDirectionPop(mWoList);
        } else if (id == mPay_money_type_ll.getId()) {//付款的类型
            checkFlag = 3;
            showDirectionPop(mMoneyList);
        } else if (id == R.id.cancle_btn) {//取消卧室，朝向，租金弹框
            mDirectionPop.dismiss();
            mDirectionPosition = 0;
        } else if (id == R.id.sure_btn) {//确定卧室，朝向，租金弹框

            if (checkFlag == 1) {
                mWo_name.setText(mWoList.get(mDirectionPosition));
            } else if (checkFlag == 2) {
                mDirection_tv.setText(mDirectionList.get(mDirectionPosition));
            } else if (checkFlag == 3) {
                mPay_money_tv.setText(mMoneyList.get(mDirectionPosition));
            }
            mDirectionPop.dismiss();
        } else if (id == mSelect_city_rl.getId()) {//城市弹框
            showCityPop();
        } else if (id == R.id.sure_city_btn) {//确定城市
            mCityPop.dismiss();
            mCity_name.setText(mapAreas.get(listCitys.get(mBigCityPosition)).get(mSmallCityPosition));

        } else if (id == R.id.cancle_city_btn) {//取消城市
            mCityPop.dismiss();
            mBigCityPosition = 0;
            mSmallCityPosition = 0;
        } else if (id == mSubmit_btn.getId()) {//提交
            if (postNum!=-1){

                if (postNum==8){
                    Toast.makeText(this,"抱歉,您本月发布的的房源已经达到上限，请下个月发布",Toast.LENGTH_SHORT).show();
                }else {
                    if (mZuFlag==1){
                        if (mImgList.size()==0||mPay_money_tv.getText().toString().trim().equals("")||mSelectConfigureList.size()==0||mDirection_tv.getText().toString().trim().equals("")||mCity_name.getText().toString().trim().equals("") ||getAreaname().equals("")||getShi().equals("")||getWei().equals("")||getTing().equals("")||getMsize().equals("")||getceng().equals("")||getAllceng().equals("")||getMoney().equals("")||getMName().equals("")||getMPhone().equals("")){
                            Toast.makeText(this,"请补全信息",Toast.LENGTH_SHORT).show();
                        }else {
                            MyDialog.showDialog(this);
                            StringBuilder stringBuilder=new StringBuilder();
                            for (int i=0;i<mSelectConfigureList.size();i++){
                                stringBuilder.append(mSelectConfigureList.get(i).getName()+"；");
                            }

                            AjaxParams ajaxParams=new AjaxParams();
                            ajaxParams.put("rentalTyoe",1+"");
                            ajaxParams.put("cyty",mCity_name.getText().toString().trim());
                            Log.e("cyty",mCity_name.getText().toString().trim());
                            ajaxParams.put("residentialQuarters",getAreaname());
                            ajaxParams.put("apartmentLayout",getShi()+"室"+getTing()+"厅"+getWei()+"卫");
                            ajaxParams.put("housingArea",getMsize());
                            ajaxParams.put("floor",getceng());
                            ajaxParams.put("floord",getAllceng());
                            ajaxParams.put("direction",mDirection_tv.getText().toString());
                            ajaxParams.put("houseAllocation",stringBuilder.toString());
                            ajaxParams.put("rent",getMoney());
                            ajaxParams.put("paymentMethod",mPay_money_tv.getText().toString().trim());
                            ajaxParams.put("contacts",getMName());
                            ajaxParams.put("telephone",getMPhone());

                            for (int i=0;i<mImgList.size();i++){
                                File file=   transImage(mImgList.get(i), ImgUitls.getWith(this),ImgUitls.getHeight(this),90,"图片"+i);
                                try {
                                    ajaxParams.put("图片"+i,file);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhttptools.housePostCard(handler,ajaxParams);

                        }

                    }else if (mZuFlag==2){
                        if (mWo_name.getText().toString().toString().trim().equals("")||mImgList.size()==0||mPay_money_tv.getText().toString().trim().equals("")||mSelectConfigureList.size()==0||mDirection_tv.getText().toString().trim().equals("")||mCity_name.getText().toString().trim().equals("") ||getAreaname().equals("")||getMsize().equals("")||getceng().equals("")||getAllceng().equals("")||getMoney().equals("")||getMName().equals("")||getMPhone().equals("")){
                            Toast.makeText(this,"请补全信息",Toast.LENGTH_SHORT).show();
                        }else {
                            MyDialog.showDialog(this);
                            StringBuilder stringBuilder=new StringBuilder();
                            for (int i=0;i<mSelectConfigureList.size();i++){
                                stringBuilder.append(mSelectConfigureList.get(i).getName()+"；");
                            }

                            AjaxParams ajaxParams=new AjaxParams();
                            ajaxParams.put("rentalTyoe",2+"");
                            ajaxParams.put("cyty",mCity_name.getText().toString().trim());
                            ajaxParams.put("residentialQuarters",getAreaname());
                            ajaxParams.put("apartmentLayout",mWo_name.getText().toString().toString());
                            ajaxParams.put("housingArea",getMsize());
                            ajaxParams.put("floor",getceng());
                            ajaxParams.put("floord",getAllceng());
                            ajaxParams.put("direction",mDirection_tv.getText().toString());
                            ajaxParams.put("houseAllocation",stringBuilder.toString());
                            ajaxParams.put("rent",getMoney());
                            ajaxParams.put("paymentMethod",mPay_money_tv.getText().toString().trim());
                            ajaxParams.put("contacts",getMName());
                            ajaxParams.put("telephone",getMPhone());
                            for (int i=0;i<mImgList.size();i++){
                                File file=   transImage(mImgList.get(i), ImgUitls.getWith(this),ImgUitls.getHeight(this),90,"图片"+i);
                                try {
                                    ajaxParams.put("图片"+i,file);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhttptools.housePostCard(handler,ajaxParams);

                        }

                    }
                }
            }else {
                Toast.makeText(this,"抱歉,无法获取您本月发布房屋的数量",Toast.LENGTH_SHORT).show();
            }


        }

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
            Log.i("--file-大小----", file.length() / 1024 + "");
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //卧室，朝向，租金弹框
    private void showDirectionPop(List<String> list) {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);

        direction = LayoutInflater.from(this).inflate(R.layout.house_direction, null);
        mCancle_direction = (TextView) direction.findViewById(R.id.cancle_btn);
        mSure_direction = (TextView) direction.findViewById(R.id.sure_btn);
        mCancle_direction.setOnClickListener(this);
        mSure_direction.setOnClickListener(this);
        mDirectionView = (WheelView) direction.findViewById(R.id.direction);

        mDirectionAdapter = new MyWheelAdapter50(this, list, "");
        mDirectionView.setViewAdapter(mDirectionAdapter);
        mDirectionView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mDirectionPosition = newValue;
            }
        });


        mDirectionPop = new PopupWindow();
        mDirectionPop.setContentView(direction);
        //设置弹框的款，高
        mDirectionPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mDirectionPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mDirectionPop.setFocusable(true);//如果有交互需要设置焦点为true
        mDirectionPop.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mDirectionPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mDirectionPop.setAnimationStyle(R.style.popup3_anim);
        //popupwindow相对的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_house_post_card);
        //相对于父控件的位置
        mDirectionPop.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mDirectionPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mDirectionPosition = 0;
            }
        });
    }

    //城市弹框
    private void showCityPop() {
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);

        cityView = LayoutInflater.from(this).inflate(R.layout.house_city, null);
        mCity_sure_btn = (TextView) cityView.findViewById(R.id.sure_city_btn);
        mCity_cancle_btn = (TextView) cityView.findViewById(R.id.cancle_city_btn);
        mCity_sure_btn.setOnClickListener(this);
        mCity_cancle_btn.setOnClickListener(this);

        bigCityWheelView = (WheelView) cityView.findViewById(R.id.big_city);
        smallCityWheelView = (WheelView) cityView.findViewById(R.id.small_city);

        bigCityAda = new MyWheelAdapter50(this, listCitys, "");
        bigCityWheelView.setViewAdapter(bigCityAda);

        smallCityAda = new MyWheelAdapter50(HousePostCardActivity.this, mapAreas.get(listCitys.get(mBigCityPosition)), "");
        smallCityWheelView.setViewAdapter(smallCityAda);

        bigCityWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mBigCityPosition = newValue;
                smallCityAda = new MyWheelAdapter50(HousePostCardActivity.this, mapAreas.get(listCitys.get(mBigCityPosition)), "");
                smallCityWheelView.setViewAdapter(smallCityAda);
            }
        });

        smallCityWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mSmallCityPosition = newValue;
            }
        });

        mCityPop = new PopupWindow();
        mCityPop.setContentView(cityView);
        //设置弹框的款，高
        mCityPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mCityPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mCityPop.setFocusable(true);//如果有交互需要设置焦点为true
        mCityPop.setOutsideTouchable(false);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mCityPop.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mCityPop.setAnimationStyle(R.style.popup3_anim);
        //popupwindow相对的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_house_post_card);
        //相对于父控件的位置
        mCityPop.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mCityPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                mBigCityPosition = 0;
                mSmallCityPosition = 0;
            }
        });

    }

    private List<String> listCitys;//所有市级
    private List<String> listAreas; //某一个市下的所有县区集合
    private String currentCity;//当前解析到的市的名字
    private Map<String, List<String>> mapAreas;//所有市对应的各个县区集合

    // 解析数据
    public boolean pullXml() {
        try {
            InputStream is = getResources().getAssets().open("city.xml");
            //    创建XmlPullParserFactory解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //    通过XmlPullParserFactory工厂类实例化一个XmlPullParser解析类
            XmlPullParser parser = factory.newPullParser();
            //    根据指定的编码来解析xml文档
            parser.setInput(is, "utf-8");
            //    得到当前的事件类型
            int eventType = parser.getEventType();
            //    只要没有解析到xml的文档结束，就一直解析
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //    解析到文档开始的时候
                    case XmlPullParser.START_DOCUMENT:
//                        mProvince = new ArrayList<>();//省份集合
//                        mCity=new HashMap<>();//市区集合
//                        mArea=new HashMap<>();//县区集合
                        listCitys = new ArrayList<>();
                        mapAreas = new HashMap<>();
                        break;
                    //    解析到xml标签的时候
                    case XmlPullParser.START_TAG:
                        switch (parser.getName()) {
//                            List<String>listCitys;
//                            Map<String,String>mapAreas;
                            case "province":
//                                Map<String,String>mp=new HashMap<>();
//                                mp.put("name",parser.getAttributeValue(0));
//                                mp.put("code",parser.getAttributeValue(1));
//                                mProvince.add(mp);
//                                listCity=new ArrayList<>();
//                                provice=parser.getAttributeValue(0);
                                break;
                            case "city":
                                listCitys.add(parser.getAttributeValue(0));
                                currentCity = parser.getAttributeValue(0);
                                listAreas = new ArrayList<>();
//                                Map<String,String>mp1=new HashMap<>();
//                                mp1.put("name",parser.getAttributeValue(0));
//                                mp1.put("code",parser.getAttributeValue(1));
//                                listCity.add(mp1);
//                                city=parser.getAttributeValue(0);
//                                listArea=new ArrayList<>();
                                break;
                            case "area":
                                listAreas.add(parser.getAttributeValue(0));
//                                Map<String,String>mp2=new HashMap<>();
//                                mp2.put("name",parser.getAttributeValue(0));
//                                mp2.put("code",parser.getAttributeValue(1));
//                                listArea.add(mp2);
                                break;
                        }
                        break;
                    //    解析到xml标签结束的时候
                    case XmlPullParser.END_TAG:
                        switch (parser.getName()) {
//                            case "province":
//                                mCity.put(provice,listCity);
//                                break;
                            case "city":
                                mapAreas.put(currentCity, listAreas);
                                break;
                        }
                        break;
                }
                //    通过next()方法触发下一个事件
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查手机号
     *
     * @param phonenum
     * @return true 代表手机号正确
     */
    public boolean checkPhone(String phonenum) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phonenum);
        b = m.matches();
        return b;
    }

    public String getAreaname() {
        if (mAreaName.getText().toString().trim().equals("")) {
            return "";
        }
        return mAreaName.getText().toString().trim();
    }

    public String getShi() {
        if (mShi.getText().toString().trim().equals("")) {
            return "";
        }
        return mShi.getText().toString().trim();
    }
    public String getTing() {
        if (mTing.getText().toString().trim().equals("")) {
            return "";
        }
        return mTing.getText().toString().trim();
    }

    public String getWei() {
        if (mWei.getText().toString().trim().equals("")) {
            return "";
        }
        return mWei.getText().toString().trim();
    }

    public String getMsize() {
        if (mM_size.getText().toString().trim().equals("")) {
            return "";
        }
        return mM_size.getText().toString().trim();
    }

    public String getceng() {
        if (mCeng.getText().toString().trim().equals("")) {
            return "";
        }
        return mCeng.getText().toString().trim();
    }

    public String getAllceng() {
        if (mAll_ceng.getText().toString().trim().equals("")) {
            return "";
        }
        return mAll_ceng.getText().toString().trim();
    }

    public String getMoney() {
        if (mMoney.getText().toString().trim().equals("")) {
            return "";
        }
        return mMoney.getText().toString().trim();
    }

    public String getMName() {
        if (mName.getText().toString().trim().equals("")) {
            return "";
        }
        return mName.getText().toString().trim();
    }

    public String getMPhone() {
        if (!checkPhone(mPhone.getText().toString().trim())) {
            return "";
        }
        return mPhone.getText().toString().trim();
    }

}

