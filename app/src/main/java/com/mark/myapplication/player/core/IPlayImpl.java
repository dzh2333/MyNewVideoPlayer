package com.mark.myapplication.player.core;

import android.util.Log;
import android.view.View;

import com.mark.myapplication.util.AppExecutors;

public class IPlayImpl implements IPlay{
    @Override
    public void setRenderView(Object surface) {
    }

    @Override
    public void setSource(String url, View view, int resType) {
        PlayerCore.openUrl(url, view, resType);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.stopVideo();
            }
        });
    }

    @Override
    public void pauseOrResume() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                boolean isPause = PlayerCore.getPauseStatus();
                Log.e("IPlayImpl", "pauseOrResume: " +isPause  );
                if (isPause){
                    PlayerCore.setPause(false);
                }else {
                    PlayerCore.setPause(true);
                }
            }
        });
    }

    @Override
    public void pause() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.setPause(true);
            }
        });
    }

    @Override
    public void resume() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.setPause(false);
            }
        });
    }

    @Override
    public void seekTo(double time) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.seekTo(time);
            }
        });
    }


    @Override
    public void release() {
        PlayerCore.onRelease();
    }
}
