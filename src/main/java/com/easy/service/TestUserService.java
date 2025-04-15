package com.easy.service;

import com.easy.entity.TestUser;

/**
 * @author : hello_@
 * @Date : 2025/4/15
 */
public interface TestUserService {
    TestUser findUserBySessionId(String sessionId);

    void save(TestUser user);
}
