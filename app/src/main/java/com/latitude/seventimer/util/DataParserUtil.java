package com.latitude.seventimer.util;

import android.os.Message;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.Address;
import com.latitude.seventimer.model.AstroWeather;
import com.latitude.seventimer.model.AstroWeatherCluster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by cloud on 2019/4/20.
 */
public class DataParserUtil {

    public static Address parseLocation(String response, float latitude, float longitude) {
        Address location = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultArray = jsonObject.getJSONArray("results");
            if (resultArray.length() > 0) {
                JSONObject subObject = resultArray.getJSONObject(0);
                String address_name = subObject.getString("formatted_address");
                StringTokenizer tokenizer = new StringTokenizer(address_name);
                address_name = tokenizer.nextToken();
                location = new Address(address_name, R.drawable.weather1,
                        latitude, longitude);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location;
    }

    public static AstroWeatherCluster parseAstroWeather(String response, float latitude, float longitude) {
        AstroWeatherCluster astroWeatherCluster = null;
        List<AstroWeather> astroWeatheList2 = new ArrayList<AstroWeather>();
        //由longitude算所在时区
        int timeZone;
        if (longitude % 15 < 7.5) {
            timeZone = (int) longitude / 15;
        } else {
            timeZone = ((int) longitude / 15) + 1;
        }
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String product = jsonObject1.getString("product");
            String dateStr = jsonObject1.getString("init");
            String format = "yyyyMMddHH";
            DateFormat showDateFormat = new SimpleDateFormat("dd日", Locale.UK);
            DateFormat showUpdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.UK);
            Date originDate = new SimpleDateFormat(format,Locale.UK).parse(dateStr);
            Calendar calendar = Calendar.getInstance(Locale.UK);
            calendar.setTime(originDate);
            calendar.add(Calendar.HOUR_OF_DAY, timeZone);
            String updateTime = showUpdateFormat.format(calendar.getTime());

            int astro_cloud[] = {R.drawable.astro_cloud_1, R.drawable.astro_cloud_2, R.drawable.astro_cloud_3,
                    R.drawable.astro_cloud_4, R.drawable.astro_cloud_5, R.drawable.astro_cloud_6,
                    R.drawable.astro_cloud_7, R.drawable.astro_cloud_8, R.drawable.astro_cloud_9};
            int astro_seeing[] = {R.drawable.astro_seeing_1, R.drawable.astro_seeing_2,
                    R.drawable.astro_seeing_3, R.drawable.astro_seeing_4, R.drawable.astro_seeing_5,
                    R.drawable.astro_seeing_6, R.drawable.astro_seeing_7, R.drawable.astro_seeing_8};
            int astro_trans[] = {R.drawable.astro_transparency_1, R.drawable.astro_transparency_2,
                    R.drawable.astro_transparency_3, R.drawable.astro_transparency_4,
                    R.drawable.astro_transparency_5, R.drawable.astro_transparency_6,
                    R.drawable.astro_transparency_7, R.drawable.astro_transparency_8};
            int astro_unstable[] = {R.drawable.astro_unstable_1, R.drawable.astro_unstable_2,
                    R.drawable.astro_unstable_3};
            int astro_rh[] = {R.drawable.astro_rh_1, R.drawable.astro_rh_2, R.drawable.astro_rh_3};
            int astro_rainsnow[] = {R.drawable.astro_rainsnow_rain, R.drawable.astro_rainsnow_snow};
            int astro_wind[] = {R.drawable.astro_wind_1, R.drawable.astro_wind_2, R.drawable.astro_wind_3};

            JSONArray jsonArray2 = jsonObject1.getJSONArray("dataseries");
            String[] date = new String[jsonArray2.length()];
            String[] dateRef = new String[jsonArray2.length()];
            int[] isShowDivider = new int[jsonArray2.length()];
            int[] hour = new int[jsonArray2.length()];
            int[] astroCloudId = new int[jsonArray2.length()];
            int[] astroSeeingId = new int[jsonArray2.length()];
            int[] astroTransparencyId = new int[jsonArray2.length()];
            int[] temp = new int[jsonArray2.length()];
            int[] astroRhId = new int[jsonArray2.length()];
            int[] astroRainsnowId = new int[jsonArray2.length()];
            int[] astroUnstableId = new int[jsonArray2.length()];
            int[] astroWindId = new int[jsonArray2.length()];

            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                int timepoint = jsonObject2.getInt("timepoint");
                int cloudcover = jsonObject2.getInt("cloudcover");
                int seeing = jsonObject2.getInt("seeing");
                int trans = jsonObject2.getInt("transparency");
                int lifted_index = jsonObject2.getInt("lifted_index");
                int rh2m = jsonObject2.getInt("rh2m");
                int temp2m = jsonObject2.getInt("temp2m");
                String prec_type = jsonObject2.getString("prec_type");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("wind10m");

                String direction = jsonObject3.getString("direction");
                int speed = jsonObject3.getInt("speed");

                calendar.add(Calendar.HOUR_OF_DAY, timepoint);
                date[i] = showDateFormat.format(calendar.getTime());
                dateRef[i] = showDateFormat.format(calendar.getTime());
                hour[i] = calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY, -timepoint);
                astroCloudId[i] = astro_cloud[cloudcover-1];
                astroSeeingId[i] = astro_seeing[seeing-1];
                astroTransparencyId[i] = astro_trans[trans-1];
                temp[i] = temp2m;
                //判断相对湿度
                if (rh2m == 12 || rh2m == 13) {
                    astroRhId[i] = astro_rh[0];
                } else if (rh2m == 14) {
                    astroRhId[i] = astro_rh[1];
                } else if (rh2m == 15 || rh2m == 16) {
                    astroRhId[i] = astro_rh[2];
                } else {
                    astroRhId[i] = R.drawable.astro_null;
                }
                //判断降水类型
                if (prec_type == "rain") {
                    astroRainsnowId[i] = astro_rainsnow[0];
                } else if (prec_type == "snow") {
                    astroRainsnowId[i] = astro_rainsnow[1];
                } else {
                    astroRainsnowId[i] = R.drawable.astro_null;
                }
                //判断抬升指数
                if (lifted_index == -1) {
                    astroUnstableId[i] = astro_unstable[0];
                } else if (lifted_index == -4) {
                    astroUnstableId[i] = astro_unstable[1];
                } else if (lifted_index <= -5) {
                    astroUnstableId[i] = astro_unstable[2];
                } else {
                    astroUnstableId[i] = R.drawable.astro_null;
                }
                //判断大风预警
                if (speed == 5) {
                    astroWindId[i] = astro_wind[0];
                } else if (speed == 6) {
                    astroWindId[i] =astro_wind[1];
                } else if (speed > 6) {
                    astroWindId[i] =astro_wind[2];
                } else {
                    astroWindId[i] = R.drawable.astro_null;
                }
                isShowDivider[i] = AstroWeather.NOSHOWDIVIDER;
            }
            for(int i = 1; i < jsonArray2.length(); i++) {
                if (dateRef[i].equals(dateRef[i-1])) {
                    date[i] = " ";
                } else {
                    isShowDivider[i-1] = AstroWeather.SHOWDIVIDER;
                }
            }
            for (int i = 0; i < jsonArray2.length(); i++) {
                AstroWeather astroWeather = new AstroWeather(date[i], hour[i], astroCloudId[i],
                        astroSeeingId[i], astroTransparencyId[i], temp[i], astroRhId[i], astroRainsnowId[i],
                        astroUnstableId[i], astroWindId[i], isShowDivider[i]);
                astroWeatheList2.add(astroWeather);
            }

            astroWeatherCluster = new AstroWeatherCluster(astroWeatheList2, timeZone,
                    updateTime);

        } catch (JSONException e ) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return astroWeatherCluster;
    }
}
