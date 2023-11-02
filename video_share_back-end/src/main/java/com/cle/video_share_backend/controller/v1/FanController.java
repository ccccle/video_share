package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public ResponseResult getFollow(@RequestParam(value = "page",required = false) Integer page,
                                    @RequestParam(value = "size",required = false) Integer size,
                                    @RequestParam("user_id") Long userId,
                                    @RequestParam("type") String type){
        if (page != null && size != null) {
                Page<FanVo> fanVoPage = fanService.getFollow(page, size, userId,type);
            return ResponseResult.success(fanVoPage);
        }
        else {
            List<FanVo> fanVos = fanService.getFollow(userId,type);
            return ResponseResult.success(fanVos);
        }

    }
    @GetMapping("/getCount")
    public ResponseResult getCount(@RequestParam("user_id") Long userId,@RequestParam("type") String type){
       long count = fanService.getCount(userId,type);
       return ResponseResult.success(count);
    }
}
