package com.latitude.seventimer.support.exception;

/**
 * Created by yechy on 2017/8/9.
 */

public class Result<T> {
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_ERROR = 1;

    private String resultMessage;
    private int resultCode;
    private T data;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
