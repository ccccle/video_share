package com.cle.video_share_backend.service;

import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.vo.LikeVo;

import java.util.List;

public interface RedisService {
    /**
     * 将like保存到redis
     * @param likeVo
     */
    void saveLikeVoToRedis(LikeVo likeVo);

    /**
     * 使用管道批量从redis中获取数据
     * @return
     */
    List<Like> getLikeFromRedis();
}
