package com.cle.video_share_backend.service.Impl;

import com.cle.video_share_backend.common.RedisConstant;
import com.cle.video_share_backend.pojo.Like;
import com.cle.video_share_backend.pojo.Video;
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
    public List<Like> getLikeListFromRedis() {
        Set<String> keys = redisTemplate.keys((RedisConstant.LIKE + "*"));
        //获取点赞数据
        SessionCallback<List<Object>> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String key : keys) {
                    operations.opsForHash().entries(key);
                }
                return null;
            }
        };
    //TODO 删除点赞
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

    @Override
    public void videoCountIncrease(Long videoId, int count,String hKey) {
        String key = RedisConstant.VIDEO_COUNT+videoId;
        Long videoCount =Long.valueOf(String.valueOf(redisTemplate.opsForHash().get(key, hKey)));
        if(videoCount==null){
            videoCount= 0L;
        }
        redisUtils.hashPut(key,hKey,videoCount+count);
    }

    @Override
    public void videoCountInit(Long videoId) {
        String key = RedisConstant.VIDEO_COUNT+videoId;
        redisUtils.hashPut(key,RedisConstant.PLAY_COUNT,0L);
        redisUtils.hashPut(key,RedisConstant.COMMENT_COUNT,0L);
        redisUtils.hashPut(key,RedisConstant.LIKE_COUNT,0L);
    }

    @Override
    public List<Video> getVideoCount() {
        Set<String> keys = redisTemplate.keys((RedisConstant.VIDEO_COUNT + "*"));
        SessionCallback<List<Object>> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String key : keys) {
                    operations.opsForHash().entries(key);
                }
                return null;
            }
        };
        List<Map<String,Long>> list = redisTemplate.executePipelined(sessionCallback);

        List<Video> videoList = new ArrayList<>();
        List keyList=new ArrayList(keys);
        int i=0;
        for (Map<String, Long> map : list) {
            Video video = new Video();
            String key = (String) keyList.get(i++);
            Long videoId =Long.valueOf(key.split("_")[2]) ;
            video.setId(videoId);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                Long count = Long.valueOf(String.valueOf(entry.getValue()));
                switch (entry.getKey()){
                    case RedisConstant.COMMENT_COUNT -> video.setCommentCount(count);
                    case RedisConstant.LIKE_COUNT -> video.setLikeCount(count);
                    case RedisConstant.PLAY_COUNT -> video.setPlayCount(count);
                }
            }
            videoList.add(video);
        }
        return videoList;
    }

    @Override
    public Boolean getLikeFromRedis(Long videoId, Long userId) {
        String key = RedisConstant.LIKE+videoId;
        String hKey = userId.toString();
         Boolean like = (Boolean) redisTemplate.opsForHash().get(key, hKey);
         return like;
    }


}