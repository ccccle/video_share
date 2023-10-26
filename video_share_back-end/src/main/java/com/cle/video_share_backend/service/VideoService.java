package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.vo.VideoVo;

public interface VideoService extends IService<Video> {
    //上传视频
    void uploadVideo(VideoVo videoVo);
    //视频流
    Page<VideoVo> feed(Integer size, Integer page, String key, Long channelId);
    //获取详细视频信息
    VideoVo getVideo(Long id);

    /**
     * 获取用户上传的视频
     *
     * @param size
     * @param page
     * @param userId
     * @return
     */
    Page<VideoVo> getVideoByUserId(Integer page, Integer size, Long userId);

    /**
     * 删除视频
     *
     * @param id
     */
    void delVideo(Long id);
}
