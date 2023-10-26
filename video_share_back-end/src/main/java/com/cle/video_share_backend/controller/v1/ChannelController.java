package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.service.ChannelService;
import com.cle.video_share_backend.vo.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @GetMapping("/getChannelList")
    public ResponseResult getChannelList(){
        List<ChannelVo> list = channelService.getChannelList();
        return ResponseResult.success(list);
    }
}
