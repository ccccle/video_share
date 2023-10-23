package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.service.FanService;
import com.cle.video_share_backend.vo.FanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fan")
public class FanController {
    @Autowired
    private FanService fanService;
    @PostMapping("/follow")
    public ResponseResult follow(@RequestBody FanVo fanVo){
        fanService.follow(fanVo);
        return ResponseResult.success();
    }

}
