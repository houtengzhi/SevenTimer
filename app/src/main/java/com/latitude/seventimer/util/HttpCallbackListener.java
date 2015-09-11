package com.latitude.seventimer.util;

/**
 * Created by yechy on 2015/9/7.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onFinish(String response, float latitude, float longitude);
    void onError(Exception e);
}
