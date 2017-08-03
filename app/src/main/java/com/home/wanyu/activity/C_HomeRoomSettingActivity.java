package com.home.wanyu.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.C_Model.C_HomeRoomSettingModel;
import com.home.wanyu.C_View.C_HomeRoomSettingView;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PermissionCheck.IntentAction;
import com.home.wanyu.PermissionCheck.PermissionCheck;
import com.home.wanyu.PermissionCheck.PermissionFile;
import com.home.wanyu.PermissionCheck.PermissionName;
import com.home.wanyu.R;
import com.home.wanyu.myUtils.CMyActivity;
import com.home.wanyu.myview.MyGridView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//添加／修改房间
public class C_HomeRoomSettingActivity extends CMyActivity {
    final int REQUESTCODE=100;//上文的请求吗
//    int defaultResId=R.mipmap.homebackground;//默认的房间背景图片的id
    C_HomeRoomSettingView c_homeDeviceSettingView;
    C_HomeRoomSettingModel roomSettingModel;
    File outImage;//房间图片
    boolean isBitmapChange=false;//是否更换了房间图片
    @BindView(R.id.c_home_device_image)ImageView c_home_device_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__home_device_setting);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        titleButton= (TextView) findViewById(R.id.titleButton);
        initTitle("房间设置","确定",false);
        unbinder= ButterKnife.bind(this);
        c_homeDeviceSettingView=new C_HomeRoomSettingView(this);
        roomSettingModel=new C_HomeRoomSettingModel();
    }
    @OnClick({R.id.home_device_imageSelect})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.home_device_imageSelect://选择照片的按钮
                startActivityForResult(new Intent(this,RoomPictureSelectActivity.class),REQUESTCODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUESTCODE){
                int tp=data.getIntExtra("type",-1);
                switch (tp){
                    case 0://默认图片1
                        c_home_device_image.setImageResource(R.mipmap.pictureone);
                        break;
                    case 1://默认图片2
                        c_home_device_image.setImageResource(R.mipmap.picturetwo);
                        break;
                    case 2://拍照或者相册选取的图片
                        String pth=data.getStringExtra("image");
                        isBitmapChange=true;
                        outImage=new File(pth);
                        if (outImage.isFile()){
                            Log.e("保存成功---",outImage.getAbsolutePath());
                        }
                        else {
                            Log.e("保存失败---","----");
                        }
                        Bitmap bt=BitmapFactory.decodeFile(outImage.getAbsolutePath());
                        if (bt==null){
                            mToast.Toast(this,"图片转换出现错误");
                        }
                        else {
                            c_home_device_image.setImageBitmap(bt);
                        }
                        break;
                }


        }
    }
}
