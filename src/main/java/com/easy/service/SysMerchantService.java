package com.easy.service;

import com.easy.entity.SysMerchant;
import com.easy.vo.SysMerchantVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : hello_@
 * @Date : 2025/4/12
 */
public interface SysMerchantService {

    List<SysMerchantVo> findList();

    SysMerchant findByStatusAndProvider(int status, String provider);

    SysMerchant findByMerchantIdAndStatus(int status, int merchantId);

    SysMerchant queryByMerchantDomain(String domain);

    SysMerchant queryByMerchantDomainAndIp(String pid, String domain, String ip);

    SysMerchant queryByMerchantDomain(String pid, String domain, String ip);

    BigDecimal queryBalance(int merchantId);

    void updateBalanceById(double merchantBalance, int merchantId);
}
