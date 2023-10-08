package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.vo.UserVo;

public interface UserService extends IService<User> {

//发送验证码
    void sendCode(String email);
//登录
    void login(UserVo userVo);
}
