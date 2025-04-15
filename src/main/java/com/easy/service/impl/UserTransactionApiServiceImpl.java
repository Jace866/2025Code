package com.easy.service.impl;

import com.easy.dao.UserTransactionApiDao;
import com.easy.entity.UserTransactionApi;
import com.easy.service.UserTransactionApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class UserTransactionApiServiceImpl implements UserTransactionApiService {
    @Autowired
    private UserTransactionApiDao userTransactionApiDao;

    @Override
    public void save(UserTransactionApi transApi) {
        userTransactionApiDao.insert(transApi);
    }
}
