package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Comment;
import com.cle.video_share_backend.vo.CommentVo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**
     * 保存评论
     * @param commentVo
     */
    void sendComment(CommentVo commentVo);

    /**
     * 获取视频评论
     * @param videoId
     * @return
     */
    List<CommentVo> getComment(Long videoId);

    IPage<Comment> pageList(Integer page, Integer size, Long videoId);
}
