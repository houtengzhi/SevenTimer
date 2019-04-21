package com.latitude.seventimer.support.exception;

/**
 * Created by yechy on 2017/8/9.
 */

public class CustomException extends RuntimeException {
    private int code;

    public CustomException(int code) {
        this.code = code;
    }

    public CustomException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public CustomException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        String msg;
        switch (code) {
            case ExceptionCode.ERROR_NO_NETWORK:
                msg = "No reachable network error!";
                break;

            default:
                msg = super.getMessage();
                break;
        }
        return msg;
    }
}
