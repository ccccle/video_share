package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Fan;
import com.cle.video_share_backend.vo.FanVo;

import java.util.List;

public interface FanService extends IService<Fan> {
    /**
     * 关注
     * @param fanVo
     */
    void follow(FanVo fanVo);

    /**
     * 获取关注列表(分页)
     *
     * @param page
     * @param size
     * @param userId
     * @param type
     * @return
     */
    Page<FanVo> getFollow(Integer page, Integer size, Long userId, String type);
    /**
     * 获取关注列表(不分页)
     *
     * @param userId
     * @param type
     * @return
     */
    List<FanVo> getFollow(Long userId, String type);

    long getCount(Long userId, String type);
}
