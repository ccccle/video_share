package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.vo.UserVo;

public interface UserService extends IService<User> {

//发送验证码
    void sendCode(String email);
//登录
UserVo login(UserVo userVo) throws ServiceException;
//获取个人信息
    UserVo getUserInfo(Long id);

    void updateUserInfo(UserVo userVo);
}
