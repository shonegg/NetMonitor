package com.net.framework;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@Deprecated
public class NetworkUtils {

    /**
     * 判断是不是wifi网络状态
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        return "2".equals(getNetType(context)[0]);
    }

    /**
     * 判断是不是2/3G网络状态
     *
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        return "1".equals(getNetType(context)[0]);
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        if ("1".equals(getNetType(context)[0]) || "2".equals(getNetType(context)[0])) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络状态 返回2代表wifi,1代表2G/3G
     *
     * @param context
     * @return
     */
    private static String[] getNetType(Context context) {
        String[] result = {"Unknown", "Unknown"};
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission("android.permission.ACCESS_NETWORK_STATE",
                context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            result[0] = "Unknown";
            return result;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            result[0] = "Unknown";
            return result;
        }

        NetworkInfo networkInfo1 = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//1
        if (networkInfo1 != null && networkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            result[0] = "2";
            return result;
        }

        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);//0
        if (networkInfo2 != null && networkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            result[0] = "1";
            result[1] = networkInfo2.getSubtypeName();
            return result;
        }

        return result;
    }
}