package com.latitude.seventimer.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.latitude.seventimer.App;

import javax.inject.Inject;

import static android.R.attr.name;

/**
 * Created by yechy on 2017/8/19.
 */

public class PreferencesHelper implements IPreferencesHelper {
    private static final String TAG = PreferencesHelper.class.getSimpleName();
    private static final String SP_CONFIG_NAME = "gxdvb_config";
    private static final String SP_DATA_NAME = "gxdvb_data";

    private static final String LAST_TV_NAME = "LAST_TV_NAME";
    private static final String LAST_RADIO_NAME = "LAST_RADIO_NAME";
    private static final String CONFIG_INFO = "CONFIG_INFO";

    private static final String VOLUME_UNIFIED = "VOLUME_UNIFIED";
    private static final String VOLUME_VALUE = "VOLUME_VALUE";
    private static final String VOLUME_MUTE = "VOLUME_MUTE";

    private static final String REMOTE_CHANNEL_VERSION = "REMOTE_CHANNEL_VERSION";
    private static final String LOCAL_CHANNEL_VERSION = "LOCAL_CHANNEL_VERSION";
    private static final String REMOTE_EPG_VERSION = "REMOTE_EPG_VERSION";
    private static final String PLAYBACK_DURATION = "PLAYBACK_DURATION";

    private static final String CARD_UPGRADE_TIME = "CARD_UPGRADE_TIME";
    private static final String CARD_UPGRADE_STATUS = "CARD_UPGRADE_STATUS";

    private final SharedPreferences mConfigSp, mDataSp;

    @Inject
    public PreferencesHelper() {
        mConfigSp = App.getInstance().getSharedPreferences(SP_CONFIG_NAME, Context.MODE_PRIVATE);
        mDataSp = App.getInstance().getSharedPreferences(SP_DATA_NAME, Context.MODE_PRIVATE);
    }
}
