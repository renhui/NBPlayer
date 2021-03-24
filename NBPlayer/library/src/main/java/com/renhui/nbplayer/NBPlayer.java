package com.renhui.nbplayer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.SurfaceHolder;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import static org.bytedeco.javacpp.avutil.AV_PIX_FMT_RGBA;

/**
 * FFmpegPlayer
 * Created by renhui on 2017/8/6.
 */
public class NBPlayer extends Player {

    private SurfaceHolder mHolder; // 视频绘制容器
    private FFmpegFrameGrabber mFrameGrabber;//解码器
    private OpenCVFrameConverter.ToIplImage mOpencvConverter; // 帧转换器（by OpenCV）
    private AndroidFrameConverter mFrameConverter; // 帧转换器（by Android原生）
    private PlayerThread mPlayerThread; // 播放线程

    private AudioDevice mAudioDevice;

    //状态相关
    private boolean hasInit = false;

    public static NBPlayer create(SurfaceHolder mHolder) {
        return new NBPlayer(mHolder);
    }

    public NBPlayer(SurfaceHolder mHolder) {
        this.mHolder = mHolder;
    }

    public NBPlayer(SurfaceHolder mHolder, String path) {
        this.mHolder = mHolder;
    }

    public void setDataSource(String path) {
        // 清理之前的界面
        clearView();
        // 初始化相关参数
        this.curFrameNumber = 0;
        this.hasInit = false;

        mFrameConverter = new AndroidFrameConverter();
        mOpencvConverter = new OpenCVFrameConverter.ToIplImage();

        try {
            //如果已经有实例，则先释放资源再初始化
            if (mFrameGrabber != null) {
                mFrameGrabber.stop();
                mFrameGrabber.release();
                mFrameGrabber = null;
            }
            mFrameGrabber = FFmpegFrameGrabber.createDefault(path);
            mFrameGrabber.setPixelFormat(AV_PIX_FMT_RGBA);
            mFrameGrabber.start();
            this.rate = Math.round(1000d / mFrameGrabber.getFrameRate());
            videoSizeListener.videoSize(mFrameGrabber.getImageWidth(), mFrameGrabber.getImageHeight());
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        mPlayerThread = new PlayerThread();
        mPlayerThread.initTrack(mFrameGrabber.getSampleRate(), mFrameGrabber.getAudioChannels());
        mPlayerThread.start();
        this.hasInit = true;
        if (isAutoPlay()) play();
    }

    private void clearView() {
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) return;
        canvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(canvas);
    }

    private boolean draw(Frame frame) {
        if (frame == null || frame.image == null) {
            return false;
        }
        Bitmap bmp = mFrameConverter.convert(frame);
        if (bmp == null) {
            return false;
        }
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) return true;
        canvas.drawBitmap(bmp, null, new Rect(0, 0, canvas.getWidth(), frame.imageHeight * canvas.getWidth() / frame.imageWidth), null);
        mHolder.unlockCanvasAndPost(canvas);
        return true;
    }

    private class PlayerThread extends Thread {

        // 初始化音频播放设备
        void initTrack(int sampleRate, int channels) {
            mAudioDevice = new AudioDevice(sampleRate, channels);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            super.run();
            try {
                Frame grabframe = mFrameGrabber.grab();
                opencv_core.IplImage grabbedImage = null;
                if (grabframe != null) {
                    mOpencvConverter.convert(grabframe);
                }
                while ((grabframe = mFrameGrabber.grab()) != null) {
                    // 解析音频并播放
                    if (grabframe.samples != null) {
                        mAudioDevice.writeSamples(grabframe.samples);
                    }
                    grabbedImage = mOpencvConverter.convert(grabframe);
                    Frame rotatedFrame = mOpencvConverter.convert(grabbedImage);
                    draw(rotatedFrame);
                }
                mFrameGrabber.stop();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void seek(int number) {
        synchronized (mFrameGrabber) {
            if (number > mFrameGrabber.getLengthInFrames()) return;
            super.seek(number);
            this.curFrameNumber = number;
            try {
                mFrameGrabber.setFrameNumber(curFrameNumber);
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void play() {
        if (!hasInit) return;
        super.play();
    }

    @Override
    public void pause() {
        if (!hasInit) return;
        super.pause();
    }

    @Override
    public void release() {
        if (!hasInit) return;
        super.release();
        if (mFrameGrabber != null) {
            try {
                mFrameGrabber.stop();
                mFrameGrabber.release();
                mFrameGrabber = null;
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
        if (mAudioDevice != null) {
            mAudioDevice.release();
            mAudioDevice = null;
        }
    }

    @Override
    public boolean isLooping() {
        return looping;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    @Override
    public boolean isAutoPlay() {
        return autoPlay;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    private OnVideoSizeListener videoSizeListener;

    public void setVideoSizeListener(OnVideoSizeListener videoSizeListener) {
        this.videoSizeListener = videoSizeListener;
    }
}