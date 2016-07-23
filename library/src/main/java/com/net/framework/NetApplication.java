package com.net.framework;

import android.app.Application;

/**
 * Author   Shone
 * Date     23/07/16.
 * Github   https://github.com/shonegg
 */
public class NetApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NetMonitor.getInstance().init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetMonitor.getInstance().destory();
    }

}
