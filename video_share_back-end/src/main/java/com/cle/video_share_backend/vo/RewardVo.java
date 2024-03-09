package com.cle.video_share_backend.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RewardVo {
    private String title;

    private Long price;

    private Long likeCount;

    private LocalDateTime createTime;

}
