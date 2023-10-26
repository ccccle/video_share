package com.cle.video_share_backend.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FanVo {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fan_id")
    private Long fanId;
    private Short status;
    @JsonProperty("create_time")
    private LocalDateTime createTime;
    @JsonProperty("update_time")
    private LocalDateTime updateTime;
    @JsonProperty("user")
    private UserVo userVo;
    @JsonProperty("fan")
    private UserVo fanUserVo;
}
