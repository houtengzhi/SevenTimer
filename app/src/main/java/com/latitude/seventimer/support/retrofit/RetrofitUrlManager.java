package com.latitude.seventimer.support.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.latitude.seventimer.util.ConfigManager;
import com.latitude.seventimer.util.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yechy
 * @date 2017/12/20
 * @email yechengyun@ahgxtx.com
 * @describe
 *
 */
public class RetrofitUrlManager {
    private static final String TAG = "RetrofitUrlManager";
    private static final boolean DEPENDENCY_OKHTTP;
    private static final String DOMAIN_NAME = "Domain-Name";

    //默认开始运行,可以随时停止运行,比如你在 App 启动后已经不需要在动态切换 baseurl 了
    private boolean isRun = true;
    private final Interceptor mInterceptor;
    private final List<OnUrlChangeListener> mListeners = new ArrayList<>();
    private AdvancedUrlParser mUrlParser;

    static {
        boolean hasDependency;
        try {
            Class.forName("okhttp3.OkHttpClient");
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        DEPENDENCY_OKHTTP = hasDependency;
    }


    private RetrofitUrlManager() {
        if (!DEPENDENCY_OKHTTP) { //使用本管理器必须依赖 Okhttp
            throw new IllegalStateException("Must be dependency Okhttp");
        }
        setUrlParser(new AdvancedUrlParser());
        this.mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (!isRun()) // 可以在 App 运行时,随时通过 setRun(false) 来结束本管理器的运行,
                    return chain.proceed(chain.request());
                return chain.proceed(processRequest(chain.request()));
            }
        };
    }

    private static class RetrofitUrlManagerHolder {
        private static final RetrofitUrlManager INSTANCE = new RetrofitUrlManager();
    }

    public static final RetrofitUrlManager getInstance() {
        return RetrofitUrlManagerHolder.INSTANCE;
    }

    /**
     * 将 {@link OkHttpClient.Builder} 传入,配置一些本管理器需要的参数
     *
     * @param builder
     * @return
     */
    public OkHttpClient.Builder with(OkHttpClient.Builder builder) {
        return builder
                .addInterceptor(mInterceptor);
    }

    /**
     * 对 {@link Request} 进行一些必要的加工
     *
     * @param request
     * @return
     */
    public Request processRequest(Request request) {

        Request.Builder newBuilder = request.newBuilder();

        String domainName = obtainDomainNameFromHeaders(request);

        HttpUrl httpUrl = null;

        Object[] listeners = listenersToArray();

        // 如果有 header,获取 header 中 domainName 所映射的 url,若没有,则检查全局的 BaseUrl,未找到则为null
        if (!TextUtils.isEmpty(domainName)) {
            notifyListener(request, domainName, listeners);
            httpUrl = ConfigManager.getInstance().getDomain(domainName);
            newBuilder.removeHeader(DOMAIN_NAME);
        }
        L.d(TAG, "httpUrl =  " + httpUrl);
        if (null != httpUrl) {
            HttpUrl newUrl = mUrlParser.parseUrl(httpUrl, request.url());
            Log.d(TAG, "New Url is { " + newUrl.toString() + " } , Old Url is { " + request.url().toString() + " }");

            if (listeners != null) {
                for (int i = 0; i < listeners.length; i++) {
                    ((OnUrlChangeListener) listeners[i]).onUrlChanged(newUrl, request.url()); // 通知监听器此 Url 的 BaseUrl 已被改变
                }
            }

            return newBuilder
                    .url(newUrl)
                    .build();
        }

        return newBuilder.build();

    }

    private void notifyListener(Request request, String domainName, Object[] listeners) {
        if (listeners != null) {
            for (int i = 0; i < listeners.length; i++) {
                ((OnUrlChangeListener) listeners[i]).onUrlChangeBefore(request.url(),domainName);
            }
        }
    }

    /**
     * 管理器是否在运行
     *
     * @return
     */
    public boolean isRun() {
        return this.isRun;
    }

    /**
     * 控制管理器是否运行,在每个域名地址都已经确定,不需要再动态更改时可设置为 false
     *
     * @param run
     */
    public void setRun(boolean run) {
        this.isRun = run;
    }

    /**
     * 可自行实现 {@link UrlParser} 动态切换 Url 解析策略
     *
     * @param parser
     */
    public void setUrlParser(AdvancedUrlParser parser) {
        this.mUrlParser = parser;
    }

    /**
     * 注册当 Url 的 BaseUrl 被改变时会被回调的监听器
     *
     * @param listener
     */
    public void registerUrlChangeListener(OnUrlChangeListener listener) {
        synchronized (mListeners) {
            mListeners.add(listener);
        }
    }

    /**
     * 注销当 Url 的 BaseUrl 被改变时会被回调的监听器
     *
     * @param listener
     */
    public void unregisterUrlChangeListener(OnUrlChangeListener listener) {
        synchronized (mListeners) {
            mListeners.remove(listener);
        }
    }

    private Object[] listenersToArray() {
        Object[] listeners = null;
        synchronized (mListeners) {
            if (mListeners.size() > 0) {
                listeners = mListeners.toArray();
            }
        }
        return listeners;
    }

    /**
     * 从 {@link Request#header(String)} 中取出 DomainName
     *
     * @param request
     * @return
     */
    private String obtainDomainNameFromHeaders(Request request) {
        List<String> headers = request.headers(DOMAIN_NAME);
        if (headers == null || headers.size() == 0)
            return null;
        if (headers.size() > 1)
            throw new IllegalArgumentException("Only one Domain-Name in the headers");
        return request.header(DOMAIN_NAME);
    }
}
