package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Comment;
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
    public ResponseResult getComment(@RequestParam(value = "page",required = false) Integer page,@RequestParam(value = "size",required = false) Integer size, @RequestParam("video_id") Long videoId){
        if(page!=null&&page!=null){
            //分页查询评论
            IPage<Comment> commentIPage = commentService.pageList(page, size, videoId);
            return ResponseResult.success(commentIPage);
        }
        List<CommentVo> commentVoList = commentService.getComment(videoId);
        return ResponseResult.success(commentVoList);
    }
    @DeleteMapping("/delComment")
    public ResponseResult delComment(Long id){
        commentService.removeById(id);
        return ResponseResult.success();
    }
}
