package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.ChannelMapper;
import com.cle.video_share_backend.pojo.Channel;
import com.cle.video_share_backend.service.ChannelService;
import com.cle.video_share_backend.vo.ChannelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {
    @Override
    public List<ChannelVo> getChannelList() {

        List<Channel> list = this.list();
        List<ChannelVo> collect = list.stream().map(item -> {
            ChannelVo channelVo = new ChannelVo();
            BeanUtils.copyProperties(item, channelVo);
            return channelVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
