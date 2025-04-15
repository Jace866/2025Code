package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.SysSequence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
@Mapper
public interface SysSequenceDao extends BaseMapper<SysSequence> {
    Integer queryPostValue(@Param("seqName") String seqName);
}
