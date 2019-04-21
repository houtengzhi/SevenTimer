package com.latitude.seventimer.util;

import okhttp3.HttpUrl;

/**
 * Created by cloud on 2019/4/21.
 */
public class ConfigManager {

    private static ConfigManager configManager;

    public static ConfigManager getInstance() {
        if (configManager == null) {
            synchronized (ConfigManager.class) {
                if (configManager == null) {
                    configManager = new ConfigManager();
                }
            }
        }
        return configManager;
    }

    public HttpUrl getDomain(String domainName) {
        return HttpUrl.parse(ConstantConfig.SEVEN_TIMER_DOMAIN);
    }
}
