package com.latitude.seventimer.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by yechy on 2015/9/7.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String url, final
    HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.addHeader("Accept-Language","zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    String response = "";
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //请求和响应成功
                        HttpEntity entity = httpResponse.getEntity();
                        response = EntityUtils.toString(entity,"utf-8");
                    }
                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }

            }
        }).start();

    }

    public static void sendHttpRequest(final String url, final float latitude, final
    float longitude, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.addHeader("Accept-Language","zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    String response = "";
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //请求和响应成功
                        HttpEntity entity = httpResponse.getEntity();
                        response = EntityUtils.toString(entity,"utf-8");
                    }
                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response, latitude, longitude);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }

            }
        }).start();

    }

    public static void sendHttpRequestNormal(final String url,final float latitude, final
    float longitude, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);

                    //httpGet.addHeader("Accept-Language","zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    String response = "";
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //请求和响应成功

                        HttpEntity entity = httpResponse.getEntity();
                        response = EntityUtils.toString(entity, "utf-8");

                    }
                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response, latitude, longitude);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }

            }
        }).start();

    }
}
