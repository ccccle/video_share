package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Fan;
import com.cle.video_share_backend.vo.FanVo;

public interface FanService extends IService<Fan> {
    /**
     * 关注
     * @param fanVo
     */
    void follow(FanVo fanVo);
}
