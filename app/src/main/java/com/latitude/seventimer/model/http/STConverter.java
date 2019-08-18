package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.AstroWeatherBinder;
import com.latitude.seventimer.util.DataParserUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by cloud on 2019-08-17.
 */
public class STConverter implements Converter<ResponseBody, AstroWeatherResponse> {

    @Override
    public AstroWeatherResponse convert(ResponseBody value) throws IOException {
        String response = value.string();
        AstroWeatherBinder cluster = DataParserUtil.parseAstroWeather(response, 0, 0);
        AstroWeatherResponse apiResponse = new AstroWeatherResponse(response, cluster);
        return apiResponse;
    }
}
