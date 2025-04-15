package com.easy.service;

import com.easy.entity.UserTransaction;

/**
 * @author : hello_@
 * @Date : 2025/4/14
 */
public interface UserTransactionService {
    UserTransaction findFirstByUserIdAndrefNo(int uid, String refNo);

    void save(UserTransaction trans);
}
