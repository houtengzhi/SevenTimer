package com.latitude.seventimer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by yechy on 2017/8/8.
 */

public class NetUtil {
    private final static String TAG = NetUtil.class.getSimpleName();

    /**
     * 检查有线网络状态
     * @param context
     * @return
     */
    public static NetworkInfo.State checkEtherNetConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (netInfo != null) {
            return netInfo.getState();
        }
        return null;
    }

    /**
     * 判断网络是否可用
     * @param context Context对象
     */
    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        NetworkInfo ether = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (current == null || !current.isAvailable()) {
            L.i(TAG, "Network is not available");
            return false;
        }
        if (ether != null) {
            L.d(TAG, ether.getTypeName() + " available: " + ether.isAvailable());
        }

        if (wifi != null) {
            L.d(TAG, wifi.getTypeName() + " available: " + wifi.isAvailable());
        }
        L.i(TAG, "Network is available, active net is " + current.getTypeName());
        return true;
    }

    public static String getNetworkIP() {
        try {
            // 获取本地设备的所有网络接口
            Enumeration<NetworkInterface> enumerationNi = NetworkInterface
                    .getNetworkInterfaces();
            while (enumerationNi.hasMoreElements()) {
                NetworkInterface networkInterface = enumerationNi.nextElement();
                String interfaceName = networkInterface.getDisplayName();
                L.d(TAG, "getNetworkIP(), " + interfaceName);

                // 如果是有限网卡
                if (interfaceName.equals("eth0")) {
                    Enumeration<InetAddress> enumIpAddr = networkInterface
                            .getInetAddresses();

                    while (enumIpAddr.hasMoreElements()) {
                        // 返回枚举集合中的下一个IP地址信息
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        // 不是回环地址，并且是ipv4的地址
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            L.d(TAG, "getNetworkIP(), " + inetAddress.getHostAddress() + "   ");

                            return inetAddress.getHostAddress();
                        }
                    }
                    //  如果是无限网卡
                } else if (interfaceName.equals("wlan0")) {
                    Enumeration<InetAddress> enumIpAddr = networkInterface
                            .getInetAddresses();

                    while (enumIpAddr.hasMoreElements()) {
                        // 返回枚举集合中的下一个IP地址信息
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        // 不是回环地址，并且是ipv4的地址
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            L.d(TAG, "getNetworkIP(), " + inetAddress.getHostAddress() + "   ");

                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
