package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.service.UserService;
import com.cle.video_share_backend.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    //发送验证码
    @GetMapping("/sendCode")
    public ResponseResult getLoginCode(String email){
        userService.sendCode(email);
        return ResponseResult.success();
    }
    //登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserVo userVo) throws ServiceException {
        UserVo vo = userService.login(userVo);
        return ResponseResult.success(1,"登录成功",vo);
    }
    //获取个人信息
    @GetMapping("/getUserInfo")
    public ResponseResult getUserInfo(Long id){
        UserVo userInfo = userService.getUserInfo(id);
        return ResponseResult.success(1,"获取成功",userInfo);
    }

}
