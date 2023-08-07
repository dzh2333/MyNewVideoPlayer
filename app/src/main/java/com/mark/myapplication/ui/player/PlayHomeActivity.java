package com.mark.myapplication.ui.player;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mark.myapplication.R;
import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.bean.MediaType;
import com.mark.myapplication.databinding.ActivityPlayerMainBinding;
import com.mark.myapplication.player.core.PlayManager;
import com.mark.myapplication.player.core.PlayerCore;
import com.mark.myapplication.ui.BaseActivity;
import com.mark.myapplication.ui.view.DMarkVideo;
import com.mark.myapplication.util.AppExecutors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PlayHomeActivity extends BaseActivity {

    private ActivityPlayerMainBinding activityPlayerMainBinding;
    private static int mPlayIndex = 0;
    private static List<MediaBean> playMediaBeans = new ArrayList<>();
    private boolean firstStart  = true;

    public static void lunchActivity(Context context, List<MediaBean> mediaBeans, int playIndex){
        Intent intent = new Intent(context,  PlayHomeActivity.class);
        playMediaBeans = mediaBeans;
        mPlayIndex = playIndex;
        context.startActivity(intent);
    }

    @Override
    public void initLayout() {
        activityPlayerMainBinding = ActivityPlayerMainBinding.inflate(getLayoutInflater());
        setContentView(activityPlayerMainBinding.getRoot());
        activityPlayerMainBinding.glView.setVideoViewLayout(1920);
    }

    @Override
    public void initView() {
        activityPlayerMainBinding.glView.setClickBack(view -> {
            if (view.getId() == R.id.include_player_top_toolbar_back){
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void play(){
        PlayManager.getInstance().startPlay(playMediaBeans, mPlayIndex, activityPlayerMainBinding.glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstStart){
            play();
            firstStart = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayManager.getInstance().pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayManager.getInstance().stop();
    }

    @Override
    public void initData() {
    }
}
