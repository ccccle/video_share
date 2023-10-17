package com.cle.video_share_backend.service;

import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.vo.LikeVo;

import java.util.List;

public interface RedisService {

    /**
     * 将like保存到redis
     * @param likeVo
     */
    void saveLikeVoToRedis(LikeVo likeVo);

    /**
     * 使用管道批量从redis中获取点赞数据
     * @return
     */
    List<Like> getLikeListFromRedis();

    /**
     * 增加视频统计数
     */
    void videoCountIncrease(Long videoId, int count,String hKey);

    /**
     * 初始化video的count
     * @param videoId
     */
    void videoCountInit(Long videoId);

    /**
     * 获取video的count
     * @return
     */
    List<Video> getVideoCount();
    /**
     * 使用从redis中获取点赞数据
     *
     * @return
     */

    Boolean getLikeFromRedis(Long videoId, Long userId);
}
