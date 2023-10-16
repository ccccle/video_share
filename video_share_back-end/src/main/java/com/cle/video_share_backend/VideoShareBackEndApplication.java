package com.cle.video_share_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VideoShareBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoShareBackEndApplication.class, args);
    }

}
