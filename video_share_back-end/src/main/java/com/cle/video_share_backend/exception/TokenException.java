package com.cle.video_share_backend.exception;

import lombok.Data;

@Data
public class TokenException extends Exception{
    public TokenException( String msg) {
        this.msg = msg;
    }

    private String msg;
}
