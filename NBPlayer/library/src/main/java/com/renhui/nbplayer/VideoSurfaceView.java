package com.renhui.nbplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 视频播放控件
 * Created by renhui on 2017/8/6.
 */
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "VideoSurfaceView";

    private NBPlayer mPlayer;
    private OnPreparedListener onPreparedListener;

    private Context mContext;

    public VideoSurfaceView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public void initPlayer(String path) {
        mPlayer = NBPlayer.create(getHolder(), path);
        mPlayer.setLooping(true);
    }

    public void play() {
        mPlayer.play();
    }


    private void initLayout(int width, int height) {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        float scale = width / (float) height;
        getLayoutParams().height = (int) (display.getWidth() / scale);
        requestLayout();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initLayout(mPlayer.getWidth(), mPlayer.getHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initLayout(mPlayer.getWidth(), mPlayer.getHeight());
        play();
        if (onPreparedListener != null)
            onPreparedListener.onPrepared();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v(TAG, "surfaceChanged...");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mPlayer.pause();
    }

    public void releasePlaer() {
        mPlayer.stop();
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.onPreparedListener = onPreparedListener;
    }

    public interface OnPreparedListener {
        void onPrepared();
    }
}
