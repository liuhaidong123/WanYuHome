package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.adapter.PopFamilyListAdapter;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//家人信息编辑
public class MyHouseFamilyEditor extends MyActivity {
    @BindView(R.id.activity_my_house_familyEditor_in_image)RoundImageView activity_my_house_family_info_image;//头像
    @BindView(R.id.activity_my_house_familyEditor_in_name)TextView activity_my_house_family_info_name;//姓名
    @BindView(R.id.activity_my_house_familyEditor_in_tele)TextView activity_my_house_family_info_tele;//电话号
    @BindView(R.id.activity_my_house_familyEditor_se_shebei)ImageView activity_my_house_family_se_shebei;//控制我的设备的权限
    @BindView(R.id.activity_my_house_familyEditor_se_mensuo)ImageView activity_my_house_family_se_mensuo;//控制我的门锁
    @BindView(R.id.activity_my_house_familyEditor_se_tianjia)ImageView activity_my_house_family_se_tianjia;//添加设备到我的家
    @BindView(R.id.activity_my_house_familyEditor_se_shanchu)ImageView activity_my_house_family_se_shanchu;//从我的家删除设备
    @BindView(R.id.activity_my_house_family_editor_image)ImageView activity_my_house_family_editor_image;//下拉框的image
    @BindView(R.id.activity_my_house_family_editor_pri)EditText activity_my_house_family_editor_pri;//显示家人备注的viwe
    private  EditText activity_my_house_family_add_msmCode_p;
    private PopupWindow pop,popWx;
    private int Selectpositon;//选择的备注（0到4，4是自定义）
    @BindArray(R.array.family) String[]family;//家人身份的数组(最后一个是自定义）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_my_house_family_editor);
        setTitle("添加家人");
        ShowChildView(DEFAULTRESID);
        unbinder= ButterKnife.bind(this,ChildView);
        activity_my_house_family_editor_pri.setEnabled(false);
    }

    @Override
    public void getSerVerData() {

    }

    @OnClick({R.id.activity_my_house_familyEditor_se_shebei,R.id.activity_my_house_familyEditor_se_mensuo,R.id.activity_my_house_familyEditor_se_tianjia
            ,R.id.activity_my_house_familyEditor_se_shanchu,R.id.activity_my_house_family_editor_layotut,R.id.activity_my_house_family_editor_submit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_house_familyEditor_se_shebei://控制我的设备的权限
                activity_my_house_family_se_shebei.setSelected(!activity_my_house_family_se_shebei.isSelected());
                break;
            case R.id.activity_my_house_familyEditor_se_mensuo://控制我的门锁
                activity_my_house_family_se_mensuo.setSelected(!activity_my_house_family_se_mensuo.isSelected());
                break;
            case R.id.activity_my_house_familyEditor_se_tianjia://添加设备到我的家
                activity_my_house_family_se_tianjia.setSelected(!activity_my_house_family_se_tianjia.isSelected());
                break;
            case R.id.activity_my_house_familyEditor_se_shanchu://从我的家删除设备
                activity_my_house_family_se_shanchu.setSelected(!activity_my_house_family_se_shanchu.isSelected());
                break;
            case R.id.activity_my_house_family_editor_layotut://下拉选择备注方式
                activity_my_house_family_editor_image.setSelected(true);
                showWindow();
                break;
            case R.id.activity_my_house_family_editor_submit:
                mToast.Toast(con,"确定");
                break;
        }
    }

    private void showWindow() {
        pop = new PopupWindow();
        View v = LayoutInflater.from(con).inflate(R.layout.pop_family, null);
        ListView pop_family_list= (ListView) v.findViewById(R.id.pop_family_list);
        PopFamilyListAdapter adapter=new PopFamilyListAdapter(con,family);
        pop_family_list.setAdapter(adapter);
        pop_family_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position!=family.length-1){
                    activity_my_house_family_editor_pri.setEnabled(false);
                    activity_my_house_family_editor_pri.setText(family[position]);
                    Selectpositon=position;
                    pop.dismiss();
                }
                else {
                    Selectpositon=position;
                    activity_my_house_family_editor_pri.setEnabled(true);
                    pop.dismiss();
//                    ShowWindow();
                    }
            }

        });
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_my_house_family_editor_layotut);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        getWindow().setAttributes(params);

        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(parent.getMeasuredWidth());

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(v);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup4_anim);
        pop.showAsDropDown(parent, 0, 2);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activity_my_house_family_editor_image.setSelected(false);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

//    //自定义的输入框
//    private void ShowWindow() {
//
//        popWx = new PopupWindow();
//        View v = LayoutInflater.from(con).inflate(R.layout.pop_family_editor, null);
//        TextView activity_my_house_family_add_getSmSCode_p= (TextView) v.findViewById(R.id.activity_my_house_family_add_getSmSCode_p);
//        activity_my_house_family_add_msmCode_p= (EditText) v.findViewById(R.id.activity_my_house_family_add_msmCode_p);
//        activity_my_house_family_add_getSmSCode_p.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text=activity_my_house_family_add_msmCode_p.getText().toString();
//                if (!"".equals(text)&&!TextUtils.isEmpty(text)){
//                    Selectpositon=family.length-1;
//                    activity_my_house_family_editor_pri.setText(text);
//                    popWx.dismiss();
//                }
//                else {
//                    mToast.Toast(con,"输入信息不正确");
//                }
//            }
//        });
//        RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_my_house_family_editor);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.6f;
//        getWindow().setAttributes(params);
//
//        popWx.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popWx.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//
//        popWx.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        popWx.setContentView(v);
//        popWx.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
//        popWx.setTouchable(true);
//        popWx.setFocusable(true);
//        popWx.setOutsideTouchable(true);
//
//        popWx.setAnimationStyle(R.style.popup3_anim);
//        popWx.showAtLocation(parent, Gravity.BOTTOM,0,0);
//        popWx.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
//            }
//        });
//
//    }
}
