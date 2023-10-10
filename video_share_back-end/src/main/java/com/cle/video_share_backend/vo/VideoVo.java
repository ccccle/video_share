package com.cle.video_share_backend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
public class VideoVo {
    @JsonProperty("video_cover")
    private MultipartFile videoCover;
    @JsonProperty("video_data")
    private MultipartFile videoData;
    @JsonProperty("video_name")
    private String videoName;
    @JsonProperty("video_description")
    private String videoDescription;
    @JsonProperty("channel_id")
    private Long channelId;
}
