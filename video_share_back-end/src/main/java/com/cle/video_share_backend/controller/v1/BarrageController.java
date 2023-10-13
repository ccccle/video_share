package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Barrage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/barrage")
public class BarrageController {
    @GetMapping("/get/v3/")
    public String get(String id){

        return "{\n" +
                "\t\"code\": 0,\n" +
                "\t\"data\":"+
                "[\n" +
                "  {\n" +
                "    \"time\": 0.5,\n" +
                "    \"mode\": 1,\n" +
                "    \"color\": 16777215,\n" +
                "    \"text\": \"这是一条弹幕1\",\n" +
                "    \"author\": \"用户A\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"time\": 3.0,\n" +
                "    \"mode\": 0,\n" +
                "    \"color\": 16711680,\n" +
                "    \"text\": \"这是一条弹幕2\",\n" +
                "    \"author\": \"用户B\"\n" +
                "  }\n" +
                "]\n"+
                "}";

    }
}
