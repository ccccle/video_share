package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.RewardHistoryMapper;
import com.cle.video_share_backend.pojo.Reward;
import com.cle.video_share_backend.pojo.RewardHistory;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.*;
import com.cle.video_share_backend.utils.UserThreadLocal;
import com.cle.video_share_backend.vo.RewardHistoryVo;
import com.cle.video_share_backend.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardHistoryServiceImpl extends ServiceImpl<RewardHistoryMapper, RewardHistory>implements RewardHistoryService {
    @Autowired
    private VideoService videoService;
    @Autowired
    private RewardService rewardService;

    @Override
    public RewardHistory getByRewardIdAndVideoId(Long rewardId, Long videoId) {
        LambdaQueryWrapper<RewardHistory> rewardHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rewardHistoryLambdaQueryWrapper.eq(RewardHistory::getRewardId,rewardId);
        rewardHistoryLambdaQueryWrapper.eq(RewardHistory::getVideoId,videoId);
        RewardHistory rewardHistory = this.getOne(rewardHistoryLambdaQueryWrapper);
        return rewardHistory;
    }

    @Override
    public IPage pageSelf(Integer page, Integer size) {
        IPage<RewardHistory> rewardHistoryIPage =  new Page<>(page,size);
        User user = UserThreadLocal.get();
        Long userId = user.getId();
        LambdaQueryWrapper<RewardHistory> rewardHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rewardHistoryLambdaQueryWrapper.eq(RewardHistory::getUserId,userId);
        this.page(rewardHistoryIPage,rewardHistoryLambdaQueryWrapper);
        List<RewardHistory> records = rewardHistoryIPage.getRecords();
        List<RewardHistoryVo> collect = records.stream().map(rewardHistory -> {
            RewardHistoryVo rewardHistoryVo = new RewardHistoryVo();
            Reward rewardServiceById = rewardService.getById(rewardHistory.getRewardId());
            rewardHistoryVo.setReward(rewardServiceById);
            VideoVo videoVo = videoService.getVideo(rewardHistory.getVideoId());
            rewardHistoryVo.setVideoVo(videoVo);
            rewardHistoryVo.setCreateTime(rewardHistory.getCreateTime());
            return rewardHistoryVo;
        }).collect(Collectors.toList());
        IPage<RewardHistoryVo> rewardHistoryVoIPage = new Page<>();
        BeanUtils.copyProperties(rewardHistoryIPage,rewardHistoryVoIPage);
        rewardHistoryVoIPage.setRecords(collect);
        return rewardHistoryVoIPage;
    }
}
