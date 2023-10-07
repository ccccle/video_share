package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.BaseResponse;
import com.cle.video_share_backend.common.CodeEnum;
import com.cle.video_share_backend.common.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping("/login")
    public Response getLoginCode(String email){
        Response response = new Response();
        response.setCode(CodeEnum.SUCCESS.getCode());
        response.setMsg(CodeEnum.SUCCESS.getMsg());
        return response;
    }
}
