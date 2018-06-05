package com.renhui.nbplayer;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;

import java.nio.Buffer;
import java.nio.FloatBuffer;

/**
 * 音频设备
 * Created by renhui on 2017/8/6.
 */
public class AudioDevice {

    private int streamType = AudioManager.STREAM_MUSIC; // 流类型
    private int sampleRateInHz = 44100; // 设置音频数据的采样率
    private int channelConfig = AudioFormat.CHANNEL_OUT_STEREO; //CHANNEL_OUT_MONO类型是单声道
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT; //采样精度
    private int minBufSize = 0;
    private int mode = AudioTrack.MODE_STREAM; // 设置模式类型，在这里设置为流类型
    private AudioTrack audioTrack;
    private short[] buffer = new short[1024];
    private float[] floatBuffer = new float[1024];

    public AudioDevice(int rate, int channels) {
        sampleRateInHz = rate;
        channelConfig = channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO;
        audioFormat = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? AudioFormat.ENCODING_PCM_16BIT : AudioFormat.ENCODING_PCM_FLOAT;
        minBufSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        audioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, minBufSize, mode);
        audioTrack.play();
        Log.v("AudioDevice", toString());
    }

    public void writeSamples(Buffer[] buffers) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fillByteBuffer(buffers);
            audioTrack.write(buffer, 0, buffer.length);
        } else {
            fillFloatBuffer(buffers);
            audioTrack.write(floatBuffer, 0, floatBuffer.length, AudioTrack.WRITE_BLOCKING);
        }
    }

    private void fillByteBuffer(Buffer[] buffers) {
        if (buffer.length == 1) {
            FloatBuffer b = (FloatBuffer) buffers[0];
            b.rewind();
            if (buffer.length < b.capacity())
                buffer = new short[b.capacity()];
            for (int i = 0; i < b.capacity(); i++)
                buffer[i] = (byte) (b.get(i) * Short.MAX_VALUE);
        } else {
            FloatBuffer b1 = (FloatBuffer) buffers[0];
            FloatBuffer b2 = (FloatBuffer) buffers[0];
            if (buffer.length < b1.capacity() + b2.capacity())
                buffer = new short[b1.capacity() + b2.capacity()];
            for (int i = 0; i < b1.capacity(); i++) {
                buffer[2 * i] = (short) (b1.get(i) * Short.MAX_VALUE);
                buffer[2 * i + 1] = (short) (b2.get(i) * Short.MAX_VALUE);
            }
        }
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
