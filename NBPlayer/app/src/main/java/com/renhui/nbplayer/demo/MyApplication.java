package com.renhui.nbplayer.demo;

import android.app.Application;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.renhui.nbplayer.utils.LoggerUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder() //是否选择显示线程信息，默认为true
                .showThreadInfo(false) //方法数显示多少行，默认2行
                .methodCount(0) //隐藏方法内部调用到偏移量，默认5
                .methodOffset(7) //自定义TAG全部标签，默认PRETTY_LOGGER
                .tag("NBPlayer")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return LoggerUtil.DEBUG;
            }
        });
    }
}
