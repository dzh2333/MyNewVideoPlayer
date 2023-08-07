package com.mark.myapplication.player.core;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.exception.PlayException;
import com.mark.myapplication.util.AppExecutors;
import com.mark.myapplication.util.PhoneLightUtil;
import com.mark.myapplication.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

public class PlayManager {

    private int mPlayIndex;
    private List<MediaBean> mPlayList;
    private MediaBean playBean;
    private View renderView;

    private IPlay iPlay;
    private Application mApplication;

    private View videoView;

    private PlayStatus mPlayStatus = PlayStatus.PLAYER_STATUS_IDLE;

    private static HashSet<PlayCompleteCallBack> completeCallBacks;
    private static HashSet<PlayCallBack> playSeekCallBacks;

    private static PlayManager instance;
    private PlayManager(){}

    public static PlayManager getInstance(){
        if (instance == null){
            synchronized (PlayManager.class){
                if (instance == null){
                    instance = new PlayManager();

                }
            }
        }
        return instance;
    }

    public void init(Application application){
        this.mApplication = application;
        iPlay = new IPlayImpl();

        completeCallBacks = new LinkedHashSet<>();
        playSeekCallBacks = new LinkedHashSet<>();
    }

    public void registerCompleteCallBack(PlayCompleteCallBack playCompleteCallBack){
        completeCallBacks.add(playCompleteCallBack);
    }
    public void moveCompleteCallback(PlayCompleteCallBack playCompleteCallBack){
        completeCallBacks.remove(playCompleteCallBack);
    }
    public void notificCompleteCallback(){
        if (mPlayStatus != PlayStatus.PLAYER_STATUS_OVER){
            mPlayStatus = PlayStatus.PLAYER_STATUS_OVER;
        }else {
            return;
        }
        AppExecutors.getInstance().mainThread().execute(() -> {
            for (PlayCompleteCallBack callBack : completeCallBacks) {
                callBack.complete();
            }
        });
    }

    public void nextVideo(){
        if (mPlayIndex < -1){
            return;
        }
        if (mPlayList.size() == 1){
            showNotMoreVideo();
            return;
        }
        if (mPlayList.size() - 1 < mPlayIndex + 1){
            showNotMoreVideo();
            return;
        }else {
            ++mPlayIndex;
            playBean = mPlayList.get(mPlayIndex);
        }
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.openUrl(mPlayList.get(mPlayIndex).getUrl(), videoView, 0);
                PlayerCore.start();
                PlayManager.getInstance().notifiVideoReady();
            }
        });
    }

    private void showNotMoreVideo(){
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast("No More Video...");
            }
        });
    }

    public void lastVideo(){
        if (mPlayIndex < -1){
            return;
        }
        if (mPlayList.size() == 1){
            showNotMoreVideo();
            return;
        }
        if (0 > mPlayIndex - 1){
            showNotMoreVideo();
            return;
        }else {
            --mPlayIndex;
            playBean = mPlayList.get(mPlayIndex);
        }
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.openUrl(mPlayList.get(mPlayIndex).getUrl(), videoView, 0);
                PlayerCore.start();
                PlayManager.getInstance().notifiVideoReady();
            }
        });
    }

    public MediaBean getPlayBean() {
        return playBean;
    }

    public void registerPlayCallBack(PlayCallBack playCompleteCallBack){
        playSeekCallBacks.add(playCompleteCallBack);
    }

    public void release(){
        clearAllListener();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                iPlay.release();
            }
        });
    }

    public void clearAllListener(){
        playSeekCallBacks.clear();
        completeCallBacks.clear();
    }

    public void notifiSeekTime(long time){
        AppExecutors.getInstance().mainThread().execute(() -> {
            mPlayStatus = PlayStatus.PLAYER_STATUS_PLAYING;
            for (PlayCallBack callBack : playSeekCallBacks) {
                callBack.playTimeCallBack(time);
                callBack.seekBack(time);
                callBack.videoResume();
            }
        });
    }

    public void notifiVideoReady(){
        AppExecutors.getInstance().mainThread().execute(() -> {
            mPlayStatus = PlayStatus.PLAYER_STATUS_READY;
            for (PlayCallBack callBack : playSeekCallBacks) {
                callBack.readyPlay();
            }
        });
    }
    public void notifiTotalTime(long time){
        AppExecutors.getInstance().mainThread().execute(() -> {
            playBean.setTotalTime(time);
            for (PlayCallBack callBack : playSeekCallBacks) {
                callBack.totalTimeBack(time);
            }
        });
    }

    public synchronized void startPlay(List<MediaBean> list, int playIndex, View view){
        this.videoView = view;
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayManager.getInstance().setPlayRes(list, playIndex);
                PlayerCore.openUrl(list.get(playIndex).getUrl(), view, 0);
                PlayerCore.start();
                PlayManager.getInstance().notifiVideoReady();
            }
        });
    }

    public void setPlayRes(List<MediaBean> list, int playIndex){
        if (list == null || list.size() == 0 || playIndex < 0){
            try {
                throw new PlayException();
            } catch (PlayException e) {
                e.printStackTrace();
            }
        }
        this.mPlayList = list;
        this.mPlayIndex = playIndex;

        playBean = mPlayList.get(mPlayIndex);

    }

    public void start(){
        iPlay.setSource(playBean.url, renderView, 0);
        iPlay.start();
        mPlayStatus = PlayStatus.PLAYER_STATUS_INIT;
        playBean = mPlayList.get(mPlayIndex);
    }

    public void setMediaTotalTime(long time){
        playBean.setTotalTime(time);
    }

    public void pauseOrResume(){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                iPlay.pauseOrResume();
            }
        });
    }

    public synchronized void stop(){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                iPlay.stop();
            }
        });
    }
    public void pause(){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                iPlay.pause();
            }
        });
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                for (PlayCallBack callBack : playSeekCallBacks) {
                    callBack.videoPaused();
                }
            }
        });
    }
    public void resume(){
        iPlay.resume();
        for (PlayCallBack callBack : playSeekCallBacks) {
            callBack.videoResume();
        }
    }

    public synchronized void seekTo(double time){
        iPlay.seekTo(time);
    }

    public List<MediaBean> getPlayList() {
        return mPlayList;
    }

    /**
     * *********************************************       CallBack       ************************************
     */
    public interface PlayCompleteCallBack{
        void complete();
    }
    public interface PlayCallBack{
        void seekBack(long time);

        /**
         * 总时长回调
         * @param time  MS
         */
        void totalTimeBack(long time);
        void playTimeCallBack(long time);
        void videoPaused();
        void videoResume();
        void readyPlay();
    }
}
