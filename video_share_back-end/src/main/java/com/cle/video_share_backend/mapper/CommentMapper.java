package com.cle.video_share_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cle.video_share_backend.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
