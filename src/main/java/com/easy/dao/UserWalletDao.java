package com.easy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.entity.UserWallet;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
@Mapper
public interface UserWalletDao extends BaseMapper<UserWallet> {
    BigDecimal queryBalance(int uid);

    void updateBalanceByUserId(double balance, int uid);

    void updateBalanceBytableBey(String tableBey, String balance, Integer uid);
}
