package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.EmailService;
import com.cle.video_share_backend.service.UserService;
import com.cle.video_share_backend.utils.JWTUtils;
import com.cle.video_share_backend.utils.RedisUtils;
import com.cle.video_share_backend.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
        redisUtils.set(RedisConstant.LOGIN_CODE + email, String.valueOf(code), 60);
    }

    @Override
    public UserVo login(UserVo userVo) throws ServiceException {
        String code = redisUtils.get(RedisConstant.LOGIN_CODE + userVo.getEmail());
        if (code.equals(userVo.getCode())) {
            User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, userVo.getEmail()));
            //说明没有注册过   开始注册
            if (user == null) {
                user = new User();
                user.setAvatar(User.DEFAULT_AVATAR);
                user.setEmail(userVo.getEmail());
                user.setName(userVo.getEmail());
                this.save(user);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("id", user.getId().toString());
            map.put("email", user.getEmail());
            map.put("name", user.getName());
            map.put("avatar", user.getAvatar());
            userVo.setName(user.getName());
            userVo.setAvatar(user.getAvatar());
            userVo.setToken(JWTUtils.getToken(map));
        } else {
            throw new ServiceException("验证码错误");
        }
        return userVo;
    }

    @Override
    public UserVo getUserInfo(Long id) {
        User userDb = this.getById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDb,userVo);
        return userVo;

    }
}
