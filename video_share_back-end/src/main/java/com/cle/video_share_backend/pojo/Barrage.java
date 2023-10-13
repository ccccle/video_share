package com.cle.video_share_backend.pojo;

import lombok.Data;

@Data
public class Barrage {
    private Double time;//弹幕时间
    private String type;
    private Integer colorTen;
    private String color;
    private String author;
    private String text;
}
