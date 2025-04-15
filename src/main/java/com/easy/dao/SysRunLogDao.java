package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.SysRunLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
@Mapper
public interface SysRunLogDao extends BaseMapper<SysRunLog> {

    String getLogContent(@Param("logType") int logType, @Param("logContent") String logContent);
}
