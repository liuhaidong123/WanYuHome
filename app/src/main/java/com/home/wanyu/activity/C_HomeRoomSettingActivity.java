package com.home.wanyu.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.C_Model.C_HomeRoomSettingModel;
import com.home.wanyu.C_View.C_HomeRoomSettingView;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PermissionCheck.IntentAction;
import com.home.wanyu.PermissionCheck.PermissionCheck;
import com.home.wanyu.PermissionCheck.PermissionFaile;
import com.home.wanyu.PermissionCheck.PermissionName;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.BitmapUtils;
import com.home.wanyu.lzhUtils.WindowUtils;
import com.home.wanyu.myUtils.CMyActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//添加／修改房间
public class C_HomeRoomSettingActivity extends CMyActivity {
    public final int REQUEST_PERMI_CODE=100;//权限请求码
    public final int REQUEST_CODE=200;//activity回掉码
    public final int REQUEST_CURT=300;//图片裁剪
    int defaultResId=R.mipmap.homebackground;//默认的房间背景图片的id
    C_HomeRoomSettingView c_homeDeviceSettingView;
    C_HomeRoomSettingModel roomSettingModel;
    File outImage;//房间图片
    boolean isBitmapChange=false;//是否更换了房间图片
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
                getRoomImage();
                break;
        }
    }
    @Override
    public void getServerData() {

    }
    //获取房间照片
    public void getRoomImage(){
        if (Build.VERSION.SDK_INT>=23){
            if (PermissionCheck.checkStroagePri(this,new String[]{PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE})){
                IntentAction.getInstance().getImage(this,getFile(),REQUEST_CODE);
            }
            else {
                requestPermissions(new String[]{PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE},REQUEST_PERMI_CODE);
            }
        }
        else {
            IntentAction.getInstance().getImage(this,getFile(),REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMI_CODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                IntentAction.getInstance().getImage(this,getFile(),REQUEST_CODE);
            }
            else {
                mToast.Toast(this, PermissionFaile.READ_WRITE_STROAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE://获取照片
                    IntentAction.getInstance().curtImage(data,700,1200,outImage,this,REQUEST_CURT);
                    break;
                case REQUEST_CURT://图片裁剪
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outImage.getAbsolutePath());
                        if (bitmap!=null){
                            isBitmapChange=true;
                            c_homeDeviceSettingView.setImageBack(bitmap);
                        }
                        else {
                            Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    //设置file
    public File getFile(){
        outImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"room"+".jpg");
        if (outImage.exists()) {
            outImage.delete();
        }
        try {
            outImage.createNewFile();
        } catch (IOException e) {
            Log.e("----",e.toString());
            e.printStackTrace();
        }
        return outImage;
    }
}
