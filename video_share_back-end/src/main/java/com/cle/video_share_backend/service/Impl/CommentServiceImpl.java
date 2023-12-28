package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.mapper.CommentMapper;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.pojo.Comment;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.CommentService;
import com.cle.video_share_backend.service.RedisService;
import com.cle.video_share_backend.utils.UserThreadLocal;
import com.cle.video_share_backend.vo.CommentVo;
import com.cle.video_share_backend.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
   @Autowired
   private UserMapper userMapper;
   @Autowired
   private RedisService redisService;
    @Override
    public void sendComment(CommentVo commentVo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo,comment);
        User user = UserThreadLocal.get();
        comment.setCommentUserId(user.getId());
        //评论数+1
        redisService.videoCountIncrease(commentVo.getVideoId(),1, RedisConstant.COMMENT_COUNT);
        this.save(comment);
    }

    @Override
    public List<CommentVo> getComment(Long videoId) {
        LambdaQueryWrapper<Comment> commentEqVideoId = new LambdaQueryWrapper<Comment>().eq(Comment::getVideoId, videoId);
        List<Comment> commentList = this.list(commentEqVideoId);
        //查出来符合的评论转换成commentVo,筛选出来commentId为null的,一级评论
        List<CommentVo> commentV1 = commentList.stream().map(comment -> {
            if (comment.getCommentId() == null) {
                CommentVo commentVo = new CommentVo();
                BeanUtils.copyProperties(comment, commentVo);
                User commentUser = userMapper.selectById(commentVo.getCommentUserId());
                UserVo commentUserVo = new UserVo();
                BeanUtils.copyProperties(commentUser,commentUserVo);
                commentVo.setCommentUserVo(commentUserVo);
                return commentVo;
            }
            return null;
        }).filter(commentVo -> commentVo!=null).collect(Collectors.toList());
        //筛选出来一级评论下的所有评论
        commentV1.stream().forEach(commentVo -> {
            List<CommentVo> collect = commentList.stream().map(comment -> {
                CommentVo commentVo1 = new CommentVo();
                if (commentVo.getId().equals(comment.getCommentId())) {
                    BeanUtils.copyProperties(comment, commentVo1);
                    User commentUser = userMapper.selectById(commentVo1.getCommentUserId());
                    UserVo commentUserVo = new UserVo();
                    BeanUtils.copyProperties(commentUser,commentUserVo);
                    User toUser = userMapper.selectById(commentVo1.getToUserId());
                    UserVo toUserVo = new UserVo();
                    BeanUtils.copyProperties(toUser,toUserVo);
                    commentVo1.setCommentUserVo(commentUserVo);
                    commentVo1.setToUserVo(toUserVo);
                    return commentVo1;
                }
                return null;
            })
                    .filter(commentVo1 -> commentVo1!=null)
                    .sorted(Comparator.comparing(CommentVo::getCreateTime))
                    .collect(Collectors.toList());
            commentVo.setCommentVoList(collect);
        });
        return commentV1;
    }

    @Override
    public IPage<Comment> pageList(Integer page, Integer size, Long videoId) {
        IPage<Comment> commentIPage = new Page<>(page,size);
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<Comment>().eq(Comment::getVideoId,videoId);
        this.page(commentIPage,lambdaQueryWrapper);
        return commentIPage;
    }


//    //找出一级评论下的所有评论
//    private CommentVo filterComment(CommentVo commentVo,List<Comment> commentList) {
//        Long id = commentVo.getId();
//        List<CommentVo> collect = commentList.stream().map(comment -> {
//            if(id.equals(comment.getCommentId())) {
//                CommentVo commentVo1 = new CommentVo();
//                BeanUtils.copyProperties(comment, commentVo1);
//                commentVo1= filterComment(commentVo1,commentList);
//                return commentVo1;
//            }
//            return null;
//                }
//        ).filter(commentVo1 -> commentVo1!=null)
//                .sorted(Comparator.comparing(CommentVo::getCreateTime))
//                .collect(Collectors.toList());
//        commentVo.setCommentVoList(collect.size()==0?null:collect);
//        Long userId = commentVo.getUserId();
//        User user = userMapper.selectById(userId);
//        UserVo userVo = new UserVo();
//        BeanUtils.copyProperties(user,userVo);
//        commentVo.setUserVo(userVo);
//        return commentVo;
//    }
}
