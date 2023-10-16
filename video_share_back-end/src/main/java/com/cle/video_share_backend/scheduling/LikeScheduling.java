package com.cle.video_share_backend.scheduling;

import com.cle.video_share_backend.mapper.LikeMapper;
import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.service.LikeService;
import com.cle.video_share_backend.service.RedisService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikeScheduling {
    @Autowired
    private RedisService redisService;
    @Autowired
    private LikeService likeService;
    @Scheduled(cron = "*/5 * * * * ?")
    public void saveLikeFormRedisToDB() {
        List<Like> likeList = redisService.getLikeFromRedis();
        for (Like like : likeList) {
            likeService.saveOrUpdateLike(like);
        }

    }
}
