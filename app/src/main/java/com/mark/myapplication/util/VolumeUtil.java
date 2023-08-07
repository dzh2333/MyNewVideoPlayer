package com.mark.myapplication.util;

import static android.media.AudioManager.FLAG_SHOW_UI;

import android.content.Context;
import android.media.AudioManager;

import com.mark.myapplication.AppApplication;

public class VolumeUtil {

    private static final int aimVolumeType = AudioManager.STREAM_MUSIC;

    public static void addVolume(){
        AudioManager audioManager = (AudioManager) AppApplication.getApplication().getSystemService(Context.AUDIO_SERVICE);
        int nowNum = audioManager.getStreamVolume(aimVolumeType);
        audioManager.setStreamVolume(aimVolumeType, nowNum + 1, FLAG_SHOW_UI);
    }

    public static void reduceVolume(){
        AudioManager audioManager = (AudioManager) AppApplication.getApplication().getSystemService(Context.AUDIO_SERVICE);
        int nowNum = audioManager.getStreamVolume(aimVolumeType);
        audioManager.setStreamVolume(aimVolumeType, nowNum - 1, FLAG_SHOW_UI);
    }
}
