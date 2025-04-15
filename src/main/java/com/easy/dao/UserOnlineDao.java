package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.UserOnline;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : hello_@
 * @Date : 2025/4/15
 */
@Mapper
public interface UserOnlineDao extends BaseMapper<UserOnline> {
    UserOnline findBySessionIdAndUserId(String sessionId, Integer userId);

    void kickoutByUid(int uid);
}
