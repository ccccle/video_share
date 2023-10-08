package com.cle.video_share_backend.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception{
    public ServiceException( String msg) {
        this.msg = msg;
    }

    private String msg;
}
