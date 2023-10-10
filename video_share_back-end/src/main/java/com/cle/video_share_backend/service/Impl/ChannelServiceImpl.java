package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.ChannelMapper;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {
}
