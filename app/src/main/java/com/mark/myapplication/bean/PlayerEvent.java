package com.mark.myapplication.bean;

public class PlayerEvent {

    public static final int PLAY_START = 1;
    public static final int PLAY_OVER = 2;
    public static final int PLAY_PAUSE = 3;
    public static final int PLAY_RESTART = 4;
    public static final int PLAY_DESTORY = 5;
    public static final int PLAY_CHANGE_SEEK = 6;
    public static final int PLAY_CHANGE_URL_LAST = 7;
    public static final int PLAY_CHANGE_URL_NEXT = 8;
    public static final int PLAY_CHANGE_WIDTH_HEIGHT = 9;

    public static final int PLAY_AUDIO_SEEK_CHANGE = 10;
    public static final int PLAY_AUDIO_OVER = 11;
    public static final int PLAY_AUDIO_PLAY = 12;

    private int type;
    private String data;
    private double seek;

    public PlayerEvent(int type, String data, double seek) {
        this.type = type;
        this.data = data;
        this.seek = seek;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getSeek() {
        return seek;
    }

    public void setSeek(double seek) {
        this.seek = seek;
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "type=" + type +
                ", data='" + data + '\'' +
                ", seek=" + seek +
                '}';
    }



}
