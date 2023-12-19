package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.BarrageMapper;
import com.cle.video_share_backend.pojo.Barrage;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.service.BarrageService;
import com.cle.video_share_backend.utils.UserThreadLocal;
import com.cle.video_share_backend.vo.BarrageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarrageServiceImpl extends ServiceImpl<BarrageMapper, Barrage> implements BarrageService {
    @Override
    public void save(BarrageVo barrageVo) {
        Barrage barrage = new Barrage();
        BeanUtils.copyProperties(barrageVo,barrage);
        User user = UserThreadLocal.get();
        barrage.setUserId(user.getId());
        this.save(barrage);
    }

    @Override
    public List<List<Object>> getBarrage(Long id) {
        LambdaQueryWrapper<Barrage> barrageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        barrageLambdaQueryWrapper.eq(Barrage::getVideoId,id);
        List<Barrage> barrageList = this.list(barrageLambdaQueryWrapper);
        List<List<Object>> collect = barrageList.stream().map(barrage -> {
            List<Object> list = new ArrayList<>();
            list.add(barrage.getTime());
            list.add(barrage.getType());
            list.add(barrage.getColor());
            list.add("");
            list.add(barrage.getText());
            return list;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public IPage<BarrageVo> getBarrageList(Integer page, Integer size, Long id) {
        IPage<Barrage> barrageIPage = new Page<>(page,size);
        LambdaQueryWrapper<Barrage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Barrage::getVideoId,id);
        this.page(barrageIPage,lambdaQueryWrapper);
        List<Barrage> records = barrageIPage.getRecords();
        List<BarrageVo> collect = records.stream().map(barrage -> {
            BarrageVo barrageVo = new BarrageVo();
            BeanUtils.copyProperties(barrage, barrageVo);
            return barrageVo;
        }).collect(Collectors.toList());
        IPage<BarrageVo> barrageVoIPage = new Page<>(page,size);
        BeanUtils.copyProperties(barrageIPage,barrageVoIPage);
        barrageVoIPage.setRecords(collect);
        return barrageVoIPage;
    }
}
