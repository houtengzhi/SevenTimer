package com.latitude.seventimer.support.retrofit;

import okhttp3.HttpUrl;

/**
 * @author yechy
 * @date 2017/12/20
 * @email yechengyun@ahgxtx.com
 * @describe
 *
 */

public interface OnUrlChangeListener {
    void onUrlChangeBefore(HttpUrl oldUrl, String domainName);
    void onUrlChanged(HttpUrl newUrl, HttpUrl oldUrl);
}
