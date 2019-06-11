package com.application.paramountfinefoods.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Hashtable;

public class Util {

    public static boolean isOnline(Context context) {
        if (context != null) {
            final ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mgr != null) {

                final NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (wifiInfo != null && wifiInfo.isAvailable() && wifiInfo.isAvailable() && wifiInfo.isConnected()) {

                    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    final WifiInfo wifiInfoStatus = wifiManager.getConnectionInfo();
                    final SupplicantState supState = wifiInfoStatus.getSupplicantState();

                    if (String.valueOf(supState).equalsIgnoreCase("COMPLETED") || String.valueOf(supState).equalsIgnoreCase("ASSOCIATED")) {
                        // WiFi is connected
                        return true;
                    }
                }

                if (mobileInfo != null && mobileInfo.isAvailable() && mobileInfo.isConnected()) {
                    // Mobile Network is connected
                    return true;
                }
            }
        }
        return false;
    }


    public static class FontCache {

        private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

        public static Typeface get(String name, Context context) {
            Typeface tf = fontCache.get(name);
            if (tf == null) {
                try {
                    tf = Typeface.createFromAsset(context.getAssets(), name);
                } catch (Exception e) {
                    return null;
                }
                fontCache.put(name, tf);
            }
            return tf;
        }
    }

}
