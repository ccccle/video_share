package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.RewardHistoryMapper;
import com.cle.video_share_backend.pojo.RewardHistory;
import com.cle.video_share_backend.service.RewardHistoryService;
import org.springframework.stereotype.Service;

@Service
public class RewardHistoryServiceImpl extends ServiceImpl<RewardHistoryMapper, RewardHistory>implements RewardHistoryService {


    @Override
    public RewardHistory getByRewardIdAndVideoId(Long rewardId, Long videoId) {
        LambdaQueryWrapper<RewardHistory> rewardHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rewardHistoryLambdaQueryWrapper.eq(RewardHistory::getRewardId,rewardId);
        rewardHistoryLambdaQueryWrapper.eq(RewardHistory::getVideoId,videoId);
        RewardHistory rewardHistory = this.getOne(rewardHistoryLambdaQueryWrapper);
        return rewardHistory;
    }
}
