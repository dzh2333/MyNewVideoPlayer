package com.mark.myapplication.ui.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mark.myapplication.AppApplication;
import com.mark.myapplication.R;
import com.mark.myapplication.bean.PlayerEvent;
import com.mark.myapplication.player.core.PlayManager;
import com.mark.myapplication.player.core.PlayerCore;
import com.mark.myapplication.ui.fragment.LeftFragment;
import com.mark.myapplication.ui.fragment.RightFragment;
import com.mark.myapplication.util.AppExecutors;
import com.mark.myapplication.util.DateUtils;
import com.mark.myapplication.util.PhoneLightUtil;
import com.mark.myapplication.util.VolumeUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

public class DMarkVideo extends ConstraintLayout {

    private static final int DMarkNV_SHOW = 0;
    private static final int DMarkNV_HIDE = 1;

    private LeftFragment leftFragment;
    private RightFragment rightFragment;

    private LinearLayout videoContainer;
    private DMarkVideoView dMarkVideoView;

    private ImageView playOrPause;
    private ImageView lastButton;
    private ImageView nextButton;
    private AppCompatSeekBar appCompatSeekBar;

    private View rootView;
    private ViewGroup nvViewGroup;
    private ViewGroup topToolbar;
    private long totleTime = 0;

    private int nowNight = 0;

    private int mVideoWidth;
    private int mVideoHeight;

    private volatile long clickTime;
    private volatile int nowShowStatus = DMarkNV_SHOW;

    private WeakReference<Context> reference;

    public DMarkVideo(Context context, boolean smbFile){
        super(context);
        reference = new WeakReference<>(context);
        initView(reference.get());
        String
    }

