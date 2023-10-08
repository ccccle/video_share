package com.cle.video_share_backend.config;


import com.cle.video_share_backend.common.ResponseResult;

import com.cle.video_share_backend.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理 配置
 *
 * @author javadog
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getMsg());
    }


}
