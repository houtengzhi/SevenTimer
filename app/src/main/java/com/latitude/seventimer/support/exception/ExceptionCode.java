package com.latitude.seventimer.support.exception;

/**
 * Created by yechy on 2017/8/9.
 */

public class ExceptionCode {

    /**网络错误**/
    public static final int ERROR_UNKNOWN = 0x0;
    public static final int ERROR_NO_NETWORK = 0x1;

    /*****数据错误*****/
    public static final int ERROR_PARSE = 0x10;
    public static final int ERROR_NO_PROGRAM = 0x11;
    public static final int ERROR_INVALID_URL = 0x12;
    public static final int ERROR_NULL = 0x13;

    public static final String MSG_ERROR_UNKNOWN = "ERROR_UNKNOWN";
    public static final String MSG_ERROR_NO_NETWORK = "NO_NETWORK";
    public static final String MSG_ERROR_PARSE = "ERROR_PARSE";
    public static final String MSG_ERROR_NO_PROGRAM = "NO_PROGRAM";
    public static final String MSG_ERROR_INVALID_URL = "Invalid url exception";
    public static final String MSG_ERROR_NULL = "Null exception";
}
