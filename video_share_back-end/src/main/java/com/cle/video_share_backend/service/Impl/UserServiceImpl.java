package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.EmailService;
import com.cle.video_share_backend.service.UserService;
import com.cle.video_share_backend.utils.RedisUtils;
import com.cle.video_share_backend.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginContext;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void sendCode(String email) {
        //发送邮件
        int code = emailService.sendEmailCode(email);
        //验证码存入redis
        redisUtils.set(RedisConstant.LOGIN_CODE+email,code,60);
    }

    @Override
    public void login(UserVo userVo) {
        User user = new User();
        user.set
    }
}
