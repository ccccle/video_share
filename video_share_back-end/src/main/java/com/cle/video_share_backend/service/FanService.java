package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Fan;
import com.cle.video_share_backend.vo.FanVo;

import java.util.List;

public interface FanService extends IService<Fan> {
    /**
     * 关注
     * @param fanVo
     */
    void follow(FanVo fanVo);

    /**
     * 获取关注列表
     * @param userId
     * @return
     */
    List<FanVo> getFollow(Long userId);
}
