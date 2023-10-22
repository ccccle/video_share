package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("Comment")
public class Comment {
    @TableId
    private Long id;
    @TableField("text")
    private String text;
    @TableField("comment_id")
    private Long commentId;
    @TableField("video_id")
    private Long videoId;
    @TableField("comment_user_id")
    private Long commentUserId;
    @TableField("to_user_id")
    private Long toUserId;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "flag",fill = FieldFill.INSERT)
    private Integer flag;
}
