package com.cle.video_share_backend.config;


import com.cle.video_share_backend.common.ResponseResult;

import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.exception.TokenException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 异常处理 配置
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getMsg());
    }
    @ExceptionHandler(TokenException.class)
    public ResponseResult handleTokenException(TokenException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getMsg());
    }

}
