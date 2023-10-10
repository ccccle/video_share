package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.vo.VideoVo;

public interface VideoService extends IService<Video> {
    //上传视频
    void uploadVideo(VideoVo videoVo);
}
