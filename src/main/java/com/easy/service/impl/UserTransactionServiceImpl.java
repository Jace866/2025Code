package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.UserTransactionDao;
import com.easy.entity.UserTransaction;
import com.easy.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
@Service
public class UserTransactionServiceImpl implements UserTransactionService {
    @Autowired
    private UserTransactionDao userTransactionDao;

    @Override
    public UserTransaction findFirstByUserIdAndrefNo(int uid, String refNo) {
        QueryWrapper<UserTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("ref_no", refNo);
        return userTransactionDao.selectOne(wrapper);
    }

    @Override
    public void save(UserTransaction trans) {
        userTransactionDao.insert(trans);
    }
}
