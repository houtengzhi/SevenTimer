package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.AstroWeatherBinder;

/**
 * Created by cloud on 2019-08-18.
 */
public class AstroWeatherResponse extends ApiResponse<AstroWeatherBinder> {

    public AstroWeatherResponse() {
        super();
    }

    public AstroWeatherResponse(String response) {
        super(response);
    }

    public AstroWeatherResponse(int code) {
        super(code);
    }

    public AstroWeatherResponse(int code, String response) {
        super(code, response);
    }

    public AstroWeatherResponse(String response, AstroWeatherBinder data) {
        super(response, data);
    }

    public AstroWeatherResponse(int code, String response, AstroWeatherBinder data) {
        super(code, response, data);
    }
}
