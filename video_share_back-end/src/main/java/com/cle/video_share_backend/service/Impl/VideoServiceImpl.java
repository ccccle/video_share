package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.config.MinioProperties;
import com.cle.video_share_backend.mapper.ChannelMapper;
import com.cle.video_share_backend.mapper.VideoMapper;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.pojo.Video;
import com.cle.video_share_backend.service.VideoService;
import com.cle.video_share_backend.utils.FfmpegUtil;
import com.cle.video_share_backend.utils.MinioUtil;
import com.cle.video_share_backend.vo.VideoVo;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private MinioProperties minioProperties;
    @Override
    public void uploadVideo(VideoVo videoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoVo,video);
        //获取当前分区的名字
        Channel channel = channelMapper.selectById(video.getChannelId());
        video.setChannelName(channel.getChannelName());
        MultipartFile videoData = videoVo.getVideoData();
        try {
            //把所有的格式都转为MP4
            InputStream inputStream = videoData.getResource().getInputStream();
            String mp4 = FfmpegUtil.videoToMP4(inputStream);
            LocalDate now = LocalDate.now();
            //设置文件名方便minio分文件夹
            String objectName = "/"+now.getYear()+"/"+now.getMonth().getValue()+"/"+now.getDayOfMonth()+"/"+UUID.randomUUID() + ".MP4";
            //minio上传文件
            MinioUtil.uploadFile(minioProperties.getBucket(), objectName, mp4);
            File file = new File(mp4);
            //删除临时文件
            file.delete();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        //如果没有上传视频封面
        if(videoVo.getVideoCover()==null){
            //抓取视频内容第一帧作为封面
            

        }

    }
}
