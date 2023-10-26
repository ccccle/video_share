package com.cle.video_share_backend.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ChannelVo {
    private Long id;
    @JsonProperty("channel_name")
    private String channelName;
    @JsonProperty("create_time")
    private LocalDateTime createTime;
}
