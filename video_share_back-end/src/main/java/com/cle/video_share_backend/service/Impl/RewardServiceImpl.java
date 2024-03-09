package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.RewardMapper;
import com.cle.video_share_backend.pojo.Reward;
import com.cle.video_share_backend.service.RewardService;
import org.springframework.stereotype.Service;

@Service
public class RewardServiceImpl extends ServiceImpl<RewardMapper, Reward> implements RewardService {

    @Override
    public IPage pageReward(Integer page, Integer size) {
        IPage<Reward> rewardIPage = new Page<>(page,size);
        this.page(rewardIPage);
        return rewardIPage;
    }
}
