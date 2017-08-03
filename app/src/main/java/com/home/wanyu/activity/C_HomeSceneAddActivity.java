package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.home.wanyu.C_Model.C_HomeSceneAddModel;
import com.home.wanyu.C_View.C_HomeSceneAddView;
import com.home.wanyu.Icons.SceneIcons;
import com.home.wanyu.R;
import com.home.wanyu.mEmeu.SceneStartMode;
import com.home.wanyu.myUtils.CMyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加情景
public class C_HomeSceneAddActivity extends CMyActivity implements C_HomeSceneAddView.SceneModelSelect{
    final int REQUESTCODE=100;
    final int RESULTCODE=200;
    final String RESULT="name";
    C_HomeSceneAddView c_homeSceneSettingView;//view显示层
    C_HomeSceneAddModel c_homeSceneSettingModel;//数据处理
    SceneStartMode selectMode=SceneStartMode.BUTTON;//当前选中的启动方式(默认是一键启动)
    @BindView(R.id.c_homescenesetting_imageSelect)ImageView c_homescenesetting_imageSelect;//情景图标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_homescenesetting);
        unbinder= ButterKnife.bind(this);
        c_homeSceneSettingView=new C_HomeSceneAddView(this,this);
        c_homeSceneSettingModel=new C_HomeSceneAddModel();
    }
    //提交按钮
    @Override
    public void Submit(View vi) {
        super.Submit(vi);
    }
    @OnClick({R.id.home_sence_scene_rela_condition_relaLayout_c,R.id.c_homescenesetting_imageSelect})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.home_sence_scene_rela_condition_relaLayout_c://弹出启动选择启动方式的view
                c_homeSceneSettingView.showWindowMode();//弹出选择启动方式的view
                break;
            case R.id.c_homescenesetting_imageSelect:
                startActivityForResult(new Intent(this,ScenePictureSelectActivity.class),REQUESTCODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUESTCODE){
            if (resultCode==RESULTCODE){
                 int pos=data.getIntExtra(RESULT,-1);
                if (pos==-1){
                    Log.e("C_HomeSceneAddActivity","error:返回的情景图标的下表不正确");
                }
                else {
                    c_homescenesetting_imageSelect.setImageResource(SceneIcons.SceneIcons[pos]);
                }
            }
        }
    }

    @Override
    public void getServerData() {

    }
    /**
     * 启动方式选择后的回调
     * model：启动方式BUTTON，TIME，LOCATION，UNKONE
     *一键启动方式下其余四个参数的值均为-1
     * TIME（定时启动方式下）参数hours，minute表示重复的小时与分钟，参数repeat表示重复的天数（周一，周二，周三，周四，周五，周六，周末等），location参数为-1
     * LOCATION启动方式下参数hours，minute，repeat均为-1，参数location表示启动的距离
     * */
    @Override
    public void modelSelect(SceneStartMode mode, String hours, String Minute, String repeat, String location) {

    }
}
