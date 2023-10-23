package com.cle.video_share_backend;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
public class VideoShareBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoShareBackEndApplication.class, args);
    }


}
