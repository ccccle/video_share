package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Reward;

public interface RewardService extends IService<Reward> {
    IPage pageReward(Integer page, Integer size);
}
