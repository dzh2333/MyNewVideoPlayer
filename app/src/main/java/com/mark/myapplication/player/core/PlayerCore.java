package com.mark.myapplication.player.core;


public class PlayerCore {

    /**
     * 初始化
     * @param surface
     * @return
     */
    public native static int InitView(Object surface);

    /**
     * 开始播放
     * @param url
     * @param view
     * @param playMode
     * @return
     */
    public static native int openUrl(String url, Object view, int playMode);

    public static native int start();

    /**
     * 切换URL
     * @param url
     */
    public static native void changeURL(String url);

    /**
     *
     * @param view
     */
    public static native void changeView(Object view);

    /**
     * 暂停
     * @param pause
     */
    public native static void setPause(boolean pause);

    /**
     * 终止播放
     */
    public native static void stopVideo();

    /**
     * 切换进度
     * @param pos
     */
    public native static void seekTo(double pos);

    public static native boolean onRelease();

    public static native boolean getPauseStatus();

    public static native String getSupportProcel();

}
