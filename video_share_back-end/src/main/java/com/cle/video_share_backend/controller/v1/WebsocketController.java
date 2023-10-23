package com.cle.video_share_backend.controller.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsocketController {

    @GetMapping("/")
    public String init() {
        return "websocket";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chatRoom";
    }
}
