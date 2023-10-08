package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.BaseResponse;
import com.cle.video_share_backend.common.CodeEnum;
import com.cle.video_share_backend.common.Response;
import com.cle.video_share_backend.service.EmailService;
import com.cle.video_share_backend.service.UserService;
import com.cle.video_share_backend.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    //发送验证码
    @GetMapping("/sendCode")
    public Response getLoginCode(String email){
        userService.sendCode(email);
        Response response = new Response();
        response.setCode(CodeEnum.SUCCESS.getCode());
        response.setMsg(CodeEnum.SUCCESS.getMsg());
        return response;
    }
    //登录
    @PostMapping("/login")
    public Response login(@RequestBody UserVo userVo){
        userService.login(userVo);
        Response response = new Response();
        return response;
    }
}
