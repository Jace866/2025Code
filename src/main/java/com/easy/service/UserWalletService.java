package com.easy.service;

import com.easy.entity.UserWallet;
import com.easy.vo.InterfaceBean;

import java.math.BigDecimal;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface UserWalletService {
    void save(UserWallet wallet);

    BigDecimal queryBalance(int uid);

    void updateBalanceByUserId(double balance, int uid);

    String getTransType(int index);

    String selBalance(InterfaceBean param);
}
