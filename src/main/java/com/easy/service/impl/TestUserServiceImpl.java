package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.TestUserDao;
import com.easy.entity.TestUser;
import com.easy.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/15
 */
@Service
public class TestUserServiceImpl implements TestUserService {
    @Autowired
    private TestUserDao testUserDao;

    @Override
    public TestUser findUserBySessionId(String sessionId) {
        QueryWrapper<TestUser> wrapper = new QueryWrapper<>();
        wrapper.eq("session_id", sessionId);
        return testUserDao.selectOne(wrapper);
    }

    @Override
    public void save(TestUser user) {
        testUserDao.insert(user);
    }
}
