package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.UserTransactionApiLogDao;
import com.easy.entity.UserTransactionApiLog;
import com.easy.service.UserTransactionApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Service
public class UserTransactionApiLogServiceImpl implements UserTransactionApiLogService {
    @Autowired
    private UserTransactionApiLogDao userTransactionApiLogDao;
    @Override
    public void updateTranLog(String remark, int tranStatus, int uid, String transNo) {
        userTransactionApiLogDao.updateTranLog(remark, tranStatus, uid, transNo);
    }

    @Override
    public void save(UserTransactionApiLog userTransactionApiLog) {
        userTransactionApiLogDao.insert(userTransactionApiLog);
    }

    @Override
    public UserTransactionApiLog findFirstByUserIdAndTransNo(int uid, String billno) {
        QueryWrapper<UserTransactionApiLog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        wrapper.eq("trans_no", billno);
        return userTransactionApiLogDao.selectOne(wrapper);
    }
}
