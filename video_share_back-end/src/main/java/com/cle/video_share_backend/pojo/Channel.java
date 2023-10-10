package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("channel")
public class Channel {
    @TableId
    private Long id;
    @TableField("channel_name")
    private String channelName;
    @TableField(value = "create_time",fill = FieldFill.UPDATE)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer flag;
}
