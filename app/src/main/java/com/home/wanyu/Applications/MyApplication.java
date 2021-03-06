package com.home.wanyu.Applications;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wanyu on 2017/5/26.
 */

public class MyApplication extends Application{
    public static Activity activityCurrent;
    private static List<Activity> list;
    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 14) {//4.0以上
            list = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= 14) {//4.0以上
                registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        list.add(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        activityCurrent = activity;
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        list.remove(activity);
                    }
                });
            }

        }

        JPushInterface.setDebugMode(true);//发不时设为false
        JPushInterface.init(getApplicationContext());
    }


    public static void removeActivity() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Activity activity = list.get(i);
                Log.i("remove-名字--", activity.getClass().getSimpleName());
                activity.finish();
            }
            list.clear();
        }

    }
}
