package com.cle.video_share_backend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserVo {
    private Long id;
    private String email;
    private String name;
    @JsonProperty("avatar_data")
    private MultipartFile avatarData;
    private String avatar;
    private String code;
    private String token;
}
