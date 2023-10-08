package com.cle.video_share_backend.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String email;
    private String name;
    private String avatar;
    private String code;
    private String token;
}
