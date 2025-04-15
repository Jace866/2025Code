package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.TestUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : hello_@
 * @Date : 2025/4/15
 */
@Mapper
public interface TestUserDao extends BaseMapper<TestUser> {
}
