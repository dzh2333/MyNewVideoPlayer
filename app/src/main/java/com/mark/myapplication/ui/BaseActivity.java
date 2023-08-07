package com.mark.myapplication.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;

import com.mark.myapplication.R;
import com.mark.myapplication.databinding.ActivityMainBinding;
import com.mark.myapplication.event.BaseEvent;
import com.mark.myapplication.event.BaseStickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initLayout();
        initView();
        initData();
        initStatusbar();
    }

    public abstract void initLayout();
    public abstract void initView();
    public abstract void initData();

    private void initStatusbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_500));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event){
        dispatchEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStickyEvent(BaseStickyEvent event){
        dispatchStickyEvent(event);
    }

    public void dispatchEvent(BaseEvent event){
    }
    public void dispatchStickyEvent(BaseStickyEvent event){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
