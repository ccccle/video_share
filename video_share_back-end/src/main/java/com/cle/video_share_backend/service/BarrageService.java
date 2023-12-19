package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Barrage;
import com.cle.video_share_backend.vo.BarrageVo;

import java.util.List;

public interface BarrageService extends IService<Barrage> {
    void save(BarrageVo barrageVo);

    List<List<Object>> getBarrage(Long id);

    IPage<BarrageVo> getBarrageList(Integer page, Integer size, Long id);
}
