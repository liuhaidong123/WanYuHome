package com.home.wanyu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.CommercialAddressHositoryAda;
import com.home.wanyu.bean.Hository;
import com.home.wanyu.myUtils.SearchAreaDB;
import com.home.wanyu.myUtils.SearchCityDB;

import java.util.ArrayList;
import java.util.List;

public class HouseSearchAddessAndAreaActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mCancle_btn;
    private ImageView mDelete;
    private EditText mEdit;

    private RelativeLayout mNoResult_rl;
    private TextView mNoResult_tv;
    private ListView mListview;
    private CommercialAddressHositoryAda mAdapter;
    private List<Hository> mCityList = new ArrayList<>();
    private List<Hository> mAreaList = new ArrayList<>();

    private SearchCityDB mCityBD;
    private SQLiteDatabase mCitySqliteDB;
    private SearchAreaDB mAreaDB;
    private SQLiteDatabase mAreaSqliteDB;
    private int mType;
private String mCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_search_addess_and_area);
        initView();
    }

    private void initView() {
        mType = getIntent().getIntExtra("type", -1);
        mCancle_btn = (TextView) findViewById(R.id.search_cancle);
        mCancle_btn.setOnClickListener(this);


        //城市数据库
        mCityBD = new SearchCityDB(this, "city_bd", null, 1);
        mCitySqliteDB = mCityBD.getWritableDatabase();

        //小区户型数据库
        mAreaDB = new SearchAreaDB(this, "area_db", null, 1);
        mAreaSqliteDB = mAreaDB.getWritableDatabase();

        //查看city表中所有数据
        Cursor cursor = mCitySqliteDB.rawQuery("select * from city order by _id desc", null);
        while (cursor.moveToNext()) {
            String cityname = cursor.getString(cursor.getColumnIndex("cityname"));
            Log.e("城市地址：", "------------" + cityname);
            mCityList.add(new Hository(cityname));
        }
        cursor.close();

        //查看小区户型表中所有数据
        Cursor cursor1 = mAreaSqliteDB.rawQuery("select * from area order by _id desc", null);
        while (cursor1.moveToNext()) {
            String areaname = cursor1.getString(cursor1.getColumnIndex("areaname"));
            Log.e("小区户型地址：", "------------" + areaname);
            mAreaList.add(new Hository(areaname));
        }
        cursor1.close();

        mListview = (ListView) findViewById(R.id.house_search_listview);
        if (mType == 1) {//搜索城市的历史
            mAdapter = new CommercialAddressHositoryAda(this, mCityList);
            mListview.setAdapter(mAdapter);
        } else if (mType == 2) {//搜索小区户型的历史
            mAdapter = new CommercialAddressHositoryAda(this, mAreaList);
            mListview.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, "无法获取搜索的是城市还是小区信息", Toast.LENGTH_SHORT).show();
        }

        //清空历史数据
        mDelete = (ImageView) findViewById(R.id.house_delete);
        mDelete.setOnClickListener(this);
        //、输入城市或者小区
        mEdit = (EditText) findViewById(R.id.search_city_or_area_edit);
        if (mType==1){
            mEdit.setHint(R.string.city_hint);
        }else if (mType==2){
            mEdit.setHint(R.string.area_hint);
        }else {
            mEdit.setHint(R.string.no_hint);
        }
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //按下的时候会执行：手指按下和手指松开俩个过程
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //do something;

                        if (!getContent().equals("")) {
                            if (mType == 1) {//城市
                                //将搜索的内容存到数据库中，如果搜索的是城市，搜索到以后将城市传回去
                                ContentValues valuesDrug = new ContentValues();
                                valuesDrug.put("cityname", getContent());
                                mCitySqliteDB.insert("city", null, valuesDrug);
                            } else if (mType == 2) {//小区或户型
                                //将搜索的内容存到数据库中，如果搜索的是小区，搜索到以后跳转到小区页面HouseSearchAreaListActivity
                                ContentValues valuesDrug = new ContentValues();
                                valuesDrug.put("areaname", getContent());
                                mAreaSqliteDB.insert("area", null, valuesDrug);
                                mCity=getIntent().getStringExtra("city");

                                Intent intent=new Intent(HouseSearchAddessAndAreaActivity.this,HouseSearchAreaListActivity.class);
                                intent.putExtra("city",mCity);
                                intent.putExtra("searchArea",mEdit.getText().toString());
                                startActivity(intent);

                            } else {
                                Toast.makeText(HouseSearchAddessAndAreaActivity.this, "无法获取搜索的是城市还是小区信息", Toast.LENGTH_SHORT).show();
                            }

                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(HouseSearchAddessAndAreaActivity.this, "请输入内容哦", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mCancle_btn.getId()) {
            finish();
        } else if (id == mDelete.getId()) {
            //清空搜索城市的历史
            if (getIntent().getIntExtra("type", -1) == 1) {
                mCitySqliteDB.execSQL("delete from city");
                mCityList.clear();
                mAdapter.notifyDataSetChanged();
                //清空搜索小区户型的历史
            } else if (getIntent().getIntExtra("type", -1) == 2) {
                mAreaSqliteDB.execSQL("delete from area");
                mAreaList.clear();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public String getContent() {
        if (mEdit.getText().toString().trim().equals("")) {
            return "";
        }
        return mEdit.getText().toString().trim();
    }
}
