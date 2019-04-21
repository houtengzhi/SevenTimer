package com.latitude.seventimer.support.exception;

/**
 * Created by yechy on 2017/8/9.
 */

public class ApiException extends Exception {
    /*错误码*/
    private int code;

    /*错误信息*/
    private String message;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    public ApiException(Throwable throwable, int code, String message) {
        super(throwable);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        String msg;
        switch (code) {
            case ExceptionCode.ERROR_NO_NETWORK:
                msg = ExceptionCode.MSG_ERROR_NO_NETWORK;
                break;

            case ExceptionCode.ERROR_NO_PROGRAM:
                msg = ExceptionCode.MSG_ERROR_NO_PROGRAM;
                break;

            case ExceptionCode.ERROR_PARSE:
                msg = ExceptionCode.MSG_ERROR_PARSE;
                break;

            default:
                msg = message;
                break;
        }
        return msg;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
