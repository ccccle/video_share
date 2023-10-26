package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.service.VideoService;
import com.cle.video_share_backend.vo.UserVo;
import com.cle.video_share_backend.vo.VideoVo;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    //推荐的视频流
    @GetMapping("/feed")
    public ResponseResult feed(Integer page,Integer size,String key,@RequestParam(value = "channel_id",required = false ) Long channelId){
        Page<VideoVo> userVoList =  videoService.feed(page,size,key,channelId);
        return ResponseResult.success(userVoList);
    }
    //获取具体视频信息
    @GetMapping("/getVideo")
    public ResponseResult getVideo(Long id){
       VideoVo videoVo = videoService.getVideo(id);
       return ResponseResult.success(videoVo);
    }

    /**
     * 获取用户上传的视频
     * @param userId
     * @return
     */
    @GetMapping("/getUserVideo")
    public ResponseResult getUserVideo(Integer size,Integer page, @RequestParam("user_id") Long userId){
       Page<VideoVo> videoVoList= videoService.getVideoByUserId(size,page,userId);
       return ResponseResult.success(videoVoList);
    }
    @DeleteMapping("/delVideo")
    public ResponseResult delVideo( Long id){
        videoService.delVideo(id);
        return ResponseResult.success();
    }
}
