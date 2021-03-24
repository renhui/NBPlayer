package com.renhui.nbplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.renhui.nbplayer.utils.LoggerUtil;

/**
 * 视频播放控件
 * Created by renhui on 2017/8/6.
 */
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback, OnVideoSizeListener {

    private NBPlayer mPlayer;

    private Context mContext;

    public VideoSurfaceView(Context context) {
        super(context);
        mContext = context;
        initSurfaceCallBack();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initSurfaceCallBack();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initSurfaceCallBack();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSurfaceCallBack();
    }

    private void initSurfaceCallBack() {
        getHolder().addCallback(this);
    }

    public void initPlayer(String path) {
        mPlayer = NBPlayer.create(getHolder());
        mPlayer.setVideoSizeListener(this);
        mPlayer.setDataSource(path);
        mPlayer.setLooping(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPlayer.play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mPlayer.pause();
    }

    public void releasePlayer() {
        mPlayer.release();
    }

    @Override
    public void videoSize(int videoWidth, int videoHeight) {
        LoggerUtil.w("视频宽："+ videoWidth + ", 视频高：" + videoHeight);
        setLayoutParams(getVideoSizeParams(videoWidth, videoHeight));
    }

    private RelativeLayout.LayoutParams getVideoSizeParams(int videoWidth, int videoHeight) {
        int width = getScreenWidth(mContext);
        int height = getScreenHeight(mContext);

        if (videoWidth == 0) {
            videoWidth = 600;
        }
        if (videoHeight == 0) {
            videoHeight = 400;
        }
        if (videoWidth > width || videoHeight > height) {
            float wRatio = (float) videoWidth / (float) width;
            float hRatio = (float) videoHeight / (float) height;
            float ratio = Math.max(wRatio, hRatio);
            width = (int) Math.ceil((float)videoWidth / ratio);
            height = (int) Math.ceil((float)videoHeight / ratio);
        } else {
            float wRatio = (float) width / (float) videoWidth;
            float hRatio = (float) height / (float) videoHeight;
            float ratio = Math.min(wRatio, hRatio);
            width = (int) Math.ceil(((float)videoWidth * ratio));
            height = (int) Math.ceil(((float)videoHeight * ratio));
        }
        LoggerUtil.w("调整宽："+ width + ", 调整高：" + height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }

}
