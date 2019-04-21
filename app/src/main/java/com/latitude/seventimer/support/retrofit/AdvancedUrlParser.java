package com.latitude.seventimer.support.retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

public class AdvancedUrlParser implements UrlParser {

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;

        HttpUrl.Builder builder = url.newBuilder();


        if (domainUrl.pathSize() > 1 || checkNotNull(domainUrl.encodedPathSegments().get(0))) {
            for (int i = 0; i < url.pathSize(); i++) {
                builder.removePathSegment(0);
            }

            List<String> newPathSegments = new ArrayList<>(domainUrl.encodedPathSegments());

            List<String> oldEncodePathSegments = url.encodedPathSegments();
            for (int i = domainUrl.pathSize(); i < oldEncodePathSegments.size(); i++) {
                newPathSegments.add(oldEncodePathSegments.get(i));
            }

            for (String segment : newPathSegments) {
                builder.addEncodedPathSegment(segment);
            }
        }

        return builder
                .scheme(domainUrl.scheme())
                .host(domainUrl.host())
                .port(domainUrl.port())
                .build();
    }

    private boolean checkNotNull(String s) {
        return s != null && s.trim().length() > 0;
    }
}
