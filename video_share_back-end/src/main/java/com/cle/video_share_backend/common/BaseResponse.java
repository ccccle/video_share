package com.cle.video_share_backend.common;

import lombok.Data;

/**
 * 响应数据基类
 */
@Data
public class BaseResponse {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    protected BaseResponse() {}

    protected BaseResponse(CodeEnum code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }
}
