package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.vo.LikeVo;

public interface LikeService extends IService <Like> {
    /**
     * 点赞
     * @param likeVo
     */
    void Like(LikeVo likeVo);

    /**
     * 保存like到数据库，如果有则更新
     * @param like
     */
    void saveOrUpdateLike(Like like);
}
