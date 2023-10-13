package com.cle.video_share_backend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVo {
    public VideoVo(MultipartFile videoCover, MultipartFile videoData, String videoName, String videoDescription, Long channelId) {
        this.videoCover = videoCover;
        this.videoData = videoData;
        this.videoName = videoName;
        this.videoDescription = videoDescription;
        this.channelId = channelId;
    }
    @JsonProperty("id")
    private Long id;
    @JsonProperty("video_cover")
    private MultipartFile videoCover;
    @JsonProperty("video_data")
    private MultipartFile videoData;
    @JsonProperty("video_cover_url")
    private String videoCoverUrl;
    @JsonProperty("video_url")
    private String videoUrl;
    @JsonProperty("video_name")
    private String videoName;
    @JsonProperty("video_description")
    private String videoDescription;
    @JsonProperty("channel_id")
    private Long channelId;
    @JsonProperty("create_time")
    private LocalDateTime createTime;
    @JsonProperty("user")
    private UserVo userVo;


}
