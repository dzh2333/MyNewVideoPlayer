package com.mark.myapplication.ui.player.audio;

public interface IAudioPlayer {

    void setPause(boolean pause);

    void setPauseOrPlay();

    void startUrl(String url, Object player);

    void lastAudio();

    void nextAudio();
}
