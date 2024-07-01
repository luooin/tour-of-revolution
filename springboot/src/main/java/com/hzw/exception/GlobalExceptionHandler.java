package com.hzw.exception;

import com.hzw.comon.ResponseResult;
import com.hzw.constant.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        return new ResponseResult(CommonConstants.FAIL,"发生了一个未捕获的异常：" + e.getMessage());
    }
}
