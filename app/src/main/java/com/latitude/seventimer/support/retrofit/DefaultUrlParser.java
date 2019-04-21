package com.latitude.seventimer.support.retrofit;

import okhttp3.HttpUrl;

/**
 * Created by yechy on 2017/12/20.
 */

public class DefaultUrlParser implements UrlParser {
    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;
        return url.newBuilder()
                .scheme(domainUrl.scheme())
                .host(domainUrl.host())
                .port(domainUrl.port())
                .build();
    }
}
