package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.service.FanService;
import com.cle.video_share_backend.vo.FanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fan")
public class FanController {
    @Autowired
    private FanService fanService;

    /**
     * 关注
     * @param fanVo
     * @return
     */
    @PostMapping("/follow")
    public ResponseResult follow(@RequestBody FanVo fanVo){
        fanService.follow(fanVo);
        return ResponseResult.success();
    }

    @GetMapping("/getFollow")
    public ResponseResult getFollow(@RequestParam("user_id") Long userId){
       List<FanVo> fanVoList = fanService.getFollow(userId);
       return ResponseResult.success(fanVoList);
    }
}
