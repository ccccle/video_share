package com.cle.video_share_backend.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LikeVo {

@JsonProperty("user_id")
    private Long userId;
@JsonProperty("video_id")
    private Long videoId;
@JsonProperty("like")
    private Boolean like;

}
