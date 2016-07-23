package com.net.framework;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Observable;
import java.util.Observer;

public class NetObservable extends Observable {
    private Context context;

    public NetObservable(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void addObserver(Observer observer) {
        try {
            super.addObserver(observer);
            NetworkInfo networkInfo = Network.getCurrentActiveNetwork(this.context);
            if (networkInfo != null) {
                if (!networkInfo.isAvailable()) {
                    observer.update(this, new NetObserver.NetAction(false, false, Network.getSubType(context)));
                    return;
                }

                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    observer.update(this, new NetObserver.NetAction(true, true, Network.getSubType(context)));
                    return;
                }

                observer.update(this, new NetObserver.NetAction(true, false, Network.getSubType(context)));
                return;
            }

            observer.update(this, new NetObserver.NetAction(false, false, Network.getSubType(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers(Object data) {
        try {
            this.setChanged();
            super.notifyObservers(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}