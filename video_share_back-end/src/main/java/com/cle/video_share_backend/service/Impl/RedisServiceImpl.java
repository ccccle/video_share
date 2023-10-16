package com.cle.video_share_backend.service.Impl;

import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.service.RedisService;
import com.cle.video_share_backend.utils.RedisUtils;
import com.cle.video_share_backend.vo.LikeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveLikeVoToRedis(LikeVo likeVo) {
        String key = RedisConstant.LIKE + likeVo.getVideoId();
        String hKey = likeVo.getUserId().toString();
        redisUtils.hashPut(key, hKey, likeVo.getLike());
    }

    @Override
    public List<Like> getLikeFromRedis() {
        Set<String> keys = redisTemplate.keys((RedisConstant.LIKE + "*"));
        SessionCallback<List<Object>> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String key : keys) {
                    operations.opsForHash().entries(key);
                }
                return null;
            }
        };
        List<Map<String,Boolean>> list = redisTemplate.executePipelined(sessionCallback);
        List<Like> likeList = new ArrayList<>();
        List keyList=new ArrayList(keys);
        int i=0;
        for (Map<String, Boolean> map : list) {
            String key = (String) keyList.get(i++);
            Long videoId =Long.valueOf(key.split("_")[1]) ;
            for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                Like like = new Like();
                like.setUserId(Long.valueOf(entry.getKey()));
                like.setLike(entry.getValue());
                like.setVideoId(videoId);
                likeList.add(like);
            }
            }
        return likeList;
    }
}