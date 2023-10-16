package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("`like`")
public class Like {
    @TableId
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("video_id")
    private Long videoId;
    @TableField("`like`")
    private Boolean like;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_Time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime UpdateTime;
    @TableField(value = "flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer flag;
}
