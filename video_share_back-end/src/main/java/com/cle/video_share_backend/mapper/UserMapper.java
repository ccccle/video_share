package com.cle.video_share_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cle.video_share_backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
