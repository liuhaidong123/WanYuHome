package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.wanyu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */

public class HomeFragment extends Fragment {
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
        ButterKnife.bind(this,vi);
        return vi;
    }
}
