package com.net.framework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class Network {

    public enum Type {
        UNKNOWN, WIFI, MOBILE, MOBILE2G, MOBILE3G, MOBILE4G
    }

    public static NetworkInfo getCurrentActiveNetwork(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                return connectivity.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> netWorkInterface = NetworkInterface.getNetworkInterfaces();
            while (netWorkInterface.hasMoreElements()) {
                Enumeration<InetAddress> address = netWorkInterface.nextElement().getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress inetAddr = address.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr instanceof Inet4Address) {
                            return inetAddr.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 得到当前的手机网络类型
     * @param context
     * @return
     */
    public static Type getSubType(Context context) {
        NetworkInfo netWorkInfo = Network.getCurrentActiveNetwork(context);
        if (netWorkInfo != null && netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (netWorkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS://GPRS （联通2g）
                case TelephonyManager.NETWORK_TYPE_EDGE://EDGE（移动2g）
                case TelephonyManager.NETWORK_TYPE_CDMA: {// CDMA （电信2g）
                    return Type.MOBILE2G;//2G网络
                }
                case TelephonyManager.NETWORK_TYPE_UMTS://UMTS（联通3g）
                case TelephonyManager.NETWORK_TYPE_HSDPA://HSDPA（联通3g）
                case TelephonyManager.NETWORK_TYPE_EVDO_B://EVDO  版本B（电信3g）
                case TelephonyManager.NETWORK_TYPE_EVDO_0://EVDO  版本0.（电信3g）
                case TelephonyManager.NETWORK_TYPE_EVDO_A: {//EVDO   版本A （电信3g）
                    return Type.MOBILE3G;//3G网络
                }
                case TelephonyManager.NETWORK_TYPE_LTE:{
                    return Type.MOBILE4G;//4G网络
                }
            }
            return Type.MOBILE;//未知的移动网络
        } else if (netWorkInfo != null && netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return Type.WIFI;
        }
        return Type.UNKNOWN;//未知网络
    }

    public static String getSubTypeName(Context context) {
        NetworkInfo netWorkInfo = getCurrentActiveNetwork(context);
        return netWorkInfo != null ? netWorkInfo.getSubtypeName() : null;
    }

    public static Type getType(Context context) {
        NetworkInfo netWork = getCurrentActiveNetwork(context);
        if (netWork != null) {
            switch (netWork.getType()) {
                case ConnectivityManager.TYPE_MOBILE: {
                    return Type.MOBILE;
                }
                case ConnectivityManager.TYPE_WIFI: {
                    return Type.WIFI;
                }
            }
        }
        return Type.UNKNOWN;
    }

    public static String getTypeName(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null ? net.getTypeName() : null;
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null && net.isAvailable();
    }

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null && net.isConnected();
    }
}
