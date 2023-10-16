package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.service.LikeService;
import com.cle.video_share_backend.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @PostMapping("/like")
    public ResponseResult like(@RequestBody LikeVo likeVo){
        likeService.Like(likeVo);
        return ResponseResult.success();
    }

}
