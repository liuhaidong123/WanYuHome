package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ServerCode;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.adapter.HomeDeviceSettingListAdapter;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.bean.Bean_RoomSetting;
import com.home.wanyu.bean.Bean_deleteScene;
import com.home.wanyu.bean.Bean_getRoomData;
import com.home.wanyu.lzhUtils.MyActivity;

import com.home.wanyu.lzhUtils.UtilsBitmap;
import com.home.wanyu.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//设备选项中房间设置页面
//intent.putExtra("id",listBean.getId()+"");
//        intent.putExtra("name",SceneName);
public class HomeDeviceSettingActivity extends MyActivity {
    private TextView my_user_info_submmit;
    private TextView myActivity_title;
    private int deleteId;//删除的下表
    private PopupWindow pop;//删除设备的弹窗
    private String RoomId;//房间id
    @BindView(R.id.home_device_roomsetting_rela_eidtext) EditText home_sence_scene_rela_eidtext;
    @BindView(R.id.home_device_roomsetting_listview)MyListView home_sence_listview;
    @BindView(R.id.home_device_roomsetting_rela_condition_imageView)ImageView home_device_roomsetting_rela_condition_imageView;//添加按钮的image
    private List<Bean_getRoomData.RoomBean.EquipmentListBean> list;
    private String[]title={"客厅灯","电视","客厅插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    private HomeDeviceSettingListAdapter adapter;

    private File outputImage;//存放选择的图片的文件
    private Boolean isBitChange=false;//是否更换了图片
    private Bitmap bit;
    private String bit64;
    Bean_getRoomData roomData;
    String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1://获取房间信息
                    try{
                        roomData= mGson.gson.fromJson(mTools.mResponStr,Bean_getRoomData.class);
                        if (roomData!=null){
                            String code=roomData.getCode();
                                if ("0".equals(code)){
                                    if (roomData.getRoom()!=null){
                                        if (roomData.getRoom().getEquipmentList()!=null&&roomData.getRoom().getEquipmentList().size()>0){
                                            list.addAll(roomData.getRoom().getEquipmentList());
                                            adapter.notifyDataSetChanged();
                                        }
                                        if (roomData.getRoom().getPictures()!=null&&!"".equals(roomData.getRoom().getPictures())){
                                            Picasso.with(con).load(Ip.imagePath+roomData.getRoom().getPictures()).error(R.mipmap.errorphoto).into(home_device_roomsetting_rela_condition_imageView);
                                        }
                                    }
                                    else {
                                        mToast.Toast(con,"获取房间信息失败");
                                    }

                                }
                               else {
                                    mToast.Toast(con,roomData.getMessage());
                                }


                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
                case 2://房间信息修改
                    try{
                        Bean_RoomSetting roomSetting=mGson.gson.fromJson(resStr,Bean_RoomSetting.class);
                        if (roomSetting!=null){
                            if ("0".equals(roomSetting.getCode())){
                                mToast.Toast(con,"提交成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,roomSetting.getMessage());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.FAILD);
                    }
                    break;
                case 3:
                    try{
                        Bean_deleteScene dele=mGson.gson.fromJson(resStr,Bean_deleteScene.class);
                        if (dele!=null){
                            if ("0".equals(dele.getCode())){
                                mToast.Toast(con,"删除成功");
                                finish();
                            }
                            else {
                                mToast.Toast(con,dele.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.activity_my_user_title);
        myActivity_title= (TextView) findViewById(R.id.myActivity_title);
        my_user_info_submmit= (TextView) findViewById(R.id.my_user_info_submmit);
        my_user_info_submmit.setText("删除");
        initChildView(R.layout.activity_home_device_setting);
        unbinder= ButterKnife.bind(this,ChildView);
        myActivity_title.setText(getIntent().getStringExtra("name"));
        RoomId=getIntent().getStringExtra("id");
        ShowChildView(DEFAULTRESID);

        initView();
        initData();
        mTools=new okhttpTools();
        getSerVerData();//获取单个房间的详细信息
    }

    private void initData() {
        list=new ArrayList<>();
        adapter = new HomeDeviceSettingListAdapter(list,HomeDeviceSettingActivity.this);
        home_sence_listview.setAdapter(adapter);
        home_sence_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showWindow(position);
                return false;
            }
        });
        home_sence_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initView() {
        String name=getIntent().getStringExtra("name");
        home_sence_scene_rela_eidtext.setText(name);
    }
    @OnClick({R.id.home_device_roomsetting_rela_condition_relaLayout,R.id.activity_device_roomsetting_rela_add,R.id.activity_homescene_setting_submit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.home_device_roomsetting_rela_condition_relaLayout:
                mToast.DebugToast(con,"添加照片");
                SearchPhoto();
                break;
            case R.id.activity_device_roomsetting_rela_add:
                mToast.DebugToast(con,"添加设备");
                Intent intent=new Intent();
                intent.putExtra("type","roomAdd");
                Bundle bundle=new Bundle();
                ArrayList<String>listName=new ArrayList<>();
                if (list!=null&&list.size()>0){
                    for (int i=0;i<list.size();i++){
                        listName.add(list.get(i).getName());
                    }
                }
                bundle.putSerializable("data",listName);
                intent.putExtra("data",bundle);
                intent.setClass(con,MyHouseDeviceManagerActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.activity_homescene_setting_submit://提交修改房间信息
                sendRoomMsg();
                break;
        }
    }
    //初始化网络请求
    @Override
    public void getSerVerData() {
        if (!"".equals(RoomId)&&!TextUtils.isEmpty(RoomId)){
            HashMap<String,String>map=new HashMap<>();
            map.put("id",RoomId);map.put("token", UserInfo.userToken);
            mTools.getServerData(handler,1, Ip.serverPath+Ip.interface_HomeScene_getRoom,map,"请求单个房间--");
        }
        else {
            Log.e("HomeDeviceSettingAct-","请求房间信息失败，ronm的Id为空");
        }
    }

    //图库选取
    private void SearchPhoto() {
        if (Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
            }
            else {
                outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"room"+".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            }
        }
        else {
            outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"room"+".jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            if (requestCode==100){//添加设备返回
                Bundle bundle=data.getBundleExtra("data");
                Bean_getRoomData.RoomBean.EquipmentListBean bean= (Bean_getRoomData.RoomBean.EquipmentListBean) bundle.getSerializable("data");
                list.add(bean);
                roomData.getRoom().setEquipmentList(list);
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 20:
                    //此处启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //此处注释掉的部分是针对android 4.4路径修改的一个测试
                    //有兴趣的读者可以自己调试看看
                    intent.setDataAndType(data.getData(), "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 800);//宽度
                    intent.putExtra("outputY", 800);//高度
//                    intent.putExtra("return-data", true);
//                    intent.putExtra("noFaceDetection", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                    startActivityForResult(intent, 12);
                    break;
                case 12:
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
                        if (bitmap!=null){
                            isBitChange=true;
                            bit=bitmap;
                            home_device_roomsetting_rela_condition_imageView.setImageBitmap(bit);
                        }
                        else {
                            Toast.makeText(HomeDeviceSettingActivity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
                            }
                    }
                    catch (Exception e){
                        Toast.makeText(HomeDeviceSettingActivity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 11:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SearchPhoto();
                }
                else {
                    Toast.makeText(HomeDeviceSettingActivity.this,"存储权限被禁用，无法选取图片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //提交房间信息http://192.168.1.55:8080/smarthome/mobileapi/room/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    private void sendRoomMsg(){
        if (roomData==null){
            mToast.Toast(con,"无法获取到此房间到信息，提交失败");
            return;
        }
        String name=home_sence_scene_rela_eidtext.getText().toString();
        if (!"".equals(name)&&!TextUtils.isEmpty(name)){
            Map<String,String>mp=new HashMap<>();
            mp.put("id",roomData.getRoom().getId()+"");
            mp.put("roomName",name);
            mp.put("oid",roomData.getRoom().getOid()+"");
            mp.put("familyId",roomData.getRoom().getFamilyId()+"");
            if (roomData.getRoom().getEquipmentList()!=null&&roomData.getRoom().getEquipmentList().size()>0){
                String json=mGson.gson.toJson(roomData.getRoom().getEquipmentList());
                mp.put("equipmentList",json);
            }
           else {
                mp.put("equipmentList","");
                }
            List<File>li=new ArrayList<>();
            if (isBitChange){
                li.add(outputImage);
            }

            okhttp.getOkhttpFileCallCooki(mp,li,Ip.serverPath+Ip.interface_HomeScene_setRoom,UserInfo.userToken).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(0);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                        Log.i("房间信息修改--",resStr);
                        handler.sendEmptyMessage(2);
                }
            });
        }
        else {
            mToast.Toast(con,"房间名不能为空");
        }

    }

    //删除设备的按钮
    private void showWindow(int pos) {
        deleteId=pos;
        pop = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_device_delete, null);
        TextView pop_delete= (TextView) v.findViewById(R.id.pop_delete);
        TextView pop_cancle= (TextView) v.findViewById(R.id.pop_cancle);
        pop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    list.remove(deleteId);
                roomData.getRoom().setEquipmentList(list);
                    adapter.notifyDataSetChanged();
                    pop.dismiss();
            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.pop);
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

    //删除房间的按钮
    public void Submit(View vi){
        if (roomData==null){
            mToast.Toast(con,"没有查询到该情景的相关信息，无法删除");
            return;
        }
        HashMap<String,String>mp=new HashMap<>();
        mp.put("token",UserInfo.userToken);
        mp.put("ids",roomData.getRoom().getId()+"");
        okhttp.getCall(Ip.serverPath+Ip.interface_deleteRoom,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("删除房间--","名称---"+roomData.getRoom().getRoomName()+"---结果--"+resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }
}
