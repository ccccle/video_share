package com.cle.video_share_backend.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentVo {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("video_id")
    private Long videoId;
    @JsonProperty("comment_user_id")
    private Long commentUserId;
    @JsonProperty("to_user_id")
    private Long toUserId;
    @JsonProperty("create_time")
    private LocalDateTime createTime;
    @JsonProperty("update_time")
    private LocalDateTime updateTime;
    @JsonProperty("comment_list")
    private List<CommentVo>  commentVoList;
    @JsonProperty("comment_user")
    private UserVo commentUserVo;
    @JsonProperty("to_user")
    private UserVo toUserVo;

}
