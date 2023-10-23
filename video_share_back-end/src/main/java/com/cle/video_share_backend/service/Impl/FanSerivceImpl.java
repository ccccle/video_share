package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.FanMapper;
import com.cle.video_share_backend.pojo.Fan;
import com.cle.video_share_backend.service.FanService;
import com.cle.video_share_backend.vo.FanVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FanSerivceImpl extends ServiceImpl<FanMapper, Fan> implements FanService {
    @Override
    public void follow(FanVo fanVo) {
        LambdaQueryWrapper<Fan> fanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fanLambdaQueryWrapper.eq(Fan::getUserId,fanVo.getUserId());
        fanLambdaQueryWrapper.eq(Fan::getFanId,fanVo.getFanId());
        Fan fanDb = this.getOne(fanLambdaQueryWrapper);
        //如果未关注过
        if(fanDb==null){
            Fan fan = new Fan();
            BeanUtils.copyProperties(fanVo,fan);
            fan.setStatus((short) 1);
            this.save(fan);
        }
        //如果关注过
        else {
            fanDb.setStatus(fanVo.getStatus());
            this.updateById(fanDb);
        }

    }
}
