package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("video")
@Data
public class Video {
    @TableId
    private Long id;
    @TableField("video_name")
    private String videoName;
    @TableField("video_cover")
    private String videoCover;
    @TableField("video_url")
    private String videoUrl;
    @TableField("video_description")
    private String videoDescription;
    @TableField("like_count")
    private Long likeCount;
    @TableField("comment_count")
    private Long commentCount;
    @TableField("play_count")
    private Long playCount;
    @TableField("channel_id")
    private Long channelId;
    @TableField("channel_name")
    private String channelName;
    @TableField("user_id")
    private Long userId;
    @TableField("user_name")
    private String userName;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer flag;
}
