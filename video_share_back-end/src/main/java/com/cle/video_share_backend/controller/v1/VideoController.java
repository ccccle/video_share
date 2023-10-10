package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.service.VideoService;
import com.cle.video_share_backend.vo.VideoVo;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    //上传视频
    @PostMapping("/uploadVideo")
    public ResponseResult uploadVideo(
            @RequestPart("video_data") MultipartFile videoData,
            @RequestPart(value = "video_cover",required = false) MultipartFile videoCover,
            @RequestParam("video_name") String videoName,
            @RequestParam("channel_id") Long channelId,
            @RequestParam("video_description") String videoDescription) {
        VideoVo videoVo = new VideoVo(videoCover, videoData, videoName, videoDescription, channelId);
        videoService.uploadVideo(videoVo);
        return ResponseResult.success();
    }
}
