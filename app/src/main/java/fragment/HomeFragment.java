package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.wanyu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/5/2.
 */
//家
public class HomeFragment extends Fragment {
    private Unbinder unbinder;
    private final int QJSelect=0;//选中情景
    private final int SBSelect=1;//选中设备
    @BindView(R.id.fragment_home_top_qj) TextView fragment_home_top_qj;
    @BindView(R.id.fragment_home_top_shebei) TextView fragment_home_top_shebei;
    public static HomeFragment mFragment;
    public static HomeFragment getInstance(){
        if (mFragment==null){
            mFragment=new HomeFragment();
        }
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_home,null);
        unbinder= ButterKnife.bind(this,vi);
        initView();
        return vi;
    }

    private void initView() {
        setSelect(QJSelect);
    }
    public void setSelect(int pos){
        switch (pos){
            case QJSelect:

                break;
            case SBSelect:
                break;
        }
    }

    @OnClick({R.id.fragment_home_top_qj,R.id.fragment_home_top_shebei})
    public void click(View v){
        switch (v.getId()){
            case R.id.fragment_home_top_qj:
                fragment_home_top_qj.setSelected(true);
                fragment_home_top_shebei.setSelected(false);
                break;
            case R.id.fragment_home_top_shebei:
                fragment_home_top_qj.setSelected(false);
                fragment_home_top_shebei.setSelected(true);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
