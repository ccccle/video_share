package com.cle.video_share_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {
    /**
     * 对象存储服务的URL
     */
    private String endpoint;
    /**
     * Access key就像用户ID，可以唯一标识你的账户
     */
    private String accessKey;
    /**
     * Secret key是你账户的密码
     */
    private String secretKey;

    /**
     * 默认文件桶
     */
    private String bucket;

}
