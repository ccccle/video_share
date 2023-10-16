package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.config.MinioProperties;
import com.cle.video_share_backend.mapper.ChannelMapper;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.mapper.VideoMapper;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.service.VideoService;
import com.cle.video_share_backend.utils.FfmpegUtil;
import com.cle.video_share_backend.utils.MinioUtil;
import com.cle.video_share_backend.utils.UserThreadLocal;
import com.cle.video_share_backend.vo.UserVo;
import com.cle.video_share_backend.vo.VideoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void uploadVideo(VideoVo videoVo) {
        String minioBaseUrl = minioProperties.getEndpoint() + "/" + minioProperties.getBucket();
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        //获取当前分区的名字
        Channel channel = channelMapper.selectById(video.getChannelId());
        video.setChannelName(channel.getChannelName());
        MultipartFile videoData = videoVo.getVideoData();
        String mp4 = null;
        String pngName = null;
        try {
            InputStream inputStream = videoData.getResource().getInputStream();
            //把所有的格式都转为MP4
            mp4 = FfmpegUtil.videoToMP4(inputStream);
            LocalDate now = LocalDate.now();
            //设置文件名方便minio分文件夹
            String mp4Name = "/" + now.getYear() + "/" + now.getMonth().getValue() + "/" + now.getDayOfMonth() + "/" + UUID.randomUUID() + ".MP4";
            //minio上传文件
            MinioUtil.uploadFile(minioProperties.getBucket(), mp4Name, mp4);
            //如果不存在视频封面
            pngName = "/" + now.getYear() + "/" + now.getMonth().getValue() + "/" + now.getDayOfMonth() + "/" + UUID.randomUUID() + ".png";
            if (videoVo.getVideoCover() == null) {
                //抓取视频内容第一帧作为封面
                String firstFramePng = FfmpegUtil.getFirstFrame(mp4);
                //minio上传文件
                MinioUtil.uploadFile(minioProperties.getBucket(), pngName, firstFramePng);
            }
            //存在直接上传
            else {
                //minio上传文件
                MinioUtil.uploadFile(minioProperties.getBucket(), pngName, videoVo.getVideoCover());
            }
            //设置封面和视频地址
            video.setVideoCover(minioBaseUrl + pngName);
            video.setVideoUrl(minioBaseUrl + mp4Name);
            User user = UserThreadLocal.get();
            video.setUserId(user.getId());
            video.setUserName(user.getName());
            this.save(video);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File mp4File = new File(mp4);
            //删除临时文件
            mp4File.delete();
            File pngNameFile = new File(pngName);
            //删除临时文件
            pngNameFile.delete();
        }
        //如果没有上传视频封面


    }

    @Override
    public Page<VideoVo> feed(Integer size, Integer page, String key) {
        Page<Video> pageVideo = new Page<>(page, size);
        LambdaQueryWrapper<Video> like = new LambdaQueryWrapper<Video>();
        //根据key模糊查询视频名
        if (key != null) {
            like.like(Video::getVideoName, key);
        }
        Page<Video> videoPage = this.page(pageVideo, like);
        List<Video> list = videoPage.getRecords();
        //把videolist转为videovolist
        List<VideoVo> videoVoList = list.stream().map(video -> {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(video, videoVo);
            videoVo.setVideoCoverUrl(video.getVideoCover());
            Long userId = video.getUserId();
            User user = userMapper.selectById(userId);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            videoVo.setUserVo(userVo);
            System.out.println(video);
            System.out.println(videoVo);
            return videoVo;
        }).collect(Collectors.toList());
        Page<VideoVo> videoVoPage = new Page<VideoVo>();
        BeanUtils.copyProperties(videoPage, videoVoPage);
        videoVoPage.setRecords(videoVoList);
        return videoVoPage;

    }

    @Override
    public VideoVo getVideo(Long id) {

        Video video = this.getById(id);
        VideoVo videoVo = new VideoVo();
        User user = userMapper.selectById(video.getUserId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        BeanUtils.copyProperties(video,videoVo);
        videoVo.setUserVo(userVo);
        return videoVo;
    }
}
