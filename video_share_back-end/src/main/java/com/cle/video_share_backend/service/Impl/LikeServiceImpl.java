package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.mapper.LikeMapper;
import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.service.LikeService;
import com.cle.video_share_backend.service.RedisService;
import com.cle.video_share_backend.vo.LikeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    private RedisService redisService;


    @Override
    public void Like(LikeVo likeVo) {
        //将点赞保存到redis
        redisService.saveLikeVoToRedis(likeVo);
        //统计点赞数
        if(likeVo.getLike()){
            redisService.videoCountIncrease(likeVo.getVideoId(),1, RedisConstant.LIKE_COUNT);
        }else {
            redisService.videoCountIncrease(likeVo.getVideoId(),-1, RedisConstant.LIKE_COUNT);
        }
    }

    @Override
    public void saveOrUpdateLike(Like like) {
        //查询视频id和用户id相同的
        LambdaQueryWrapper<Like> likeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        likeLambdaQueryWrapper.eq(Like::getVideoId,like.getVideoId());
        likeLambdaQueryWrapper.eq(Like::getUserId,like.getUserId());
        Like likeDB = this.getOne(likeLambdaQueryWrapper);
        if(likeDB!=null){
            likeDB.setLike(like.getLike());
            this.updateById(likeDB);
        }
        else {
            this.save(like);
        }

    }
}
