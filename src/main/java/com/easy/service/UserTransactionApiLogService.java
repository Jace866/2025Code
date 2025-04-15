package com.easy.service;

import com.easy.entity.UserTransactionApiLog;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface UserTransactionApiLogService {

    void updateTranLog(String remark, int tranStatus, int uid, String transNo);

    void save(UserTransactionApiLog userTransactionApiLog);

    UserTransactionApiLog findFirstByUserIdAndTransNo(int uid, String billno);
}
