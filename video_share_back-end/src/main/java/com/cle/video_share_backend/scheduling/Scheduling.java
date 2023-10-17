package com.cle.video_share_backend.scheduling;

import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.service.LikeService;
import com.cle.video_share_backend.service.RedisService;
import com.cle.video_share_backend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduling {
    @Autowired
    private RedisService redisService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private VideoService videoService;
    /**
     * 定时从redis里面获取点赞数据同步到数据库
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void saveLikeFormRedisToDB() {
        List<Like> likeList = redisService.getLikeListFromRedis();
        //同步到数据库
        for (Like like : likeList) {
            likeService.saveOrUpdateLike(like);
        }
    }
    /**
     * 定时从redis里面获取视频统计同步到数据库
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void saveVideoCountFormRedisToDB() {
        List<Video> videoList = redisService.getVideoCount();
        //同步到数据库
        for (Video video : videoList) {
            videoService.updateById(video);
        }
    }

}
