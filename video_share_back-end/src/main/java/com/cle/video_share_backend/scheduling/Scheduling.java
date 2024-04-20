package com.cle.video_share_backend.scheduling;

import com.cle.video_share_backend.pojo.*;
import com.cle.video_share_backend.service.*;
import com.cle.video_share_backend.vo.VideoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class Scheduling {
    @Autowired
    private RedisService redisService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private RewardHistoryService rewardHistoryService;
    @Autowired
    private WalletService walletService;

    /**
     * 定时从redis里面获取点赞数据同步到数据库
     */
//    @Scheduled(cron = "*/5 * * * * ?")
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
        List<Reward> rewardList = rewardService.list();
        //同步到数据库
        for (Video video : videoList) {
            if(Objects.isNull(video)){
                continue;
            }
            videoService.updateById(video);
            for (Reward reward : rewardList) {
                if(Objects.isNull(video)){
                    continue;
                }
                //评论符合奖励 (点赞数满足并且奖励发布时间在视频发布时间之前)
                if (reward.getLikeCount() <= video.getLikeCount()) {
                    video = videoService.getById(video.getId());
                    if(Objects.isNull(video)){
                        continue;
                    }
                    Long userId = video.getUserId();
                    Wallet wallet = walletService.getByUserId(userId);
                    if(wallet==null){
                        //未开通创作激励  终止
                        return;
                    }
                    if (reward.getCreateTime().isBefore(video.getCreateTime())) {
                        //判断是否获得过奖励
                        RewardHistory rewardHistory = rewardHistoryService.getByRewardIdAndVideoId(reward.getId(), video.getId());
                        if (rewardHistory == null) {
                            //没有得过奖励
                            //发放奖励
                            wallet.setBalance(wallet.getBalance()+reward.getPrice());
                            walletService.updateById(wallet);
                            //记录奖励
                            RewardHistory rewardHistory1 = new RewardHistory();
                            rewardHistory1.setUserId(video.getUserId());
                            rewardHistory1.setRewardId(reward.getId());
                            rewardHistory1.setVideoId(video.getId());
                            rewardHistoryService.save(rewardHistory1);
                        }
                    }
                }
            }
        }

    }
}
