package com.renhui.nbplayer;

/**
 * 播放器抽象类
 * Created by renhui on 2017/8/6.
 */
public abstract class Player {

    protected int curFrameNumber = 0;
    protected long rate = 0;
    protected boolean play = false;
    protected boolean looping;
    protected boolean autoPlay = true;


    public void seek(int number) {
        this.curFrameNumber = number;
    }

    public void play() {
        this.play = true;
    }

    public void pause() {
        this.play = false;
    }

    public void stop() {
        this.play = false;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }
}
