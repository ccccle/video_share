package com.cle.video_share_backend.common;

/**
 * 响应状态码枚举
 */
public enum CodeEnum {

    SUCCESS(1, "成功"),
    FAIL(0, "失败"),
    ;

    private final Integer code;

    private final String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
