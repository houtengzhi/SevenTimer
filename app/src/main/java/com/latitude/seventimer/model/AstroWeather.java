package com.latitude.seventimer.model;

/**
 * Created by yechy on 2015/9/6.
 */
public class AstroWeather {

    public static final int SHOWDIVIDER = 1;
    public static final int NOSHOWDIVIDER = 0;

    private String date;
    private int hour;
    private int astroCloudId;
    private int astroSeeingId;
    private int astroTransparencyId;
    private int temp;
    private int astroRhId;
    private int astroRainsnowId;
    private int astroUnstableId;
    private int astroWindId;
    private int isShowDivider;

    public AstroWeather() {
    }

    public AstroWeather(String date, int hour, int astroCloudId, int astroSeeingId, int astroTransparencyId,
                        int temp, int astroRhId, int astroRainsnowId, int astroUnstableId, int astroWindId,
                        int isShowDivider) {
        this.date = date;
        this.hour = hour;
        this.astroCloudId = astroCloudId;
        this.astroSeeingId = astroSeeingId;
        this.astroTransparencyId = astroTransparencyId;
        this.temp = temp;
        this.astroRhId = astroRhId;
        this.astroRainsnowId = astroRainsnowId;
        this.astroUnstableId = astroUnstableId;
        this.astroWindId = astroWindId;
        this.isShowDivider = isShowDivider;
    }

    public String getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getAstroCloudId() {
        return astroCloudId;
    }

    public int getAstroSeeingId() {
        return astroSeeingId;
    }

    public int getAstroTransparencyId() {
        return astroTransparencyId;
    }

    public int getTemp() {
        return temp;
    }

    public int getAstroRhId() {
        return astroRhId;
    }

    public int getAstroRainsnowId() {
        return astroRainsnowId;
    }

    public int getAstroUnstableId() {
        return astroUnstableId;
    }

    public int getAstroWindId() {
        return astroWindId;
    }

    public int getIsShowDivider() {
        return isShowDivider;
    }
}
