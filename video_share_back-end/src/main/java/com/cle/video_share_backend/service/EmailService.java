package com.cle.video_share_backend.service;


public interface EmailService {
    //发送邮件验证码
    int sendEmailCode(String email);
}
