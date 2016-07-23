package com.net.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetMonitor extends BroadcastReceiver {
    private static final String TAG = "NetMonitor";
    private static NetMonitor instance;
    private NetObservable observable;


    public void addObserver(NetObserver observer) {
        this.observable.addObserver(observer);
    }

    public void delObserver(NetObserver observer) {
        this.observable.deleteObserver(observer);
    }

    public void destory() {
        this.observable.deleteObservers();
    }

    public static NetMonitor getInstance() {
        if (instance == null) {
            synchronized (NetMonitor.class) {
                if (instance == null) {
                    instance = new NetMonitor();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.observable = new NetObservable(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(this, intentFilter);
    }

    private void notifyNetState(Context context) {
        try {
            NetworkInfo networkInfo = Network.getCurrentActiveNetwork(context);
            if (networkInfo != null) {
                if (!networkInfo.isAvailable()) {
                    this.observable.notifyObservers(new NetObserver.NetAction(false, false, Network.getSubType(context)));
                    return;
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    this.observable.notifyObservers(new NetObserver.NetAction(true, true, Network.getSubType(context)));
                    return;
                }

                this.observable.notifyObservers(new NetObserver.NetAction(true, false, Network.getSubType(context)));
                return;
            }

            this.observable.notifyObservers(new NetObserver.NetAction(false, false, Network.getSubType(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notifyNetState(context);
    }
}

