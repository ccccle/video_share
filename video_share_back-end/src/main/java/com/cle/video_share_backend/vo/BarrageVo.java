package com.cle.video_share_backend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BarrageVo {
//        private Long id;
        @JsonProperty("id")
        private Long videoId;
        private Double time;//弹幕时间
        private Integer type;
        private Long color;
        private String text;
}
