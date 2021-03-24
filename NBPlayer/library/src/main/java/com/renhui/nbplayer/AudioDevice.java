package com.renhui.nbplayer;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.renhui.nbplayer.utils.LoggerUtil;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

/**
 * 音频设备
 * Created by renhui on 2017/8/6.
 */
public class AudioDevice {

    private int streamType = AudioManager.STREAM_MUSIC; // 流类型
    private int sampleRateInHz;
    private int channelConfig;
    private int audioFormat;
    private int minBufSize;
    private int mode = AudioTrack.MODE_STREAM; // 设置模式类型，在这里设置为流类型
    private AudioTrack audioTrack;
    private short[] buffer = new short[1024];
    private float[] floatBuffer = new float[1024];

    public AudioDevice(int sampleRate, int channels) {
        LoggerUtil.w("sampleRate = " + sampleRate + ", channels = " + channels);
        sampleRateInHz = sampleRate * (channels == 1 ? 2 : 1);
        channelConfig = channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO;
        audioFormat = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? AudioFormat.ENCODING_PCM_16BIT : AudioFormat.ENCODING_PCM_FLOAT;
        minBufSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        audioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, minBufSize, mode);
        audioTrack.play();
        LoggerUtil.w("MinBufSize = " + minBufSize);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void writeSamples(Buffer[] buffers) {
        fillFloatBuffer(buffers);
        audioTrack.write(floatBuffer, 0, floatBuffer.length, AudioTrack.WRITE_BLOCKING);
    }

    private void fillFloatBuffer(Buffer[] buffers) {
        if (buffer.length == 1) {
            FloatBuffer b = (FloatBuffer) buffers[0];
            b.rewind();
            if (floatBuffer.length < b.capacity())
                floatBuffer = new float[b.capacity()];
            for (int i = 0; i < b.capacity(); i++)
                floatBuffer[i] = b.get(i) * Short.MAX_VALUE;
        } else {
            FloatBuffer b1 = (FloatBuffer) buffers[0];
            FloatBuffer b2 = (FloatBuffer) buffers[0];
            if (floatBuffer.length < b1.capacity() + b2.capacity())
                floatBuffer = new float[b1.capacity() + b2.capacity()];
            for (int i = 0; i < b1.capacity(); i++) {
                floatBuffer[2 * i] = b1.get(i);
                floatBuffer[2 * i + 1] = b2.get(i);
            }
        }
    }

    public void release() {
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    @Override
    public String toString() {
        return "AudioDevice{" +
                "streamType=" + streamType +
                ", sampleRateInHz=" + sampleRateInHz +
                ", channelConfig=" + channelConfig +
                ", audioFormat=" + audioFormat +
                ", minBufSize=" + minBufSize +
                ", mode=" + mode +
                '}';
    }
}
