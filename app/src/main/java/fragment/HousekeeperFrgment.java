package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.wanyu.R;

import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/2.
 */
//物业管家
public class HousekeeperFrgment extends Fragment{
    private static HousekeeperFrgment mFragment;
    public static HousekeeperFrgment getInstance(){
        if (mFragment==null){
            mFragment=new HousekeeperFrgment();
        }
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_housekeeper,null);
        ButterKnife.bind(this,vi);
        return vi;
    }
}
