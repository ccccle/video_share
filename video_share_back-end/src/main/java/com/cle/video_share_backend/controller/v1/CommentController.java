package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.service.CommentService;
import com.cle.video_share_backend.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/sendComment")
    public ResponseResult sendComment(@RequestBody CommentVo commentVo){
        commentService.sendComment(commentVo);
        return ResponseResult.success();
    }
    @GetMapping("/getComment")
    public ResponseResult getComment( @RequestParam("video_id") Long videoId){
        List<CommentVo> commentVoList = commentService.getComment(videoId);
        return ResponseResult.success(commentVoList);
    }
}
