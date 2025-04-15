package com.easy.service.impl;

import com.easy.dao.UserInfoExtDao;
import com.easy.entity.UserInfoExt;
import com.easy.service.UserInfoExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class UserInfoExtServiceImpl implements UserInfoExtService {
    @Autowired
    private UserInfoExtDao userInfoExtDao;
    @Override
    public void save(UserInfoExt userInfoExt) {
        userInfoExtDao.insert(userInfoExt);
    }
}
