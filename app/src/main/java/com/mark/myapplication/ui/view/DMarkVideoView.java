


package com.mark.myapplication.ui.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.mark.myapplication.player.core.PlayManager;
import com.mark.myapplication.player.core.PlayerCore;
import com.mark.myapplication.util.AppExecutors;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class DMarkVideoView extends GLSurfaceView implements SurfaceHolder.Callback, GLSurfaceView.Renderer, View.OnClickListener {

    private int width;
    private int height;
    private SurfaceHolder mHolder;

    public DMarkVideoView(Context context){
        super(context);
        setRenderer( this );
        setOnClickListener( this );
    }

    public DMarkVideoView(Context context, AttributeSet attrs) {
        super( context, attrs );

        setRenderer( this );
        setOnClickListener( this );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(width != 0 && height != 0){
            super.onMeasure(width, height);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mHolder = holder;
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                PlayerCore.InitView(holder.getSurface());
            }
        });
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4)
    {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder var1)
    {

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }


    @Override
    public void onClick(View v) {

    }
}
