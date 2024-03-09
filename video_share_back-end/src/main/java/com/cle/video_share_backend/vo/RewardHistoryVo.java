package com.cle.video_share_backend.vo;
import com.cle.video_share_backend.pojo.Reward;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RewardHistoryVo {

    private VideoVo videoVo;

    private Reward reward;

    private LocalDateTime createTime;
}
