package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("barrage")
public class Barrage {
    @TableId
    private Long id;
    @TableField("video_id")
    private Long videoId;
    @TableField("time")
    private Double time;//弹幕时间
    @TableField("type")
    private Integer type;
    @TableField("color")
    private Long color;
    @TableField("text")
    private String text;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(value = "flag",fill = FieldFill.INSERT)
    private Integer flag;
}
