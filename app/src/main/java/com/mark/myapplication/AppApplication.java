package com.mark.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.db.DBAdapter;
import com.mark.myapplication.db.MyDBOpenHelper;
import com.mark.myapplication.player.core.PlayManager;

public class AppApplication extends Application {

    private static Application mApplication;

    public static Application getApplication(){
        return mApplication;
    }

    public static DBAdapter dbAdapter;
    private final static int DBVersion = 1;


    public static Activity topActivity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        PlayManager.getInstance().init(this);
        dbAdapter = new DBAdapter(this);
        dbAdapter.createDB();

        registerActivityListener();
    }

    private void registerActivityListener(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                topActivity = activity;
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }
}
