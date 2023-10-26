package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.FanMapper;
import com.cle.video_share_backend.mapper.UserMapper;
import com.cle.video_share_backend.pojo.Fan;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.FanService;
import com.cle.video_share_backend.vo.FanVo;
import com.cle.video_share_backend.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FanSerivceImpl extends ServiceImpl<FanMapper, Fan> implements FanService {
    @Autowired
    private UserMapper userMapper;
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

    @Override
    public List<FanVo> getFollow(Long userId) {
        LambdaQueryWrapper<Fan> fanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fanLambdaQueryWrapper.eq(Fan::getUserId,userId);
        List<Fan> list = this.list(fanLambdaQueryWrapper);
        List<FanVo> collect = list.stream().map(item -> {
            FanVo fanVo = new FanVo();
            BeanUtils.copyProperties(item, fanVo);
            User user = userMapper.selectById(item.getUserId());
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            User fan = userMapper.selectById(item.getFanId());
            UserVo fanUserVo = new UserVo();
            BeanUtils.copyProperties(fan,fanUserVo);
            fanVo.setFanUserVo(fanUserVo);
            fanVo.setUserVo(userVo);
            return fanVo;
        }).collect(Collectors.toList());
        return collect;

    }
}
