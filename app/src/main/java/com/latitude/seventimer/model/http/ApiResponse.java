package com.latitude.seventimer.model.http;

/**
 * Created by cloud on 2019-08-17.
 */
public class ApiResponse<T> {

    public static final int CODE_SUCCESS = 0x1;
    public static final int CODE_NO_DATA = 0x2;

    protected int code;
    protected String response;
    protected T data;

    public ApiResponse() {
    }

    public ApiResponse(String response) {
        this(CODE_SUCCESS, response, null);
    }

    public ApiResponse(int code) {
        this(code, null, null);
    }

    public ApiResponse(int code, String response) {
        this(code, response, null);
    }

    public ApiResponse(String response, T data) {
        this(CODE_SUCCESS, response, data);
    }

    public ApiResponse(int code, String response, T data) {
        this.code = code;
        this.response = response;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }

    public T getData() {
        return data;
    }

    public boolean isDataValid() {
        return code == CODE_SUCCESS;
    }
}
