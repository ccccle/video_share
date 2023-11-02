package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.config.MinioProperties;
import com.cle.video_share_backend.mapper.ChannelMapper;
import com.cle.video_share_backend.mapper.FanMapper;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.mapper.VideoMapper;
import com.cle.video_share_backend.pojo.*;
import com.cle.video_share_backend.service.LikeService;
import com.cle.video_share_backend.service.RedisService;
import com.cle.video_share_backend.service.VideoService;
import com.cle.video_share_backend.utils.FfmpegUtil;
import com.cle.video_share_backend.utils.MinioUtil;
import com.cle.video_share_backend.utils.UserThreadLocal;
import com.cle.video_share_backend.vo.FanVo;
import com.cle.video_share_backend.vo.LikeVo;
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
    @Autowired
    private RedisService redisService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FanMapper fanMapper;
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
        String png = null;
        try {
            InputStream inputStream = videoData.getResource().getInputStream();
            //把所有的格式都转为MP4
            mp4 = FfmpegUtil.videoToMP4(inputStream);
            LocalDate now = LocalDate.now();
            //设置文件名方便minio分文件夹
            String mp4Name = "/" + now.getYear() + "/" + now.getMonth().getValue() + "/" + now.getDayOfMonth() + "/" + UUID.randomUUID() + ".MP4";
            //minio上传文件
            MinioUtil.uploadFile(minioProperties.getBucket(), mp4Name, mp4);
             png = UUID.randomUUID() + ".png";
            //如果不存在视频封面
            String pngName = "/" + now.getYear() + "/" + now.getMonth().getValue() + "/" + now.getDayOfMonth() + "/" + png;
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
            redisService.videoCountInit(video.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File mp4File = new File(mp4);
            //删除临时文件
            mp4File.delete();
            File pngNameFile = new File(png);
            //删除临时文件
            pngNameFile.delete();
        }
        //如果没有上传视频封面


    }

    @Override
    public Page<VideoVo> feed(Integer size, Integer page, String key, Long channelId) {
        Page<Video> pageVideo = new Page<>(size, page);
        LambdaQueryWrapper<Video> like = new LambdaQueryWrapper<Video>();
        //根据key模糊查询视频名
        if (key != null) {
            like.like(Video::getVideoName, key);
        }
        if(channelId !=null){
            like.like(Video::getChannelId, channelId);
        }
        this.page(pageVideo, like);
        List<Video> list = pageVideo.getRecords();
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
            return videoVo;
        }).collect(Collectors.toList());
        Page<VideoVo> videoVoPage = new Page<VideoVo>();
        BeanUtils.copyProperties(pageVideo, videoVoPage);
        videoVoPage.setRecords(videoVoList);
        return videoVoPage;

    }

    @Override
    public VideoVo getVideo(Long id) {
        //观看数+1
        redisService.videoCountIncrease(id,1, RedisConstant.PLAY_COUNT);
        //获取视频信息
        Video video = this.getById(id);
        VideoVo videoVo = getVideoVoByVideo(video);
        return videoVo;
    }
    public VideoVo getVideoVoByVideo(Video video){
        VideoVo videoVo = new VideoVo();
        //获取创作者信息
        User user = userMapper.selectById(video.getUserId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        BeanUtils.copyProperties(video,videoVo);
        //获取登录用户点赞信息
        User loginUser = UserThreadLocal.get();
        //先去数据库查
        Like like = likeService.getById(loginUser.getId());
        //再去redis查
        Boolean islike = redisService.getLikeFromRedis(video.getId(), loginUser.getId());
        LikeVo likeVo = new LikeVo();
        //如果redis不存在
        if(islike==null){
            //如果数据库不存在
            if(like==null){
                likeVo.setLike(false);
            }else {
                likeVo.setLike(like.getLike());
            }
        }else {
            likeVo.setLike(islike);
        }
        videoVo.setVideoCoverUrl(video.getVideoCover());
        videoVo.setLikeVo(likeVo);
        //获取对作者的关注信息
        LambdaQueryWrapper<Fan> fanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fanLambdaQueryWrapper.eq(Fan::getUserId,loginUser.getId());
        fanLambdaQueryWrapper.eq(Fan::getFanId,video.getUserId());
        Fan fan = fanMapper.selectOne(fanLambdaQueryWrapper);
        if(fan!=null){
            FanVo fanVo = new FanVo();
            BeanUtils.copyProperties(fan,fanVo);
            userVo.setFanVo(fanVo);
        }
        videoVo.setUserVo(userVo);
        return videoVo;
    }
    @Override
    public Page<VideoVo> getVideoByUserId(Integer page, Integer size, Long userId) {
        Page<Video> videoPage = new Page<>(size,page);
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<Video>().eq(Video::getUserId, userId);
        this.page(videoPage, videoLambdaQueryWrapper);
        List<Video> videoList = videoPage.getRecords();
        List<VideoVo> videoVoList = videoList.stream().map(video -> {
            VideoVo videoVo = getVideoVoByVideo(video);
            return videoVo;
        }).collect(Collectors.toList());
        Page<VideoVo> videoVoPage = new Page<>();
        BeanUtils.copyProperties(videoPage,videoVoPage);
        videoVoPage.setRecords(videoVoList);
        return videoVoPage;
    }

    @Override
    public void delVideo(Long id) {
        this.removeById(id);
    }
}
