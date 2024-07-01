package com.hzw.exception;

import com.hzw.comon.ResponseResult;
import com.hzw.constant.CommonConstants;
public class GlobalExceptionMyHandler extends RuntimeException {
    private static String customMessage;

    public GlobalExceptionMyHandler(String message) {
        super(message);
        this.customMessage = message;
    }
    public String getCustomMessage() {
        return customMessage;
    }

    public ResponseResult handleException() {
        return new ResponseResult(CommonConstants.FAIL, getCustomMessage());
    }

}
