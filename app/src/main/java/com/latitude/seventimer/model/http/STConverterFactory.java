package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.AstroWeatherBinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by cloud on 2019-08-17.
 */
public class STConverterFactory extends Converter.Factory {

    public static STConverterFactory create() {
        return new STConverterFactory();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == AstroWeatherResponse.class) {
            return new STConverter();
        } else {
            return null;
        }
    }
}