    public DMarkVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        reference = new WeakReference<>(context);
        initView(reference.get());
    }

    public DMarkVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        reference = new WeakReference<>(context);
        initView(reference.get());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(Context context){
        rootView = LayoutInflater.from(context).inflate(R.layout.player_main_view, this);
        leftFragment = rootView.findViewById(R.id.player_home_play_left);
        rightFragment = rootView.findViewById(R.id.player_home_play_right);
        nvViewGroup = (ViewGroup) rootView.findViewById(R.id.player_home_play_nv);
        topToolbar = (ViewGroup) rootView.findViewById(R.id.player_home_play_top);
        appCompatSeekBar = rootView.findViewById(R.id.player_home_play_seekbar);
        lastButton = rootView.findViewById(R.id.player_home_play_last);
        nextButton = rootView.findViewById(R.id.player_home_play_next);
        videoContainer = rootView.findViewById(R.id.player_home_videoview_container);

        nvViewGroup.setVisibility(View.VISIBLE);
        topToolbar.setVisibility(VISIBLE);

        ContentResolver contentResolver = AppApplication.getApplication().getContentResolver();
        nowNight = Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, 125);

        appCompatSeekBar.setMax(1000);
        appCompatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double process = (double) seekBar.getProgress() / seekBar.getMax();
                AppExecutors.getInstance().networkIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        PlayManager.getInstance().seekTo(process);
                    }
                });
            }
        });

        AppCompatImageView imageView = topToolbar.findViewById(R.id.include_player_top_toolbar_back);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBack != null){
                    clickBack.click(imageView);
                }
            }
        });

        leftFragment.setOnTouchListener(new OnTouchListener() {
            int lastX, lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        clickTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        if (dy >= 100){
                            nowNight = PhoneLightUtil.setLight(false, nowNight);
                        }else if (dy <= -100){
                            nowNight = PhoneLightUtil.setLight(true, nowNight);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        long nowTime = System.currentTimeMillis();
                        if ((nowTime - clickTime) <= 800){
                            showNotificationOrPause();
                        }
                        clickTime = nowTime;
                        break;
                }
                return true;
            }
        });

        rightFragment.setOnTouchListener(new OnTouchListener() {
            int lastX, lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        if (dy >= 100){
                            VolumeUtil.reduceVolume();
                        }else if (dy <= -100){
                            VolumeUtil.addVolume();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        long nowTime = System.currentTimeMillis();
                        if ((nowTime - clickTime) <= 800){
                            showNotificationOrPause();
                        }
                        clickTime = nowTime;
                        break;
                }
                return true;
            }
        });
        initNV();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        PlayManager.getInstance().registerCompleteCallBack(new PlayManager.PlayCompleteCallBack() {
            @Override
            public void complete() {
                resetUI();
            }
        });
        PlayManager.getInstance().registerPlayCallBack(new PlayManager.PlayCallBack() {
            @Override
            public void seekBack(long time) {
                long maxProcess = appCompatSeekBar.getMax();
                long totalTime = PlayManager.getInstance().getPlayBean().getTotalTime();
                if (totalTime == 0){
                    return;
                }
                int num = (int) (maxProcess * time/totalTime);
                appCompatSeekBar.setProgress(num);
            }
            @Override
            public void totalTimeBack(long time) {
                TextView textView = nvViewGroup.findViewById(R.id.player_home_play_seekbar_totle_pos);
                textView.setText(DateUtils.formatTime(time));
            }
            @Override
            public void playTimeCallBack(long time) {
                TextView textView = nvViewGroup.findViewById(R.id.player_home_play_seekbar_curr_pos);
                textView.setText(DateUtils.formatTime(time));
            }

            @Override
            public void videoPaused() {
                playOrPause.setImageResource(R.drawable.player_play);
            }

            @Override
            public void videoResume() {
                playOrPause.setImageResource(R.drawable.player_pause);
            }

            @Override
            public void readyPlay() {
                if (dMarkVideoView != null){
                    dMarkVideoView.setVisibility(VISIBLE);
                }
                showURLInfo();
            }
        });
    }

    private void resetUI(){
        if (dMarkVideoView != null){
            dMarkVideoView.setVisibility(GONE);
        }
        TextView textView = nvViewGroup.findViewById(R.id.player_home_play_seekbar_totle_pos);
        textView.setText("00:00");
        TextView textView2 = nvViewGroup.findViewById(R.id.player_home_play_seekbar_curr_pos);
        textView2.setText("00:00");
        appCompatSeekBar.setProgress(0);
    }

    /**
     * 展示或者隐藏
     */
    private void showNotificationOrPause(){
        if (nowShowStatus == DMarkNV_SHOW){
            //隐藏起来
            nowShowStatus = DMarkNV_HIDE;
            nvViewGroup.setVisibility(View.GONE);
            topToolbar.setVisibility(GONE);
        }else {
            //展示出来
            nowShowStatus = DMarkNV_SHOW;
            nvViewGroup.setVisibility(View.VISIBLE);
            topToolbar.setVisibility(VISIBLE);
        }
    }

    private void showURLInfo(){
        TextView textView = rootView.findViewById(R.id.include_player_top_toolbar_title);
        if (PlayManager.getInstance().getPlayBean() != null){
            textView.setText(PlayManager.getInstance().getPlayBean().getUrl());
        }
    }

    private void initNV(){
        playOrPause = rootView.findViewById(R.id.player_home_play_or_pause);
        playOrPause.setOnClickListener(v -> {
            boolean videoIsPause = PlayerCore.getPauseStatus();
            if (!videoIsPause){
                playOrPause.setImageResource(R.drawable.player_play);
            }else {
                playOrPause.setImageResource(R.drawable.player_pause);
            }
            PlayManager.getInstance().pauseOrResume();
        });
        lastButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayManager.getInstance().lastVideo();
            }
        });
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayManager.getInstance().nextVideo();
            }
        });
    }


    /**
     * JNI调用改变Seek进度
     * @param seek
     */
    public static void changeSeek(long seek){
        PlayManager.getInstance().notifiSeekTime(seek);
    }

    public static void setVideoViewLayoutParams( final int width, final int height){
        PlayManager.getInstance().getPlayBean().setWidth(width);
        PlayManager.getInstance().getPlayBean().setHeight(height);
    }

    public static void setTotalTime(int time){
        PlayManager.getInstance().notifiTotalTime(time);
    }

    public void setVideoViewLayout(final int width){
        videoContainer.removeView(dMarkVideoView);
        dMarkVideoView = new DMarkVideoView(getContext());
        LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dMarkVideoView.setLayoutParams(layoutParams);
        videoContainer.addView(dMarkVideoView);
    }


    /**
     * 播放结束
     */
    public static void playOver(){
        PlayManager.getInstance().notificCompleteCallback();
    }

    public void setClickBack(ClickBack clickBack) {
        this.clickBack = clickBack;
    }
    private ClickBack clickBack;
    public interface ClickBack{
        void click(View view);
    }
}
