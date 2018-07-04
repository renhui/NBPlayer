package com.renhui.nbplayer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
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

    private final static String TAG = "FFmpegPlayer"; // 日志TAG
    private final Object object = new Object(); // 同步锁对象

    private int video_width;
    private int video_height;

    private SurfaceHolder mHolder; // 视频绘制容器
    private FFmpegFrameGrabber mFrameGrabber;//解码器
    private OpenCVFrameConverter.ToIplImage mOpencvConverter; // 帧转换器（by OpenCV）
    private AndroidFrameConverter mFrameConverter; // 帧转换器（by Android原生）
    private PlayerThread mPlayerThread; // 播放线程

    private AudioDevice mAudioDevice;

    //状态相关
    private boolean hasInit = false;

    //设置相关
    private String sourcePath;

    public static NBPlayer create(SurfaceHolder mHolder) {
        return new NBPlayer(mHolder);
    }

    public static NBPlayer create(SurfaceHolder mHolder, String path) {
        return new NBPlayer(mHolder, path);
    }

    public NBPlayer(SurfaceHolder mHolder) {
        this.mHolder = mHolder;
    }

    public NBPlayer(SurfaceHolder mHolder, String path) {
        this.mHolder = mHolder;
        setDataSource(path);
    }

    public void setDataSource(String path) {
        // 清理之前的界面
        clearView();
        // 初始化相关参数
        this.sourcePath = path;
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

            // 此处可以设置nobuffer -- 降低延迟 -- 但是可能会出现闪屏闪音的情况
            // mFrameGrabber.setOption("fflags", "nobuffer");

            mFrameGrabber.start();
            this.rate = Math.round(1000d / mFrameGrabber.getFrameRate());
            video_width = mFrameGrabber.getImageWidth();
            video_height = mFrameGrabber.getImageHeight();
            Log.e("111", "mFrameGrabber.getFrameRate() = " + mFrameGrabber.getFrameRate());
            Log.e("111", String.format("width=%d, height=%d, delay=%d, frame lenght=%d", mFrameGrabber.getImageWidth(), mFrameGrabber.getImageHeight(), rate, mFrameGrabber.getLengthInFrames()));
            Log.e("111", "AudioChannels=" + mFrameGrabber.getAudioChannels() + ", AudioBitrate=" + mFrameGrabber.getSampleRate());
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        mPlayerThread = new PlayerThread();
        mPlayerThread.initTrack(mFrameGrabber.getSampleRate(), mFrameGrabber.getAudioChannels());
        mPlayerThread.start();
        this.hasInit = true;
        if (isAutoPlay()) play();
    }

    private int frameType(Frame frame) {
        if (frame == null) return -1;
        if (frame.image != null) return 0;
        else if (frame.samples != null) return 1;
        else return -1;
    }

    private void clearView() {
        synchronized (object) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas == null) return;
            canvas.drawColor(Color.BLACK);
            mHolder.unlockCanvasAndPost(canvas);
        }
    }

    private boolean draw(Frame frame) {
        if (frame == null || frame.image == null) {
            return false;
        }
        Bitmap bmp = mFrameConverter.convert(frame);
        if (bmp == null) {
            return false;
        }
        synchronized (object) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas == null) return true;
            canvas.drawBitmap(bmp, null, new Rect(0, 0, canvas.getWidth(), frame.imageHeight * canvas.getWidth() / frame.imageWidth), null);
            mHolder.unlockCanvasAndPost(canvas);
        }
        return true;
    }

    private class PlayerThread extends Thread {

        // 初始化音频播放设备
        void initTrack(int sampleRate, int channels) {
            mAudioDevice = new AudioDevice(sampleRate, channels);
        }

        @Override
        public void run() {
            super.run();
            try {

                synchronized (object) {
                    Frame grabframe = mFrameGrabber.grab();
                    opencv_core.IplImage grabbedImage = null;
                    if (grabframe != null) {
                        mOpencvConverter.convert(grabframe);
                        Log.e(TAG, "has fetched first frame");
                    } else {
                        Log.e(TAG, "not fetched first frame");
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
    public void stop() {
        if (!hasInit) return;
        super.stop();
        try {
            synchronized (mFrameGrabber) {
                if (mFrameGrabber != null) {
                    mFrameGrabber.stop();
                    mFrameGrabber.release();
                    mFrameGrabber = null;
                }
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return video_width;
    }

    @Override
    public int getHeight() {
        return video_height;
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

    public String getSourcePath() {
        return sourcePath;
    }
}