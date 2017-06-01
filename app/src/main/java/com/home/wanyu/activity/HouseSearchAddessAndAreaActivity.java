package com.home.wanyu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.apater.CommercialAddressHositoryAda;
import com.home.wanyu.apater.HouseSearchAreaAda;
import com.home.wanyu.apater.HouseSearchCityAda;
import com.home.wanyu.bean.Hository;
import com.home.wanyu.bean.HouseSearchCity.Result;
import com.home.wanyu.bean.HouseSearchCity.Root;
import com.home.wanyu.myUtils.SearchAreaDB;
import com.home.wanyu.myUtils.SearchCityDB;

import java.util.ArrayList;
import java.util.List;

public class HouseSearchAddessAndAreaActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mCancle_btn;
    private ImageView mDelete;
    private EditText mEdit;

    private RelativeLayout mHository_rl;
    private RelativeLayout mNoResult_rl;
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

    private ListView mSearch_ListView;
    private HouseSearchCityAda mCityAda;
    private HouseSearchAreaAda mAreaAda;
    private List<Result> mSearchCityList = new ArrayList<>();
    private List<com.home.wanyu.bean.HouseSearchArea.Result> mSearchAreaList = new ArrayList<>();

    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 144) {//搜索城市
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.HouseSearchCity.Root) {
                    com.home.wanyu.bean.HouseSearchCity.Root root = (Root) o;
                    if (root.getResult() != null) {
                        mSearchCityList = root.getResult();
                        if (mSearchCityList.size() != 0) {
                            mSearch_ListView.setVisibility(View.VISIBLE);
                            mCityAda = new HouseSearchCityAda(HouseSearchAddessAndAreaActivity.this, mSearchCityList);
                            mSearch_ListView.setAdapter(mCityAda);
                            mHository_rl.setVisibility(View.GONE);
                            mNoResult_rl.setVisibility(View.GONE);
                            mListview.setVisibility(View.GONE);
                        } else {
                            mSearch_ListView.setVisibility(View.GONE);
                            mHository_rl.setVisibility(View.GONE);
                            mNoResult_rl.setVisibility(View.VISIBLE);
                            mListview.setVisibility(View.GONE);
                        }
                    }
                }
            } else if (msg.what == 145) {//搜索小区
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.HouseSearchArea.Root) {
                    com.home.wanyu.bean.HouseSearchArea.Root root = (com.home.wanyu.bean.HouseSearchArea.Root) o;
                    if (root.getResult() != null) {
                        mSearchAreaList = root.getResult();
                        if (mSearchAreaList.size() != 0) {
                            mSearch_ListView.setVisibility(View.VISIBLE);
                            mAreaAda = new HouseSearchAreaAda(HouseSearchAddessAndAreaActivity.this, mSearchAreaList);
                            mSearch_ListView.setAdapter(mAreaAda);
                            mHository_rl.setVisibility(View.GONE);
                            mNoResult_rl.setVisibility(View.GONE);
                            mListview.setVisibility(View.GONE);
                        } else {
                            mSearch_ListView.setVisibility(View.GONE);
                            mHository_rl.setVisibility(View.GONE);
                            mNoResult_rl.setVisibility(View.VISIBLE);
                            mListview.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_search_addess_and_area);
        mHttptools = HttpTools.getHttpToolsInstance();
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
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mType == 1) {//搜索城市
                    mEdit.setText(mCityList.get(position).getAddress());
                    mHttptools.getHouseCityList(handler, getContent());//搜索城市
                } else if (mType == 2) {//搜索小区
                    mCity = getIntent().getStringExtra("city");
                    mEdit.setText(mAreaList.get(position).getAddress());
                    mHttptools.getHouseAreaByCity(handler, getContent(), mCity);//根据城市搜索 小区
                }


            }
        });
        if (mType == 1) {//搜索城市的历史
            mAdapter = new CommercialAddressHositoryAda(this, mCityList);
            mListview.setAdapter(mAdapter);
        } else if (mType == 2) {//搜索小区户型的历史

            mAdapter = new CommercialAddressHositoryAda(this, mAreaList);
            mListview.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, "无法获取搜索的是城市还是小区信息", Toast.LENGTH_SHORT).show();
        }

        mHository_rl = (RelativeLayout) findViewById(R.id.house_delete_rl);
        mNoResult_rl = (RelativeLayout) findViewById(R.id.show_result);
        //清空历史数据
        mDelete = (ImageView) findViewById(R.id.house_delete);
        mDelete.setOnClickListener(this);
        //输入城市或者小区
        mEdit = (EditText) findViewById(R.id.search_city_or_area_edit);
        if (mType == 1) {
            mEdit.setHint(R.string.city_hint);
        } else if (mType == 2) {
            mEdit.setHint(R.string.area_hint);
        } else {
            mEdit.setHint(R.string.no_hint);
        }
        //点击搜索
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
                                mHttptools.getHouseCityList(handler, getContent());//搜索城市

                            } else if (mType == 2) {//小区或户型
                                //将搜索的内容存到数据库中，如果搜索的是小区，搜索到以后跳转到小区页面HouseSearchAreaListActivity
                                ContentValues valuesDrug = new ContentValues();
                                valuesDrug.put("areaname", getContent());
                                mAreaSqliteDB.insert("area", null, valuesDrug);
                                mCity = getIntent().getStringExtra("city");
                                mHttptools.getHouseAreaByCity(handler, getContent(), mCity);//根据城市搜索 小区
                                Log.e("城市=",mCity);
                                Log.e("小区=",getContent());

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

        //搜索框文字变化
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    mSearch_ListView.setVisibility(View.GONE);
                    mHository_rl.setVisibility(View.VISIBLE);
                    mNoResult_rl.setVisibility(View.GONE);
                    mListview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //搜索到的城市和小区Listview
        mSearch_ListView = (ListView) findViewById(R.id.search_city_result_listview);
        mSearch_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mType == 1) {//点击搜索到城市
                    Intent intent = getIntent();
                    intent.putExtra("cityname", mSearchCityList.get(position).getAreaName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {//点击搜索到小区
                    Intent intent = new Intent(HouseSearchAddessAndAreaActivity.this, HouseSearchAreaListActivity.class);
                    intent.putExtra("city", mCity);
                    intent.putExtra("searchArea", mSearchAreaList.get(position).getRname());
                    startActivity(intent);
                    finish();
                }

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
