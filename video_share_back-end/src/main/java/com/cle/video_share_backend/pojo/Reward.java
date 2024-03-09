package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("reward")
public class Reward {
    @TableId
    private Long id;
    @TableField("title")
    private String title;
    @TableField("price")
    private Long price;
    @TableField("like_count")
    private Long likeCount;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_Time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer flag;
}
