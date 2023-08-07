package com.mark.myapplication.player.core;

import android.view.View;

public interface IPlay {

    void setRenderView(Object surface);

    void setSource(String url,View view, int resType);

    /**
     * 启动播放
     */
    void start();

    /**
     *
     */
    void stop();

    /**
     * pause、resume
     */
    void pauseOrResume();

    void pause();
    void resume();

    /**
     * 调进度
     * @param time
     */
    void seekTo(double  time);

    /**
     * 回收
     */
    void release();
}
