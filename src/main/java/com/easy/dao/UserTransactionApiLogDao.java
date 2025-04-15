package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.UserTransactionApiLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Mapper
public interface UserTransactionApiLogDao extends BaseMapper<UserTransactionApiLog> {
    void updateTranLog(String remark, int tranStatus, int uid, String transNo);
}
