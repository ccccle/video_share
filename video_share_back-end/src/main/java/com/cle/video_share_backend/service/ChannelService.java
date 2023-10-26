package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.vo.ChannelVo;

import java.util.List;

public interface ChannelService extends IService<Channel> {
    List<ChannelVo> getChannelList();


}
