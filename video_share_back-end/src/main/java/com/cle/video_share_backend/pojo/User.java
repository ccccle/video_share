package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import jdk.jfr.Label;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId
    private Long id;
    @TableField("email")
    private String email;
    @TableField("name")
    private String name;
    @TableField("avatar")
    private String avatar;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField("flag")
    @TableLogic
    private Integer flag;
}
