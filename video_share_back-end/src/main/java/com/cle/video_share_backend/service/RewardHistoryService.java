package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.RewardHistory;

public interface RewardHistoryService extends IService<RewardHistory> {
    /**
     * 查询是否有拿过rewardId奖励的视频
     * @param rewardId
     * @param videoId
     * @return
     */
    RewardHistory getByRewardIdAndVideoId(Long rewardId,Long videoId);
}
